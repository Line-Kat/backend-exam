CODE STRUCTURE
main
    controller (contains controller class for each domain)
    model (contains domain class for each domain)
    repository (contains repository class for each domain)
    service (contains service class for each domain)
test
    each domain has a folder with a file with tests for controller class and a file with tests for service class 
    under resources is a folder with sql scripts used in the controller tests


CONTROLLER CLASSES
Each controller class has methods for get, getAll with pagination, update, create and delete

ADDITIONAL FUNCTIONALITY
Customer
    addAddressToCustomer()
    createCustomerWithAddress()

TESTS
Use @SpringBootTest in all test classes
Use @DirtiesContext. It tells the testing framework to close and recreate the context for later tests
Use @Sql so the sql script runs before the test is executed
Use TestRestTemplate to make a REST call to the server
Use @MockBean to mock out parts of the code when testing
Use MockMvc for end-to-end-testing

    
SOURCES
Source for pagination: https://www.javaguides.net/2022/02/spring-data-jpa-pagination-and-sorting.html

Source for the method clearDatabase in MachineControllerTests, PartControllerTests and SubassemblyControllerTests: 
https://maciejwalkowiak.com/blog/spring-boot-flyway-clear-database-integration-tests/