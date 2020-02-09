package net.nh.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    private String text;


    public Long getId() {
        return id;
    }

    @Column(name = "txt")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
