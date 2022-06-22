package com.tiketbakend.tiket.repository.mysqldb;


import com.tiketbakend.tiket.model.mysqldb.PartyMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PartyMasterRepository  extends JpaRepository<PartyMaster,Integer> {
    List<PartyMaster> findByDeleted(boolean delete);
}
