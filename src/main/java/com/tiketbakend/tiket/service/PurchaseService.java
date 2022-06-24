package com.tiketbakend.tiket.service;

import com.tiketbakend.tiket.model.mongodb.Stock;
import com.tiketbakend.tiket.model.mysqldb.PurchaseHead;
import com.tiketbakend.tiket.model.mysqldb.PurchaseItems;
import com.tiketbakend.tiket.repository.mysqldb.ItemMasterRepository;
import com.tiketbakend.tiket.repository.mysqldb.PartyMasterRepository;
import com.tiketbakend.tiket.repository.mysqldb.PurchaseHeadRepository;
import com.tiketbakend.tiket.repository.mysqldb.PurchaseItemsRepository;
import com.tiketbakend.tiket.util.Item;
import com.tiketbakend.tiket.util.ReactiveApiCall;
import com.tiketbakend.tiket.util.SalePurchase;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class PurchaseService {


    private static final String REDIS_CACHE_VALUE = "purchase";
   // @Cacheable(value = REDIS_CACHE_VALUE)
    public List<SalePurchase> getAll(PurchaseHeadRepository headrepo, PurchaseItemsRepository itemrepo){
        List<SalePurchase> sp=new ArrayList<>();
        List<Item> items=new ArrayList<>();
        List<PurchaseHead> pheads=headrepo.findByDeleted(false);
        double amounts=0,qtys=0;
        for (PurchaseHead p:pheads) {
            List<PurchaseItems> it=itemrepo.findBypurchasehead_IdAndDeleted(p.getId(),false);
            for (PurchaseItems pi:it) {
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

   // @CachePut(value = REDIS_CACHE_VALUE, key = "#pt.id")
    public SalePurchase create(SalePurchase pt, PurchaseHeadRepository purcheadrepo, PurchaseItemsRepository purcitemrepo, PartyMasterRepository partyrepo, ItemMasterRepository itemrepo){
        PurchaseHead phead=new PurchaseHead();
        phead.setTotalquantity(pt.getTotalquantity());
        phead.setParty(new PartyMasterService().getById(pt.getPartyid(),partyrepo));
        phead.setTotalamount(pt.getTotalamount());
        phead.setDate(LocalDateTime.now().toString());
        phead.setGrandtotal(pt.getGrandtotal());
        phead.setOthers(pt.getOthers());
        phead.setTotalamount(pt.getTotalamount());

        phead=purcheadrepo.save(phead);

        for (Item it:pt.getItems()) {
            PurchaseItems pi=new PurchaseItems();
            pi.setAmount(it.getAmount());
            pi.setQuantity(it.getQuantity());
            pi.setRate(it.getRate());
            pi.setItemmaster(new ItemMasterService().getById(it.getItemid(),itemrepo));
            pi.setPurchasehead(phead);

            purcitemrepo.save(pi);


            Stock stk=new Stock(it.getItemid(),new ItemMasterService().getById(it.getItemid(),itemrepo).getItemgroup().getId(),0,pi.getAmount(),0, pi.getQuantity());
            ReactiveApiCall api=new ReactiveApiCall();
            api.makePutApiCall("/PurcItemId/"+it.getItemid(),stk);

        }

        return  new PurchaseService().getById(phead.getId(),purcheadrepo,purcitemrepo);
    }

    //@Cacheable(value = REDIS_CACHE_VALUE, key = "#Id")
    public SalePurchase getById( int Id,PurchaseHeadRepository headrepo, PurchaseItemsRepository itemrepo){
        List<Item> items=new ArrayList<>();
        PurchaseHead p=headrepo.findByDeletedAndId(false,Id);
        double amounts=0,qtys=0;

            List<PurchaseItems> it=itemrepo.findBypurchasehead_IdAndDeleted(p.getId(),false);
            for (PurchaseItems pi:it) {
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

   // @CacheEvict(value = REDIS_CACHE_VALUE, key = "#Id",allEntries = true)
    public SalePurchase updateById( int Id, SalePurchase pt,PurchaseHeadRepository purcheadrepo, PurchaseItemsRepository purcitemrepo, PartyMasterRepository partyrepo, ItemMasterRepository itemrepo){
        List<PurchaseItems> itm=purcitemrepo.findBypurchasehead_IdAndDeleted(Id,false);
        for (PurchaseItems pi:itm) {
            pi.setDeleted(true);
            purcitemrepo.save(pi);

            Stock stk=new Stock(pi.getItemmaster().getId(),pi.getItemmaster().getItemgroup().getId(),0,pi.getAmount(),0, pi.getQuantity());
            ReactiveApiCall api=new ReactiveApiCall();
            api.makePutApiCall("/Reset/PurcItemId/"+pi.getItemmaster().getId(),stk);


        }
        PurchaseHead phead=purcheadrepo.findByDeletedAndId(false,Id);
        phead.setTotalquantity(pt.getTotalquantity());
        phead.setParty(new PartyMasterService().getById(pt.getPartyid(),partyrepo));
        phead.setTotalamount(pt.getTotalamount());
        phead.setDate(LocalDateTime.now().toString());
        phead.setGrandtotal(pt.getGrandtotal());
        phead.setOthers(pt.getOthers());
        phead.setTotalamount(pt.getTotalamount());

        phead=purcheadrepo.save(phead);

        for (Item it:pt.getItems()) {
            PurchaseItems pi=new PurchaseItems();
            pi.setAmount(it.getAmount());
            pi.setQuantity(it.getQuantity());
            pi.setRate(it.getRate());
            pi.setItemmaster(new ItemMasterService().getById(it.getItemid(),itemrepo));
            pi.setPurchasehead(phead);

            purcitemrepo.save(pi);


            Stock stk=new Stock(it.getItemid(),new ItemMasterService().getById(it.getItemid(),itemrepo).getItemgroup().getId(),0,pi.getAmount(),0, pi.getQuantity());
            ReactiveApiCall api=new ReactiveApiCall();
            api.makePutApiCall("/PurcItemId/"+it.getItemid(),stk);
     }

        return new PurchaseService().getById(phead.getId(),purcheadrepo,purcitemrepo);
    }
   // @CacheEvict(value = REDIS_CACHE_VALUE, key = "#Id")
    public String deleteById( int Id,PurchaseHeadRepository purcheadrepo,PurchaseItemsRepository purcitemrepo){

        List<PurchaseItems> it=purcitemrepo.findBypurchasehead_IdAndDeleted(Id,false);
        for (PurchaseItems pi:it) {
          pi.setDeleted(true);
          purcitemrepo.save(pi);

            Stock stk=new Stock(pi.getItemmaster().getId(),pi.getItemmaster().getItemgroup().getId(),0,pi.getAmount(),0, pi.getQuantity());
            ReactiveApiCall api=new ReactiveApiCall();
            api.makePutApiCall("/Reset/PurcItemId/"+pi.getItemmaster().getId(),stk);
        }
        PurchaseHead p=purcheadrepo.findByDeletedAndId(false,Id);
        p.setDeleted(true);
        purcheadrepo.save(p);


        return "with id :"+Id+" is deleted";
    }
}
