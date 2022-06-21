package com.tiketbakend.tiket.inventorymysql.repository;


import com.tiketbakend.tiket.inventorymysql.model.purchaseitems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseItemsRepository extends JpaRepository<purchaseitems,Integer> {
    List<purchaseitems> findByDeleted(boolean delete);
    List<purchaseitems> findBypurchasehead_IdAndDeleted(int Id,boolean delete);
}
