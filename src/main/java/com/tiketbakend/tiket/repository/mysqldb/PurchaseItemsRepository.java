package com.tiketbakend.tiket.repository.mysqldb;


import com.tiketbakend.tiket.model.mysqldb.PurchaseItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PurchaseItemsRepository extends JpaRepository<PurchaseItems,Integer> {
    List<PurchaseItems> findByDeleted(boolean delete);
    List<PurchaseItems> findBypurchasehead_IdAndDeleted(int Id, boolean delete);
}
