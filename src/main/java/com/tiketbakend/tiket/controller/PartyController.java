package com.tiketbakend.tiket.controller;

import com.tiketbakend.tiket.model.mysqldb.PartyMaster;
import com.tiketbakend.tiket.repository.mysqldb.PartyMasterRepository;
import com.tiketbakend.tiket.service.PartyMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/party")

public class PartyController {


    @Autowired
    private PartyMasterRepository repo;
    @Autowired
    private PartyMasterService service;

    @GetMapping
    public List<PartyMaster> getAll(){
        return service.getAll(repo);
    }


    @PostMapping
    public PartyMaster create(@RequestBody PartyMaster newpt){
        return service.create(newpt,repo);
    }

    @GetMapping(value = "/Id/{Id}")
    public PartyMaster getById(@PathVariable int Id){
        PartyMaster pt=service.getById(Id,repo);
        return pt;
    }

    //update ticket by id
    @PutMapping(value = "/Id/{Id}")
    public PartyMaster updateById(@PathVariable int Id, @RequestBody PartyMaster updatedata){

        PartyMaster ht=new PartyMaster();
        try{
            ht=service.getById(Id,repo);
        }
        catch (Exception e){}
        if(ht!=null){
            service.updateById(Id,updatedata,repo,ht);
            return updatedata;}
        return new PartyMaster();
    }

    @DeleteMapping(value = "/Id/{Id}")
    public String deleteById(@PathVariable int Id){
        PartyMaster ht=new PartyMaster();
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
