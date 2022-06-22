package com.tiketbakend.tiket.inventorymysql.controller;

import com.tiketbakend.tiket.inventorymongodb.model.stock;
import com.tiketbakend.tiket.inventorymongodb.Repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stock")
public class StockController {
    @Autowired
    private StockRepository repo;

    @GetMapping
    public List<stock> getAll(){
        return repo.findAll();
    }
    @GetMapping(value = "/ItemId/{Id}")
    public stock getAllByItemId(@PathVariable int Id){
        return repo.findItemById(Id);
    }
    @GetMapping(value = "/ItemGroupId/{Id}")
    public List<stock> getAllByItemGroupId(@PathVariable int Id){
        return repo.findItemByGroupId(Id);
    }

    @PostMapping
    public stock create(@RequestBody stock newpt){
        return repo.save(newpt);
    }
    @PutMapping(value = "/PurcItemId/{Id}")
    public stock updateByPurcItemId(@PathVariable int Id, @RequestBody stock updatedata){

        stock ht=new stock();
        try{
            ht=repo.findItemById(Id);
        }
        catch (Exception e){}
        if(ht!=null){
            ht.setPurcamount(ht.getPurcamount()+ updatedata.getPurcamount());
            ht.setQuantityinstock(ht.getQuantityinstock()+ updatedata.getQuantityinstock());
           return repo.save(ht);

            }
        return new stock();
    }
    @PutMapping(value = "/SaleItemId/{Id}")
    public stock updateBySaleItemId(@PathVariable int Id, @RequestBody stock updatedata){

        stock ht=new stock();
        try{
            ht=repo.findItemById(Id);
        }
        catch (Exception e){}
        if(ht!=null){
            ht.setSaleamount(ht.getSaleamount()+ updatedata.getSaleamount());
            ht.setQuantitysold(ht.getQuantitysold()+ updatedata.getQuantitysold());
            ht.setQuantityinstock(ht.getQuantityinstock()- updatedata.getQuantitysold());
            return repo.save(ht);

        }
        return new stock();
    }
}
