## Auth

### **Base URL:**
`/api`

### **General Response Structure:**

```json
{
  "code": Integer,   // 1 for success, 0 for error
  "msg": String,     // Message indicating success or error
  "data": Object     // Additional response data
}
```

---

### **1. Login**

- **URL:** `/login`
- **Method:** POST
- **Description:** Authenticate a user and return a JWT token.

#### **Parameters:**

| Name          | Type   | Required | Description     |
| ------------- | ------ | -------- | --------------- |
| user.username | String | Yes      | User's username |
| user.password | String | Yes      | User's password |

#### **Sample Request Body:**
```json
{
  "user": {
    "username": "exampleUser",
    "password": "examplePassword"
  }
}

```

#### **Response Body (On Success):**

```json
{
  "code": 1,
  "msg": "success",
  "data": "JWT_TOKEN_STRING_HERE"
}
```

#### **Response Body (On Failure):**

```json
{
  "code": 0,
  "msg": "invalid username and password",
  "data": null
}
```

------

### **2. Register**

- **URL:** `/register`
- **Method:** POST
- **Description:** Register a new user.

#### **Parameters:**

| Name          | Type   | Required | Description          |
| ------------- | ------ | -------- | -------------------- |
| user.username | String | Yes      | User's username      |
| user.password | String | Yes      | User's password      |
| user.email    | String | Yes      | User's email address |

#### **Sample Request Body:**
```json
{
  "user": {
    "username": "exampleUser",
    "password": "examplePassword",
    "email": "example@email.com"
  }
}
```

#### **Response Body (On Success):**

```json
{
  "code": 1,
  "msg": "success",
  "data": null
}
```

#### **Response Body (On Failure - Username Exists):**

```json
{
  "code": 0,
  "msg": "username already exists",
  "data": null
}
```

#### 

---

### **3. Reset Password**

- **URL:** `/reset_password`
- **Method:** POST
- **Description:** Reset the password for an existing user.

#### **Parameters:**

| Name          | Type    | Required | Description         |
| ------------- | ------- | -------- | ------------------- |
| user.username | String  | Yes      | User's username     |
| user.password | String  | Yes      | User's new password |
| user.id       | Integer | Yes      | User's ID           |

#### **Sample Request Body:**
```json
{
  "user": {
    "username": "exampleUser",
    "password": "newPassword",
    "id": 123456
  }
}
```

#### **Response Body :**

```json
{
  "code": 1,
  "msg": "success",
  "data": null
}
```

---

## **Notes**

### **Base URL:** `/api/notes`

All requests should be accompanied by a JWT token to validate token authenticity. The token will be parsed in an interceptor to derive the user's ID (`userId`), which will then be passed as a request attribute.

### **General Response Structure:**

```json
{
  "code": Integer,   // 1 for success, 0 for error
  "msg": String,     // "success" for successful operations or "error" for errors
  "data": Object     // Additional response data (specific to each endpoint)
}
```

---

### **1. Get All Tags**

- **URL:** `/tags`
- **Method:** GET
- **Description:** Retrieve all available tags.

#### **Response:**

A list of all available tags.

```json
{
  "code": 1,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "tagName": "Work"
    },
    {
      "id": 2,
      "tagName": "Personal"
    }
  ]
}
```

---

### **2. Get All Notes for a User**

- **URL:** `/`
- **Method:** GET
- **Description:** Retrieve all notes for the authenticated user.

#### **Response:**

A list of all notes without content for the specified `userId`.

```json
{
  "code": 1,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "title": "Meeting Notes",
      "creatorId": 12345,
      "abs": "Brief overview of the meeting notes.",
      "createTime": "2023-08-22T14:30:00",
      "updateTime": "2023-08-22T15:00:00",
      "tags": [
        {
          "id": 1,
          "tagName": "Work"
        }
      ],
      "img": "path_to_image.jpg"
    }
  ]
}
```

---

### **3. Rename Note**

- **URL:** `/{id}/rename`
- **Method:** POST
- **Description:** Rename a specific note.

#### **Parameters:**

| Name | Type    | Required | Description                         |
| ---- | ------- | -------- | ----------------------------------- |
| id   | Integer | Yes      | Note's primary key (path parameter) |

