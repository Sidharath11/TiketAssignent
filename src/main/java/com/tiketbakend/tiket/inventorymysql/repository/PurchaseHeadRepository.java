package com.tiketbakend.tiket.inventorymysql.repository;


import com.tiketbakend.tiket.inventorymysql.model.purchasehead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseHeadRepository extends JpaRepository<purchasehead,Integer> {
    List<purchasehead> findByDeleted(boolean delete);
    purchasehead findByDeletedAndId(boolean delete,int Id);
}
