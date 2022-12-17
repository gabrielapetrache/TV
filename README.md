**Petrache Gabriela**
**322CA**

## POO TV

### Description:
* Implemented a project that resembles a streaming platform with movies, in java.
* On this platform, users are allowed to purchase movies, watch, like and rate them, using tokens they can buy with their own money
* There are two types of accounts a user can have: standard, in which every movie must be purchased separately, and premium, which has up to 15 movies to watch for free, before the user needs to purchase tokens
* To access the platform, the user must either log in or create an account
* The user has the ability to filter movies by genre, actors, name, and also to sort the movies in either ascending or descending order based on duration and ratings

### Implementation:
* The input was parsed from the json files, using  class input.Input, which stored the given lists of users, movies and actions
* The data about users can be found in the users package, which contains a class for the credentials, and one for the actual user, with methods that help perform different actions on movies
* In the platform package, there is the entry point of the program, which takes the actions that need to be executed, and executes them, depending on the case. In this class, there are separate cases for each type of action: on page and change page
* During the execution, the program stores a list of all existing users, the current active user, the current working page, and the current list of movies on the page
* The list of movies can be altered by the page that the user is currently on, and the filters applied
* For filters, I have used a separate class that contains a list of filters, and using the strategy design pattern, I have created a method that executes each operation, based on what type of filtering there needs to be applied
* In the printer package, I have a helper class that helps print the output of the commands, in case of error and success, in a json format

### Comments:
* I will try to better modularize the code for the second part of the project, by implementing classes for each page, which would be able to perform the page's specific actions, and by adding more design patterns to the project, where useful
