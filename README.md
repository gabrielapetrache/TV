**Petrache Gabriela**
**322CA**

## OOP TV

### Description:
* Implemented a project that resembles a streaming platform with movies, in java
* To access the platform, the user must either log in or create an account
* On this platform, users are allowed to purchase movies, watch, like and rate them
* To watch a movie, the user must purchase it first
* There are two types of accounts a user can have: standard, in which every movie must be purchased separately, 
and premium, which has up to 15 movies to watch for free, before the user needs to purchase tokens
* Tokens can be bought by the user with their own money and used to purchase movies on the platform
* The platform has a list of movies that can be updated at any time by an administrator
* When an update to the list of movies is made, certain users are notified
* The user has the ability to filter movies by genre, actors, name, and also to sort the movies in either ascending or
descending order based on duration and ratings
* Premium users will recieve personalized movie recommendations based on the movie genres they liked
* A user can subscribe to different genres, and get notified when a new movie they might like is added to the platform

### Implementation:
* The input was parsed from the json files, using  class input.Input, which stored the given lists of users, movies and 
actions
* The file hierarchy is as follows:
```
.
├── Main.java
├── Test.java
├── actions
│   ├── ChangePage.java
│   ├── database
│   │   ├── AddMovie.java
│   │   ├── ChangeDatabase.java
│   │   ├── Command.java
│   │   ├── DeleteMovie.java
│   │   └── Modifier.java
│   └── onpage
│       ├── BuyPremium.java
│       ├── BuyToken.java
│       ├── Feature.java
│       ├── FeatureFactory.java
│       ├── Filter.java
│       ├── Like.java
│       ├── Login.java
│       ├── Purchase.java
│       ├── Rate.java
│       ├── Register.java
│       ├── Search.java
│       ├── Subscribe.java
│       └── Watch.java
├── checker
│   ├── Checker.java
│   ├── CheckerConstants.java
│   ├── Checkstyle.java
│   ├── Constants.java
│   ├── checkstyle-8.36.2-all.jar
│   ├── checkstyle.sh
│   └── poo_checks.xml
├── filters
│   ├── Contains.java
│   ├── FilterStrategy.java
│   ├── Filters.java
│   └── Sort.java
├── input
│   ├── Action.java
│   ├── Input.java
│   └── Movie.java
├── pages
│   └── PageStrings.java
├── platform
│   └── Platform.java
├── printer
│   └── OutputPrinter.java
└── users
    ├── Credentials.java
    ├── Notification.java
    └── User.java
```

* In the actions package, there are two subpackages: database and onpage, along with a ChangePage class
  * The database package contains the classes that modify the database, by adding and deleting movies
    * For the implementation of these actions, I have used a Command design pattern
  * The onpage package contains the classes that helped me implement the actions that can be performed on a page
    * For the implementation of these actions, I have used a Factory design pattern
    * The FeatureFactory class is the factory, and the Feature interface is the product
    * Based on the action from the input, the factory will return the corresponding class that implements the Feature
    * Each class performs a different action, the names being self-explanatory and they all return the users array and
    the current user, because they suffer modifications during different actions and need to be updated
  * The ChangePage class is used to change the page the user is currently on, or return to the previous page. It contains
  checks to see if the user is allowed to enter the desired page and methods to actually execute the change

* The checker package contains the classes that check the execution of the program

* The filters package contains the classes that implement the filtering methods
  * For the implementation of the filters, I have used a Strategy design pattern
  * The FilterStrategy interface is the strategy, and the Filters class is the context
  * Based on the input, the Filters class will sort the movies using the corresponding strategy
  * The list can be filtered by the actors and genres, and sorted by rating and duration

* The input package contains the classes that parse the input from the json files

* The pages package contains the strings that are used throughout the program for design purposes

* The platform package contains the Platform class, which is the main class of the program
  * This is the class that contains the main method, and it is responsible for the execution of the program
  * This class performs as a database, storing the users and movies
  * It also contains the methods that perform the actions, and the methods that update the users
  * During the execution, the program stores a list of all existing users and movies, the current active user,
  the current working page, and the current list of movies on the page
  * The list of movies can be altered by the page that the user is currently on, and the filters applied

* The printer package contains the OutputPrinter class
  * This is a helper class that is responsible for printing the output to the json files
  * It is a Singleton class, that is instantiated only once, and it is used throughout the program

* The users package contains the classes that implement the users
  * The User class has all the fields and methods that are available to users
  * The Credentials class is used to store the user's personal information
  * The Notification class is used to store the notifications that are sent to the user
  
* The Test class is used to test the program and check if the output of the tests is correct
