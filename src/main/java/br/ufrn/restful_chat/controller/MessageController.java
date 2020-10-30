package br.ufrn.restful_chat.controller;

import br.ufrn.restful_chat.model.Message;
import br.ufrn.restful_chat.service.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Controller
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService){
        this.messageService = messageService;
    }

    @GetMapping("/listMessage")
    public List<Message> listMessages(HttpServletRequest request, HttpServletResponse response){

        System.out.println("Método sendo chamado");
        messageService.listMessages(request, response);
        // TODO: listar as ultimas 10 mensagens do chat

        return null;

    }

    @PostMapping("/sendMessage")
    public boolean sendMessage(@PathVariable("texto") String texto, @PathVariable("data") Date data){

        // TODO: listar as ultimas 10 mensagens do chat

        return false;

    }

}
