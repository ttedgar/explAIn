# explAIn

AI-powered document Q&A tool that lets users upload documents (PDF, DOCX, TXT) and ask questions about their content using natural language.

## Tech Stack

### Backend
- Java 17
- Spring Boot 3.x
- Maven
- OpenAI API / LangChain (for AI processing)
- Apache PDFBox / Apache POI (for document parsing)

### Frontend
- React 19
- Vite
- Modern UI components

## Project Structure

```
explAIn/
├── backend/          # Spring Boot REST API
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml
├── frontend/         # React application
│   ├── src/
│   ├── public/
│   └── package.json
└── README.md
```

## Getting Started

### Backend Setup

1. Navigate to the backend directory:
   ```bash
   cd backend
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The backend will start on `http://localhost:8080`

### Frontend Setup

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm run dev
   ```

The frontend will start on `http://localhost:5173`

## Features

- [ ] Document upload (PDF, DOCX, TXT)
- [ ] Document text extraction and processing
- [ ] AI-powered question answering
- [ ] Chat-like interface for document queries
- [ ] Document management (list, delete)
- [ ] Response history

## Environment Variables

See `.env.example` files in respective directories for required configuration.

## API Documentation

Once the backend is running, API documentation will be available at:
- Swagger UI: `http://localhost:8080/swagger-ui.html`

## License

MIT
