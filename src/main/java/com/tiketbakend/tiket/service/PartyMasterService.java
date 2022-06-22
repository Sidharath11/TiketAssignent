package com.tiketbakend.tiket.service;

import com.tiketbakend.tiket.exception.ResourceNotFoundException;
import com.tiketbakend.tiket.model.mysqldb.PartyMaster;
import com.tiketbakend.tiket.repository.mysqldb.PartyMasterRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
@Component
public class PartyMasterService {


    public List<PartyMaster> getAll(PartyMasterRepository repo){
        return repo.findByDeleted(false);
    }


    public PartyMaster create(PartyMaster pt, PartyMasterRepository repo){

        return repo.save(pt);
    }

    public PartyMaster getById(int Id, PartyMasterRepository repo){
        PartyMaster pt=repo.findById(Id).orElseThrow(()-> new ResourceNotFoundException("not exist with Id - "+Id));

        return pt;
    }

    public PartyMaster updateById(int Id, PartyMaster updatedata, PartyMasterRepository repo, PartyMaster pt){

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

    public String deleteById(int Id, PartyMasterRepository repo, PartyMaster pt){

       pt.setDeleted(true);
       repo.save(pt);
        return "with id :"+Id+" is deleted";
    }
}
