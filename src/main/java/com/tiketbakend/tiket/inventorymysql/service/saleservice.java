package com.tiketbakend.tiket.inventorymysql.service;

import com.tiketbakend.tiket.inventorymongodb.model.stock;
import com.tiketbakend.tiket.inventorymongodb.Repository.StockRepository;
import com.tiketbakend.tiket.inventorymysql.model.salehead;
import com.tiketbakend.tiket.inventorymysql.model.saleitems;
import com.tiketbakend.tiket.inventorymysql.repository.ItemMasterRepository;
import com.tiketbakend.tiket.inventorymysql.repository.PartyMasterRepository;
import com.tiketbakend.tiket.inventorymysql.repository.SaleHeadRepository;
import com.tiketbakend.tiket.inventorymysql.repository.SaleItemsRepository;
import com.tiketbakend.tiket.inventorymysql.util.Item;
import com.tiketbakend.tiket.inventorymysql.util.SalePurchase;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class saleservice {

    private StockRepository repo;

    public saleservice(StockRepository stck){
        this.repo=stck;
    }
    public List<SalePurchase> getAll(SaleHeadRepository headrepo, SaleItemsRepository itemrepo){
        List<SalePurchase> sp=new ArrayList<>();
        List<Item> items=new ArrayList<>();
        List<salehead> pheads=headrepo.findByDeleted(false);
        double amounts=0,qtys=0;
        for (salehead p:pheads) {
            List<saleitems> it=itemrepo.findBysalehead_IdAndDeleted(p.getId(),false);
            for (saleitems pi:it) {
                amounts+=pi.getAmount();
                qtys+=pi.getQuantity();

                Item item=new Item();
                item.setItemid(pi.getItemmaster().getId());
                item.setAmount(pi.getAmount());
                item.setRate(pi.getRate());
                item.setQuantity(pi.getQuantity());
                items.add(item);
            }
            SalePurchase purchase=new SalePurchase();
            purchase.setVchno(p.getId());
            purchase.setPartyid(p.getParty().getId());
            purchase.setDate(p.getDate());
            purchase.setGrandtotal(p.getGrandtotal());
            purchase.setOthers(p.getOthers());
            purchase.setTotalamount(p.getTotalamount());
            purchase.setItems(items);
            purchase.setTotalquantity(p.getTotalquantity());
            sp.add(purchase);
        }
        return sp;
    }


    public SalePurchase create(SalePurchase pt, SaleHeadRepository purcheadrepo, SaleItemsRepository purcitemrepo, PartyMasterRepository partyrepo, ItemMasterRepository itemrepo){
        salehead phead=new salehead();
        phead.setTotalquantity(pt.getTotalquantity());
        phead.setParty(new partymasterservice().getById(pt.getPartyid(),partyrepo));
        phead.setTotalamount(pt.getTotalamount());
        phead.setDate(LocalDateTime.now().toString());
        phead.setGrandtotal(pt.getGrandtotal());
        phead.setOthers(pt.getOthers());
        phead.setTotalamount(pt.getTotalamount());

        phead=purcheadrepo.save(phead);

        for (Item it:pt.getItems()) {
            saleitems pi=new saleitems();
            pi.setAmount(it.getAmount());
            pi.setQuantity(it.getQuantity());
            pi.setRate(it.getRate());
            pi.setItemmaster(new itemmasterservice().getById(it.getItemid(),itemrepo));
            pi.setSalehead(phead);

            purcitemrepo.save(pi);

            stock ht=new stock();
            try{
                ht=repo.findItemById(it.getItemid());
            }
            catch (Exception e){}
            if(ht!=null) {
                repo.delete(ht);
            }
                ht.setSaleamount(ht.getSaleamount()+it.getAmount());
                ht.setQuantitysold(ht.getQuantitysold()+ it.getQuantity());
                ht.setQuantityinstock(ht.getQuantityinstock()- it.getQuantity());
                repo.save(ht);


        }


        return  new saleservice(repo).getById(phead.getId(),purcheadrepo,purcitemrepo);
    }

    public SalePurchase getById( int Id,SaleHeadRepository headrepo, SaleItemsRepository itemrepo){
        List<Item> items=new ArrayList<>();
        salehead p=headrepo.findByDeletedAndId(false,Id);
        double amounts=0,qtys=0;

            List<saleitems> it=itemrepo.findBysalehead_IdAndDeleted(p.getId(),false);
            for (saleitems pi:it) {
                amounts+=pi.getAmount();
                qtys+=pi.getQuantity();

                Item item=new Item();
                item.setItemid(pi.getItemmaster().getId());
                item.setAmount(pi.getAmount());
                item.setRate(pi.getRate());
                item.setQuantity(pi.getQuantity());
                items.add(item);
            }
            SalePurchase purchase=new SalePurchase();
            purchase.setPartyid(p.getParty().getId());
            purchase.setDate(p.getDate());
            purchase.setVchno(p.getId());
            purchase.setGrandtotal(p.getGrandtotal());
            purchase.setOthers(p.getOthers());
            purchase.setTotalamount(p.getTotalamount());
            purchase.setItems(items);
            purchase.setTotalquantity(p.getTotalquantity());


        return purchase;
    }


    public SalePurchase updateById( int Id, SalePurchase pt,SaleHeadRepository purcheadrepo, SaleItemsRepository purcitemrepo, PartyMasterRepository partyrepo, ItemMasterRepository itemrepo){
        List<saleitems> itm=purcitemrepo.findBysalehead_IdAndDeleted(Id,false);
        for (saleitems pi:itm) {
            pi.setDeleted(true);
            purcitemrepo.save(pi);
            stock ht=new stock();
            try{
                ht=repo.findItemById(pi.getItemmaster().getId());
            }
            catch (Exception e){}
            if(ht!=null){
                repo.delete(ht);
                ht.setSaleamount(ht.getSaleamount()-pi.getAmount());
                ht.setQuantitysold(ht.getQuantitysold()- pi.getQuantity());
                ht.setQuantityinstock(ht.getQuantityinstock()+ pi.getQuantity());
                repo.save(ht);

            }
        }
        salehead phead=purcheadrepo.findByDeletedAndId(false,Id);
        phead.setTotalquantity(pt.getTotalquantity());
        phead.setParty(new partymasterservice().getById(pt.getPartyid(),partyrepo));
        phead.setTotalamount(pt.getTotalamount());
        phead.setDate(LocalDateTime.now().toString());
        phead.setGrandtotal(pt.getGrandtotal());
        phead.setOthers(pt.getOthers());
        phead.setTotalamount(pt.getTotalamount());

        phead=purcheadrepo.save(phead);

        for (Item it:pt.getItems()) {
            saleitems pi=new saleitems();
            pi.setAmount(it.getAmount());
            pi.setQuantity(it.getQuantity());
            pi.setRate(it.getRate());
            pi.setItemmaster(new itemmasterservice().getById(it.getItemid(),itemrepo));
            pi.setSalehead(phead);

            purcitemrepo.save(pi);

            stock ht=new stock();
            try{
                ht=repo.findItemById(it.getItemid());
            }
            catch (Exception e){}
            if(ht!=null){
                repo.delete(ht);
                ht.setSaleamount(ht.getSaleamount()+it.getAmount());
                ht.setQuantitysold(ht.getQuantitysold()+ it.getQuantity());
                ht.setQuantityinstock(ht.getQuantityinstock()- it.getQuantity());
                repo.save(ht);

            }
        }

        return new saleservice(repo).getById(phead.getId(),purcheadrepo,purcitemrepo);
    }

    public String deleteById( int Id,SaleHeadRepository purcheadrepo,SaleItemsRepository purcitemrepo){

        List<saleitems> it=purcitemrepo.findBysalehead_IdAndDeleted(Id,false);
        for (saleitems pi:it) {
          pi.setDeleted(true);
          purcitemrepo.save(pi);

            stock ht=new stock();
            try{
                ht=repo.findItemById(pi.getItemmaster().getId());
            }
            catch (Exception e){}
            if(ht!=null){
                repo.delete(ht);
                ht.setSaleamount(ht.getSaleamount()-pi.getAmount());
                ht.setQuantitysold(ht.getQuantitysold()- pi.getQuantity());
                ht.setQuantityinstock(ht.getQuantityinstock()+ pi.getQuantity());
                repo.save(ht);

            }
        }
        salehead p=purcheadrepo.findByDeletedAndId(false,Id);
        p.setDeleted(true);
        purcheadrepo.save(p);

        return "with id :"+Id+" is deleted";
    }
}
