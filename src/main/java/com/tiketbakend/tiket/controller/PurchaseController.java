package com.tiketbakend.tiket.controller;

import com.tiketbakend.tiket.repository.mongodb.StockRepository;
import com.tiketbakend.tiket.repository.mysqldb.ItemMasterRepository;
import com.tiketbakend.tiket.repository.mysqldb.PartyMasterRepository;
import com.tiketbakend.tiket.repository.mysqldb.PurchaseHeadRepository;
import com.tiketbakend.tiket.repository.mysqldb.PurchaseItemsRepository;
import com.tiketbakend.tiket.service.PurchaseService;
import com.tiketbakend.tiket.util.SalePurchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/purchase")

public class PurchaseController {


    @Autowired
    private PurchaseHeadRepository purcheadrepo;
    @Autowired
    private PurchaseItemsRepository purcitemrepo;
    @Autowired
    private PartyMasterRepository partyrepo;
    @Autowired
    private ItemMasterRepository itemrepo;

    @Autowired
    private StockRepository stk;

    @Autowired
    private PurchaseService service=new PurchaseService(stk);

    @GetMapping
    public List<SalePurchase> getAll(){
        return service.getAll(purcheadrepo,purcitemrepo);
    }


    @PostMapping
    public SalePurchase create(@RequestBody SalePurchase newpt){
        return service.create(newpt,purcheadrepo,purcitemrepo,partyrepo,itemrepo);
    }

    @GetMapping(value = "/Id/{Id}")
    public SalePurchase getById(@PathVariable int Id){
        SalePurchase pt=service.getById(Id,purcheadrepo,purcitemrepo);
        return pt;
    }

    //update ticket by id
    @PutMapping(value = "/Id/{Id}")
    public SalePurchase updateById(@PathVariable int Id,@RequestBody SalePurchase updatedata){

        SalePurchase ht=new SalePurchase();
        try{
            ht=service.getById(Id,purcheadrepo,purcitemrepo);
        }
        catch (Exception e){}
        if(ht!=null){
            return service.updateById(Id,updatedata,purcheadrepo,purcitemrepo,partyrepo,itemrepo);
             }
        return new SalePurchase();
    }

    @DeleteMapping(value = "/Id/{Id}")
    public String deleteById(@PathVariable int Id){
        SalePurchase ht=new SalePurchase();
        try{
            ht=service.getById(Id,purcheadrepo,purcitemrepo);
        }
        catch (Exception e){}
        if(ht!=null){
            service.deleteById(Id,purcheadrepo,purcitemrepo);
            return " with id :"+Id+" is deleted";}

        return " with id :"+Id+" is not deleted";

    }

}
