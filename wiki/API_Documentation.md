# API Documentation

## LocalTime Endpoint

### Request
**URL:** `/api/localtime`

**Method:** `GET`

### Request Examples
```http
GET /api/localtime?city=London
```

### Response
```json
{
  "timezone": "Europe/London",
  "local_time": "2026-04-15 05:10:10"
}
```

### Error Codes
- `400 Bad Request`: Invalid city name.
- `404 Not Found`: City not found.
- `500 Internal Server Error`: Unexpected error occurred.