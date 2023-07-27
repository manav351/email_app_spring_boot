package com.springBoot.EmailApplication.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "email_bank")
@Data @AllArgsConstructor @NoArgsConstructor
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String emailId;

    @Column(name = "submit_time")
    private String submitTime;

    @Column(name = "status")
    private Boolean sentStatus;
}
