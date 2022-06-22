package com.tiketbakend.tiket.inventorymysql.repository;


import com.tiketbakend.tiket.inventorymysql.model.partymaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PartyMasterRepository  extends JpaRepository<partymaster,Integer> {
    List<partymaster> findByDeleted(boolean delete);
}
