# Java Spring Boot API & PostgreSQL Warehouse Management

This is a warehouse management system built using Java Spring Boot and PostgreSQL. It allows users to manage their warehouse by adding, updating, and deleting items, as well as tracking the shipping in and out of the warehouse. The system also provides features for managing shippers.

## Convirgance Conversion

The codebase as [originally written](https://github.com/wanrif/warehouse-management-backend) by [@wanrif](https://github.com/wanrif/) uses **JPA** and **Lombok** for ORM services. It was written as a near-ideal example of an ORM use case. This is a conversion of that code to **Convirgance** for purposes of demonstrating productivity gains.

The resutling conversion drops the Java code from 22 classes down to 9 classes and from 351 lines of code to 257 lines of code. (LoC metrics are in a comparable NCSS form.)

*This represents a* **59% drop** *in class files and* **35% drop** *in lines of code!*

In addition, the codebase received *transactional safety* in the conversion ensuring no partial commits. While this is a nice to have for an example project like this, it is a hard requirement for production applications. 

## Features

The following are the main features of this warehouse management system:

- **Items**: CRUD operations for managing items in the warehouse, including adding new items, updating existing items, retrieving all items, and deleting items.
- **Shippers**: CRUD operations for managing shippers who transport items to and from the warehouse, including adding new shippers, updating existing shippers, retrieving all shippers, and deleting shippers.
- **Shipping In**: Users can record the arrival of items to the warehouse by specifying the item, the quantity, and the shipper who delivered the items.
- **Shipping Out**: Users can record the departure of items from the warehouse by specifying the item, the quantity, and the shipper who received the items.

## Tech Stack

The following technologies were used to build this warehouse management system:

- **Java Spring Boot**: A powerful framework for building web applications in Java.
- **PostgreSQL**: A powerful open-source relational database management system.
- **Maven**: A build automation tool for Java projects.
- **Convirgance**: A SQL-based tool for database interaction and data tranformation.
