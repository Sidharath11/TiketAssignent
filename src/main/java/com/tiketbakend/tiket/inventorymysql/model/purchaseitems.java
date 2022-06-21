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
@Table(name = "purchaseitems")

public class purchaseitems implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "creation_date")
    private String creation_date= LocalDateTime.now().toString();
    @Column(name = "updtion_date")
    private  String updtion_date=LocalDateTime.now().toString();
    @Column(name = "deleted")
    private boolean deleted=false;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "purchaseheadid", referencedColumnName = "id")
    private purchasehead purchasehead;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "itemid", referencedColumnName = "id")
    private com.tiketbakend.tiket.inventorymysql.model.itemmaster itemmaster;
    @Column(name = "rate",nullable = false)
    private double rate;
    @Column(name = "quantity",nullable = false)
    private double quantity;
    @Column(name = "amount",nullable = false)
    private double amount;

}
