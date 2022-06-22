package com.tiketbakend.tiket.repository.mysqldb;


import com.tiketbakend.tiket.model.mysqldb.SaleItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SaleItemsRepository extends JpaRepository<SaleItems,Integer> {
    List<SaleItems> findByDeleted(boolean delete);
    List<SaleItems> findBysalehead_IdAndDeleted(int Id, boolean delete);
}