#### **Sample Request Body:**

```json
{
  "note": {
    "title": "NewTitleForNote"
  }
}
```

#### **Response:**

```json
{
  "code": 1,
  "msg": "success",
  "data": null
}
```

---

### **4. Edit Note Abstract**

- **URL:** `/{id}/edit_abs`
- **Method:** POST
- **Description:** Edit the abstract of a specific note.

#### **Parameters:**

| Name     | Type    | Required | Description               |
| -------- | ------- | -------- | ------------------------- |
| id       | Integer | Yes      | Note's primary key        |
| note.abs | String  | Yes      | New abstract for the note |

#### **Sample Request Body:** 

```json
{
  "note": {
    "abs": "NewAbstractForNote"
  }
}
```

#### **Response:**

```json
{
  "code": 1,
  "msg": "success",
  "data": null
}
```

---

### **5. Change Note Image**

- **URL:** `/{id}/change_img`
- **Method:** POST
- **Description:** Change the image of a specific note. The image can be a URL or a base64 encoded string.

#### **Parameters:**

| Name     | Type    | Required | Description                    |
| -------- | ------- | -------- | ------------------------------ |
| id       | Integer | Yes      | Note's primary key             |
| note.img | String  | Yes      | New image URL or base64 string |

#### **Sample Request Body (URL example):**

```json
{
  "note": {
    "img": "https://example.com/image.jpg"
  }
}
```

#### **Sample Request Body (base64 example):**

```json
{
  "note": {
    "img": "data:image/png;base64,iVBORw..."
  }
}
```

#### **Response:**

```json
{
  "code": 1,
  "msg": "success",
  "data": null
}
```

---

### **6. Delete Note**

- **URL:** `/{id}/delete`
- **Method:** DELETE
- **Description:** Delete a specific note by its ID.

#### **Parameters:**

| Name | Type    | Required | Description        |
| ---- | ------- | -------- | ------------------ |
| id   | Integer | Yes      | Note's primary key |

#### **Response:**

```json
{
  "code": 1,
  "msg": "success",
  "data": null
}
```

---

### **7. Add a New Note**

- **URL:** `/add`
- **Method:** POST
- **Description:** Add a new note.

#### **Parameters:**

| Name       | Type   | Required | Description          |
| ---------- | ------ | -------- | -------------------- |
| note.title | String | Yes      | Title of the note    |
| note.abs   | String | No       | Abstract of the note |

#### **Sample Request Body:**

```json
{
  "note": {
    "title": "SampleTitle",
    "abs": "SampleAbstract"
  }
}
```

#### **Response:**

```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "id": 99,
    "title": "SampleTitle",
    "abs": "SampleAbstract",
    // ... other fields with default or set values
  }
}
```



---

### **8. Get Note Details by ID**

- **URL:** `/{id}`
- **Method:** GET
- **Description:** Retrieve the details of a specific note by its ID.

#### **Parameters:**

| Name | Type    | Required | Description        |
| ---- | ------- | -------- | ------------------ |
| id   | Integer | Yes      | Note's primary key |

#### **Response:** 

A detailed `Note` object for the specified `id`.

```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "id": 1,
    "title": "Meeting Notes",
    "creatorId": 12345,
    "abs": "Brief overview of the meeting notes.",
    "createTime": "2023-08-22T14:30:00",
    "updateTime": "2023-08-22T15:00:00",
    "tags": [
      {
        "id": 1,
        "tagName": "Work"
      }
    ],
    "img": "path_to_image.jpg"
  }
}
```

#### **Response:**

```json
{
  "code": 1,
  "msg": "success",
  "data": null
}
```

---

### **9. Add Tag to Note by ID**

- **URL:** `/{id}/add_tag`
- **Method:** POST
- **Description:** Add a tag to a specific note.

#### **Parameters:**

| Name        | Type    | Required | Description                 |
| ----------- | ------- | -------- | --------------------------- |
| id          | Integer | Yes      | Note's primary key          |
| tag.tagName | String  | Yes      | Name of the tag to be added |

#### **Sample Request Body:** 

