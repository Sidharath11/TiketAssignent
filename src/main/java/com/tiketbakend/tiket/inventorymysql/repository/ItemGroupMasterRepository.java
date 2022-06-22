package com.tiketbakend.tiket.inventorymysql.repository;


import com.tiketbakend.tiket.inventorymysql.model.itemgroupmaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ItemGroupMasterRepository extends JpaRepository<itemgroupmaster,Integer> {
    List<itemgroupmaster> findByDeleted(boolean delete);
}
