package com.tiketbakend.tiket.inventorymysql.controller;

import com.tiketbakend.tiket.inventorymysql.model.partymaster;
import com.tiketbakend.tiket.inventorymysql.repository.PartyMasterRepository;
import com.tiketbakend.tiket.inventorymysql.service.partymasterservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/party")

public class PartyController {


    @Autowired
    private PartyMasterRepository repo;
    @Autowired
    private partymasterservice service;

    @GetMapping
    public List<partymaster> getAll(){
        return service.getAll(repo);
    }


    @PostMapping
    public partymaster create(@RequestBody partymaster newpt){
        return service.create(newpt,repo);
    }

    @GetMapping(value = "/Id/{Id}")
    public partymaster getById(@PathVariable int Id){
        partymaster pt=service.getById(Id,repo);
        return pt;
    }

    //update ticket by id
    @PutMapping(value = "/Id/{Id}")
    public partymaster updateById(@PathVariable int Id,@RequestBody partymaster updatedata){

        partymaster ht=new partymaster();
        try{
            ht=service.getById(Id,repo);
        }
        catch (Exception e){}
        if(ht!=null){
            service.updateById(Id,updatedata,repo,ht);
            return updatedata;}
        return new partymaster();
    }

    //delete ticket by id
    @DeleteMapping(value = "/Id/{Id}")
    public String deleteById(@PathVariable int Id){
        partymaster ht=new partymaster();
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
