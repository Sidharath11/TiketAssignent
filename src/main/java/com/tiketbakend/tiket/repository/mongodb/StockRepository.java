package com.tiketbakend.tiket.repository.mongodb;

import com.tiketbakend.tiket.model.mongodb.Stock;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends MongoRepository<Stock, String> {

    @Query("itemid:'?0'}")
    Stock findItemById(int id);
    @Query("itemgroupid:'?0'}")
    List<Stock> findItemByGroupId(int id);

    List<Stock> findAll();

    public long count();

}
