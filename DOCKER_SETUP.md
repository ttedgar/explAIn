# Docker Setup Guide

This guide explains how to run the explAIn application using Docker.

## Prerequisites

- Docker installed ([Download here](https://www.docker.com/products/docker-desktop))
- Docker Compose installed (included with Docker Desktop)
- Google Gemini API key ([Get one here](https://aistudio.google.com/app/apikey))

## Quick Start

### 1. Set up your environment variables

Create a `.env` file in the project root:

```bash
cp .env.example .env
```

Edit `.env` and add your actual Gemini API key:

```
GEMINI_API_KEY=your_actual_api_key_here
```

### 2. Run the application

```bash
docker-compose up --build
```

**First time setup:** This will take 3-5 minutes to download dependencies and build images.

**Subsequent runs:** Use `docker-compose up` (without `--build`) for faster startup.

### 3. Access the application

- **Frontend:** http://localhost (or http://localhost:80)
- **Backend API:** http://localhost:8080

### 4. Stop the application

Press `Ctrl+C` in the terminal, then run:

```bash
docker-compose down
```

## Useful Commands

### View logs
```bash
# All services
docker-compose logs -f

# Backend only
docker-compose logs -f backend

# Frontend only
docker-compose logs -f frontend
```

### Rebuild after code changes
```bash
docker-compose up --build
```

### Remove containers and volumes
```bash
docker-compose down -v
```

### Check running containers
```bash
docker-compose ps
```

## Architecture

- **Backend:** Spring Boot app running on port 8080 (Java 17)
- **Frontend:** React app served by Nginx on port 80
- **Network:** Both services communicate via a Docker bridge network

## Troubleshooting

### Port already in use
If port 80 or 8080 is already in use, edit `docker-compose.yml`:

```yaml
ports:
  - "3000:80"  # Change frontend to port 3000
  - "8081:8080"  # Change backend to port 8081
```

### Backend won't start
- Check that your `GEMINI_API_KEY` is set in the `.env` file
- View logs: `docker-compose logs backend`

### Frontend can't reach backend
- Ensure both containers are running: `docker-compose ps`
- Check that the frontend is pointing to the correct backend URL

## For Development

If you want to develop with hot-reload instead of using Docker:

**Backend:**
```bash
cd backend
mvn spring-boot:run
```

**Frontend:**
```bash
cd frontend
npm run dev
```

## Production Deployment

For production deployment, consider:
1. Using environment-specific `.env` files
2. Setting up HTTPS with a reverse proxy (e.g., Traefik, Nginx)
3. Implementing proper logging and monitoring
4. Using Docker secrets for sensitive data
5. Setting up health checks and auto-restart policies

## Image Sizes

The multi-stage builds keep images small:
- Backend: ~200-250 MB (JRE only, no build tools)
- Frontend: ~25-30 MB (Nginx + static files)

## For Your CV/Portfolio

Mention these Docker features:
- ✅ Multi-stage builds for optimized image sizes
- ✅ Docker Compose for multi-container orchestration
- ✅ Health checks for reliability
- ✅ Environment variable configuration
- ✅ Production-ready Nginx configuration with caching
- ✅ Proper .dockerignore for faster builds