```json
{
  "tag": {
    "tagName": "SampleTag"
  }
}
```

#### **Response:**

```json
{
  "code": 1,
  "msg": "success",
  "data": null
}
```

---

### **10. Remove Tag from Note by ID**

- **URL:** `/{noteId}/remove_tag`
- **Method:** POST
- **Description:** Remove a tag from a specific note.

#### **Parameters:**

| Name        | Type    | Required | Description                   |
| ----------- | ------- | -------- | ----------------------------- |
| noteId      | Integer | Yes      | Note's primary key            |
| tag.tagName | String  | Yes      | Name of the tag to be removed |

#### **Sample Request Body:** 

```json
{
  "tag": {
    "tagName": "SampleTagToRemove"
  }
}
```

#### **Response:**

```json
{
  "code": 1,
  "msg": "success",
  "data": null
}
```

---

### **11. Add New Tag**

- **URL:** `/tags/add`
- **Method:** POST
- **Description:** Add a new tag.

#### **Parameters:**

| Name        | Type   | Required | Description         |
| ----------- | ------ | -------- | ------------------- |
| tag.tagName | String | Yes      | Name of the new tag |

#### **Sample Request Body:** 

```json
{
  "tag": {
    "tagName": "NewTag"
  }
}
```

#### **Response:**

```json
{
  "code": 1,
  "msg": "success",
  "data": null
}
```

---

### **12. Save Note Content**

- **URL:** `/{id}/save`
- **Method:** POST
- **Description:** Save the content of a note.

#### **Parameters:**

| Name         | Type    | Required | Description                        |
| ------------ | ------- | -------- | ---------------------------------- |
| id           | Integer | Yes      | Note's primary key                 |
| note.content | String  | Yes      | Content of the note in HTML format |

#### **Sample Request Body:** 

```json
{
  "note": {
    "content": "<p>This is a sample content.</p>"
  }
}
```

#### **Response:**

```json
{
  "code": 1,
  "msg": "success",
  "data": null
}
```

---

### **13. Make Note Public**

- **URL:** `/{id}/public`
- **Method:** POST
- **Description:** Make a specific note public.

#### **Parameters:**

| Name         | Type    | Required | Description                        |
| ------------ | ------- | -------- | ---------------------------------- |
| id           | Integer | Yes      | Note's primary key                 |
| note.content | String  | Yes      | Content of the note in HTML format |

#### **Sample Request Body:** 

```json
{
  "note": {
    "content": "<p>This is a sample content for a public note.</p>"
  }
}
```

#### **Response:**

```json
{
  "code": 1,
  "msg": "success",
  "data": null
}
```

---

## **Post **

### **Base URL:**

`/api/posts`

---

### **1. Get Post by ID**

- **URL:** `/{id}`
- **Method:** GET
- **Description:** Retrieve complete information of a specific post, including its content, based on its ID.

#### **Parameters:**

| Name | Type    | In   | Required | Description            |
| ---- | ------- | ---- | -------- | ---------------------- |
| id   | Integer | Path | Yes      | ID of the desired post |

#### **Response Body (On Success):**

```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "id": 12,
    "noteId": 112,
    "userId": 23,
    "title": "Sample Post Title",
    "abs": "This is a short abstract of the post.",
    "content": "This is the complete content of the sample post. It can be much longer.",
    "createTime": "2023-08-15T10:20:30",
    "updateTime": "2023-08-17T12:30:45",
    "img": "https://sample.com/image.jpg",
    "creator": {
      "id": 23,
      "username": "sampleUser",
      "avatar": "https://sample.com/avatar.jpg",
    },
    "tags": [
      {
        "id": 1,
        "tagName": "sampleTag1"
      },
      {
        "id": 2,
        "tagName": "sampleTag2"
      }
    ],
    "likeCount": 100,
    "commentCount": 20,
    "viewCount": 1500
  }
}
```

### **2. Delete Post by ID**

- **URL:** `/{postId}/delete`
- **Method:** DELETE
- **Description:** Delete a post by its ID. A token must be provided in the request header for interceptor validation. Upon validation, the token is parsed to obtain the `userId`.

