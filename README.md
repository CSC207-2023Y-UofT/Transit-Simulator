# Transit-system-tracker #

This program is a (mock) transit system tracker that has offers many functions to a user, from directions and local 
model schedules to the average customer, as well as offering statistics to the 'local' transit company who 'made' the 
program.

This program is :
- A group project for CSC207 in Summer 2023.
- Coded mostly in Java.
- A demonstration of clean architecture and design patterns in programming.

This program is licensed under the terms of the MIT license.

## Examples ##

- A customer wants to go from point A to point B; this program would provide information like model schedules, ETAs, 
directions and more from the 'local' transit system.
- The 'local' transit company would like to see some statistics; this program would show how many passengers purchased 
tickets on a given day, model capacities during specific times, analytics, etc.
- The company would like to modify the railway and management system such as stations, tracks, ticket prices, and employment.

## How To Use ##
- Clone the repository
- Run main.java to access the management system GUI.
- To create a new station: First you need to create a new TransitTracker (See TransitTracker class) if there does not exist one. Then execute createNode method on the transitTracker.
- To create a new train: Create a new TrackSegment (See TransitTracker class) object if there does not exist one. Then, execute createTrain method on the trackSegment. 
- To modify the ticket price: Go to ticket file, locate the ticket class corresponding to the ticket to be modified. Modify the "price" in the class constructor to change the price.

## Java SDK Version ##
- Amazon Corretto Version 11.0.19
## Credits ##

Group name: ~~Kotlin Koding Klub~~ The BBC

GitHub pages: 
[Binhe Jia](https://github.com/Binhe-Jia "Jarrett's GitHub page"), 
[Charles Cheung](https://github.com/charlescheung22 "Charles' GitHub page"), 
[Grace Liu](https://github.com/gracelliu "Grace's GitHub page"), 
[Matthew Lack](https://github.com/mattlack15 "Matt's GitHub page"), 
[Zoey Lee](https://github.com/zoeyzlee "Zoey's GitHub page")


