# TalentReef Java API Interview Template

## Description

This [Spring Boot](https://spring.io/projects/spring-boot) project is provided as a starting template for the TalentReef take-home interview. Feel free to make whatever modifications are necessary to complete the exercise.

## Requirements

Java 21 -- Java can be acquired using [SDKMAN!](https://sdkman.io/)

## Running the Application

Start the server using Gradle:

```shell
./gradlew bootRun
```

See [Running your Application with Gradle](https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/htmlsingle/#running-your-application) for more information.

Execute tests using Gradle:

```shell
./gradlew test
```

## Additional Information

TalentReef will provide you the contact information of a person who can answer questions about the exercise.

--------------------------------

## Updated

The `Widget` model was updated to include `description` and `price` with the following contrains:

* `name` The unique identifier for the widget: 
    * Constraints:
        * Required.
        * It is a String between 3 and 100 characters and must be non-null.
     
* `description` A text description of the widget: 
    * Constraints:
        * Required.
        * It is a String between 5 and 1000 characters and must be non-null.

* `price` The price of the widget: 
    * Constraints:
        * Required.
        * Must be a positive value between 1 and 20000

## Postman collection

This repository includes a Postman collection (in the root of the repository) to test the APIs, the file is called `Mitratech Interview APIs.postman_collection.json`

![image](https://github.com/user-attachments/assets/300eec2b-843f-4597-bed6-8039ee7cfd23)

Using this collection to test the fields contraints:

* Testing when the name does not meet the requirements
* ![image](https://github.com/user-attachments/assets/a8aaae17-0292-494f-a24b-7be3d3a8bc2f)

* Testing when a widget already exists
* ![image](https://github.com/user-attachments/assets/9fb1ef41-3673-4b10-bf10-cffa427961ca)

Other field contrains can be tested too for the `description` and `price` fields.

## Custom Exceptions

Added a custom exception called `WidgetAlreadyExistsException` to handle cases where a widget with the same name already exists in the system.

## Tests

Added test for the controller and service files, also created a new file for testing the repository too.