#### **Parameters:**

| Name   | Type    | In     | Required | Description                      |
| ------ | ------- | ------ | -------- | -------------------------------- |
| postId | Integer | Path   | Yes      | ID of the post to be deleted     |
| token  | String  | Header | Yes      | Token for interceptor validation |

#### **Response Body (On Success):**

```json
{
  "code": 1,
  "msg": "success",
  "data": null
}
```

---

Alright, based on the provided endpoint and information, here's the API documentation for the "Get Most Viewed Posts" endpoint:

---

### **3. Get Most Viewed Posts**

- **URL:** `/most_viewed`
- **Method:** GET
- **Description:** Retrieves a list of the most viewed posts.

#### **Parameters:**

| Name     | Type    | In    | Required | Default | Description                               |
| -------- | ------- | ----- | -------- | ------- | ----------------------------------------- |
| page     | Integer | Query | No       | 1       | Specifies which page of results to return |
| pageSize | Integer | Query | No       | 6       | Number of posts to return per page        |

#### **Response Body (On Success):**

```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "total": 100,  // total number of most viewed posts
    "rows": [
      {
        "id": 12,
        "noteId": 345,
        "userId": 789,
        "title": "Sample Post Title 1",
        "abs": "Sample abstract 1",
        "createTime": "2023-08-22T10:30:00",
        "updateTime": "2023-08-23T12:30:00",
        "img": "https://sample.com/image1.jpg",
        "creator": {
          "id": 789,
          "username": "sampleUser1",
          "avatar": "https://sample.com/avatar1.jpg",
        },
        "likeCount": 120,
        "commentCount": 45,
        "viewCount": 1000
      },
     
    ]
  }
}
```



---

### **4. Get Most Liked Posts**

- **URL:** `/most_liked`
- **Method:** GET
- **Description:** Retrieves a list of the most liked posts.

#### **Parameters:**

| Name     | Type    | In    | Required | Default | Description                               |
| -------- | ------- | ----- | -------- | ------- | ----------------------------------------- |
| page     | Integer | Query | No       | 1       | Specifies which page of results to return |
| pageSize | Integer | Query | No       | 6       | Number of posts to return per page        |

#### **Response Body (On Success):**

```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "total": 150,  // total number of most liked posts
    "rows": [
      {
        "id": 13,
        "noteId": 346,
        "userId": 789,
        "title": "Sample Post Title A",
        "abs": "Sample abstract A",
        "createTime": "2023-08-22T11:30:00",
        "updateTime": "2023-08-23T13:30:00",
        "img": "https://sample.com/imageA.jpg",
        "creator": {
          "id": 789,
          "username": "sampleUserA",
          "avatar": "https://sample.com/avatarA.jpg",
        },
        "likeCount": 250,
        "commentCount": 60,
        "viewCount": 850
      },
      // ... Additional posts, up to a total of 'pageSize' posts
    ]
  }
}
```

------

### **5. Get Recent Posts**

- **URL:** `/recent`
- **Method:** GET
- **Description:** Retrieve a list of the most recent posts, paginated.

#### **Parameters:**

| Name     | Type    | In    | Required | Default | Description                         |
| -------- | ------- | ----- | -------- | ------- | ----------------------------------- |
| page     | Integer | Query | No       | 1       | Number of the page to retrieve      |
| pageSize | Integer | Query | No       | 6       | Number of posts to display per page |

#### **Response Body (On Success):**

```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "total": 120,
    "rows": [
      {
        "id": 15,
        "noteId": 115,
        "userId": 24,
        "title": "Sample Recent Post Title 1",
        "abs": "Abstract of the recent post 1.",
        "createTime": "2023-08-20T11:10:20",
        "updateTime": "2023-08-21T12:15:25",
        "img": "https://sample.com/image1.jpg",
        "likeCount": 85,
        "commentCount": 14,
        "viewCount": 1100,
        "creator": {
          "id": 23,
          "username": "sampleUser",
          "avatar": "https://sample.com/avatar.jpg"
        }
      },
      {
        "id": 16,
        "noteId": 116,
        "userId": 25,
        "title": "Sample Recent Post Title 2",
        "abs": "Abstract of the recent post 2.",
        "createTime": "2023-08-19T13:14:15",
        "updateTime": "2023-08-20T15:18:19",
        "img": "https://sample.com/image2.jpg",
        "tags": [
          {
            "id": 4,
            "tagName": "sampleTag4"
          }
        ],
        "likeCount": 95,
        "commentCount": 18,
        "viewCount": 1250,
        "creator": {
          "id": 24,
          "username": "sampleUser2",
          "avatar": "https://sample.com/avatar2.jpg"
        }
      }
    ]
  }
}
```

