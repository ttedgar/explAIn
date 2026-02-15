# explAIn Architecture

## System Overview

explAIn is a full-stack AI-powered document Q&A application that allows users to upload documents and ask natural language questions about their content.

## Architecture Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                         Frontend                            │
│                    (React + Vite)                           │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐        │
│  │  Document   │  │  Question   │  │   Chat      │        │
│  │   Upload    │  │   Input     │  │  History    │        │
│  └─────────────┘  └─────────────┘  └─────────────┘        │
└────────────────────────┬────────────────────────────────────┘
                         │ HTTP/REST
                         │
┌────────────────────────┼────────────────────────────────────┐
│                        ▼         Backend                    │
│                 (Spring Boot)                               │
│  ┌──────────────────────────────────────────────────────┐  │
│  │              REST Controllers                        │  │
│  │  /documents  /questions  /health                     │  │
│  └────┬─────────────────────────────────────────────┬───┘  │
│       │                                             │       │
│  ┌────▼──────────┐  ┌──────────────┐  ┌───────────▼────┐  │
│  │   Document    │  │  Question    │  │   File        │  │
│  │   Service     │  │  Service     │  │   Storage     │  │
│  └────┬──────────┘  └──────┬───────┘  └───────────────┘  │
│       │                    │                               │
│  ┌────▼────────────────────▼───────┐  ┌─────────────────┐ │
│  │       JPA Repositories          │  │   OpenAI API    │ │
│  └────┬────────────────────────────┘  └─────────────────┘ │
│       │                                                    │
│  ┌────▼────────────────────────────┐                      │
│  │      H2 Database (Dev)          │                      │
│  │   (PostgreSQL in Production)    │                      │
│  └─────────────────────────────────┘                      │
└─────────────────────────────────────────────────────────────┘
```

## Technology Stack

### Frontend
- **React 19**: UI framework
- **Vite**: Build tool and dev server
- **ES6+**: Modern JavaScript

### Backend
- **Spring Boot 3.2**: Application framework
- **Spring Data JPA**: Database access
- **Spring Web**: REST API
- **H2**: In-memory database (development)
- **Apache PDFBox**: PDF text extraction
- **Apache POI**: DOCX text extraction
- **WebFlux**: HTTP client for OpenAI API

### External Services
- **OpenAI API**: Natural language processing and question answering

## Key Components

### Frontend

#### Services Layer (`/src/services`)
- **api.js**: Centralized API client for backend communication
- Handles all HTTP requests and error handling

#### Pages (`/src/pages`)
- Page-level components for routing
- Container components that orchestrate data flow

#### Components (`/src/components`)
- Reusable UI components
- Document upload, question input, chat display

#### Hooks (`/src/hooks`)
- Custom React hooks for shared logic
- State management, data fetching

### Backend

#### Controllers (`/controller`)
- REST endpoints for document and question operations
- Request validation and response formatting

#### Services (`/service`)
- **DocumentService**: Document upload, storage, and text extraction
- **QuestionService**: Question processing and AI integration
- **OpenAIService**: Integration with OpenAI API

#### Repositories (`/repository`)
- JPA repositories for database access
- CRUD operations for documents and questions

#### Models (`/model`)
- **Document**: Represents uploaded documents
- **Question**: Represents Q&A history

#### Configuration (`/config`)
- **CorsConfig**: CORS settings for frontend communication
- **FileStorageConfig**: File upload directory setup

## Data Flow

### Document Upload Flow
1. User selects file in frontend
2. Frontend sends multipart form data to `/api/documents/upload`
3. Backend validates file type and size
4. Backend extracts text from document (PDF/DOCX/TXT)
5. Backend saves file to filesystem and metadata to database
6. Backend returns document ID and metadata to frontend

### Question Answering Flow
1. User enters question in frontend
2. Frontend sends question + document ID to `/api/questions/ask`
3. Backend retrieves document content from database
4. Backend constructs prompt with document context
5. Backend calls OpenAI API with prompt
6. Backend receives AI response
7. Backend saves Q&A to database
8. Backend returns answer to frontend
9. Frontend displays answer in chat interface

## Security Considerations

### Current Implementation
- CORS configuration for frontend-backend communication
- File type validation
- File size limits
- API key management via environment variables

### Future Enhancements
- User authentication and authorization
- Rate limiting for API calls
- Document access control
- Encryption for sensitive documents
- Input sanitization and validation

## Scalability Considerations

### Current Architecture (MVP)
- Single-server deployment
- In-memory H2 database
- Local file storage
- Synchronous processing

### Production Enhancements
- PostgreSQL or MongoDB for production database
- Cloud storage (S3, Azure Blob) for documents
- Redis for caching
- Queue-based processing for large documents
- Load balancing for multiple backend instances
- CDN for frontend assets

## API Endpoints

### Document Endpoints
- `POST /api/documents/upload` - Upload a document
- `GET /api/documents` - List all documents
- `GET /api/documents/{id}` - Get document details
- `DELETE /api/documents/{id}` - Delete a document

### Question Endpoints
- `POST /api/questions/ask` - Ask a question about a document
- `GET /api/questions/history/{documentId}` - Get Q&A history for a document

### System Endpoints
- `GET /api/health` - Health check

## Database Schema

### Document Table
```
documents
- id (PRIMARY KEY)
- filename
- original_filename
- file_type (PDF, DOCX, TXT)
- file_size
- file_path
- extracted_text (TEXT)
- created_at
- updated_at
```

### Question Table
```
questions
- id (PRIMARY KEY)
- document_id (FOREIGN KEY)
- question (TEXT)
- answer (TEXT)
- created_at
```

## Configuration

### Backend Configuration (`application.yml`)
- Server port and context path
- File upload limits
- CORS settings
- Database connection
- OpenAI API settings

### Frontend Configuration (`.env`)
- API base URL
- Maximum file size

## Deployment

### Development
- Run backend and frontend separately
- Hot reload enabled for both

### Production
- Docker containerized deployment
- Nginx reverse proxy
- Environment-specific configurations
- Automated CI/CD pipeline

## Monitoring and Logging

### Current Implementation
- Spring Boot Actuator for health checks
- Console logging

### Future Enhancements
- Centralized logging (ELK stack)
- Application monitoring (Prometheus + Grafana)
- Error tracking (Sentry)
- Performance monitoring
