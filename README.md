# Ldap Connector to management user in ldap
[![N|Solid](http://www.dxnet.io/dxnet-logo.png)](https://dxnet.io)

## REST API project using Spring Boot, H2, Ldap Connector to management user in ldap

###  Steps to Setup
**1. Clone the application**
[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://github.com/josemmarneca/ManagementUsersLdap.git)
```
https://github.com/josemmarneca/ManagementUsersLdap.git
```
**2. Configure application properties with ldap properties**

+ open `src/main/resources/application.properties`

+ change 
```  
        ldap.url=ldap://ip:port
        ldap.base=DC=dxnet,DC=lab
        ldap.principal=CN=User Admin,OU=Group
        ldap.password=123456789
        ldap.referral=follow
        ldap.load.groups=CN=Users,OU=Persons
        ldap.load.filter=person,user
```

**4. Build and run the app using maven**

```bash
mvn package
java -jar target/ldap-connector-0.0.1-SNAPSHOT.jar
```

Alternatively, you can run the app without packaging it using -

```bash
mvn spring-boot:run
```

The app will start running at <http://localhost:8095>.
## Explore Rest APIs

The app defines following CRUD APIs. 
 + USER 
    ```
    GET /api/v1/user/get/all
    
    GET /api/v1/user/get/{userName}
    
    POST /api/v1/user/create
    
    POST /api/v1/user/login
    
    PUT /api/v1/user/update
    
    DELETE /api/v1/user/delete
    ```

 + LDAP 
    ```
    GET /api/v1/ldap/get/all/group/{group}
    
    GET /api/v1/ldap/get/user/{userDn}
    
    PUT /api/v1/ldap/update/user
    
    DELETE /api/v1/ldap/delete/user

    ```
    

You can find the properties in LDAP
http://www.kouti.com/tables/userattributes.htm




 

 

 


### Development

Want to contribute? Great!

Dillinger uses Gulp + Webpack for fast developing.
Make a change in your file and instantanously see your updates!

Open your favorite Terminal and run these commands.

First Tab:
```sh
$ node app
```

Second Tab:
```sh
$ gulp watch
```

(optional) Third:
```sh
$ karma test
```
 
