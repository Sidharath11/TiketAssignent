package com.tiketbakend.tiket.inventorymysql.service;

import com.tiketbakend.tiket.inventorymysql.exception.ResourceNotFoundException;
import com.tiketbakend.tiket.inventorymysql.model.partymaster;
import com.tiketbakend.tiket.inventorymysql.repository.PartyMasterRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
@Component
public class partymasterservice {


    public List<partymaster> getAll(PartyMasterRepository repo){
        return repo.findByDeleted(false);
    }


    public partymaster create(partymaster pt,PartyMasterRepository repo){

        return repo.save(pt);
    }

    public partymaster getById( int Id,PartyMasterRepository repo){
        partymaster pt=repo.findById(Id).orElseThrow(()-> new ResourceNotFoundException("not exist with Id - "+Id));

        return pt;
    }

    public partymaster updateById( int Id, partymaster updatedata,PartyMasterRepository repo,partymaster pt){

        pt.setPartname(updatedata.getPartname());
        pt.setAddress(updatedata.getAddress());
        pt.setCity(updatedata.getCity());
        pt.setEmailid(updatedata.getEmailid());
        pt.setPhoneno(updatedata.getPhoneno());
        pt.setPincode(updatedata.getPincode());
        pt.setUpdtion_date(LocalDateTime.now().toString());
        repo.save(pt);
        return updatedata;
    }

    public String deleteById( int Id,PartyMasterRepository repo,partymaster pt){

       pt.setDeleted(true);
       repo.save(pt);
        return "with id :"+Id+" is deleted";
    }
}