---

### **6. Search Posts**

- **URL:** `/api/posts/search`
- **Method:** GET
- **Description:** Search for posts based on the given criteria: title, content, or tag.

#### **Parameters:**

| Name     | Type    | In    | Required | Default | Description                         |
| -------- | ------- | ----- | -------- | ------- | ----------------------------------- |
| page     | Integer | Query | No       | 1       | Number of the page to retrieve      |
| pageSize | Integer | Query | No       | 6       | Number of posts to display per page |
| title    | String  | Query | No       | null    | Search for posts by their title.    |
| content  | String  | Query | No       | null    | Search for posts by their content.  |
| tag      | String  | Query | No       | null    | Search for posts by a specific tag. |

*Note: At least one of the `title`, `content`, or `tag` parameters should be provided.*

#### **Response Body (On Success):**

```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "total": 50,
    "rows": [
      {
        "id": 30,
        "noteId": 130,
        "userId": 27,
        "title": "Sample Search Post Title 1",
        "abs": "Abstract of the search post 1.",
        "createTime": "2023-08-22T11:12:13",
        "updateTime": "2023-08-23T14:15:16",
        "img": "https://sample.com/searchimage1.jpg",
        "tags": [
          {
            "id": 6,
            "tagName": "sampleTag6"
          }
        ],
        "likeCount": 100,
        "commentCount": 20,
        "viewCount": 1400,
        "creator": {
          "id": 25,
          "username": "searchUser1",
          "avatar": "https://sample.com/searchavatar1.jpg"
        }
      },
      // ... Other posts, up to a total of 'pageSize' posts
    ]
  }
}
```

---

### **7. Get Popular Tags**

- **URL:** `/api/posts/tags`
- **Method:** GET
- **Description:** Retrieves the list of popular tags.

#### **Parameters:**

| Name     | Type    | In    | Required | Default | Description                        |
| -------- | ------- | ----- | -------- | ------- | ---------------------------------- |
| page     | Integer | Query | No       | 1       | Number of the page to retrieve     |
| pageSize | Integer | Query | No       | 6       | Number of tags to display per page |

#### **Response Body (On Success):**

```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "total": 100,
    "rows": [
      {
        "id": 12,
        "tagName": "sampleTag12"
      },
      {
        "id": 13,
        "tagName": "sampleTag13"
      },
      // ... Other tags, up to a total of 'pageSize' tags
    ]
  }
}
```

#### **Response Body (On Failure):**

```json
{
  "code": 0,
  "msg": "error message detailing the reason for failure",
  "data": null
}
```

---

## **User **

### **Base URL:**

`/api/user`

---

### **1. Get User by Username**

- **URL:** `/{username}`
- **Method:** GET
- **Description:** Retrieves details of a user based on the provided username.

#### **Path Parameters:**

| Name     | Type   | Description                       |
| -------- | ------ | --------------------------------- |
| username | String | The username of the desired user. |

#### **Response Body (On Success):**

```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "id": 123,
    "username": "sampleUser",
    "avatar": "https://sample.com/avatar.jpg",
    "createTime": "2023-08-01T10:00:00",
    "updateTime": "2023-08-02T12:00:00",
    "email": "sampleUser@email.com",
    "bio": "Sample bio text",
    "backgroundImage": "https://sample.com/background.jpg"
  }
}
```

---

### **2. Get User's Posts**

- **URL:** `/post`
- **Method:** POST
- **Description:** Fetches the posts associated with a particular user.

#### **Request Body:**

```json
{
  "id": 123,
  "username": "sampleUser",
  // ... Other optional User properties.
}
```

