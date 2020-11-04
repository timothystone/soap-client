# Java Web Services

## Section 10

In this section, Bharath Thippireddy in his Udemy course, Java Web Services, goes over the five (5) steps for creating a 
Java SOAP client from scratch.

This repository will follow along each step. Each branch will reflect the completed portion, including the assignment in
Lesson 99. `master` will reflect the full working project.

### Repository Notes

Bharath uses Eclipse throughout his course. I'm neither a fan or user of Eclipse. This repository and the instructions 
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

This project did not follow the specifics laid out by Bharath, but the intent. My steps are outlined below and reflected
in the repository commits.

1. Create a new Spring Initializr project. 

The details of the IntelliJ dialog is an exercise left for the reader, but the important parts are provided below.

I choose to use **JDK 14** for the project. 

For the Spring Initializr Java Version, I choose **JDK 11**. This creates a Maven `<properties>` element of 
`<java.version>11</java.version>`. The Spring Initializr at the time of this project allowed for Java versions of 8, 11,
and 15.

The initial `git` commit reflects the generated code by Spring Initializr. The project was cleaned up throughout the 
section lessons. The following "polish" to the project reflects some of my personal choices and goals. YMMV.

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

Bharath sets the Apache CXF for JAXWS dependency. Use the property `${cxf.version}` after pasting the dependency 
from the previous section's project.

There is a bit of a deviation here that is initially confusing. Bharath walks us through retrieving the WSDL from the 
WSDL First Service section lessons and creates a `wsdl` directory in `src/main/resources`. This part is unnecessary as 
we will be retrieving it directly from the running service in Lesson 96.

## Lesson 96 — Retrieve the WSDL

## Lesson 97 — Generate the Stubs

## Lesson 98 — Implement the Client

## Lesson 99 — Assignment

## Lesson 100 — Flow, and the Service Provide Mechanism

## Lesson 101 — Section Summary



