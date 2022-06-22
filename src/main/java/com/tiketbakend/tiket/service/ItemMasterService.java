package com.tiketbakend.tiket.service;

import com.tiketbakend.tiket.exception.ResourceNotFoundException;
import com.tiketbakend.tiket.model.mysqldb.ItemMaster;
import com.tiketbakend.tiket.repository.mysqldb.ItemMasterRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ItemMasterService {

    public List<ItemMaster> getAll(ItemMasterRepository repo){
        return repo.findByDeleted(false);
    }


    public ItemMaster create(ItemMaster pt, ItemMasterRepository repo){
        return repo.save(pt);
    }

    public ItemMaster getById(int Id, ItemMasterRepository repo){
        ItemMaster pt=repo.findById(Id).orElseThrow(()-> new ResourceNotFoundException("not exist with Id - "+Id));

        return pt;
    }


    public ItemMaster updateById(int Id, ItemMaster updatedata, ItemMasterRepository repo, ItemMaster pt){


        pt.setItemgroup(updatedata.getItemgroup());
        pt.setItemname(updatedata.getItemname());
        pt.setPurcrate(updatedata.getPurcrate());
        pt.setSalerate(updatedata.getSalerate());
        pt.setUnits(updatedata.getUnits());
        pt.setUpdtion_date(LocalDateTime.now().toString());
        repo.save(pt);
        return updatedata;
    }

    public String deleteById(int Id, ItemMasterRepository repo, ItemMaster pt){

        pt.setDeleted(true);
        repo.save(pt);
        return "with id :"+Id+" is deleted";
    }
}