#### **Response Body (On Success):**

```json
{
  "code": 1,
  "msg": "success",
  "data": [
    {
      "id": 13,
      "noteId": 346,
      "userId": 789,
      "title": "Sample Post Title A",
      "abs": "Sample abstract A",
      "createTime": "2023-08-22T11:30:00",
      "updateTime": "2023-08-23T13:30:00",
      "img": "https://sample.com/imageA.jpg",
    },
    // ... Additional posts.
  ]
}
```

---

### **3. Update User Information**

- **URL:** `update`
- **Method:** POST
- **Description:** Updates user information. Users can only update their own profiles.

#### **Request Body:**

```json
{
  "id": 123,   // This is inferred from the userId attribute, not required in the body.
  "username": "sampleUser",
  "avatar": "https://sample.com/newavatar.jpg",
  "bio": "Updated bio text",
  // ... Other User properties to be updated.
}
```

#### **Response Body (On Success):**

```json
{
  "code": 1,
  "msg": "success",
  "data": null
}
```

#### **Response Body (On Failure):**

```json
{
  "code": 0,
  "msg": "error message detailing the reason for failure",
  "data": null
}
```

---

## **Notification**

**BASE URL:** `/api/notifications`

---

### **1. Get Unread Notifications**

- **URL:** `/unread`
- **Method:** GET

#### **Parameters:**

| Name  | Type   | In     | Required | Description                      |
| ----- | ------ | ------ | -------- | -------------------------------- |
| token | String | Header | Yes      | Token for interceptor validation |

#### **Response Body (On Success):**

```json
{
  "code": 1,
  "msg": "success",
  "data": [
    {
  "id": 45,
  "senderId": 102,
  "receiverId": 105,
  "content": "JohnDoe liked your post on 'How to bake a cake'",
  "type": 1,
  "relatedPostId": 67,
  "status": 0,
  "createTime": "2023-08-22T14:30:45.678",
  "sender": {
    "id": 102,
    "username": "JohnDoe",
    "avatar": "https://samplewebsite.com/avatar/JohnDoe.jpg"
  }
},
	//...
  ]
}
```

---

### **2. Get Read Notifications**

- **URL:** `/read`
- **Method:** GET

#### **Parameters:**

| Name  | Type   | In     | Required | Description                      |
| ----- | ------ | ------ | -------- | -------------------------------- |
| token | String | Header | Yes      | Token for interceptor validation |

#### **Response Body (On Success):**

```json
{
  "code": 1,
  "msg": "success",
  "data": [
    {
  "id": 45,
  "senderId": 102,
  "receiverId": 105,
  "content": "JohnDoe liked your post on 'How to bake a cake'",
  "type": 1,
  "relatedPostId": 67,
  "status": 0,
  "createTime": "2023-08-22T14:30:45.678",
  "sender": {
    "id": 102,
    "username": "JohnDoe",
    "avatar": "https://samplewebsite.com/avatar/JohnDoe.jpg"
  	}
	},

    // ... 
  ]
}
```

---

### **3. Mark a Notification as Read**

- **URL:** `/read`
- **Method:** POST

#### **Parameters:**

| Name            | Type   | In     | Required | Description                                 |
| --------------- | ------ | ------ | -------- | ------------------------------------------- |
| notification.id | Int    | Body   | Yes      | Notification object with ID to mark as read |
| token           | String | Header | Yes      | Token for interceptor validation            |

#### **Response Body (On Success):**

```json
{
  "code": 1,
  "msg": "success",
  "data": null
}
```

---

### **4. Delete a Notification**

- **URL:** `/{id}`
- **Method:** DELETE

#### **Parameters:**

| Name  | Type    | In     | Required | Description                          |
| ----- | ------- | ------ | -------- | ------------------------------------ |
| id    | Integer | Path   | Yes      | ID of the notification to be deleted |
| token | String  | Header | Yes      | Token for interceptor validation     |

#### **Response Body (On Success):**

```json
{
  "code": 1,
  "msg": "success",
  "data": null
}
```

---

### **5. Get Unread Notifications Count**

