# Configuration Settings for Local Time Service

## Server Properties
- **Port**: 8080
- **Context Path**: /api

## Timezone Lookup Settings
- **Service**: Timezone API
- **Fallback Timezone**: UTC

## Client IP Configuration
- **Enable IP Logging**: true
- **Trusted Proxies**: 192.168.0.0/16

## Caching Options
- **Cache Duration**: 1 hour
- **Cache Type**: In-Memory

## Logging Setup
- **Log Level**: INFO
- **Log Format**: JSON

## Environment Variables
- **DATABASE_URL**: jdbc:mysql://localhost:3306/timeservice
- **REDIS_URL**: redis://localhost:6379

## Spring Profiles
- **Active Profiles**: dev, test, prod