# Troubleshooting

## Common Issues and Solutions

### IP Resolution Issues

#### Problem: Always getting localhost/127.0.0.1
**Cause:** Request is from localhost or service is behind a proxy not forwarding headers.

**Solutions:**
1. If behind a proxy/load balancer, enable proxy trust:
   ```properties
   client-ip.trust-proxy=true
   ```

2. Ensure proxy is sending `X-Forwarded-For` header:
   ```bash
   # Check if header is present
   curl -v http://localhost:8080/LocalTime | grep -i "x-forwarded-for"
   ```

3. Check trusted proxy list configuration:
   ```properties
   client-ip.trusted-proxies=YOUR_PROXY_IP
   ```

#### Problem: Getting wrong IP address
**Cause:** Multiple proxies in chain, X-Forwarded-For header includes intermediate proxies.

**Solution:** The service uses the first IP in X-Forwarded-For chain. Verify your proxy configuration sends IPs in correct order.

### Timezone Lookup Failures

#### Problem: 503 Service Unavailable / "Timezone lookup service unavailable"
**Cause:** ipwho.is service is down or unreachable.

**Solutions:**
1. Check service status:
   ```bash
   curl https://ipwho.is/?ip=8.8.8.8
   ```

2. Increase timeout in configuration:
   ```properties
   timezone.lookup.timeout=10000
   timezone.lookup.retries=3
   ```

3. Check network connectivity:
   ```bash
   ping ipwho.is
   nslookup ipwho.is
   ```

#### Problem: Rate limiting / 429 Too Many Requests
**Cause:** ipwho.is has rate limiting (typically 50 requests/hour for free tier).

**Solutions:**
1. Enable caching to reduce API calls:
   ```properties
   timezone.lookup.cache.enabled=true
   timezone.lookup.cache.ttl=1440
   ```

2. Upgrade to a commercial GeoIP provider for higher limits

3. Implement local caching layer (Redis, etc.)

#### Problem: Incorrect timezone returned
**Cause:** IP geolocation is approximate; location data may be inaccurate for mobile/VPN users.

**Note:** IP-based geolocation is never 100% accurate. For critical applications, consider requiring explicit timezone selection from users.

### Performance Issues

#### Problem: Slow response time (> 1 second)
**Cause:** 
- Timeout configured too high
- Network latency to ipwho.is service
- First-time cache miss

**Solutions:**
1. Reduce timeout:
   ```properties
   timezone.lookup.timeout=3000
   ```

2. Reduce retries:
   ```properties
   timezone.lookup.retries=1
   ```

3. Check network latency:
   ```bash
   ping ipwho.is
   ```

4. Verify caching is enabled:
   ```properties
   timezone.lookup.cache.enabled=true
   ```

#### Problem: High memory usage
**Cause:** Timezone cache growing unbounded.

**Solutions:**
1. Reduce cache TTL:
   ```properties
   timezone.lookup.cache.ttl=360  # 6 hours instead of 24
   ```

2. Implement cache size limit (requires code change)

3. Monitor cache size:
   ```bash
   # Add debug logging
   logging.level.com.example.localtime=DEBUG
   ```

### Proxy/Load Balancer Configuration

#### Behind nginx
```nginx
server {
    listen 80;
    
    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header X-Forwarded-For $remote_addr;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```

Enable trust in application:
```properties
client-ip.trust-proxy=true
client-ip.trusted-proxies=127.0.0.1
```

#### Behind AWS Load Balancer
```properties
client-ip.trust-proxy=true
# AWS load balancer IPs depend on region
client-ip.trusted-proxies=10.0.0.0/8
```

### Docker Issues

#### Problem: Cannot reach localhost:8080 from host
**Cause:** Container networking, firewall rules.

**Solutions:**
1. Map port explicitly:
   ```bash
   docker run -p 8080:8080 local-time-service
   ```

2. Check container networking:
   ```bash
   docker inspect CONTAINER_ID | grep IPAddress
   ```

3. Check firewall:
   ```bash
   sudo ufw allow 8080
   ```

### Logging and Debugging

Enable debug logging to troubleshoot:

```properties
logging.level.com.example.localtime=DEBUG
logging.level.org.springframework.web=DEBUG
```

Check logs for error details:
```bash
tail -f logs/application.log | grep ERROR
```

### Health Check

For production deployments, add a simple health check:
```bash
curl -X GET http://localhost:8080/LocalTime \
  -H "X-Forwarded-For: 8.8.8.8" \
  -w "\nStatus: %{http_code}\n"
```

Expected: 200 status with valid JSON response