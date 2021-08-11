# LinkedIn-Clone

Execution:

__Front:__  ```ng serve```

__Back:__   ```JetBrains run```

!! SSL/TLS browser security must be disabled for localhost: ```chrome://flags/#allow-insecure-localhost```


---

## User credentials and info
- POST: https://localhost:8443/login  ✅ ☑️
```json
  {
      "username":"n_withpic@mail.com",
      "password":"012345"
  }
  ```
- POST: https://localhost:8443/signup
  ```json
  {
      "username":"n_withpic@mail.com",
      "name": "new",
      "surname":"user",
      "password":"012345",
      "passwordConfirm":"012345",
      "roles":[],
      "imageFile": { }
  }
  ```
- GET:  https://localhost:8443/in/{id} : Returns user id 2 info
```json
{
    "id": 6,
    "username": "user4@mail.com",
    "password": "$2a$10$dgcMG3SqpaShA1hQtG8k9e8HVSw1nyEmP7IGYL/jkIg21WGfG/5kS",
    "passwordConfirm": null,
    "name": "name4",
    "surname": "surname4",
    "phoneNumber": null,
    "city": null,
    "currentJob": null,
    "currentCompany": null,
    "profilePicture": null,
    "roles": [
        {
            "id": 2,
            "name": "PROFESSIONAL"
        }
    ],
    "info": [],
    "usersFollowing": [],
    "userFollowedBy": [],
    "posts": [],
    "comments": [],
    "notifications": [],
    "interestReactions": [],
    "jobsCreated": [],
    "jobApplied": [],
    "messages": [],
    "chats": []
}
```
- PUT https://localhost:8443in/{id}/settings
```json
{
    "currentPassword":"012345",
    "newPassword":"0123",
    "passwordConfirm":"0123",
    "currentUsername": "user@mail.com",
    "newUsername": "newuser@mail.com"
}
```

## Feed-Posts

- GET https://localhost:8443/in/{id}/feed
```json
{
    "userDetails": {
        "id": 6,
        "username": "user4@mail.com",
        "password": "$2a$10$dgcMG3SqpaShA1hQtG8k9e8HVSw1nyEmP7IGYL/jkIg21WGfG/5kS",
        "passwordConfirm": null,
        "name": "name4",
        "surname": "surname4",
        "phoneNumber": null,
        "city": null,
        "currentJob": null,
        "currentCompany": null,
        "profilePicture": null,
        "roles": [
            {
                "id": 2,
                "name": "PROFESSIONAL"
            }
        ],
        "info": [],
        "usersFollowing": [],
        "userFollowedBy": [],
        "posts": [],
        "comments": [],
        "notifications": [],
        "interestReactions": [],
        "jobsCreated": [],
        "jobApplied": [],
        "messages": [],
        "chats": []
    },
    "posts": [],
    "connectedUsers": []
}
```
- POST https://localhost:8443/in/{id}/feed/new-post
```json
{
  "content":"post bla bla"
}
```

- PUT https://localhost:8443/in/{id}/feed/post-interested/{postdId}
No json needed, only url ids

- PUT https://localhost:8443/in/{id}/feed/comment/{postdId}
```json
{
  "content":"comment bla bla"
}
```

## Network
- PUT https://localhost:8443/in/{id}/new-connection/{newUserId}
```json
```

## Jobs
- POST https://localhost:8443/in/{id}/new-job
```json
```

## Admin
```json
```

## Chat

---

## Missing
1. Media files (image,video,audio)
2. Timestamps??
