package com.tiketbakend.tiket.service;

import com.tiketbakend.tiket.exception.ResourceNotFoundException;
import com.tiketbakend.tiket.model.mysqldb.ItemGroupMaster;
import com.tiketbakend.tiket.repository.mysqldb.ItemGroupMasterRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ItemGroupMasterService {

    private static final String REDIS_CACHE_VALUE = "itemgroup";
    @Cacheable(value = REDIS_CACHE_VALUE)
    public List<ItemGroupMaster> getAll(ItemGroupMasterRepository repo){
        return repo.findByDeleted(false);
    }

    @CachePut(value = REDIS_CACHE_VALUE, key = "#pt.id")
    public ItemGroupMaster create(ItemGroupMaster pt, ItemGroupMasterRepository repo){

        return repo.save(pt);
    }
    @Cacheable(value = REDIS_CACHE_VALUE, key = "#Id")
    public ItemGroupMaster getById(int Id, ItemGroupMasterRepository repo){
        ItemGroupMaster pt=repo.findById(Id).orElseThrow(()-> new ResourceNotFoundException("not exist with Id - "+Id));

        return pt;
    }

    @CacheEvict(value = REDIS_CACHE_VALUE, key = "#Id",allEntries = true)
    public ItemGroupMaster updateById(int Id, ItemGroupMaster updatedata, ItemGroupMasterRepository repo, ItemGroupMaster pt){

        pt.setGroupname(updatedata.getGroupname());
        pt.setUpdtion_date(LocalDateTime.now().toString());
        repo.save(pt);
        return updatedata;
    }
    @CacheEvict(value = REDIS_CACHE_VALUE, key = "#Id")
    public String deleteById(int Id, ItemGroupMasterRepository repo, ItemGroupMaster pt){

        pt.setDeleted(true);
        repo.save(pt);
        return "with id :"+Id+" is deleted";
    }
}
