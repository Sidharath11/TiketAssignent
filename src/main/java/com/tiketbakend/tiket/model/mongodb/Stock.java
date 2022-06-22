package com.tiketbakend.tiket.model.mongodb;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(value = "stock")
@Getter
@Setter
public class Stock {

    @Id
    private int id;

    private int itemid;
    private int itemgroupid;
    private double saleamount;
    private double purcamount;
    private double quantitysold;
    private double quantityinstock;

    public Stock(int itid, int itgroupid, double slamnt, double puramnt, double qtysold, double qtystck){
        super();
        this.purcamount=puramnt;
        this.saleamount=slamnt;
        this.itemid=itid;
        this.itemgroupid=itgroupid;
        this.quantityinstock=qtystck;
        this.quantitysold=qtysold;
    }

    public Stock() {

    }
}
