# Web UI automation framework
This project is built using Maven and designed to run automated UI tests using Selenide. It includes dependencies for logging, reporting, and test execution with TestNG and Allure Report

## Table of Contents
1. [Features](#features)
2. [Requirements](#requirements)
3. [Set up](#setup-installation)
4. [Run Test](#run-tests)
5. [Reports](#report)
6. [Dependencies](#dependencies)
7. [Profile](#profile)
8. [Additional Information](#additional-information)

## Features
| Feature                                     | Status                                         | Note                                 |
|---------------------------------------------|------------------------------------------------|--------------------------------------|
| Selenide FW                                 | <span style="color: green;">Implemented</span> | JAVA 21, Selenide 7.6.1              |
| Reports with HTML, Allure and Report Portal | <span style="color: green;">Implemented</span> | Report Portal is not implemented yet |
| Test retry                                  | <span style="color: green;">Implemented</span> |                                      |
| Parallel/distributed testing                | <span style="color: red;">Not Implemented</span> |                                      |
| Cross browsers testing                      | <span style="color: green;">Implemented</span> | Microsoft Edge, Google Chrome        |
| Selenium Grid/Shard                         | <span style="color: red;">Not Implemented</span> |                                      |
| CI                                          | <span style="color: red;">Not Implemented</span> |                                      |
| Content testing                             | <span style="color: green;">Implemented</span> |                                      |
| Multiple languages testing                  | <span style="color: cyan;">In - Progress</span> |                                      |
| Group tests by purposes                     | <span style="color: red;">Not Implemented</span> |                                      |
| Source control practice                     | <span style="color: cyan;">In - Progress</span> |                                      |
| Switch test environment                     | <span style="color: green;">Implemented</span> |                                      |
| Wrap custom controls                        | <span style="color: green;">Implemented</span> |                                      |

## Requirements
- Java 21: Ensure that you have JDK 21 installed.
- Maven: This project uses Maven for dependency management and build automation.

## Setup, Installation
Clone the repository and navigate to the project directory:

```sh
git clone https://github.com/your-repository/Selenide_AnDuong.git
```

## Run Tests
This command will run the default suite
```sh
mvn clean test
```
This command will execute the tests based on the default profile (local_chrome_prod_en).

## Report
After running the tests, you can generate the Allure report using the following command:
```
mvn allure:report
```
To serve the report locally:
```
mvn allure:serve
```

## Dependencies
The project includes the following main dependencies:
- Selenide: For browser automation
- TestNG: For test execution
- Allure: For test reporting
- AspectJ Weaver: For supporting aspect-oriented programming
- Lombok: For reducing boilerplate code
- SLF4J: For logging
- Log4j: For logging
- Jackson Databind: For JSON processing

## Profile
The project is configured with a Maven profile to handle different environments and locales. The default profile is local_chrome_prod_en.
- Default Profile: local_chrome_prod_en
- Environment: Production
- Locale: English
To activate a different profile, you can use the following command:
```
mvn clean test -P<profile-id>
```
Replace <profile-id> with the desired profile ID.

## Project Structure
```
src
├── main
│   └── java
│       └── common
│       └── configuration
│       └── dataprovider
│       └── element
│       └── enum
|
└── test
    └── java
         └── dataloader
         └── dataobjects
         └── listener
         └── page
             └── vietjet
         └── testcase
             └── vietjet           
         └── resources
└── suites
└── test_suite.xml
└── config
└── prod.properties
```

## Additional Information
For more details on using Selenide, TestNG, and Allure, please refer to their official documentation:


- [Selenide Documentation](https://selenide.org/documentation.html)
- [TestNG Documentation](https://testng.org/doc/)
- [Allure Documentation](https://docs.qameta.io/allure/)

```
This README file provides an overview of the project, setup instructions, details on running tests 
```

