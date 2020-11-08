# Java Web Services

## Section 10

In this section, Bharath Thippireddy in his Udemy course, Java Web Services, goes over the five (5) steps for creating a 
Java SOAP client from scratch.

This repository will follow along each step. Each branch will reflect the completed portion, including the assignment in
Lesson 99. `master` will reflect the full working project.

### Repository Notes

Bharath uses Eclipse throughout his course. I'm neither a fan nor user of Eclipse. This repository and the instructions 
that follow will use [JetBrains's IntelliJ IDEA](https://www.jetbrains.com/idea/download/) 2020.2.3 (or a version similar). 
[NetBeans 12](https://netbeans.apache.org/download/nb120/nb120.html) proved to be similarly capable, but the Spring Boot
Initializr integration was missing at the time of the project creation. Netbeans 12 Maven integration for Spring Boot 
archetypes may have proven useful, but not tested. The [Spring Boot Initializr](https://start.spring.io/) website is a 
great alternative for tools lacking direct integration.

### Overview

Each step will be as detailed as necessary to reflect the work performed on the branch and consumed by `master`.

1. Lesson 94 — Introduction
1. Lesson 95 — Create the Java Client Project
1. Lesson 96 — Retrieve the WSDL
1. Lesson 97 — Generate the Stubs
1. Lesson 98 — Implement the Client
1. Lesson 99 — Assignment
1. Lesson 100 — Flow, and the Service Provider Mechanism
1. Lesson 101 — Section Summary

## Lesson 94 — Introduction

Bharath reviews the expectations of the section. The Java SOAP Client will be created in the following five (5) steps: 

1. Create the Project
1. Copy the WSDL
1. Generate the Stubs
1. Create the client
1. Run the application 

## Lesson 95 — Create the Java Client Project

This project did not follow the specifics laid out by Bharath, but the intent. The steps are reflected in the 
repository commits.

1. Create a new Spring Initializr project. 

The details of the IntelliJ dialog is an exercise left for the reader, but the important elements are noted below.

For the Spring Initializr Java Version, I choose **JDK 11**. This creates a Maven `<properties>` element of 
`<java.version>11</java.version>`. The Spring Initializr at the time of this project allowed for Java versions of 8, 11,
and 15.

The initial `git` commit reflects the generated code by Spring Initializr. The project was cleaned up throughout the 
section lessons. The following "polish" to the project reflects some of my personal choices and goals. YMMV.

For the project's properties or structure metadata, I choose to use **JDK 14**. YMMV.

**Project Polish**

1. Rename the `HELP.md` to `README.md`.
1. Strike the Maven Wrapper support. There is possibly some value here, but it is not clearly outlined in the Spring
documentation.
1. Clean up the `.gitignore`. There a number of **negated** (`!`) excludes in the generated `.gitignore`. I strike the 
`README.md` (or `HELP.md` depending on how the refactoring is performed), Maven Wrapper content, and the negated 
excludes for packages that may include the string `target` or `build` in them; the former probably only of value to 
employees of Target Brands, Inc. and the latter NetBeans. If there is some concern about either, the option to comment 
them out exists as well.

**POM Polish**

1. Strike the `<relativePath>` element in the parent POM. It is extraneous.
1. Create the project source encoding property, `<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>`.
1. Create a `<cxf.version>` property and set it `3.4.0` (the current release at the time of this writing).
1. Set the `spring-boot-maven-plugin` version to `${project.parent.version}`.

**Lesson Notes**

Bharath uses Java 8 thoughout the section. However, note that Bharath provides information early in the course to use 
newer versions of the JDK.

Bharath sets the Apache CXF for JAXWS dependency. Use the property `${cxf.version}` after pasting the dependency 
from the previous section's project.

There is a bit of a deviation here that is initially confusing. Bharath walks us through retrieving the WSDL from the 
WSDL First Service section lessons and creates a `wsdl` directory in `src/main/resources`. This part is unnecessary as 
we will be retrieving it directly from the running service in Lesson 96.

## Lesson 96 — Retrieve the WSDL

Bharath immediately begins retrieving the WSDL from the WSDL First project in Section 9. To do so, start the service
from your IDE or on the command line.

Bharath masterfully runs most of these projects from Eclipse. To do so in IntelliJ or another IDE or editor, locate and
execute the `spring-boot:run` goal provided by the Spring Boot Maven **Plugin**. The plugin is provided by default in the
generation of the project using the Spring Initializr.

IntelliJ's Maven tooling provides access to all the Plugin goals. From the command line, the same can be performed as 
follows:

```shell
% mvn spring-boot:run
```
The WSDL is provided at the service's URL, e.g., `http://localhost:8080/wsdlfirst/customerordersservice?wsdl`. The 
specific URL may be different per your own `application.properties` configuration from Section 9.

Save the WSDL to `src/main/resources/wsdl/customerorders.wsdl`.

**Lesson Notes**

As noted, this step is performed in Lesson 95, but repeated here slightly different.

**`server.port`**

In the WSDL First client, the `server.port` SHOULD be changed in the `application.properties` file. This will become 
necessary when running the SOAP Client later. Pick an available port, e.g., `server.port=8180`. 

## Lesson 97 — Generate the Stubs

This lesson starts by retrieving the CXF Codegen Plugin configuration from the WSDL First Service created in Section 9.

See the commit history for the POM plugin configuration. The customizations I made are detailed below.

1. Use the property `${cxf.version}` for the `version` element.
1. Provide a `defaultOptions` element for the `bindingFiles`. In an earlier section, the `bindings.xjb` file is 
explained in detail. Of note, the `simple` and `serialization` options are highly recommended for most projects. 
Add this file from the CustomerOrders web service in `src/main/resources` per the `bindingFile` element.
1. Strike the `sourceRoot` element from the `configuration`. The defined value for this element is the default and 
unnecessary.
1. The `extraargs` element is a nice to have as it provides a `toString()` method to the generated stubs. Additionally,
the defined argument, `-xjc-Xts:style:multiline`, stringifies the object to the log on multiple lines, providing
an easier to read output. IMHO. To support this, the `xjcplugins` and `xjc-utils` are added to the dependencies. Also, a 
`cxf.xjc.version` property is added for ease of updates.

**Lesson Notes**

Run `mvn clean package` to test the stub generation!

The WSDL was originally named all lowercase. This was refactored using PascalCase, i.e., CustomerOrders.wsdl.

## Lesson 98 — Implement the Client

Bharath begins with creating an entry point for the webservice client. This is completed in the following steps:

1. Use the Service implementation class to get an instance and pass it the WSDL URL.
2. Get the PortType from the service instance
3. Use the portType to pass an order request
4. Process the response of the request.

**Lesson Notes**

If not already done, the `server.port` MUST be changed in the `application.properties` file. If not performed as noted
in Lesson 96 in the WSDL First service configuration, it is necessary to run the SOAP Client. Pick an available port, 
e.g., `server.port=8180`.

The Main class is called `Main`, following a pattern from larger projects I have worked on, and not a longer form as 
described in the course. 

Running the application's Main class is IDE specific, but often straight forward. YMMV.

In the refactoring of the WSDL name in Lesson 98, user error in the dialog skipped renaming the `<wsdl>`
element of the `cxf-codegen-plugin` `execution` `configuration`. This resulted in the aforementioned `xjc-plugins` and 
`runtime` failing to generate the`toString()` methods. While StackOverflow returned a number of possible fixes, I 
ignored all of them because I knew the configuration had worked previously in the WSDL First section. **I turned to the 
logging output** to find that goal for `wsdl2java` was looking for the all lowercase WSDL file. Correcting this 
addressed the problem and generated the expected `toString()` methods.

**Lesson Polish**

Use a `LOGGER` for output vs. `System.out`.

Use a local constant for the WSDL location, `WSDL_LOCATION`.

## Lesson 99 — Assignment

In this lesson, Bharath wants us to implement a createOrders request, create an order, and dump the new order to the log.

Following Lesson 99's four (4) step pattern, the implementation is part of the commit history for your review.

**Lesson Polish**

Implemented `private static` methods for clarity of the Get and Create Orders exercises.


**Question**

The Spring Initializr provides an annotated `@SpringBootApplication` with a `main()` method. There are likely advantages
to this class not detailed in the Section.

## Lesson 100 — Flow, and the Service Provide Mechanism

## Lesson 101 — Section Summary



