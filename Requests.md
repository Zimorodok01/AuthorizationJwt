### Login
http://localhost:8081/login

POST
        
        Request
        {
            "username": "admin123",
            "password": "password"
        }

### Get all users

Role: ADMIN

http://localhost:8081/api/v1/users

GET

        Request
        Header: Authorization = {access token}

        Response
        [
            {
                "name": "admin",
                "surname": "admin",
                "username": "admin123",
                "password": "$2a$05$rHkmmyL2uizknL1DwLQch.l7z9me0O9pYxYm5hn6BoA15FQcrzoXO",
                "role": "ADMIN"
            },
            {
                "name": "Ramazan",
                "surname": "Baigazy",
                "username": "Baigazy1312",
                "password": "$2a$05$L3Qq1OJfXizx9MS8MOru/eomN5KljB8AWfYNmaRtf.A8X2YlJfmlu",
                "role": "USER"
            }
        ]

### Read about yourself

http://localhost:8081/api/v1/users/myProfile

GET

        Request
        Header: Authorization = {access token}
        
        Response
        {
            "name": "admin",
            "surname": "admin",
            "username": "admin123",
            "password": "$2a$05$rHkmmyL2uizknL1DwLQch.l7z9me0O9pYxYm5hn6BoA15FQcrzoXO",
            "role": "ADMIN"
        }

### Add new user

Role: ADMIN

http://localhost:8081/api/v1/users

POST

        Request
        Header: Authorization = {access token}
        Body:
            {
                "name": "Ramazan",
                "surname": "Baigazy",
                "username": "Baigazy01",
                "password": "password",
                "role": "ADMIN"
            }

        Response
        {
            "name": "Ramazan",
            "surname": "Baigazy",
            "username": "Baigazy01",
            "password": "$2a$05$Qiv3E6nMVqU8eog9Q.7b4Ob7H9gAH5iI800LEL2/.8hwJjUiWv8s2",
            "role": "ADMIN"
        }

### Update user information

Role: ADMIN

http://localhost:8081/api/v1/users/{userId}

PUT

        Request
        Header: Authorization = {access token}
        Body:
            Parameters:
            name(is not required),
            surname(is not required),
            username(is not required),
            password(is not required),
            role(is not required)

        Response
        {
            "name": "Ramazan",
            "surname": "Baigazy",
            "username": "Baigazy01",
            "password": "$2a$05$Qiv3E6nMVqU8eog9Q.7b4Ob7H9gAH5iI800LEL2/.8hwJjUiWv8s2",
            "role": "USER"
        }

### Delete user

Role: ADMIN

http://localhost:8081/api/v1/users/{userId}

DELETE

        Request
        Header: Authorization = {access token}

        Response
        {
            "name": "Ramazan",
            "surname": "Baigazy",
            "username": "Baigazy01",
            "password": "$2a$05$Qiv3E6nMVqU8eog9Q.7b4Ob7H9gAH5iI800LEL2/.8hwJjUiWv8s2",
            "role": "USER"
        }

### Get users sorted by name

Role: ADMIN

http://localhost:8081/api/v1/users/name

GET

        Request
        Header: Authorization = {access token}

### Get users sorted by surname

Role: ADMIN

http://localhost:8081/api/v1/users/surname

GET

        Request
        Header: Authorization = {access token}

### Get users sorted by userame

Role: ADMIN

http://localhost:8081/api/v1/users/username

GET

        Request
        Header: Authorization = {access token}

### Get only admins

Role: ADMIN

http://localhost:8081/api/v1/roles/admin

GET

        Request
        Header: Authorization = {access token}

### Get only users

Role: ADMIN

http://localhost:8081/api/v1/roles/user

GET

        Request
        Header: Authorization = {access token}