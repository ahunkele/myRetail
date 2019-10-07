# myRetail

Rest Service for myRetail.

# Technology used

Java 1.8, Mockito, JUnit

MongoDB

Maven

SpringBoot 2.1.4.RELEASE

Postman

# Running application

make sure mongoDB is running on port 27017


cd to myRetail 

Build project with following:
``mvn clean build``

Now run the jar with:
``java -jar target/myRetail-0.0.1-SNAPSHOT.jar``


Now the application can be accessed via 

http://localhost:8081/products/

# End points: 
- GET: /products/  grabs all products
- GET: /products/{id} grabs single product if found  
- POST: /products/add creates a new product  
- PUT: /products/{id} updates the product with new price
- DELETE: /products/{id} deletes the product  


Example requests:

Get request on http://localhost:8082/products/13860428 will result in:

``` json
{
    "id": 13860427,
    "name": "Conan the Barbarian (dvd_video)",
    "current_price": {
        "value": 1.99,
        "currency_code": "XOR"
    }
}
```

To update said pricing, we can perform a put on http://localhost:8082/products/13860428 with a request body:

``` json
{
        "value": 200.00,
        "currency_code": "USD"
}
```

with a response of:

``` json
{
    "id": 13860427,
    "name": "Conan the Barbarian (dvd_video)",
    "current_price": {
        "value": 200.00,
        "currency_code": "USD"
    }
}
```

#Possible enhancements

1. Enable Caching to not put so much load on the db. 
2. Setup polling sensors to refresh cache.
3. Enable OAuth2 to create authorizations on put and delete requests.
