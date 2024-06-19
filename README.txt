--- ItemHub ---
Contributors: Amulya Nathala, Sahana Sri Veeragandham, Karthik Ramireddy

--- Description ---
The ItemHub is a web-based application designed to allow users to create accounts, register their information, and securely log in. The primary goal of this project is to provide a secure and user-friendly platform where individuals can access personalized services and information. The system ensures that only registered users with valid credentials can access their accounts.

Amulya's Contribution:
- Implemented the initial project structure.
- Set up the Spring Boot application with necessary dependencies.
- Created the database schema and defined the User entity.
- Implemented the registration feature including user validation.
- Designed and developed the registration and home HTML templates.
- Ensured that the application handles duplicate usernames.
- Participated in code reviews and debugging.

Karthik's Contribution:
- Worked on the login feature of the application.
- Developed the login HTML template.
- Implemented the login logic and validation on the server-side.
- Created the `loginsuccess.html` page for successful login confirmation.
- Ensured the integration of registration and login components.
- Conducted testing and debugging of the login functionality.

Sahana's Contribution:
- Implemented Spring Data JPA to interact with the database.
- Created the UserRepo interface for database operations.
- Implemented parameterized queries to prevent SQL injection.
- Addressed the requirement of preventing SQL injection attacks.
- Conducted thorough testing and validation of database interactions.
- Assisted with code optimization and performance improvements.

Technologies Used:
- Backend: Java, Spring Boot, Spring Data JPA
- Frontend: HTML, Thymeleaf templates
- Database: MySQL

Outcome:
The User Registration and Login System provides a secure and efficient platform for user registration and login, prioritizing the security of user data. The project serves as a foundation for future web applications requiring user authentication and access control.

--- Online Marketplace Phase 2 Project ---
Contributors: Amulya Nathala, Sahana Sri Veeragandham, Karthik Ramireddy

--- Description ---
The Online Marketplace Phase 2 project is an extension of our previous work on the User Registration and Login System. In this phase, we've expanded the application to create an online marketplace where users can post items for sale, search for items, write reviews, and initialize the database.

Amulya's Contribution:
- Designed and implemented the item insertion interface.
- Developed the 'insertitem.html' template for users to add items.
- Integrated automatic ID generation using the MySQL auto-increment feature.
- Implemented a daily limit of 3 item posts per user.
- Assisted with code reviews and error handling.

Karthik's Contribution:
- Created the search interface for users to search for items by category.
- Developed the `search.html` and 'searchresults.html' templates.
- Designed a dynamic table to display search results.
- Implemented a user-friendly search feature with responsive design.
- Conducted testing and validation of search functionality.

Sahana's Contribution:
- Implemented the review feature, allowing users to write reviews for items.
- Developed the 'displayitem.html' template to display item details and reviews.
- Added a dropdown menu for rating and description input.
- Enforced a limit of 3 reviews per user per day.
- Prevented users from reviewing their own items.
- Assisted with database interactions and data validation.

Additional Notes:
- The project continues to use Spring Boot and Spring Data JPA for backend development.
- The application ensures data security and a user-friendly experience.

--- Next Steps ---
For the next phase, we plan to:
- Implement the "Initialize Database" button to create and populate the necessary tables.
- Continue testing and debugging to ensure data consistency.
- Enhance the application's usability and error handling.

Technologies Used:
- Backend: Java, Spring Boot, Spring Data JPA
- Frontend: HTML, Thymeleaf templates
- Database: MySQL

Outcome:
The Online Marketplace Phase 2 project extends our previous work by introducing item posting, searching, and reviewing functionalities. It strives to provide a seamless and secure online marketplace experience for users.

--- Online Marketplace Phase 3 Project ---
Contributors: Amulya Nathala, Sahana Sri Veeragandham, Karthik Ramireddy

--- Description ---
The Online Marketplace Phase 3 project builds upon the existing features of the User Registration and Login System and the Online Marketplace Phase 2. This phase introduces advanced functionalities, enhancing the user experience and providing more comprehensive insights into user activities and item interactions.

--- Amulya's Contributions ---

1. List the Most Expensive Items in Each Category:
   - Implemented functionality to retrieve and display the most expensive items in each category.
   - Created HTML template: `mostExpensiveItems.html`.

2. Search Users Posting Two Items on the Same Day with Different Categories:
   - Introduced a search feature allowing users to find individuals who posted two items on the same day, each belonging to a different category.
   - Created HTML templates: 'usersPostingOnSameDay.html', 'usersPostingOnSameDayResults.html'.

3. Display Users Who Never Posted Excellent Items:
   - Added functionality to display users who have never posted items with "Excellent" reviews.
   - Created HTML template: 'usersNeverPostedExcellentItems.html'.

--- Karthik's Contributions ---

1. List Users Posting Most Items on a Specific Date:
   - Developed functionality to list users who posted the most items on a specified date.
   - Created HTML template: 'userWithMostItemsOnDate.html', 'usersPostingOnSameDay.html'

2. List Users Commonly Favorited by Users X and Y:
   - Implemented a feature to find users who are favorited by both selected users (User X and User Y).
   - Created HTML template: 'markFavorite.html', 'listFavorites.html', 'resultFavorites.html'.

3. Display Users No Poor Reviews:
   - Implemented a feature to display users whose items have never received any "Poor" reviews.
   - Created HTML template: 'usersNoPoorReviews.html'.

--- Sahana's Contributions ---

1. List Items with Excellent or Good Comments by User X:
   - Implemented a feature to list all items posted by a user (User X) with reviews limited to "Excellent" or "Good."
   - Created HTML template: 'itemsWithExcellentOrGoodComments.html'.

2. Display Users Who Never Posted a "Poor" Review:
   - Implemented a feature to display users who have never posted a "Poor" review.
   - Created HTML template: 'usersWithoutPoorReviews.html'.

3. Display Users Posting Only "Poor" Reviews:
   - Introduced functionality to display users who posted reviews, and all of them are marked as "Poor."
   - Created HTML template: usersWithAllPoorReviews'.

4. Display Users with Items Never Receiving "Poor" Reviews:
   - Implemented a feature to display users whose items have never received any "Poor" reviews.
   - Created HTML template: 'usersNoPoorReviews'.

Technologies Used:
- Backend: Java, Spring Boot, Spring Data JPA
- Frontend: HTML, Thymeleaf templates
- Database: MySQL

Outcome:
The Online Marketplace Phase 3 project extends the application with advanced features, providing users with a more dynamic and interactive experience. The project continues to prioritize security, usability, and data consistency.

Instructions to Install, Configure and Run the project
- Install any IDE which supports Java and Spring Boot (preferably Intellij Community Edition), extract the project into the IDE and install the required dependencies.
- Install MySQL Command Line Client and create a database named 'user'. Make sure to update the database username and password as per your MySQL Database credentials.
- Once you run the project in the IDE, access the application through a web browser using 'http://localhost:8080'.
