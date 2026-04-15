# API Documentation

## Overview
This document provides comprehensive API documentation for the local-time-service repository.

## Endpoints

### 1. Get Current Time
- **Endpoint:** `/api/time/current`
- **Method:** `GET`

#### Request Format
```
// No parameters required
```

#### Response Format
```json
{
    "status": "success",
    "data": {
        "currentTime": "2026-04-15T05:27:00Z"
    }
}
```

#### Error Codes
- `400 Bad Request`: Invalid request.
- `500 Internal Server Error`: Unexpected error occurred.

#### Example
**Request:**  
```
GET /api/time/current
```
**Response:**  
```json
{
    "status": "success",
    "data": {
        "currentTime": "2026-04-15T05:27:00Z"
    }
}
```

### 2. Get Time by Location
- **Endpoint:** `/api/time/location`
- **Method:** `GET`

#### Request Format
```json
{
    "location": "Asia/Tokyo"
}
```

#### Response Format
```json
{
    "status": "success",
    "data": {
        "location": "Asia/Tokyo",
        "currentTime": "2026-04-15T14:27:00+09:00"
    }
}
```

#### Error Codes
- `404 Not Found`: Location not found.
- `500 Internal Server Error`: Unexpected error occurred.

#### Example
**Request:**  
```
GET /api/time/location?location=Asia/Tokyo
```
**Response:**  
```json
{
    "status": "success",
    "data": {
        "location": "Asia/Tokyo",
        "currentTime": "2026-04-15T14:27:00+09:00"
    }
}
```

## Conclusion
This completes the API documentation for the local-time-service. Please refer to the endpoint details for usage and further examples.