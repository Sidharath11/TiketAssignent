package com.tiketbakend.tiket.inventorymysql.repository;


import com.tiketbakend.tiket.inventorymysql.model.itemgroupmaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemGroupMasterRepository extends JpaRepository<itemgroupmaster,Integer> {
    List<itemgroupmaster> findByDeleted(boolean delete);
}
