Source for pagination: https://www.javaguides.net/2022/02/spring-data-jpa-pagination-and-sorting.html

Source for the method clearDatabase in MachineControllerTests, PartControllerTests and SubassemblyControllerTests: 
https://maciejwalkowiak.com/blog/spring-boot-flyway-clear-database-integration-tests/

For testing controller classes for address, machine, part and subassembly, we use a sql-script (found under resources).
By using TestRestTemplate we test both integration and end-to-end.