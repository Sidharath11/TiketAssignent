package com.tiketbakend.tiket.repository.mongodb;

import com.tiketbakend.tiket.model.mongodb.Stock;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface StockRepository extends ReactiveMongoRepository<Stock, Integer> {

    @Query("itemid:'?0'}")
    Mono<Stock> findItemById(int id);
    @Query("itemgroupid:'?0'}")
    Flux<Stock> findItemByGroupId(int id);
    @Query("itemid:'?0'}")
    Mono<Stock> deleteItemById(int id);
    Flux<Stock> findAll();


}
