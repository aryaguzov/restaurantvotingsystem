[![Codacy Badge](https://app.codacy.com/project/badge/Grade/43241cae145a4f87af4b055492bc03ed)](https://www.codacy.com/gh/aryaguzov/restaurantvotingsystem/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=aryaguzov/restaurantvotingsystem&amp;utm_campaign=Badge_Grade)
# Restaurant Voting System
# Idea
The main idea is to create a voting system for deciding where to have lunch at. 
# How it works
* Admin adds a restaurant and its lunch menu of the day
* Admin changes a menu for each restaurant every day
* User can look at all restaurants and its lunch menu of the day
* User can vote for a chosen restaurant before 11:00
* User can update its vote before 11:00
# Technological stack
* Java 15
* SpringBoot
* Spring Data
* Spring Security
* H2 Database
* Hibernate
* Lombok
* JUnit
# cURL commands for testing REST API
## Users
### Note: only an admin has access to the following resources.
* ####  GET api/v1/admin/users - return a list of all users
       * curl localhost:8081/api/v1/admin/users -u admin:password
* ####  GET api/v1/admin/users/{id} - return a user with id
       * curl localhost:8081/api/v1/admin/users/10000 -u admin:password
* ####  GET api/v1/admin/users/by?name={name} - get a user with the name
       * curl 'localhost:8081/api/v1/admin/users/by?name=admin' -u admin:password
* ####  GET api/v1/admin/users/with?email={email} - get a user with the email
       * curl 'localhost:8081/api/v1/admin/users/with?email=user@gmail.com' -u admin:password
* ####  DELETE api/v1/admin/users/{id} - delete a user with id
       * curl -X DELETE localhost:8081/api/v1/admin/users/10000 -u admin:password
* ####  POST api/v1/admin/users/ - create a user
       * curl -X POST localhost:8081/api/v1/admin/users -H 'Content-type:application/json' -d '{"name":"userName","email":"username@email.com","password":"password","roles":["USER"]}' -u admin:password
* ####  PUT api/v1/admin/users/{id} - update a user
       * curl -X PUT localhost:8081/api/v1/admin/users/10002 -H 'Content-type:application/json' -d '{"name":"newName","email":"newemail@gmail.com","password":"newPassword","roles":["USER"]}' -u admin:password
### Note: only an authorized user has access to the following resources.
* ####  GET api/v1/profile - return an authorized user
       * curl localhost:8081/api/v1/profile -u user:password   
* ####  DELETE api/v1/profile - delete an authorized user
       * curl -X DELETE localhost:8081/api/v1/profile -u user:password
* ####  PUT api/v1/profile - update an authorized user
       * curl -X PUT localhost:8081/api/v1/profile -H 'Content-type:application/json' -d '{"name":"newName","email":"newemail@gmail.com","password":"newPassword","roles":["USER"]}' -u user:password
## Restaurants
### Note: only an admin has access to the following resources.
* ####  GET api/v1/admin/restaurants - return a list of all restaurants
       * curl localhost:8081/api/v1/admin/restaurants -u admin:password
* ####  GET api/v1/admin/restaurants/{id} - return a restaurant with id
       * curl localhost:8081/api/v1/admin/restaurants/10005 -u admin:password
* ####  DELETE api/v1/admin/restaurants/{id} - delete a restaurant with id
       * curl -X DELETE localhost:8081/api/v1/admin/restaurants/10005 -u admin:password
* ####  POST api/v1/admin/restaurants/ - create a restaurant
       * curl -X POST localhost:8081/api/v1/admin/restaurants -H 'Content-type:application/json' -d '{"name":"name","contacts":"contacts"}' -u admin:password
* ####  PUT api/v1/admin/restaurants/{id} - update a restaurant
       * curl -X PUT localhost:8081/api/v1/admin/restaurants/10006 -H 'Content-type:application/json' -d '{"name":"newName","contacts":"newContacts"}' -u admin:password
### Note: only an authorized user has access to the following resource.
* ####  GET api/v1/restaurants - return a list of all restaurants with its menu
       * curl localhost:8081/api/v1/restaurants -u user:password
## Menus
### Note: only an admin has access to the following resources.
* ####  GET api/v1/admin/restaurants/{id}/menus - return a list of all menus for a restaurant
       * curl localhost:8081/api/v1/admin/restaurants/10005/menus -u admin:password
* ####  GET api/v1/admin/restaurants/menus?date={date} - return a list of menus for the date
       * curl 'localhost:8081/api/v1/admin/restaurants/menus?date=2021-03-24' -u admin:password
* ####  GET api/v1/admin/restaurants/{id}/menus - return a list of all menus for a restaurant
       * curl localhost:8081/api/v1/admin/restaurants/10005/menus -u admin:password
* ####  DELETE api/v1/admin/restaurants/{id}/menus/{id} - delete a menu with id
       * curl -X DELETE localhost:8081/api/v1/admin/restaurants/10005/menus/10010 -u admin:password
* ####  POST api/v1/admin/restaurants/{id}/menus - create a menu
       * curl -X POST localhost:8081/api/v1/admin/restaurants/10005/menus -H 'Content-type:application/json' -d '{"date":"2021-03-03","dishes":[{"name": "Pizza","price": 100},{"name": "Bread","price": 10}]}' -u admin:password
* ####  PUT api/v1/admin/restaurants/{id}/menus/{id} - update a menu
       * curl -X PUT localhost:8081/api/v1/admin/restaurants/10006/menus/10011 -H 'Content-type:application/json' -d '{"date":"2021-04-04","dishes":[{"name": "NewPizza","price": 1100},{"name": "NewBread","price": 100}]}' -u admin:password
## Vote
### Note: only an authorized user has access to the following resources.
* ####  GET api/v1/votes/by?date={date} - return a list of votes for the date
       * curl 'localhost:8081/api/v1/votes/by?date=2021-03-25' -u user:password
* ####  POST api/v1/restaurants/{id}/votes - create a vote
       * curl -X POST localhost:8081/api/v1/restaurants/10005/votes -H 'Content-type:application/json' -u user:password
* ####  PUT api/v1/restaurants/{id}/votes/{id} - update a vote
       * curl -X PUT localhost:8081/api/v1/restaurants/10007/votes/10031 -H 'Content-type:application/json' -d '{"date":"2021-03-27","userId":10002}' -u user:password
