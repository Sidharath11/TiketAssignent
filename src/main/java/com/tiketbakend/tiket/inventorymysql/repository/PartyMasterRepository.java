package com.tiketbakend.tiket.inventorymysql.repository;


import com.tiketbakend.tiket.inventorymysql.model.partymaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartyMasterRepository  extends JpaRepository<partymaster,Integer> {
    List<partymaster> findByDeleted(boolean delete);
}
