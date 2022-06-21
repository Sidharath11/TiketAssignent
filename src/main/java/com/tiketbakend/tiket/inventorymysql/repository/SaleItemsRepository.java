package com.tiketbakend.tiket.inventorymysql.repository;


import com.tiketbakend.tiket.inventorymysql.model.saleitems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleItemsRepository extends JpaRepository<saleitems,Integer> {
    List<saleitems> findByDeleted(boolean delete);
    List<saleitems> findBysalehead_IdAndDeleted(int Id,boolean delete);
}
