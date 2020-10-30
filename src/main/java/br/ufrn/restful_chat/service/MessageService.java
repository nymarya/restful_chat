package br.ufrn.restful_chat.service;

import br.ufrn.restful_chat.repository.MessageRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final JwtParser jwtParser;
    static final String jwtSecret = "123456";
    static final String TOKEN_PREFIX = "Bearer ";

    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
        jwtParser = Jwts.parser().setSigningKey(jwtSecret);
    }

    public boolean listMessages(HttpServletRequest request, HttpServletResponse response){

        return verificaToken(request, response);

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
