# Ubuntu 20.04.2 LTS with OpenJDK version 11.0.12
FROM adoptopenjdk/openjdk11

# copy Jar into image
COPY target/ems.jar /app/ems.jar
COPY data/id/sequence.number data/id/sequence.number
COPY data/xml/employees.xml data/xml/employees.xml

EXPOSE 8080

# Start the Exchange Rate Service
CMD java -jar /app/ems.jar