- **URL:** `/unread_count`
- **Method:** GET

#### **Parameters:**

| Name  | Type   | In     | Required | Description                      |
| ----- | ------ | ------ | -------- | -------------------------------- |
| token | String | Header | Yes      | Token for interceptor validation |

#### **Response Body (On Success):**

```json
{
  "code": 1,
  "msg": "success",
  "data":  7
}
```

---

## Like

### Base URL
`/api/posts`

### 1. Like a Post

- **Endpoint:** `/like`
- **Method:** POST
- **Description:** Allows a user to like a post.

| Name   | Type    | In     | Required | Description                      |
| ------ | ------- | ------ | -------- | -------------------------------- |
| postId | Integer | Path   | Yes      | ID of the post to be liked.      |
| token  | String  | Header | Yes      | Token for interceptor validation |

**Success Response:**  

```json
{
  "code": 1,
  "msg": "success",
  "data": null
}
```

### 2. Unlike a Post

- **Endpoint:** `s/{postId}/unlike`
- **Method:** DELETE
- **Description:** Allows a user to unlike a post.

| Name   | Type    | In     | Required | Description                      |
| ------ | ------- | ------ | -------- | -------------------------------- |
| postId | Integer | Path   | Yes      | ID of the post to be unliked.    |
| token  | String  | Header | Yes      | Token for interceptor validation |

**Success Response:**  

```json
{
  "code": 1,
  "msg": "success",
  "data": null
}
```

### 3. Get Likes for a Post

- **Endpoint:** `/api/posts/{id}/likes`
- **Method:** GET
- **Description:** Retrieves the total number of likes for a post.

| Name | Type    | In   | Required | Description     |
| ---- | ------- | ---- | -------- | --------------- |
| id   | Integer | Path | Yes      | ID of the post. |

**Success Response:**  

```json
{
  "code": 1,
  "msg": "success",
  "data": 32
}
```

### 4. Check if User Liked a Post

- **Endpoint:** `/api/posts/{postId}/is_liked`
- **Method:** GET
- **Description:** Checks whether a user has liked a specific post.

| Name   | Type    | In     | Required | Description                      |
| ------ | ------- | ------ | -------- | -------------------------------- |
| postId | Integer | Path   | Yes      | ID of the post to be checked.    |
| token  | String  | Header | Yes      | Token for interceptor validation |

**Success Response:**  

```json
{
  "code": 1,
  "msg": "success",
  "data": true
}
```

---

## Comment

### Base URL
 `/api/posts`

### 1. Get Comments for a Post

- **Endpoint:** `/{postId}/comments`
- **Method:** GET
- **Description:** Retrieves all comments associated with a specific post. Each comment includes: id, postId, userId, content, createTime, and user. The user, nested within the comment, only includes the id, username, and avatar fields.

| Name   | Type    | In   | Required | Description                                      |
| ------ | ------- | ---- | -------- | ------------------------------------------------ |
| postId | Integer | Path | Yes      | ID of the post whose comments are to be fetched. |

**Success Response:**  

```json
{
  "code": 1,
  "msg": "success",
  "data": [
    {
      "id": 123,
      "postId": 456,
      "userId": 789,
      "content": "This is a comment.",
      "createTime": "2023-08-22T14:35:00",
      "user": {
        "id": 789,
        "username": "JohnDoe",
        "avatar": "link_to_avatar.jpg"
      }
    },
    ... (other comments)
  ]
}
```

### 2. Comment on a Post

- **Endpoint:** `/{postId}/comment`
- **Method:** POST
- **Description:** Allows a user to add a comment to a specific post. The user's ID is obtained from the token in the request header. The comment, received in the request body, must include the content and postId fields.

| Name    | Type    | In     | Required | Description                                       |
| ------- | ------- | ------ | -------- | ------------------------------------------------- |
| postId  | Integer | Path   | Yes      | ID of the post to be commented on.                |
| token   | String  | Header | Yes      | Token for interceptor validation                  |
| comment | Object  | Body   | Yes      | Comment object containing `content` and `postId`. |

**Success Response:**  

```json
{
  "code": 1,
  "msg": "success",
  "data": null
}
```

