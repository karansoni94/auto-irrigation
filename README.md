This project has been developed using Java11, Spring-Boot and H2(Testing Purpose) Database.

You can clone the repository and run the project using following command:
`mvn spring-boot:run
`
It includes swagger documentation which can be used to interact with the service. The basic workflow to test the
application would be something like mentioned below:

1. Create a Plot 
    - `{
        "plotName": "Farm in Ahmedabad",
        "totalArea": 10000,
        "address": "Ahmedabad"
        }`
2. Create an Irrigation-Config for the created plot (Try to use time 2-5 minutes in future)
   - `{
     "cropName": "Wheat",
     "startTime": "23:00:00",
     "endTime": "23:05:00",
     "intervalValue": 2,
     "intervalUnit": "DAYS"
     }`
3. Hit sensor-notifications API to see when the sensor was triggered
   - API will continuously provide with the updates for the sensor.
4. SwaggerUI is accessible at the following URL when you run it without any changes in local: http://localhost:8080/api/swagger-ui/index.html