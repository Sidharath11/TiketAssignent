package com.tiketbakend.tiket.inventorymysql.repository;


import com.tiketbakend.tiket.inventorymysql.model.salehead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleHeadRepository extends JpaRepository<salehead,Integer> {
    List<salehead> findByDeleted(boolean delete);
    salehead findByDeletedAndId(boolean delete,int Id);
}
