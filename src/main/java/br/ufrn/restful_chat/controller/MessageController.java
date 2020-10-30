package br.ufrn.restful_chat.controller;

import br.ufrn.restful_chat.model.Message;
import br.ufrn.restful_chat.service.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@RestController
public class MessageController {

    private final MessageService messageService;
   

    public MessageController(MessageService messageService){
        this.messageService = messageService;
    }

    @GetMapping("/listMessage")
    public List<Message> listMessages(HttpServletRequest request, HttpServletResponse response){

        System.out.println("Método sendo chamado");
        
        // TODO: listar as ultimas 10 mensagens do chat

        return messageService.listMessages(request, response);

    }

    @PostMapping("/sendMessage")
    public Message sendMessage(HttpServletRequest request, HttpServletResponse response){
    	System.out.println("enviando");
    	return messageService.saveMessage(request, response);

    }

}
