package com.tiketbakend.tiket.inventorymysql.repository;


import com.tiketbakend.tiket.inventorymysql.model.itemmaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ItemMasterRepository extends JpaRepository<itemmaster,Integer> {

    List<itemmaster> findByitemgroup_IdAndDeleted(int id,boolean delete);
    List<itemmaster> findByDeleted(boolean delete);
}
