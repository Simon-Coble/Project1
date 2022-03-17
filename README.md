# Project1

## Keywords
@Entity
@Id
@column

## User documentation
1. Pull the dependancies over into the desired project.

2. Create a properties file with the url, username, password. Enter in the properties file location as a string call to Configuration constructor. ex ... new Configuration ("C:\\Users\\mkolb\\Desktop\\New folder\\Project1SV\\src\\main\\resources\\application.properties");

3. After a class has been created mark the class with the @Entity annotation along with @Id for the primary key value and column for the rest of the values

4. Create an instance of DaoImpl to call the methods inside the ORM

:Use findAllClasses to create tables

:Use insert to take in an object to insert a value to the database

:Use findAll to take in an object to return a list of all the objects in a table

:Use selectById to take in an object and an id to return an object from the database with the id

:Use Select allByValueInColumn to pass 2 Strings and an object to select a single data

:Use updateSingle to take in an object and update a single row in the database

:Use deleteById to take in an object and an id to delete a single row in a database
