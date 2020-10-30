package br.ufrn.restful_chat.service;

import br.ufrn.restful_chat.model.Message;
import br.ufrn.restful_chat.model.User;
import br.ufrn.restful_chat.repository.MessageRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final JwtParser jwtParser;
    static final String jwtSecret = "123456";
    static final String TOKEN_PREFIX = "Bearer ";
    
    @Autowired
    private final UserService userService;

    public MessageService(MessageRepository messageRepository, UserService userService){
        this.messageRepository = messageRepository;
        jwtParser = Jwts.parser().setSigningKey(jwtSecret);
        this.userService = userService;
    }

    public List<Message> listMessages(HttpServletRequest request, HttpServletResponse response){
    	if ( verificaToken(request, response) ) {
    		List<Message> msgs = messageRepository.findFirst10ByOrderByDataDesc();
    		Collections.reverse(msgs);
    		return msgs;
    	}
    	return null;

    }
    
    public Message saveMessage(HttpServletRequest request, HttpServletResponse response) {
    	if (verificaToken(request, response)) {
    		String token = request.getHeader("authorization");
    		Claims claims = decodeToken(token);
    		
    		User user = userService.getUser(claims.getSubject());
    		
    		Message msg = new Message();
    		msg.setTexto(request.getParameter("texto"));
    		Date date = null;
			try {
				date = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("data"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		msg.setData(date);
    		msg.setUser(user);
    		messageRepository.save(msg);
    		
    		return messageRepository.save(msg);
    	}
    	
        return null;
    }

    public boolean verificaToken(HttpServletRequest request, HttpServletResponse response) {

        String token = request.getHeader("authorization");

        try {

            // decodifica token
            Claims claims = decodeToken(token);
            request.setAttribute("username", claims.getSubject());
            // verifica se token está ativo
            if (claims.getExpiration().before(new Date(System.currentTimeMillis()))) {
                // se token tiver expirado, retorna erro 401
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return false;
            } else {
                response.setStatus(HttpStatus.OK.value());
                return true;
            }

        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
    }

    private Claims decodeToken(String token) {
        return jwtParser.parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
    }
    
    

}
