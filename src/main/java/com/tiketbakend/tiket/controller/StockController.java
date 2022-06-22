package com.tiketbakend.tiket.controller;

import com.tiketbakend.tiket.model.mongodb.Stock;
import com.tiketbakend.tiket.repository.mongodb.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/stock")
public class StockController {
    @Autowired
    private StockRepository repo;

    @GetMapping
    public Flux<Stock> getAll(){
        return repo.findAll();
    }
    @GetMapping(value = "/ItemId/{Id}")
    public Mono<Stock> getAllByItemId(@PathVariable int Id){
        return repo.findItemById(Id);
    }
    @GetMapping(value = "/ItemGroupId/{Id}")
    public Flux<Stock> getAllByItemGroupId(@PathVariable int Id){
        return repo.findItemByGroupId(Id);
    }

    @PostMapping
    public Mono<Stock> create(@RequestBody Stock newpt){
        return repo.save(newpt);
    }
    @PutMapping(value = "/PurcItemId/{Id}")
    public Mono<Stock> updateByPurcItemId(@PathVariable int Id, @RequestBody Stock updatedata){

        Stock ht= null;
        try{
            ht=repo.findItemById(Id).block();
        }
        catch (Exception e){}
        if(ht!=null) {
            repo.delete(ht);

            ht.setPurcamount(ht.getPurcamount()+ updatedata.getPurcamount());
            ht.setQuantityinstock(ht.getQuantityinstock()+ updatedata.getQuantityinstock());}
        else{
            ht=updatedata;
        }
           return repo.save(ht);

    }
    @PutMapping(value = "/Reset/PurcItemId/{Id}")
    public Mono<Stock> resetByPurcItemId(@PathVariable int Id, @RequestBody Stock updatedata){

        Stock ht=null;
        try{
            ht=repo.findItemById(Id).block();
        }
        catch (Exception e){}
        if(ht!=null) {
            repo.delete(ht);

        ht.setPurcamount(ht.getPurcamount()- updatedata.getPurcamount());
        ht.setQuantityinstock(ht.getQuantityinstock()- updatedata.getQuantityinstock());}
        else {
            ht=updatedata;
        }
        return repo.save(ht);

    }
    @PutMapping(value = "/SaleItemId/{Id}")
    public Mono<Stock> updateBySaleItemId(@PathVariable int Id, @RequestBody Stock updatedata){

        Stock ht=null;
        try{
            ht=repo.findItemById(Id).block();
        }
        catch (Exception e){}
        if(ht!=null){
            repo.delete(ht);
            ht.setSaleamount(ht.getSaleamount()+ updatedata.getSaleamount());
            ht.setQuantitysold(ht.getQuantitysold()+ updatedata.getQuantitysold());
            ht.setQuantityinstock(ht.getQuantityinstock()- updatedata.getQuantitysold());}
        else {
            ht=updatedata;
        }
            return repo.save(ht);

    }

    @PutMapping(value = "/Reset/SaleItemId/{Id}")
    public Mono<Stock> reseteBySaleItemId(@PathVariable int Id, @RequestBody Stock updatedata){

        Stock ht=null;
        try{
            ht=repo.findItemById(Id).block();
        }
        catch (Exception e){}
        if(ht!=null){
            repo.delete(ht);
            ht.setSaleamount(ht.getSaleamount()- updatedata.getSaleamount());
            ht.setQuantitysold(ht.getQuantitysold()- updatedata.getQuantitysold());
            ht.setQuantityinstock(ht.getQuantityinstock()+ updatedata.getQuantitysold());}
        else {
            ht=updatedata;
        }
        return repo.save(ht);

    }
}
