```bash
# TiketAssignent
Inventory Management MVC with spring boot using Mysql and MongoDB

Reactive Api not implemented yet

It is a 3 tier architecture with file structure as :

com.tiketbakend.tiket
                 ├───controller
                 ├───exception
                 ├───model
                 │   ├───mongodb
                 │   └───mysqldb
                 ├───repository
                 │   ├───mongodb
                 │   └───mysqldb
                 ├───service
                 └───util



controller  
        |-> │       ItemController.java
            │       ItemGroupController.java
            │       PartyController.java
            │       PurchaseController.java
            │       SaleController.java
            │       StockController.java
 
 All these controller except StockController define methods :
 
 getAll()       for getting all the entites
 create()       for post methods with repective modals as RequestBody
 getByID()      getting record by key associated with it  named as Id (PathVariable) 
 updateById()   for put method with key named as Id (PathVariable) and repective modal as RequestBody
 deleteById()   This is used for soft delete operation with key named as Id (PathVariable).In all these entites 
                i use some common fields named as creationdate,updationdate,deleted(bool) and Id as key in each entity.
                
 As we used soft delete so we cannot get desired result with repository.findAll() method.So i define derived Query method in each repostiory
 like List<Entity> findByDeleted(boolean delete); with this i can get all rows where Deleted is false
 Also we use foreign Keys in ItemMaster,PurchaseHead,PurchaseItems,SaleHead,SaleItems so in these repositories i use derived queries method as
 List<PurchaseItems> findBypurchasehead_IdAndDeleted(int Id, boolean delete);
  
 findBypurchasehead_IdAndDeleted ====> findBy ,purchasehead->Id and Deleted 
 ==>
 select * from purchaseItems where purchasehead.Id=passedId and   deleted=passedbooleanvalue
  
  
  My Entites are like :
  
  Table: itemgroupmaster
    Columns:
    id int AI PK 
    creation_date varchar(255) 
    deleted bit(1) 
    groupname varchar(50) 
    updtion_date varchar(25)
 
  
  Table: itemmaster
    Columns:
    id int AI PK 
    creation_date varchar(255) 
    deleted bit(1) 
    itemname varchar(50) 
    purcrate double 
    salerate double 
    units varchar(10) 
    updtion_date varchar(255) 
    itemgroupid int FK
  
  Table: partymaster
    Columns:
    id int AI PK 
    address varchar(100) 
    city varchar(50) 
    creation_date varchar(255) 
    deleted bit(1) 
    emailid varchar(50) 
    partname varchar(50) 
    phoneno varchar(10) 
    pincode varchar(10) 
    updtion_date varchar(255)
  
  
  Table: purchasehead                                        
    Columns:
    id int AI PK 
    creation_date varchar(255) 
    date varchar(255) 
    deleted bit(1) 
    grandtotal double 
    others double 
    totalamount double 
    totalquantity double 
    updtion_date varchar(255) 
    partyid int FK
  
  Table: purchaseitems
    Columns:
    id int AI PK 
    amount double 
    creation_date varchar(255) 
    deleted bit(1) 
    quantity double 
    rate double 
    updtion_date varchar(255) 
    itemid int 
    purchaseheadid int
  
  
  Table: salehead
    Columns:
    id int AI PK 
    creation_date varchar(255) 
    date varchar(255) 
    deleted bit(1) 
    grandtotal double 
    others double 
    totalamount double 
    totalquantity double 
    updtion_date varchar(255) 
    partyid int
  
  
  Table: saleitems
    Columns:
    id int AI PK 
    amount double 
    creation_date varchar(255) 
    deleted bit(1) 
    quantity double 
    rate double 
    updtion_date varchar(255) 
    itemid int 
    saleheadid int
  
  
  In this project PartyMaster,ItemMaster,ItemGroupMaster,Purchase(PurchaseHead,PurchaseItems) and Sale(SaleHead,SaleItems) are associated with mysql database
  
  and 
  
  Stock with MongoDb
  
  Stock Document in MongoDb as
{
  "_id": 0,
  "itemid": 1,
  "itemgroupid": 1,
  "saleamount": 500,
  "purcamount": 325100,
  "quantitysold": 5,
  "quantityinstock": 455,
  "_class": "com.tiketbakend.tiket.inventorymongodb.model.stock"
}

```
