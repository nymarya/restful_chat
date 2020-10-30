package br.ufrn.restful_chat.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_message;

    private String texto;
    private Date data;

    @ManyToOne()
    @JoinColumn(name="id_user")
    private User user;


    public String getTexto() {
        return texto;
    }
    public void setTexto(String texto) {
        this.texto = texto;
    }
    public Date getData() {
        return data;
    }
    public void setData(Date data) {
        this.data = data;
    }
    public User getUser() {
        return user;
    }

}
