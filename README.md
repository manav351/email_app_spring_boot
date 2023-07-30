
# User Registeration Application

This projects implements a user registration where user can enter their credentials and the server will send them a welcome mail.



## Features

- Registration mail is sent as soon as the user registers
- Implementation of CRON jobs to send a daily forcast of the registered users to Admin


## User API CRUD Reference

#### Get Users based on filtering

```http
  GET /user/records
```

| Parameter | Type      | Mandatory/Optional | Description |
| :-------- | :-------  | :----------------- | :------------------------- |
| `emailId` | `string`  | **Optional**       | User's emailId to perform filtering |
| `id`      | `integer` | **Optional**       | User's Id to perform filtering |

* If both of them provided, the preference will be given to user_id
* If both of them are not provided, all the results, i.e., GET user/records/all results will be shown

#### Get all Users

```http
  GET /user/records/all
```
#### Add new unique users

```http
  POST /user/record
```

| Parameter   | Type      | Mandatory/Optional | Description |
| :--------   | :-------  | :----------------- | :------------------------- |
| `emailId`   | `string`  | **Mandatory**      | User's emailId |
| `first_name`| `integer` | **Mandatory**      | User's First Name |
| `last_name` | `integer` | **Mandatory**      | User's Last Name |

* Additional fields of the object will be dropped.
* In absense of above fields, error will be thrown

#### Delete Users

```http
  DELETE /user/record
```

| Parameter | Type      | Mandatory/Optional | Description |
| :-------- | :-------  | :----------------- | :------------------------- |
| `id`      | `Integer` | **Mandatory**      | User_id |


## Email API Reference

#### Send Email

```http
  POST /email/
```

| Parameter | Type      | Mandatory/Optional | Description |
| :-------- | :-------  | :----------------- | :------------------------- |
| `emailId` | `string`  | **Optional**       | User's emailId |
| `id`      | `integer` | **Optional**       | User's Id |

Note:
* Either one is Mandatory, if both provided, then it will verify if email and id belongs to same user or not, if they do belong, the mail will be sent, else error will be thrown.
* The mail can only be sent to users once.

#### reset/all

```http
  POST /email/reset/all
```
* This resets the sent status of all the users. (FOR DEVELOPMENT PURPOSE ONLY)

#### reset based on id

```http
  POST /email/reset/{id}
```
* Resets based on user_id