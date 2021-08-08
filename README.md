# LinkedIn-Clone

## To do

### User credentials and info
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


### Feed-Posts
- GET https://localhost:8443/in/{id}/feed
- PUT https://localhost:8443/in/{id}/feed/post-interested/{postdId}
- POST https://localhost:8443/in/{id}/feed/new-post
- PUT https://localhost:8443/in/{id}/feed/comment/{postdId}


### Network
- PUT https://localhost:8443/in/{id}/add-connection/{newUserId}


### Jobs
- POST https://localhost:8443/in/{id}/new-job


### Admin



## Missing

- Images/audio/video



## Back-end

<table>
<tr>
  <td> Request </td> <td> JSON </td>  <td> Finished </td> 
</tr>
<tr>
<td> POST: https://localhost:8443/login </td>
  <td>

  ```json
  {
    "username": "user@mail.com",
    "password": "012345"
  }
  ```
  </td>
  <td> <ul><li>- [ ] front</li><li>- [ ] back</li></ul> </td> 

</tr>
<tr>
<td> POST: https://localhost:8443/signup </td>
  <td>

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
  </td>
  <td> <ul><li>- [ ] front</li><li>- [ ] back</li></ul> </td> 
</tr>
<tr>
<td> PUT: https://localhost:8443/user/2/settings </td>
  <td>

  ```json
  {
      "currentPassword":"012345",
      "newPassword":"0123",
      "passwordConfirm":"0123",
      "currentUsername": "user@mail.com",
      "newUsername": "newuser@mail.com"
  }
  ```
  </td>
  <td> <ul><li>- [ ] front</li><li>- [ ] back</li></ul> </td> 
</tr>
<tr>
<td> GET: https://localhost:8443/admin </td>
  <td>

  ```json
  {
    [
      "user" : {
    
      },
      "user" : {
    
      },
      ...    
    ]
  }
  ```
  </td>
  <td> <ul><li>- [ ] front</li><li>- [ ] back</li></ul> </td> 
</tr>
<tr>
<td> GET: https://localhost:8443/feed </td>
  <td>

  ```json
  {
    "user_info" : {

    },
    "posts" : {
      [
        "post": {
        
        }
      ]

    },
  }
  ```
  </td>
  <td> <ul><li>- [ ] front</li><li>- [ ] back</li></ul> </td> 
</tr>
 <tr>
<td> POST: https://localhost:8443/feed/newpost </td>
  <td>

  ```json
  {
    "post" : {

    }
  }
  ```
  </td>
   <td> <ul><li>- [ ] front</li><li>- [ ] back</li></ul> </td> 
</tr>
 <tr>
<td> POST: https://localhost:8443/feed/comment/{postid} </td>
  <td>
  </td>
   <td> <ul><li>- [ ] front</li><li>- [ ] back</li></ul> </td> 
</tr>
<tr>
<td> POST: https://localhost:8443/feed/interest/{postid} </td>
  <td>
  </td>
  <td> <ul><li>- [ ] front</li><li>- [ ] back</li></ul> </td> 
</tr>
<tr>
<td> GET: https://localhost:8443/feed/network </td>
  <td>
  </td>
  <td> <ul><li>- [ ] front</li><li>- [ ] back</li></ul> </td> 
</tr>
<tr>
<td> GET: https://localhost:8443/in/{userid} </td>
  <td>
  </td>
  <td> <ul><li>- [ ] front</li><li>- [ ] back</li></ul> </td> 
</tr>
 <tr>
<td> GET: https://localhost:8443/chats </td>
  <td>
  </td>
   <td> <ul><li>- [ ] front</li><li>- [ ] back</li></ul> </td> 
</tr>  
<tr>
<td> GET: https://localhost:8443/chats/{userid} </td>
  <td>
  </td>
  <td> <ul><li>- [ ] front</li><li>- [ ] back</li></ul> </td> 
</tr>  
<tr>
  <td> GET: https://localhost:8443/jobs </td>
  <td>
  </td>
  <td> <ul><li>- [ ] front</li><li>- [ ] back</li></ul> </td> 
</tr>  
<tr>
<td> POST: https://localhost:8443/newjob/{jobid} </td>
  <td>
  </td>
  <td> <ul><li>- [ ] front</li><li>- [ ] back</li></ul> </td> 
</tr> 
<tr>
<td> GET: https://localhost:8443/notifications</td>
  <td>
  </td>
  <td> <ul><li>- [ ] front</li><li>- [ ] back</li></ul> </td> 
</tr> 
<tr>
<td> GET: https://localhost:8443/user/info</td>
  <td>
  </td>
  <td> <ul><li>- [ ] front</li><li>- [ ] back</li></ul> </td> 
</tr> 
<tr>
<td> POST: https://localhost:8443/user/infoupdate</td>
  <td>
  </td>
  <td> <ul><li>- [ ] front</li><li>- [ ] back</li></ul> </td> 
</tr> 
</table>
