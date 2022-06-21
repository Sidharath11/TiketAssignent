package com.tiketbakend.tiket.inventorymysql.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "partymaster")

public class partymaster implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "creation_date")
    private String creation_date= LocalDateTime.now().toString();
    @Column(name = "updtion_date")
    private  String updtion_date=LocalDateTime.now().toString();
    @Column(name = "deleted")
    private boolean deleted=false;

    @Column(name = "partname",nullable = false,length = 50)
    private String partname;
    @Column(name = "phoneno",nullable = false,length = 10)
    private String phoneno;
    @Column(name = "emailid",nullable = false,length = 50)
    private String emailid;
    @Column(name = "address",nullable = false,length = 100)
    private String address;
    @Column(name = "city",nullable = false,length = 50)
    private String city;
    @Column(name = "pincode",nullable = false,length = 10)
    private String pincode;

}
