package br.ufrn.restful_chat.controller;

import br.ufrn.restful_chat.model.User;
import br.ufrn.restful_chat.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/registrar")
    public User registrar(User user){
        return userService.registrar(user);
    }

    @GetMapping("/listarUsuarios")
    public List<User> listarUsuario(){
        return userService.listUsers();
    }

    @PostMapping("/entrar")
    public void entrar(HttpServletResponse response, User user){
        userService.entrar(response, user);
    }

    @PostMapping("/sair")
    public boolean sair(){

        // TODO: desabilita o token

        return false;
    }

}
