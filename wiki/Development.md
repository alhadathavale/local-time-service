# Development Guide

## Prerequisites

- **Java:** JDK 8 or higher
- **Maven:** 3.6 or higher
- **Git:** Latest version
- **IDE:** IntelliJ IDEA, VS Code, or Eclipse

### Verify Installation

```bash
java -version
mvn -version
git --version
```

## Local Setup

### 1. Clone the Repository

```bash
git clone https://github.com/alhadathavale/local-time-service.git
cd local-time-service
```

### 2. Build the Project

```bash
mvn clean install
```

This will:
- Download dependencies
- Compile the source code
- Run tests
- Create JAR file in `target/`

### 3. Run the Application

**Using Maven:**
```bash
mvn spring-boot:run
```

**Or using Java directly:**
```bash
java -jar target/local-time-service-*.jar
```

The service will start on `http://localhost:8080`

### 4. Test the Endpoint

```bash
curl http://localhost:8080/LocalTime
```

Expected response:
```json
{
  "clientIp": "127.0.0.1",
  "timezone": "America/Chicago",
  "localTime": "2026-04-15T10:32:18.123-05:00[America/Chicago]"
}
```

## IDE Setup

### IntelliJ IDEA

1. **Open Project:**
   - File → Open → select `local-time-service` folder
   - Select "Open as Project"

2. **Configure JDK:**
   - Settings → Project Structure → Project
   - Set Project SDK to JDK 8+

3. **Maven Configuration:**
   - Settings → Build, Execution, Deployment → Build Tools → Maven
   - Set Maven home directory

4. **Run Configuration:**
   - Run → Edit Configurations
   - Add new Spring Boot configuration
   - Select `LocalTimeServiceApplication` as main class
   - Click Run

### VS Code

1. **Install Extensions:**
   - Extension Pack for Java (Microsoft)
   - Spring Boot Extension Pack (Pivotal)

2. **Open Folder:**
   - File → Open Folder → select `local-time-service`

3. **Run Application:**
   - Press Ctrl+F5 to run with debugging
   - Or use Terminal → Run Task → Maven: spring-boot:run

### Eclipse

1. **Import Project:**
   - File → Import → Existing Maven Projects
   - Browse to `local-time-service` folder
   - Click Finish

2. **Configure JDK:**
   - Window → Preferences → Java → Installed JREs
   - Set default JRE to JDK 8+

3. **Run:**
   - Right-click project → Run As → Spring Boot App

## Running Tests

### Run All Tests

```bash
mvn test
```

### Run Specific Test Class

```bash
mvn test -Dtest=LocalTimeControllerTest
```

### Run with Coverage

```bash
mvn clean test jacoco:report
```

Coverage report: `target/site/jacoco/index.html`

## Code Structure

```
src/
├── main/
│   ├── java/com/example/localtime/
│   │   ├── LocalTimeServiceApplication.java    # Spring Boot entry point
│   │   ├── LocalTimeController.java            # REST endpoint handler
│   │   ├── ClientIpResolver.java               # IP resolution logic
│   │   ├── TimezoneLookupService.java          # Timezone lookup via ipwho.is
│   │   └── LocalTimeResponse.java              # Response DTO
│   └── resources/
│       ├── application.properties              # Configuration
│       └── application.yml                     # YAML config (optional)
└── test/
    ├── java/com/example/localtime/
    │   ├── LocalTimeControllerTest.java
    │   ├── ClientIpResolverTest.java
    │   └── TimezoneLookupServiceTest.java
```

## Key Classes

### LocalTimeController
Handles HTTP requests to `/LocalTime` endpoint. Calls `ClientIpResolver` and `TimezoneLookupService`.

### ClientIpResolver
Extracts client IP from request. Checks:
1. `X-Forwarded-For` header (if proxy trust enabled)
2. `X-Real-IP` header
3. `request.getRemoteAddr()`

### TimezoneLookupService
Calls ipwho.is API to get timezone for IP address. Implements retry logic and caching.

### LocalTimeResponse
DTO for API response containing:
- `clientIp`: Resolved IP address
- `timezone`: IANA timezone identifier
- `localTime`: Current time in ISO-8601 format

## Development Workflow

### 1. Create a Feature Branch

```bash
git checkout -b feature/your-feature-name
```

### 2. Make Changes

Edit code, add tests, update documentation.

### 3. Run Tests Locally

```bash
mvn clean test
```

### 4. Build and Test Locally

```bash
mvn clean install
mvn spring-boot:run
```

### 5. Commit Changes

```bash
git add .
git commit -m "feat: description of changes"
```

### 6. Push and Create Pull Request

```bash
git push origin feature/your-feature-name
```

## Code Style

- **Language:** Java 8+
- **Formatting:** Use IDE auto-format (Ctrl+Shift+L in most IDEs)
- **Naming:** camelCase for variables/methods, PascalCase for classes
- **Documentation:** Add Javadoc for public methods

## Testing

### Unit Tests
Test individual classes in isolation:
```java
@Test
public void testClientIpResolution() {
    // Test code
}
```

### Integration Tests
Test Spring context and components:
```java
@SpringBootTest
public class IntegrationTest {
    // Test code
}
```

### Test Data
Use mock objects and fixtures:
```java
@Mock
TimezoneLookupService timezoneLookupService;
```

## Debugging

### Enable Debug Logging

```properties
logging.level.com.example.localtime=DEBUG
```

### Debug in IDE

1. Set breakpoint by clicking line number
2. Run → Debug (or Shift+F9)
3. Use Step Over (F10), Step Into (F11) to navigate

### Remote Debugging

Start application with remote debug flags:
```bash
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
```

Connect IDE debugger to `localhost:5005`.

## Common Maven Commands

| Command | Purpose |
|---------|---------|
| `mvn clean` | Remove `target/` directory |
| `mvn compile` | Compile source code |
| `mvn test` | Run unit tests |
| `mvn package` | Create JAR file |
| `mvn install` | Install to local Maven repository |
| `mvn spring-boot:run` | Run Spring Boot application |
| `mvn clean install -DskipTests` | Build without running tests |

## Troubleshooting Development

#### Maven not found
```bash
# Ensure Maven is in PATH or use:
./mvnw clean install  # On Linux/Mac
mvnw.cmd clean install  # On Windows
```

#### Port 8080 already in use
```bash
# Use different port:
mvn spring-boot:run -Dserver.port=9090
```

#### Import statements showing errors
- Maven → Reload Projects (right-click project)
- Or: `mvn clean eclipse:clean eclipse:eclipse`

#### Slow Maven builds
```bash
# Use offline mode if dependencies cached
mvn -o clean install

# Or run in parallel
mvn -T 1C clean install
```

## Contributing

1. Fork repository
2. Create feature branch: `git checkout -b feature/name`
3. Make changes and add tests
4. Run `mvn clean test` to ensure all tests pass
5. Commit with clear message
6. Push to your fork
7. Open Pull Request with description

## Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Maven Documentation](https://maven.apache.org/)
- [Java 8 Documentation](https://docs.oracle.com/javase/8/docs/)
- [ipwho.is API](https://ipwho.is/)