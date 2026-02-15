# Contributing to explAIn

## Development Setup

### Prerequisites
- Java 17 or higher
- Node.js 20 or higher
- Maven 3.6+
- Git

### Initial Setup

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd explAIn
   ```

2. Set up environment variables:
   ```bash
   # Backend
   cd backend
   cp .env.example .env
   # Edit .env and add your OpenAI API key

   # Frontend
   cd ../frontend
   cp .env.example .env
   # Edit .env if needed (defaults should work for local dev)
   ```

3. Install dependencies:
   ```bash
   # Backend - Maven will download dependencies on first build
   cd backend
   mvn clean install

   # Frontend
   cd ../frontend
   npm install
   ```

## Development Workflow

### Running Locally

1. Start the backend:
   ```bash
   cd backend
   mvn spring-boot:run
   ```
   Backend will be available at `http://localhost:8080/api`

2. In a new terminal, start the frontend:
   ```bash
   cd frontend
   npm run dev
   ```
   Frontend will be available at `http://localhost:5173`

### Running with Docker

```bash
# Build and run all services
docker-compose up --build

# Run in detached mode
docker-compose up -d

# Stop all services
docker-compose down
```

## Code Structure

### Backend (`/backend`)
```
src/main/java/com/edi/
├── config/          # Configuration classes (CORS, file storage, etc.)
├── controller/      # REST API endpoints
├── service/         # Business logic
├── model/           # JPA entities
├── repository/      # Data access layer
├── dto/             # Data Transfer Objects
└── exception/       # Custom exceptions
```

### Frontend (`/frontend`)
```
src/
├── components/      # Reusable React components
├── pages/           # Page-level components
├── services/        # API and external service integrations
├── hooks/           # Custom React hooks
├── utils/           # Utility functions
└── assets/          # Static assets (images, fonts, etc.)
```

## Coding Standards

### Backend (Java)
- Follow Spring Boot best practices
- Use Lombok annotations to reduce boilerplate
- Write meaningful Javadoc comments for public APIs
- Use constructor injection for dependencies
- Write unit tests for services and integration tests for controllers

### Frontend (React)
- Use functional components with hooks
- Follow React best practices and ESLint rules
- Keep components small and focused
- Use descriptive variable and function names
- Extract reusable logic into custom hooks

## Git Workflow

1. Create a feature branch:
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. Make your changes and commit:
   ```bash
   git add .
   git commit -m "feat: add your feature description"
   ```

3. Push to your branch:
   ```bash
   git push origin feature/your-feature-name
   ```

4. Create a Pull Request

### Commit Message Convention
- `feat:` - New feature
- `fix:` - Bug fix
- `docs:` - Documentation changes
- `style:` - Code style changes (formatting, etc.)
- `refactor:` - Code refactoring
- `test:` - Adding or updating tests
- `chore:` - Maintenance tasks

## Testing

### Backend Tests
```bash
cd backend
mvn test
```

### Frontend Tests
```bash
cd frontend
npm test
```

## API Documentation

Once the backend is running, visit:
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## Common Issues

### Backend won't start
- Check if Java 17 is installed: `java -version`
- Ensure port 8080 is not in use
- Verify OpenAI API key is set in .env

### Frontend won't start
- Delete `node_modules` and run `npm install` again
- Clear npm cache: `npm cache clean --force`
- Ensure port 5173 is not in use

### File upload not working
- Check that the `uploads/` directory exists and is writable
- Verify file size is under 10MB (configurable in application.yml)
- Check CORS settings if testing from different origin

## Need Help?

- Check existing issues on GitHub
- Create a new issue with detailed description
- Join our discussion forum (if available)
