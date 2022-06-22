package com.tiketbakend.tiket.controller;

import com.tiketbakend.tiket.model.mysqldb.ItemMaster;
import com.tiketbakend.tiket.repository.mysqldb.ItemMasterRepository;
import com.tiketbakend.tiket.service.ItemMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/item")

public class ItemController {


    @Autowired
    private ItemMasterRepository repo;
    @Autowired
    private ItemMasterService service;

    @GetMapping
    public List<ItemMaster> getAll(){
        return service.getAll(repo);
    }


    @PostMapping
    public ItemMaster create(@RequestBody ItemMaster newpt){
        return service.create(newpt,repo);
    }

    @GetMapping(value = "/Id/{Id}")
    public ItemMaster getById(@PathVariable int Id){
        ItemMaster pt=service.getById(Id,repo);
        return pt;
    }

    //update ticket by id
    @PutMapping(value = "/Id/{Id}")
    public ItemMaster updateById(@PathVariable int Id, @RequestBody ItemMaster updatedata){

        ItemMaster ht=new ItemMaster();
        try{
            ht=service.getById(Id,repo);
        }
        catch (Exception e){}
        if(ht!=null){
            service.updateById(Id,updatedata,repo,ht);
            return updatedata;}
        return new ItemMaster();
    }

    @DeleteMapping(value = "/Id/{Id}")
    public String deleteById(@PathVariable int Id){
        ItemMaster ht=new ItemMaster();
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
