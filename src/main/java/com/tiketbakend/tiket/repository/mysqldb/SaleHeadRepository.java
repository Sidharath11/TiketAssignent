package com.tiketbakend.tiket.repository.mysqldb;


import com.tiketbakend.tiket.model.mysqldb.SaleHead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SaleHeadRepository extends JpaRepository<SaleHead,Integer> {
    List<SaleHead> findByDeleted(boolean delete);
    SaleHead findByDeletedAndId(boolean delete, int Id);
}
