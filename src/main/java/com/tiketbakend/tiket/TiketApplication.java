package com.tiketbakend.tiket;

import com.tiketbakend.tiket.inventorymongodb.model.stock;
import com.tiketbakend.tiket.inventorymongodb.repository.StockRepository;
import com.tiketbakend.tiket.inventorymysql.model.itemgroupmaster;
import com.tiketbakend.tiket.inventorymysql.model.itemmaster;
import com.tiketbakend.tiket.inventorymysql.model.partymaster;
import com.tiketbakend.tiket.inventorymysql.repository.*;
import com.tiketbakend.tiket.inventorymysql.service.purchaseservice;
import com.tiketbakend.tiket.inventorymysql.service.saleservice;
import com.tiketbakend.tiket.inventorymysql.util.Item;
import com.tiketbakend.tiket.inventorymysql.util.SalePurchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication(scanBasePackages ="com.tiketbakend.tiket.inventorymysql")
//@ComponentScan(basePackages = { "com.tiketbakend.tiket.inventorymysql","com.tiketbakend.tiket.inventorymongodb"} )
//@ComponentScan({"com.tiketbakend.tiket.inventorymysql"})
//@EntityScan({"com.tiketbakend.tiket.inventorymysql.model"})
//@EnableJpaRepositories("com.tiketbakend.tiket.inventorymysql.repository")
@EnableMongoRepositories(basePackages = "com.tiketbakend.tiket.inventorymongodb")
public class TiketApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TiketApplication.class, args);
	}


	@Autowired
	private PartyMasterRepository partymasterrepo;
	@Autowired
	private ItemMasterRepository itemmasterrepo;
	@Autowired
	private ItemGroupMasterRepository itemgroupmasterrepo;
	@Autowired
	private PurchaseHeadRepository purchaseHeadRepository;
	@Autowired
	private PurchaseItemsRepository purchaseItemsRepository;
	@Autowired
	private SaleHeadRepository saleHeadRepository;
	@Autowired
	private SaleItemsRepository saleItemsRepository;
    @Autowired
	private StockRepository stockRepository;

	@Override
	public void run(String... args) throws Exception {
		if (partymasterrepo.count() == 0) {
			partymaster party = new partymaster();
			party.setPartname("Ram");
			party.setAddress("148,Stno 2,New Hira Nagar");
			party.setCity("Ludhiana");
			party.setEmailid("Ram@tiket.com");
			party.setPhoneno("1234567890");
			party.setPincode("141007");

			partymasterrepo.save(party);

		}
		if (itemgroupmasterrepo.count() == 0) {
			itemgroupmaster itemgroup = new itemgroupmaster();
			itemgroup.setGroupname("Winter Cloth");
			itemgroupmasterrepo.save(itemgroup);
		}
		if (itemmasterrepo.count() == 0) {
			itemgroupmaster ig = itemgroupmasterrepo.findById(1).orElse(null);

			itemmaster item = new itemmaster();
			item.setItemgroup(ig);
			item.setItemname("Coat");
			item.setPurcrate(600);
			item.setSalerate(800);
			item.setUnits("PCS");
			itemmasterrepo.save(item);
		}

		if (purchaseHeadRepository.count() == 0) {
			SalePurchase sp = new SalePurchase();
			sp.setTotalquantity(10);
			sp.setOthers(200);
			sp.setGrandtotal(1200);
			sp.setTotalamount(1000);
			sp.setPartyid(1);
			List<Item> it = new ArrayList<Item>();
			Item i = new Item();
			i.setQuantity(10);
			i.setRate(100);
			i.setItemid(1);
			i.setAmount(1000);
			it.add(i);
			sp.setItems(it);

			new purchaseservice().create(sp, purchaseHeadRepository, purchaseItemsRepository, partymasterrepo, itemmasterrepo);
		}

		if (saleHeadRepository.count() == 0) {
			SalePurchase sp = new SalePurchase();
			sp.setTotalquantity(5);
			sp.setOthers(200);
			sp.setGrandtotal(700);
			sp.setTotalamount(500);
			sp.setPartyid(1);
			List<Item> it = new ArrayList<Item>();
			Item i = new Item();
			i.setQuantity(5);
			i.setRate(100);
			i.setItemid(1);
			i.setAmount(500);
			it.add(i);
			sp.setItems(it);

			new saleservice().create(sp, saleHeadRepository, saleItemsRepository, partymasterrepo, itemmasterrepo);
		}
		if(stockRepository.count()==0)
		{
			stockRepository.save(new stock(1,1,500,100,5,5));
		}
	}
}