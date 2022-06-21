package com.tiketbakend.tiket.inventorymongodb.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(value = "stock",collation = "restaurant")
@Getter
@Setter
public class stock {

    @Id
    private int id;

    private int itemid;
    private int itemgroupid;
    private double saleamount;
    private double purcamount;
    private int quantitysold;
    private int quantityinstock;

    public stock(int itid,int itgroupid,double slamnt,double puramnt,int qtysold,int qtystck){
        super();
        this.purcamount=puramnt;
        this.saleamount=slamnt;
        this.itemid=itid;
        this.itemgroupid=itgroupid;
        this.quantityinstock=qtystck;
        this.quantitysold=qtysold;
    }

    public stock() {

    }
}
