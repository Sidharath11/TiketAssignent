package com.tiketbakend.tiket.inventorymysql.repository;


import com.tiketbakend.tiket.inventorymysql.model.salehead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SaleHeadRepository extends JpaRepository<salehead,Integer> {
    List<salehead> findByDeleted(boolean delete);
    salehead findByDeletedAndId(boolean delete,int Id);
}
