package br.ufrn.restful_chat.service;

import br.ufrn.restful_chat.model.User;
import br.ufrn.restful_chat.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    static final long EXPIRATION_TIME = 86_400_000;
    static final String SECRET = "123456";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    // salvar dados do usuário no banco
    public User registrar(User user){

        return userRepository.save(user);

    }

    // salvar dados do usuário no banco
    public List<User> listUsers(){

        return userRepository.findAll();

    }
    
    public User getUser(String token) {
    	return userRepository.findByName(token).get(0);
    }

    public void entrar(HttpServletResponse response, User user){

        // verifica se usuário esta cadastrado no sistema
        List<User> users = userRepository.findByName(user.getName());

        if(users.isEmpty()){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else if (users.get(0).getPassword().equals(user.getPassword())) {
            // retorna token
            String JWT = Jwts.builder()
                    .setSubject(user.getName())
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(SignatureAlgorithm.HS512, SECRET)
                    .compact();

            response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

}
