package com.tiketbakend.tiket.model.mysqldb;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "itemgroupmaster")

public class ItemGroupMaster implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "creation_date")
    private String creation_date= LocalDateTime.now().toString();
    @Column(name = "updtion_date")
    private  String updtion_date=LocalDateTime.now().toString();
    @Column(name = "deleted")
    private boolean deleted=false;

    @Column(name = "groupname",nullable = false,length = 50)
    private String groupname;

}
