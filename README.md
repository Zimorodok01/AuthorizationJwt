Hello! My name is Ramazan. And this is my project. 

In my project, I integrate JWT. After authorization, you get access and refresh token. Difference between refresh and access, expiration date of access token is 1 hour. Expiration date of refresh is 10 days (you can change it in application.properties)

There are two entities: Users and Roles. There is ManyToOne connection between users and roles. So, it means many users can be in one role. There are two roles: ADMIN and USERS. ADMIN has all privileges, such as reading all user's information, read himself, add new, delete and update user. And admin can view users ordered by name, surname, username. He can view only admins or only users.
Users have only one privilege: Read about himself. 

There is Slf4j logging from Lombok.

My database connected to PostgreSQL