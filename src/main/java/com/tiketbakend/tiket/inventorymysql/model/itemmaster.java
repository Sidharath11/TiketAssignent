package com.tiketbakend.tiket.inventorymysql.model;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "itemmaster")

public class itemmaster implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "creation_date")
    private String creation_date= LocalDateTime.now().toString();
    @Column(name = "updtion_date")
    private  String updtion_date=LocalDateTime.now().toString();
    @Column(name = "deleted")
    private boolean deleted=false;

    @ManyToOne
    @JoinColumn(name = "itemgroupid", referencedColumnName = "id")
    private itemgroupmaster itemgroup;
    @Column(name = "itemname",nullable = false,length = 50)
    private String itemname;
    @Column(name = "units",nullable = false,length = 10)
    private String units;
//    @Column(name = "rate",nullable = false)
//    private double rate;
    @Column(name = "purcrate",nullable = false)
    private double purcrate;
    @Column(name = "salerate",nullable = false)
    private double salerate;

}
