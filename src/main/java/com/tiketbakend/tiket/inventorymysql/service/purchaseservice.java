package com.tiketbakend.tiket.inventorymysql.service;

import com.tiketbakend.tiket.inventorymongodb.model.stock;
import com.tiketbakend.tiket.inventorymongodb.Repository.StockRepository;
import com.tiketbakend.tiket.inventorymysql.model.purchasehead;
import com.tiketbakend.tiket.inventorymysql.model.purchaseitems;
import com.tiketbakend.tiket.inventorymysql.repository.ItemMasterRepository;
import com.tiketbakend.tiket.inventorymysql.repository.PartyMasterRepository;
import com.tiketbakend.tiket.inventorymysql.repository.PurchaseHeadRepository;
import com.tiketbakend.tiket.inventorymysql.repository.PurchaseItemsRepository;
import com.tiketbakend.tiket.inventorymysql.util.Item;
import com.tiketbakend.tiket.inventorymysql.util.SalePurchase;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class purchaseservice {


    private StockRepository repo;
    public purchaseservice(StockRepository stck){
        this.repo=stck;
    }
    public List<SalePurchase> getAll(PurchaseHeadRepository headrepo, PurchaseItemsRepository itemrepo){
        List<SalePurchase> sp=new ArrayList<>();
        List<Item> items=new ArrayList<>();
        List<purchasehead> pheads=headrepo.findByDeleted(false);
        double amounts=0,qtys=0;
        for (purchasehead p:pheads) {
            List<purchaseitems> it=itemrepo.findBypurchasehead_IdAndDeleted(p.getId(),false);
            for (purchaseitems pi:it) {
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


    public SalePurchase create(SalePurchase pt, PurchaseHeadRepository purcheadrepo, PurchaseItemsRepository purcitemrepo, PartyMasterRepository partyrepo, ItemMasterRepository itemrepo){
        purchasehead phead=new purchasehead();
        phead.setTotalquantity(pt.getTotalquantity());
        phead.setParty(new partymasterservice().getById(pt.getPartyid(),partyrepo));
        phead.setTotalamount(pt.getTotalamount());
        phead.setDate(LocalDateTime.now().toString());
        phead.setGrandtotal(pt.getGrandtotal());
        phead.setOthers(pt.getOthers());
        phead.setTotalamount(pt.getTotalamount());

        phead=purcheadrepo.save(phead);

        for (Item it:pt.getItems()) {
            purchaseitems pi=new purchaseitems();
            pi.setAmount(it.getAmount());
            pi.setQuantity(it.getQuantity());
            pi.setRate(it.getRate());
            pi.setItemmaster(new itemmasterservice().getById(it.getItemid(),itemrepo));
            pi.setPurchasehead(phead);

            purcitemrepo.save(pi);


            stock ht=new stock();
            try{
                ht=repo.findItemById(pi.getItemmaster().getId());
            }
            catch (Exception e){}
            if(ht!=null) {
                repo.delete(ht);
            }
                ht.setPurcamount(ht.getPurcamount()+pi.getAmount());
                ht.setQuantityinstock(ht.getQuantityinstock()+ pi.getQuantity());
                repo.save(ht);


        }

        return  new purchaseservice(repo).getById(phead.getId(),purcheadrepo,purcitemrepo);
    }

    public SalePurchase getById( int Id,PurchaseHeadRepository headrepo, PurchaseItemsRepository itemrepo){
        List<Item> items=new ArrayList<>();
        purchasehead p=headrepo.findByDeletedAndId(false,Id);
        double amounts=0,qtys=0;

            List<purchaseitems> it=itemrepo.findBypurchasehead_IdAndDeleted(p.getId(),false);
            for (purchaseitems pi:it) {
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


    public SalePurchase updateById( int Id, SalePurchase pt,PurchaseHeadRepository purcheadrepo, PurchaseItemsRepository purcitemrepo, PartyMasterRepository partyrepo, ItemMasterRepository itemrepo){
        List<purchaseitems> itm=purcitemrepo.findBypurchasehead_IdAndDeleted(Id,false);
        for (purchaseitems pi:itm) {
            pi.setDeleted(true);
            purcitemrepo.save(pi);

            stock ht=new stock();
            try{
                ht=repo.findItemById(pi.getItemmaster().getId());
            }
            catch (Exception e){}
            if(ht!=null){
                repo.delete(ht);
                ht.setPurcamount(ht.getPurcamount()-pi.getAmount());
                ht.setQuantityinstock(ht.getQuantityinstock()- pi.getQuantity());
                repo.save(ht);

            }
        }
        purchasehead phead=purcheadrepo.findByDeletedAndId(false,Id);
        phead.setTotalquantity(pt.getTotalquantity());
        phead.setParty(new partymasterservice().getById(pt.getPartyid(),partyrepo));
        phead.setTotalamount(pt.getTotalamount());
        phead.setDate(LocalDateTime.now().toString());
        phead.setGrandtotal(pt.getGrandtotal());
        phead.setOthers(pt.getOthers());
        phead.setTotalamount(pt.getTotalamount());

        phead=purcheadrepo.save(phead);

        for (Item it:pt.getItems()) {
            purchaseitems pi=new purchaseitems();
            pi.setAmount(it.getAmount());
            pi.setQuantity(it.getQuantity());
            pi.setRate(it.getRate());
            pi.setItemmaster(new itemmasterservice().getById(it.getItemid(),itemrepo));
            pi.setPurchasehead(phead);

            purcitemrepo.save(pi);

            stock ht=new stock();
            try{
                ht=repo.findItemById(pi.getItemmaster().getId());
            }
            catch (Exception e){}
            if(ht!=null){
                repo.delete(ht);
                ht.setPurcamount(ht.getPurcamount()+pi.getAmount());
                ht.setQuantityinstock(ht.getQuantityinstock()+ pi.getQuantity());
                repo.save(ht);

            }
        }

        return new purchaseservice(repo).getById(phead.getId(),purcheadrepo,purcitemrepo);
    }

    public String deleteById( int Id,PurchaseHeadRepository purcheadrepo,PurchaseItemsRepository purcitemrepo){

        List<purchaseitems> it=purcitemrepo.findBypurchasehead_IdAndDeleted(Id,false);
        for (purchaseitems pi:it) {
          pi.setDeleted(true);
          purcitemrepo.save(pi);

            stock ht=new stock();
            try{
                ht=repo.findItemById(pi.getItemmaster().getId());
            }
            catch (Exception e){}
            if(ht!=null){
                repo.delete(ht);
                ht.setPurcamount(ht.getPurcamount()-pi.getAmount());
                ht.setQuantityinstock(ht.getQuantityinstock()- pi.getQuantity());
                repo.save(ht);

            }
        }
        purchasehead p=purcheadrepo.findByDeletedAndId(false,Id);
        p.setDeleted(true);
        purcheadrepo.save(p);


        return "with id :"+Id+" is deleted";
    }
}
