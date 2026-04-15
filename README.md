# local-time-service

Spring Boot REST service with one endpoint:

- `GET /LocalTime`

The endpoint resolves the client IP from the request, uses `ipwho.is` to look up the timezone for that IP, and returns the current time in that timezone.

## Project structure

- `src/main/java/com/example/localtime/LocalTimeServiceApplication.java`
- `src/main/java/com/example/localtime/LocalTimeController.java`
- `src/main/java/com/example/localtime/ClientIpResolver.java`
- `src/main/java/com/example/localtime/TimezoneLookupService.java`
- `src/main/java/com/example/localtime/LocalTimeResponse.java`

## Requirements

- Java 8+
- Maven 3.6+

## Run

```powershell
cd C:\Alhad\Projects\local-time-service
mvn spring-boot:run
```

Then call:

```powershell
curl http://localhost:8080/LocalTime
```

## Example response

```json
{
  "clientIp": "8.8.8.8",
  "timezone": "America/Chicago",
  "localTime": "2026-04-14T10:32:18.123-05:00[America/Chicago]"
}
```

## Notes

- IP-based geolocation is approximate.
- If the request comes from localhost, the service falls back to the server's system timezone.
- In production behind a proxy or load balancer, trust `X-Forwarded-For` only when your infrastructure sets it.
- `ipwho.is` is used here for simplicity. For production, use a managed GeoIP provider or a local GeoIP database.
