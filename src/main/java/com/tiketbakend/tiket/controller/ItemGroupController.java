package com.tiketbakend.tiket.controller;

import com.tiketbakend.tiket.model.mysqldb.ItemGroupMaster;
import com.tiketbakend.tiket.repository.mysqldb.ItemGroupMasterRepository;
import com.tiketbakend.tiket.repository.mysqldb.ItemMasterRepository;
import com.tiketbakend.tiket.service.ItemGroupMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/itemgroup")

public class ItemGroupController {


    @Autowired
    private ItemGroupMasterRepository repo;
    @Autowired
    private ItemGroupMasterService service;
    @Autowired
    private ItemMasterRepository itemrepo;

    @GetMapping
    public List<ItemGroupMaster> getAll(){
        return service.getAll(repo);
    }


    @PostMapping
    public ItemGroupMaster create(@RequestBody ItemGroupMaster newpt){
        return service.create(newpt,repo);
    }

    @GetMapping(value = "/Id/{Id}")
    public ItemGroupMaster getById(@PathVariable int Id){
        ItemGroupMaster pt=service.getById(Id,repo);
        return pt;
    }

    //update ticket by id
    @PutMapping(value = "/Id/{Id}")
    public ItemGroupMaster updateById(@PathVariable int Id, @RequestBody ItemGroupMaster updatedata){

        ItemGroupMaster ht=new ItemGroupMaster();
        try{
            ht=service.getById(Id,repo);
        }
        catch (Exception e){}
        if(ht!=null){
            service.updateById(Id,updatedata,repo,ht);
            return updatedata;}
        return new ItemGroupMaster();
    }

    @DeleteMapping(value = "/Id/{Id}")
    public String deleteById(@PathVariable int Id){
        ItemGroupMaster ht=new ItemGroupMaster();
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
