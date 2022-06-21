package com.tiketbakend.tiket.inventorymysql.controller;

import com.tiketbakend.tiket.inventorymysql.model.itemgroupmaster;
import com.tiketbakend.tiket.inventorymysql.repository.ItemGroupMasterRepository;
import com.tiketbakend.tiket.inventorymysql.repository.ItemMasterRepository;
import com.tiketbakend.tiket.inventorymysql.service.itemgroupmasterservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/itemgroup")

public class ItemGroupController {


    @Autowired
    private ItemGroupMasterRepository repo;
    @Autowired
    private itemgroupmasterservice service;
    @Autowired
    private ItemMasterRepository itemrepo;

    @GetMapping
    public List<itemgroupmaster> getAll(){
        return service.getAll(repo);
    }


    @PostMapping
    public itemgroupmaster create(@RequestBody itemgroupmaster newpt){
        return service.create(newpt,repo);
    }

    @GetMapping(value = "/Id/{Id}")
    public itemgroupmaster getById(@PathVariable int Id){
        itemgroupmaster pt=service.getById(Id,repo);
        return pt;
    }

    //update ticket by id
    @PutMapping(value = "/Id/{Id}")
    public itemgroupmaster updateById(@PathVariable int Id,@RequestBody itemgroupmaster updatedata){

        itemgroupmaster ht=new itemgroupmaster();
        try{
            ht=service.getById(Id,repo);
        }
        catch (Exception e){}
        if(ht!=null){
            service.updateById(Id,updatedata,repo,ht);
            return updatedata;}
        return new itemgroupmaster();
    }

    //delete ticket by id
    @DeleteMapping(value = "/Id/{Id}")
    public String deleteById(@PathVariable int Id){
        itemgroupmaster ht=new itemgroupmaster();
        try{
            if(itemrepo.findByitemgroup_IdAndDeleted(Id,false).size()==0){
            ht=service.getById(Id,repo);}
        }
        catch (Exception e){}
        if(ht!=null){
            service.deleteById(Id,repo,ht);
            return " with id :"+Id+" is deleted";}

        return " with id :"+Id+" is not deleted";

    }

}
