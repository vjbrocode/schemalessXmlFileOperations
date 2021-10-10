# Getting Started

Solution is written in Java to implement the following functionalities:  
- Read the XML file.  
- Add new or delete existing employees.  
- Write to the XML file.  

The main inspiration for this solution is taken from MongoDB Schemaless collections feature and is implemented for XML payloads (instead of json/bson).

This solution provides a generic solution to any new node (for e.g., Qualifications consisting of UG, PG & certifications) added to this XML file in future.


###Steps to compile, run, and test the project
###Compile
```sh
cd <project root where pom.xml file is present>
mvn clean install  
```

###Run Employee Management Service (ems) jar
```sh 
java -jar target/ems.jar
```

###Access SWAGGER-UI
```sh
http://localhost:8080/swagger-ui/index.html#/
```

##OR You can use Docker to run this App 

###Build docker image
```sh
cd <project root>
chmod +x build_docker_image.sh 
./build_docker_image.sh 
docker images
```

###Run as docker container
```sh
docker run -d --name ems -p 8080:8080 ems:1.0 
docker ps -a
```


###Sample Payloads:
Refer ApiDocumentation.txt file for more details or Use Swagger (http://localhost:8080/swagger-ui/index.html#/)
###Add one employee with name, age, and designation.  
```sh
<employee>
    <name>Mark</name>
    <age>25</age>
    <designation>Developer</designation>
</employee>
```

###Add one employee with name, age, designation, and address (generic solution supporting a new node).  
```sh
<employee>
    <name>Elsie</name>
    <age>35</age>
    <designation>Project Manager</designation>
    <address>
        <doorNo>20</doorNo>
        <street>Hibiscus Way</street>
        <town>Tustin</town>
        <state>Texas</state>
    </address>
</employee> 
```
###Add one employee with name, age, designation, address, and qualifications (generic solution supporting any new node).  
```sh
<employee>
    <name>Vijay</name>
    <age>29</age>
    <designation>Software Engineer III</designation>
    <address>
        <doorNo>14</doorNo>
        <street>Steet B</street>
        <town>George Town</town>
        <state>Cayman Islands</state>
    </address>
    <qualifications>
        <ug>Cusat</ug>
        <pg>MIT</pg>
        <certifications>
            <certification>Cisco</certification>
            <certification>Microsoft</certification>
        </certifications>
    </qualifications>
</employee>  
```

Not included Yet:  
Unit testing  
Functional testing  
