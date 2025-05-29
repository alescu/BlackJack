
# IT ACADEMY BACKEND JAVA SPECIALIZATION

# SPRINT 5 TASK 1 - Spring Framework with WebFlux

## Authors

* [alescu](https://github.com/alescu)

## Summary

The Blackjack API, is a backend service designed to power an online basic blackjack game. This API provides all the essential endpoints for a complete player experience, from managing user accounts to handling complex game logic.

Players can easily create new accounts with a starting balance and add funds as needed. The API tracks total player counts and offers leaderboards based on total winnings. At its core, the system allows for seamless game initialization, even creating a player on the fly if they don't already exist. Players can make their moves within a game, choosing to hit or stand, with the dealer's actions automatically handled. 


Basic rules: By registering a new player his account the initial balance will be a minimum of 100.
The player can initialize directly by opening a new game, in this case his initial balance will be directly of 100.

Once the game has begun, the player praises a letter and will have to choose between asking for a letter with Hit or 's stay with what he has and to give way to the game of banking.




For administrative needs, there are endpoints to find specific games or delete them by ID, ensuring full control over game sessions.

* Player endpoints
	* Post "/player"
		*-* Add a new Player in game
	* Get "/player"
		*-* Get all players in game
    * Get "/getPlayersProfit"
		*-* Get all players in game by profit order
    * Get "/{id}"
		*-* Get player for their id
    * Post "/addAccount/{id}"
		*-* Add credit to the user's account

* Game endpoints**
    * Post "/{playerName}"
		*-* Open a new Game for a player
    * Post "/{playerName}/play"
		*-* Add a play movement to Player in game
    * Delete "/{id}"
		*-* Delete a game
    * Get "/{id}"
		*-* Show a game for their id


## Technologies

* Java 21
* Maven 3.10
* MySQL 8.2
* Mongo 
* Java Lombok 1.18
* log4j 2.24
* GitHub
* Postman
* OpenAPI 3.0 specifications
* Docker
* DOCKER OFFICIAL IMAGEs
	* mongo:latest FROM ubuntu:24.04			
	* mysql:latest FROM oraclelinux:038463507f87d6444...
	
## How to run it

1.  **Clone the repository:**
	* Clone the repository to your local machine using Git.

2.  **Configure the database:**
	* Ensure you have a running MySQL server.
	* Create the database for the virtual Escape Room application.
	* Initialize the database with the Escape Room virtual application sql script.
	* Configure the database connection credentials (username, password, URL) in the application's configuration file.

3.  **Build the project:**
	* Navigate to the project directory and build it using Maven.
	* For example: `mvn clean install`

4.  **Run the application:**
	* Execute the main class of the application to start the program.

5.  **Interact with the program (via console):**
	* The application will display a menu or allow interaction through the console to perform the following actions:
		* Create a new virtual Escape Room.
		* Add new rooms with their respective difficulty levels.
		* The new escape room will automatically incorporate themed clues into the rooms.
		* The new escape room will automatically incorporate objects to uniquely set the atmosphere of the rooms.
		* Display the updated inventory of rooms, clues, and decoration objects.
		* Visualize the total value of the inventory.
		* Allow the removal of items from the inventory.
		* Generate sales tickets for players.
		* Calculate and display the total income generated from ticket sales.
		* Register users to receive notifications about important events.
		* Simulate the notification of important events.
		* Users can be kept personally informed by email or SMS as the case may be.
		* **

## Project Structure

* The project is built using Maven.
* The Java code is located in the `src/main/java` directory.
* The `pom.xml` file contains the project's dependencies and build configurations.
* Packages are expected to be created for different functionalities.

## Git Workflow

This project follows a Git workflow based on feature branches with a primary `main` branch and a `develop` branch for testing.

1.  **`main` Branch:** Represents the stable, production-ready version of the code. Only thoroughly tested and approved changes should be merged into this branch.

2.  **`develop` Branch:** This is the branch where testing and verification of code functionality occur before it is promoted to the `main` branch.

3.  **Feature Branches:**
	* For each new feature or bug fix, a separate branch is created from the `main` branch.
	* The branch name is descriptive and corresponds to the functionality being worked on (e.g., `branch_decorationItem_ML`, `new_UserSession_menu_branch`).
	* Development work is done on these branches in isolation, with commits made as needed.

4.  **Merge to `develop`:**
	* Once a feature is complete and tested, the feature branch is merged into the `develop` branch.
	* After merging, the code is reviewed for errors, and further work continues on other feature branches.
	* The feature branch can then be deleted after.

5.  **Pull Requests:**
	* Once the `develop` branch is complete and tested, a Pull Request (PR) is opened to merge it into the `main` branch.
	* The PR must be reviewed by at least one other team member.


## API documentation reference
   BlackJack_API_specification.yaml
