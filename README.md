# Transit Simulator #

---
[![Build Status](https://travis-ci.com/CSC207-2023Y-UofT/course-project-the-bbc.svg?branch=main)](https://travis-ci.com/CSC207-2023Y-UofT/course-project-the-bbc)
[![codecov](https://codecov.io/gh/CSC207-2023Y-UofT/course-project-the-bbc/branch/main/graph/badge.svg?token=ZQZQZQZQZQ)](https://codecov.io/gh/CSC207-2023Y-UofT/course-project-the-bbc)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

[![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/en/)

---


## Overview ##

The Transit System Simulator is a comprehensive software solution designed to simulate various functions of a transit 
system. This application allows users to access features ranging from obtaining directions and viewing local model 
schedules to assessing average customer statistics.


## Details ##

- **Course Assignment**: CSC207, Summer 2023 session at the University of Toronto (UofT).
- **Programming Language**: Java.
- **Architectural Highlights**: The software serves as a testament to the implementation of Clean Architecture principles and various design patterns in programming.


## Examples ##

- A customer wants to go from point A to point B; this program would provide information like model schedules, ETAs, 
directions and more from the 'local' transit system.
- The 'local' transit company would like to see some statistics; this program would show how many passengers purchased 
tickets on a given day, model capacities during specific times, analytics, etc.
- The company would like to modify the railway and management system such as stations, tracks, ticket prices, and employment.


## Features ##

As a customer:
- Simulate the Toronto Subway System with realtime movement updates.
- Click on stations to see predicted arrival times in a specific direction.
- Simulate buying train tickets. Select from different ticket types and see their valid use time.

As staff:
- Record statistics and display them in graphs that update in realtime.
- Manage, add and remove staff across the system.
- View which staff are working on which trains, their staff types, name, numbers.


## Architecture ##

Here are the [Class-Responsibility-Collaborator Cards](https://1drv.ms/o/s!AsmO3TTchzhwgv4tlXWT_oTuhUen_A?e=b8BHb7 "Class-Responsibility-Collaborator Cards") used to design the program.

## Design Patterns ##
We used many design patterns, here are a few of the ones we used and where:

### Builder ###
We used the builder design pattern in the form of the class TransitModelBuilder.java, which is used to creating transit model from the JSON file that stores the model, and it is used in tests for setting up a model to be used in the tests.

### Strategy ###
Strategy is a very powerful pattern and is actually used quite a few times in our program:
- One of which is as a parameter to StatDataController, an instance of TimeIndexingStrategy must be passed, which represents a strategy for coming up with the time indices that statistics will be stored with.
- Another instance where we used the strategy pattern is with SuppliedLabel, which is a type of JLabel that uses a strategy for getting the text that should be on the label.
- Another two instances are with FileIOProvider and DataCompressionProvider which are two interfaces that represent strategies for synchronizing file IO between threads (and allowing asynchronous writes along with that), and compressing data respectively.
- StatAggregator classes are all strategies for analyzing statistics data (stat entries), and they allow for easy analysis of past statistics.
- Simulator classes are strategies for simulating different aspects of our program.

### Factory ###
We use the factory design pattern with NodeFactory which allows TransitModel to create any specified type of Node while still allowing it to guarantee it is added/registered with the transit model after it is created. This is also a form of dependency injection.

### Visitor ###
StatAggregator also acts as a visitor for a specified type of StatEntry object.

## Installation ##

To install this application, 
- Clone the repository to your desired directory with the following commands in terminal:
```
cd "Your-desired-file-path"
git clone https://github.com/CSC207-2023Y-UofT/Transit-Simulator.git
```
- Then run Main.java to access the management system GUI.


## Java SDK Version ##

- Amazon Corretto Version 11.0.19


## Accessibility ##

- The program is accessible to users, including those with visual impairments. The map, menu colors and fonts were selected to offer great contrast and readability.
- Buttons on the menu are enlarged for size and space.


## Team ##

- [Binhe Jia](https://github.com/Binhe-Jia "Jarrett's GitHub page")
- [Charles Cheung](https://github.com/charlescheung22 "Charles' GitHub page")
- [Grace Liu](https://github.com/gracelliu "Grace's GitHub page")
- [Matthew Lack](https://github.com/mattlack15 "Matt's GitHub page")
- [Zoey Lee](https://github.com/zoeyzlee "Zoey's GitHub page")


## Code Coverage ##
<img width="391" alt="Screen Shot 2023-08-15 at 8 48 42 PM" src="https://github.com/CSC207-2023Y-UofT/Transit-Simulator/assets/53711531/af5ac612-710d-4fd8-b444-a3d912f24a6b">

The only files that are not tested are Main.java and the UI directory.
The UI classes are difficult to test and we have been told we don't need to test them.
Main.java is the class that sets up the whole program for execution, it deals with UI and therefore can't be tested very well.

## Warnings ##

There are a few grammatical/proofreading warnings but otherwise free of Java warnings

<img width="606" alt="Screen Shot 2023-08-13 at 6 17 19 PM" src="https://github.com/CSC207-2023Y-UofT/course-project-the-bbc/assets/53711531/683eb123-7e81-480d-a5dd-ccd458e8f7d7">

## License ##

This project is licensed under the MIT License. See [LICENSE.md](LICENSE.md) for details.


