package com.tiketbakend.tiket.repository.mysqldb;


import com.tiketbakend.tiket.model.mysqldb.ItemGroupMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ItemGroupMasterRepository extends JpaRepository<ItemGroupMaster,Integer> {
    List<ItemGroupMaster> findByDeleted(boolean delete);
}
