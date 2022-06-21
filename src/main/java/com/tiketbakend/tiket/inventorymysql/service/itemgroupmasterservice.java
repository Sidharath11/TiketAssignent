package com.tiketbakend.tiket.inventorymysql.service;

import com.tiketbakend.tiket.inventorymysql.exception.ResourceNotFoundException;
import com.tiketbakend.tiket.inventorymysql.model.itemgroupmaster;
import com.tiketbakend.tiket.inventorymysql.repository.ItemGroupMasterRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class itemgroupmasterservice {


    public List<itemgroupmaster> getAll(ItemGroupMasterRepository repo){
        return repo.findByDeleted(false);
    }


    public itemgroupmaster create(itemgroupmaster pt,ItemGroupMasterRepository repo){

        return repo.save(pt);
    }

    public itemgroupmaster getById( int Id,ItemGroupMasterRepository repo){
        itemgroupmaster pt=repo.findById(Id).orElseThrow(()-> new ResourceNotFoundException("not exist with Id - "+Id));

        return pt;
    }

    public itemgroupmaster updateById( int Id, itemgroupmaster updatedata,ItemGroupMasterRepository repo,itemgroupmaster pt){

        pt.setGroupname(updatedata.getGroupname());
        pt.setUpdtion_date(LocalDateTime.now().toString());
        repo.save(pt);
        return updatedata;
    }

    public String deleteById( int Id,ItemGroupMasterRepository repo,itemgroupmaster pt){

        pt.setDeleted(true);
        repo.save(pt);
        return "with id :"+Id+" is deleted";
    }
}
