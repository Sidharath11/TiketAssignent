package com.tiketbakend.tiket.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class SalePurchase {
    private int vchno;
    private int partyid;
    private String date;
    private double totalquantity;
    private double totalamount;
    private double others;
    private double grandtotal;
    private List<Item> items;

}
