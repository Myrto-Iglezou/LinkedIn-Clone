# LinkedIn-Clone

Execution:

__Front:__  ```ng serve```  ✅ 

__Back:__   ```JetBrains run``` ☑️

!! SSL/TLS browser security must be disabled for localhost: ```chrome://flags/#allow-insecure-localhost```


---

## User credentials and info
- POST: https://localhost:8443/login  ☑️
```json
  {
      "username":"n_withpic@mail.com",
      "password":"012345"
  }
  ```
- POST: https://localhost:8443/signup ☑️
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
  
- GET:  https://localhost:8443/in/{id} ☑️ : Returns user id 2 info 
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
- PUT https://localhost:8443in/{id}/settings ☑️
```json 
{
    "currentPassword":"012345",
    "newPassword":"0123",
    "passwordConfirm":"0123",
    "currentUsername": "user@mail.com",
    "newUsername": "newuser@mail.com"
}
```

- GET:  https://localhost:8443/in/{id}/profile  ☑️ 
```json
}
```

- PUT:  https://localhost:8443/in/{id}/profile/new-info ☑️ 
```json
}
```

## Feed-Posts

- GET https://localhost:8443/in/{id}/feed  ☑️  __[BONUS]__
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
- POST https://localhost:8443/in/{id}/feed/new-post ☑️
```json
{
  "content":"post bla bla"
}
```

- PUT https://localhost:8443/in/{id}/feed/post-interested/{postdId} ☑️
```No json needed, only url ids```

- PUT https://localhost:8443/in/{id}/feed/comment/{postdId} ☑️
```json
{
  "content":"comment bla bla"
}
```

## Network
- PUT https://localhost:8443/in/{id}/new-connection/{newUserId} ☑️
```No json needed, only url ids```

- PUT https://localhost:8443/in/{id}/notifications/{connectionId}/accept-connection ☑️
```No json needed, only url ids```


- GET https://localhost:8443/in/{id}/network  ☑️
```json
[
    {
        "id": 5,
        "name": "name3",
        "surname": "surname3",
        "position": null,
        "company": null
    }
]
```

- GET https://localhost:8443/in/{id}/search
```json

```

- GET https://localhost:8443/in/{id}/profile/{userId}: Other user profile
```json

```

## Jobs
- POST https://localhost:8443/in/{id}/new-job ☑️
```json
```

- GET https://localhost:8443/in/{id}/jobs ☑️ __[BONUS]__  
```json
```

- PUT https://localhost:8443/in/{id}/jobs/make-application/{jobId} ☑️
```json
```

- GET https://localhost:8443/in/{id}/jobs/{jobId}/applicants ☑️
```json
```


## Admin

- GET https://localhost:8443/admin/users
```json
```

- GET https://localhost:8443/admin/users/{userId}/profile
```json
```

- GET https://localhost:8443/admin/users/export
```json
```


## Chat
- GET https://localhost:8443/in/{id}/chats
```json
```

- GET https://localhost:8443/in/{id}/chat/{otherUserId}
```json
```

- POST https://localhost:8443/in/{id}/chat/{otherUserId}/new-message
```json
```

## Notifications
- GET https://localhost:8443/in/{id}/notifications ☑️
```json
{
    "connectionsPending": [],
    "interestReactions": [],
    "comments": []
}
```

---

## Missing

### Feed - posts
1. textarea writes in all
2. post users images
3. post images
4. post new image/audio/video


