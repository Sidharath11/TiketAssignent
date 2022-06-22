package com.tiketbakend.tiket.repository.mysqldb;


import com.tiketbakend.tiket.model.mysqldb.ItemMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ItemMasterRepository extends JpaRepository<ItemMaster,Integer> {

    List<ItemMaster> findByitemgroup_IdAndDeleted(int id, boolean delete);
    List<ItemMaster> findByDeleted(boolean delete);
}
