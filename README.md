# Seckil System
(1) Background: Seckill has  become popular in the online shopping community to define the rapid sell out of newly advertised goods.Use spring+springMVC+myBatis framework. A list page shows the start time and end time for commodities that could be seckilled. User could seckill the goods after the specific start time with phone number, and first comes first served.

(2) DAO: Use mybatis framework, and mysql for database. One table for commodities information, one table for successfully seckill action to save user’s information. Mapper dynamic proxy access to the database. Implementation details: 1. Expose seckill interface. 2.the implementation of seckill operation. 3. Related inquiries (List query, detail page query),

(3) Service: Spring IOC help integrate Service and all the dependencies. Define the dto, entity, expectations package. Implementation details:1. Query all seckill records. 2. Query one specific seckill records. 3. When seckill starts outputs seckill interface address 4. implementate seckill execution.

(4) Web: Use Spring MVC, Controller development method is completely complying with the Service interface method for development. Each method corresponds to a resource in system URL, and the design should follow the Restful interface Url design style.

(5) Optimization: Use Redis Caching to decrease database query, and use Protostuff as the java serialization library.
