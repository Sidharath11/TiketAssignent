package com.tiketbakend.tiket.model.mysqldb;
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
@Table(name = "salehead")

public class SaleHead implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "creation_date")
    private String creation_date= LocalDateTime.now().toString();
    @Column(name = "updtion_date")
    private  String updtion_date=LocalDateTime.now().toString();
    @Column(name = "deleted")
    private boolean deleted=false;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "partyid", referencedColumnName = "id")
    private PartyMaster party;
    @Column(name = "date",nullable = false)
    private  String date;
    @Column(name = "totalquantity",nullable = false)
    private double totalquantity;
    @Column(name = "totalamount",nullable = false)
    private double totalamount;
    @Column(name = "others",nullable = false)
    private double others;
    @Column(name = "grandtotal",nullable = false)
    private double grandtotal;


}
