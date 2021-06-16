####Tools needed:
* MySQL workbench
* MySQL server
* Java (>= 8.0)
* Maven (>= 3.3)

####Steps to start the application:
1) Create __gps__ schema table
2) Run __add_default_roles.sql__ query using 'gps' schema.
3) Run __add_default_admin.sql__ query using'gps' schema. (for creating default admin user)
4) __mvn clean install__
5) __mvn spring-boot:run__