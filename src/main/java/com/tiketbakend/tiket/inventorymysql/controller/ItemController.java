package com.tiketbakend.tiket.inventorymysql.controller;

import com.tiketbakend.tiket.inventorymysql.model.itemmaster;
import com.tiketbakend.tiket.inventorymysql.repository.ItemMasterRepository;
import com.tiketbakend.tiket.inventorymysql.service.itemmasterservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/item")

public class ItemController {


    @Autowired
    private ItemMasterRepository repo;
    @Autowired
    private itemmasterservice service;

    @GetMapping
    public List<itemmaster> getAll(){
        return service.getAll(repo);
    }


    @PostMapping
    public itemmaster create(@RequestBody itemmaster newpt){
        return service.create(newpt,repo);
    }

    @GetMapping(value = "/Id/{Id}")
    public itemmaster getById(@PathVariable int Id){
        itemmaster pt=service.getById(Id,repo);
        return pt;
    }

    //update ticket by id
    @PutMapping(value = "/Id/{Id}")
    public itemmaster updateById(@PathVariable int Id,@RequestBody itemmaster updatedata){

        itemmaster ht=new itemmaster();
        try{
            ht=service.getById(Id,repo);
        }
        catch (Exception e){}
        if(ht!=null){
            service.updateById(Id,updatedata,repo,ht);
            return updatedata;}
        return new itemmaster();
    }

    //delete ticket by id
    @DeleteMapping(value = "/Id/{Id}")
    public String deleteById(@PathVariable int Id){
        itemmaster ht=new itemmaster();
        try{
            ht=service.getById(Id,repo);
        }
        catch (Exception e){}
        if(ht!=null){
            service.deleteById(Id,repo,ht);
            return " with id :"+Id+" is deleted";}

        return " with id :"+Id+" is not deleted";

    }

}
