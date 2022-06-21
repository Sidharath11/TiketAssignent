package com.tiketbakend.tiket.inventorymysql.service;

import com.tiketbakend.tiket.inventorymysql.exception.ResourceNotFoundException;
import com.tiketbakend.tiket.inventorymysql.model.itemmaster;
import com.tiketbakend.tiket.inventorymysql.repository.ItemMasterRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class itemmasterservice {

    public List<itemmaster> getAll(ItemMasterRepository repo){
        return repo.findByDeleted(false);
    }


    public itemmaster create(itemmaster pt,ItemMasterRepository repo){
        return repo.save(pt);
    }

    public itemmaster getById( int Id,ItemMasterRepository repo){
        itemmaster pt=repo.findById(Id).orElseThrow(()-> new ResourceNotFoundException("not exist with Id - "+Id));

        return pt;
    }


    public itemmaster updateById( int Id, itemmaster updatedata,ItemMasterRepository repo,itemmaster pt){


        pt.setItemgroup(updatedata.getItemgroup());
        pt.setItemname(updatedata.getItemname());
        pt.setPurcrate(updatedata.getPurcrate());
        pt.setSalerate(updatedata.getSalerate());
        pt.setUnits(updatedata.getUnits());
        pt.setUpdtion_date(LocalDateTime.now().toString());
        repo.save(pt);
        return updatedata;
    }

    public String deleteById( int Id,ItemMasterRepository repo,itemmaster pt){

        pt.setDeleted(true);
        repo.save(pt);
        return "with id :"+Id+" is deleted";
    }
}
