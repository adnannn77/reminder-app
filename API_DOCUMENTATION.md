# Authentication

## POST /api/login

### URL

POST /api/login

### Request Body

```json
{
    "email": "admin@gmail.com",
    "password": "123456"
}
```

### Success Response

```json
{
    "status": true,
    "message": "Login berhasil",
    "data": {
        "id": 1,
        "nama": "Admin",
        "email": "admin@gmail.com"
    }
}
```

### Error Response

```json
{
    "status": false,
    "message": "Password salah"
}
```