package com.tiketbakend.tiket.inventorymongodb.repository;

import com.tiketbakend.tiket.inventorymongodb.model.stock;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface StockRepository extends MongoRepository<stock, String> {

    @Query("itemid:'?0'}")
    stock findItemById(int id);
    @Query("itemgroupid:'?0'}")
    List<stock> findItemByGroupId(int id);

    List<stock> findAll();

    public long count();

}
