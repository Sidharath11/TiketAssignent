package com.tiketbakend.tiket.service;

import com.tiketbakend.tiket.model.mongodb.Stock;
import com.tiketbakend.tiket.model.mysqldb.SaleHead;
import com.tiketbakend.tiket.model.mysqldb.SaleItems;
import com.tiketbakend.tiket.repository.mysqldb.ItemMasterRepository;
import com.tiketbakend.tiket.repository.mysqldb.PartyMasterRepository;
import com.tiketbakend.tiket.repository.mysqldb.SaleHeadRepository;
import com.tiketbakend.tiket.repository.mysqldb.SaleItemsRepository;
import com.tiketbakend.tiket.util.Item;
import com.tiketbakend.tiket.util.ReactiveApiCall;
import com.tiketbakend.tiket.util.SalePurchase;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class SaleService {


    private static final String REDIS_CACHE_VALUE = "sale";
    //@Cacheable(value = REDIS_CACHE_VALUE)
    public List<SalePurchase> getAll(SaleHeadRepository headrepo, SaleItemsRepository itemrepo){
        List<SalePurchase> sp=new ArrayList<>();
        List<Item> items=new ArrayList<>();
        List<SaleHead> pheads=headrepo.findByDeleted(false);
        double amounts=0,qtys=0;
        for (SaleHead p:pheads) {
            List<SaleItems> it=itemrepo.findBysalehead_IdAndDeleted(p.getId(),false);
            for (SaleItems pi:it) {
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

    //@CachePut(value = REDIS_CACHE_VALUE, key = "#pt.id")
    public SalePurchase create(SalePurchase pt, SaleHeadRepository purcheadrepo, SaleItemsRepository purcitemrepo, PartyMasterRepository partyrepo, ItemMasterRepository itemrepo){
        SaleHead phead=new SaleHead();
        phead.setTotalquantity(pt.getTotalquantity());
        phead.setParty(new PartyMasterService().getById(pt.getPartyid(),partyrepo));
        phead.setTotalamount(pt.getTotalamount());
        phead.setDate(LocalDateTime.now().toString());
        phead.setGrandtotal(pt.getGrandtotal());
        phead.setOthers(pt.getOthers());
        phead.setTotalamount(pt.getTotalamount());

        phead=purcheadrepo.save(phead);

        for (Item it:pt.getItems()) {
            SaleItems pi=new SaleItems();
            pi.setAmount(it.getAmount());
            pi.setQuantity(it.getQuantity());
            pi.setRate(it.getRate());
            pi.setItemmaster(new ItemMasterService().getById(it.getItemid(),itemrepo));
            pi.setSalehead(phead);


            purcitemrepo.save(pi);
            Stock stk=new Stock(pi.getItemmaster().getId(),pi.getItemmaster().getItemgroup().getId(),pi.getAmount(),0,pi.getQuantity(), pi.getQuantity());
            ReactiveApiCall api=new ReactiveApiCall();
            api.makePutApiCall("/SaleItemId/"+pi.getItemmaster().getId(),stk);

        }


        return  new SaleService().getById(phead.getId(),purcheadrepo,purcitemrepo);
    }

   // @Cacheable(value = REDIS_CACHE_VALUE, key = "#Id")
    public SalePurchase getById( int Id,SaleHeadRepository headrepo, SaleItemsRepository itemrepo){
        List<Item> items=new ArrayList<>();
        SaleHead p=headrepo.findByDeletedAndId(false,Id);
        double amounts=0,qtys=0;

            List<SaleItems> it=itemrepo.findBysalehead_IdAndDeleted(p.getId(),false);
            for (SaleItems pi:it) {
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

//    @CacheEvict(value = REDIS_CACHE_VALUE, key = "#Id",allEntries = true)
    public SalePurchase updateById( int Id, SalePurchase pt,SaleHeadRepository purcheadrepo, SaleItemsRepository purcitemrepo, PartyMasterRepository partyrepo, ItemMasterRepository itemrepo){
        List<SaleItems> itm=purcitemrepo.findBysalehead_IdAndDeleted(Id,false);
        for (SaleItems pi:itm) {
            pi.setDeleted(true);
            purcitemrepo.save(pi);

            Stock stk=new Stock(pi.getItemmaster().getId(),pi.getItemmaster().getItemgroup().getId(),pi.getAmount(),0,pi.getQuantity(), pi.getQuantity());
            ReactiveApiCall api=new ReactiveApiCall();
            api.makePutApiCall("/Reset/SaleItemId/"+pi.getItemmaster().getId(),stk);
}
        SaleHead phead=purcheadrepo.findByDeletedAndId(false,Id);
        phead.setTotalquantity(pt.getTotalquantity());
        phead.setParty(new PartyMasterService().getById(pt.getPartyid(),partyrepo));
        phead.setTotalamount(pt.getTotalamount());
        phead.setDate(LocalDateTime.now().toString());
        phead.setGrandtotal(pt.getGrandtotal());
        phead.setOthers(pt.getOthers());
        phead.setTotalamount(pt.getTotalamount());

        phead=purcheadrepo.save(phead);

        for (Item it:pt.getItems()) {
            SaleItems pi=new SaleItems();
            pi.setAmount(it.getAmount());
            pi.setQuantity(it.getQuantity());
            pi.setRate(it.getRate());
            pi.setItemmaster(new ItemMasterService().getById(it.getItemid(),itemrepo));
            pi.setSalehead(phead);

            purcitemrepo.save(pi);

            Stock stk=new Stock(pi.getItemmaster().getId(),pi.getItemmaster().getItemgroup().getId(),pi.getAmount(),0,pi.getQuantity(), pi.getQuantity());
            ReactiveApiCall api=new ReactiveApiCall();
            api.makePutApiCall("/SaleItemId/"+pi.getItemmaster().getId(),stk);

        }

        return new SaleService().getById(phead.getId(),purcheadrepo,purcitemrepo);
    }
  //  @CacheEvict(value = REDIS_CACHE_VALUE, key = "#Id")
    public String deleteById( int Id,SaleHeadRepository purcheadrepo,SaleItemsRepository purcitemrepo){

        List<SaleItems> it=purcitemrepo.findBysalehead_IdAndDeleted(Id,false);
        for (SaleItems pi:it) {
          pi.setDeleted(true);
          purcitemrepo.save(pi);

            Stock stk=new Stock(pi.getItemmaster().getId(),pi.getItemmaster().getItemgroup().getId(),pi.getAmount(),0,pi.getQuantity(), pi.getQuantity());
            ReactiveApiCall api=new ReactiveApiCall();
            api.makePutApiCall("/Reset/SaleItemId/"+pi.getItemmaster().getId(),stk);
        }
        SaleHead p=purcheadrepo.findByDeletedAndId(false,Id);
        p.setDeleted(true);
        purcheadrepo.save(p);

        return "with id :"+Id+" is deleted";
    }
}
