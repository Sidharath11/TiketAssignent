package com.tiketbakend.tiket.repository.mysqldb;


import com.tiketbakend.tiket.model.mysqldb.PurchaseHead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PurchaseHeadRepository extends JpaRepository<PurchaseHead,Integer> {
    List<PurchaseHead> findByDeleted(boolean delete);
    PurchaseHead findByDeletedAndId(boolean delete, int Id);
}
