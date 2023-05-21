# Music_Streaming-Service-API
This Application Based On MySQL Database

##### :purple_square: Its an API Based Web Application
## :one: Frameworks and Languages Used -
    1. SpringBoot
    2. JAVA
    3. Postman
    4. MySQL
    
## :two: Dependency Used
    1. Spring Web
    2. Spring Boot Dev Tools
    3. Lombok
    4. Spring Data JPA
    5. MySQL Connector
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
## :three: Dataflow (Functions Used In)
### :purple_square: 1. Model - Model is used to Iniitialize the required attributes and create the accessable constructors and methods
#### :o: Stock.java
```java
@Entity
@Table
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer stockId;

    private String stockName;
    private Double stockPrice;
    private Integer stockOwnerCount;

    @Enumerated(EnumType.STRING)
    private StockType stockType;
    private Double stockMarketCap;
    private LocalDateTime stockBirthDate;
}
```

##### To See Model
:white_check_mark: [Stock-Model](https://github.com/Anushri-glitch/Stock-Management-Application/tree/master/src/main/java/com/Shrishti/StockManagement/model)
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

### :purple_square: 2. Service - This Layer is used to write the logic of our CRUD operaions.
#### :o: StockService.java
```java
@Service
public class StockService {
    @Autowired
    IStockDao stockDao;
    
    @Transactional
    public String updateMarketCapById(Double capPercentage, Integer stockId) {
        stockDao.updateMarketCapById(capPercentage, stockId);
        return "MarketCap Updated!!!";
    }
}
```

#### To See Service
:white_check_mark: [Stock-Service](https://github.com/Anushri-glitch/Stock-Management-Application/blob/master/src/main/java/com/Shrishti/StockManagement/service/StockService.java)
----------------------------------------------------------------------------------------------------------------------------------------------------

### :purple_square: 3. Controller - This Controller is used to like UI between Model and Service and also for CRUD Mappings.
#### :o: StockController.java
```java
@RestController
public class StockController {

     @Autowired
     StockService stockService;

    //Add New Stocks
    @PostMapping(value = "/stock")
    private String addStock(@RequestBody List<Stock> stock){
        return stockService.addStock(stock);
    }
}
```

#### To See Controller
:white_check_mark: [Stock-Controller](https://github.com/Anushri-glitch/Stock-Management-Application/blob/master/src/main/java/com/Shrishti/StockManagement/controller/StockController.java)
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
### :purple_square: 3. Repository : data access object (DAO) is an object that provides an abstract interface to some type of database or other persistence mechanisms.
#### :o: IStockDao.java
```java
@Repository
public interface IStockDao extends JpaRepository<Stock,Integer> {
    List<Stock> findByStockType(StockType stockType);
    
    @Modifying
    @Query(value = "update stock set stock_type = :enumValue where stock_id = :stockId", nativeQuery = true)
    void modifyStockTypeById(String enumValue, Integer stockId);
}
```

#### To See Repository
:white_check_mark: [Stock-DAO](https://github.com/Anushri-glitch/Stock-Management-Application/blob/master/src/main/java/com/Shrishti/StockManagement/repository/IStockDao.java)
-------------------------------------------------------------------------------------------------------------------------------------------------------

## :four: DataStructures Used in Project
    1. List
    2. Iterable
-------------------------------------------------------------------------------------------------------------------------------------------------------
## :five: DataBase Response In project

:arrow_right: Stock table

```sql
 select * from stock;
+----------+----------------------------+------------------+-------------+-------------------+-------------+------------+
| stock_id | stock_birth_date           | stock_market_cap | stock_name  | stock_owner_count | stock_price | stock_type |
+----------+----------------------------+------------------+-------------+-------------------+-------------+------------+
|      352 | 2018-03-29 13:34:00.000000 |              670 | NariyalVala |                24 |        4000 | Health     |
|      353 | 2013-03-29 13:34:00.000000 |           50.065 | Nike        |                12 |         708 | Self       |
|      354 | 2022-03-29 13:34:00.000000 |          220.065 | Tesla       |                34 |        1020 | IT         |
|      402 | 2018-05-29 13:34:00.000000 |           12.065 | Airtel      |                45 |         200 | IT         |
|      403 | 2020-09-29 13:34:00.000000 |           90.065 | Tata        |                45 |         300 | Self       |
|      404 | 2023-03-29 13:34:00.000000 |        70000.065 | Mahindra    |              1000 |        2000 | IT         |
+----------+----------------------------+------------------+-------------+-------------------+-------------+------------+
```

:arrow_right: After Delete Stocks Based On Owner Count

```sql
 select * from stock;
+----------+----------------------------+------------------+------------+-------------------+-------------+------------+
| stock_id | stock_birth_date           | stock_market_cap | stock_name | stock_owner_count | stock_price | stock_type |
+----------+----------------------------+------------------+------------+-------------------+-------------+------------+
|      404 | 2023-03-29 13:34:00.000000 |        70000.065 | Mahindra   |              1000 |        2000 | IT         |
+----------+----------------------------+------------------+------------+-------------------+-------------+------------+
```

----------------------------------------------------------------------------------------------------------------------------------------------------------
## :six: Project Summary
### :o: Generated API's

:white_check_mark: ADD STOCK : https://localhost:8080/stock

:white_check_mark: GET STOCKS BASED ON STOCK TYPE : https://localhost:8080/api/v1/todo-app/findall

:white_check_mark: GET STOCK ABOVE PRICE AND LOWER STOCK_BIRTH_DATE : https://localhost:8080/abovePrice/price/{stockPrice}/lowerDate/date/{stockDate}

:white_check_mark: GET ALL STOCKS ABOVE MARKET CAP : https://localhost:8080/cap/{capPercentage}

:white_check_mark: UPDATE MARKET CAP BY STOCK ID : https://localhost:8080/cap/{capPercentage}/stockId/{stockId}

:white_check_mark: UPDATE STOCK TYPE BY STOCK ID : https://localhost:8080/type/{stocktype}/stockId/{stockId}

:white_check_mark: UPDATE STOCK BY STOCK ID : https://localhost:8080/stock/{stockId}

:white_check_mark: DELETE STOCKS BASED ON OWNER COUNT : https://localhost:8080/ownerCount/{ownerCount}

--------------------------------------------------------------------------------------------------------------------------------------------------

## :seven: Project Result
### :o: Stock Response

![Screenshot (819)](https://github.com/Anushri-glitch/Stock-Management-Application/assets/47708011/5dd95669-fa0a-4485-bb38-88b0ad45e5f7)

![Screenshot (820)](https://github.com/Anushri-glitch/Stock-Management-Application/assets/47708011/37650d22-700a-41e1-ac03-004ebb504ba3)

![Screenshot (821)](https://github.com/Anushri-glitch/Stock-Management-Application/assets/47708011/3a7e7e71-64f9-4237-abea-ae46d460ade7)

![Screenshot (822)](https://github.com/Anushri-glitch/Stock-Management-Application/assets/47708011/13b2bebf-8b44-44c4-a11c-c7dd746e7887)

![Screenshot (823)](https://github.com/Anushri-glitch/Stock-Management-Application/assets/47708011/295d124f-1b77-4498-a268-53b27584e5df)

![Screenshot (824)](https://github.com/Anushri-glitch/Stock-Management-Application/assets/47708011/a8f156b8-c035-4f20-b0ae-96262d76c6ab)

![Screenshot (825)](https://github.com/Anushri-glitch/Stock-Management-Application/assets/47708011/da6503a9-c113-4185-8992-5694c630adb3)

![Screenshot (826)](https://github.com/Anushri-glitch/Stock-Management-Application/assets/47708011/83b56ed5-3484-4692-b3ca-c7cd457cdeba)





-----------------------------------------------------------------------------------------------------------------------------------------------------





