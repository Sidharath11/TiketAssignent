package com.tiketbakend.tiket.service;

import com.tiketbakend.tiket.exception.ResourceNotFoundException;
import com.tiketbakend.tiket.model.mysqldb.PartyMaster;
import com.tiketbakend.tiket.repository.mysqldb.PartyMasterRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
@Component
public class PartyMasterService {

    private static final String REDIS_CACHE_VALUE = "parties";
    @Cacheable(value = REDIS_CACHE_VALUE)
    public List<PartyMaster> getAll(PartyMasterRepository repo){
        return repo.findByDeleted(false);
    }

    @CachePut(value = REDIS_CACHE_VALUE, key = "#pt.id")
    public PartyMaster create(PartyMaster pt, PartyMasterRepository repo){

        return repo.save(pt);
    }
    @Cacheable(value = REDIS_CACHE_VALUE, key = "#Id")
    public PartyMaster getById(int Id, PartyMasterRepository repo){
        PartyMaster pt=repo.findById(Id).orElseThrow(()-> new ResourceNotFoundException("not exist with Id - "+Id));

        return pt;
    }
    @CacheEvict(value = REDIS_CACHE_VALUE, key = "#Id",allEntries = true)
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
    @CacheEvict(value = REDIS_CACHE_VALUE, key = "#Id")
    public String deleteById(int Id, PartyMasterRepository repo, PartyMaster pt){

       pt.setDeleted(true);
       repo.save(pt);
        return "with id :"+Id+" is deleted";
    }
}
