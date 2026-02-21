# explAIn - AI-Powered Document Q&A

> Upload documents and chat with AI to understand their content

## 🎥 Demo

![explAIn Demo](./explAIn-demo.gif)

*Full tutorial demonstration showing document upload and AI-powered Q&A*

---

## 📖 Overview

**explAIn** is a full-stack web application that allows users to upload documents (PDF, DOCX, TXT) and ask questions about their content using Google's Gemini AI. The application extracts text from uploaded files and provides an interactive chat interface for document analysis and comprehension.

Perfect for understanding:
- Legal documents
- Technical documentation
- Academic papers
- Business reports
- Any text-based content

## ✨ Features

- 📄 **Document Upload** - Drag & drop interface supporting PDF, DOCX, and TXT files
- 🤖 **AI Chat Interface** - Interactive Q&A powered by Google Gemini 2.5 Flash
- 💬 **Conversation History** - Maintains context across multiple questions
- 📝 **Text Extraction** - Automatic content extraction using Apache Tika
- 🎨 **Clean UI** - Minimalistic, user-friendly interface
- 🐳 **Docker Support** - Production-ready containerization with multi-stage builds
- 🔄 **Session Management** - In-memory session handling for multiple documents

## 🛠️ Tech Stack

### Backend
- **Java 17** - Modern Java runtime
- **Spring Boot 3.4.2** - REST API framework
- **Apache Tika 3.0.1** - Document text extraction
- **Maven** - Dependency management
- **Lombok** - Code generation for cleaner Java

### Frontend
- **React 18** - UI library
- **TypeScript** - Type-safe JavaScript
- **Vite** - Fast build tool and dev server
- **Tailwind CSS** - Utility-first styling
- **Lucide React** - Icon library

### AI Integration
- **Google Gemini 2.5 Flash** - Large language model for document Q&A
- **Gemini API** - REST API integration

### DevOps
- **Docker** - Containerization
- **Docker Compose** - Multi-container orchestration
- **Nginx** - Production web server for frontend

## 📋 Prerequisites

### For Local Development:
- **Java 17+** ([Download](https://adoptium.net/))
- **Maven 3.9+** ([Download](https://maven.apache.org/download.cgi)) — *or use the included Maven wrapper (`mvnw`/`mvnw.cmd`)*
- **Node.js 20+** ([Download](https://nodejs.org/))
- **npm** (comes with Node.js)
- **Google Gemini API Key** ([Get one here](https://aistudio.google.com/app/apikey))

### For Docker:
- **Docker Desktop** ([Download](https://www.docker.com/products/docker-desktop))
- **Google Gemini API Key** ([Get one here](https://aistudio.google.com/app/apikey))

## 🚀 Getting Started

### Option 1: Run Locally (Development)

#### 1. Clone the repository
```bash
git clone <your-repo-url>
cd explAIn
```

#### 2. Configure the backend
Copy the template and add your actual API key:

**On Linux/Mac:**
```bash
cp backend/src/main/resources/application-local.properties.template \
   backend/src/main/resources/application-local.properties
```

**On Windows (PowerShell):**
```powershell
Copy-Item backend/src/main/resources/application-local.properties.template `
   backend/src/main/resources/application-local.properties
```

Edit `application-local.properties` and replace `YOUR_GEMINI_API_KEY_HERE` with your actual key from [https://aistudio.google.com/app/apikey](https://aistudio.google.com/app/apikey).

#### 3. Start the backend with the local profile

**Using Maven (if installed):**
```bash
cd backend
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

**Using Maven wrapper (no Maven installation needed):**

On Linux/Mac:
```bash
cd backend
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

On Windows (PowerShell):
```powershell
cd backend
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=local"
```
Backend runs on: **http://localhost:8080**

#### 4. Start the frontend (new terminal)
```bash
cd frontend
npm install
npm run dev
```
Frontend runs on: **http://localhost:5173**

#### 5. Open your browser
Navigate to **http://localhost:5173** and start using the app!

---

### Option 2: Run with Docker (Production)

#### 1. Create environment file

**On Linux/Mac:**
```bash
cp .env.example .env
```

**On Windows (PowerShell):**
```powershell
Copy-Item .env.example .env
```

Edit `.env` and add your Gemini API key:
```
GEMINI_API_KEY=your_actual_api_key_here
```

#### 2. Build and run
```bash
docker-compose up --build
```

First build takes 3-5 minutes. Subsequent runs are faster.

#### 3. Access the application
- **Frontend:** http://localhost:3000
- **Backend API:** http://localhost:8080

#### 4. Stop the application
```bash
# Press Ctrl+C, then:
docker-compose down
```

## 📱 Usage

1. **Upload a Document**
   - Drag & drop a file or click to browse
   - Supported formats: PDF, DOCX, TXT
   - Wait for text extraction to complete

2. **Ask Questions**
   - Type your question in the chat input
   - Press Enter to send (Shift+Enter for new line)
   - AI responds based on document content

3. **Continue the Conversation**
   - Ask follow-up questions
   - Context is maintained throughout the session
   - All previous messages are considered

## 🔌 API Endpoints

### Upload Document
```http
POST /api/upload
Content-Type: multipart/form-data

Response:
{
  "message": "File uploaded successfully",
  "sessionId": "uuid-here",
  "extractedText": "..."
}
```

### Send Chat Message
```http
POST /api/chat/{sessionId}
Content-Type: application/json

{
  "message": "What is this document about?"
}

Response:
{
  "response": "This document discusses...",
  "sessionId": "uuid-here"
}
```

### Get Session Info
```http
GET /api/chat/session/{sessionId}

Response:
{
  "sessionId": "uuid-here",
  "fileName": "document.pdf",
  "messageCount": 5,
  "createdAt": "2026-02-15T10:00:00"
}
```

## 📁 Project Structure

```
explAIn/
├── backend/
│   ├── src/main/java/com/edi/explain/
│   │   ├── controller/
│   │   │   ├── ChatController.java
│   │   │   └── FileController.java
│   │   ├── model/
│   │   │   ├── ChatMessage.java
│   │   │   └── ChatSession.java
│   │   ├── service/
│   │   │   ├── GeminiService.java
│   │   │   └── SessionService.java
│   │   └── ExplainApplication.java
│   ├── src/main/resources/
│   │   ├── application.properties
│   │   └── application-local.properties.template
│   ├── Dockerfile
│   └── pom.xml
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   │   ├── ChatInput.tsx
│   │   │   ├── ChatMessage.tsx
│   │   │   ├── ChatWindow.tsx
│   │   │   └── FileUpload.tsx
│   │   ├── App.tsx
│   │   └── main.tsx
│   ├── Dockerfile
│   ├── nginx.conf
│   └── package.json
├── docker-compose.yml
├── .env.example
└── README.md
```

## 🐳 Docker Features

- **Multi-stage builds** - Optimized image sizes
  - Backend: ~200-250 MB (JRE only, no build tools)
  - Frontend: ~25-30 MB (static files + Nginx)
- **Health checks** - Automatic container health monitoring
- **Bridge networking** - Isolated service communication
- **Environment variables** - Secure API key management
- **Production-ready Nginx** - Gzip compression, caching, SPA routing

## 🔧 Configuration

### Backend Configuration
The backend uses **Spring Boot profiles** to manage API key configuration:

**For Local Development:**
- `application.properties` (committed) - contains `gemini.api.key=${GEMINI_API_KEY}` placeholder
- `application-local.properties` (gitignored) - contains your actual API key
- Copy from `application-local.properties.template` and add your key
- Run with profile flag: `mvn spring-boot:run -Dspring-boot.run.profiles=local`

**For Docker:**
- `application.properties` (same as above) - Docker sets `GEMINI_API_KEY` environment variable
- Copy `.env.example` to `.env` and add your API key
- Run: `docker-compose up --build`

```properties
# Server port
server.port=8080

# Gemini API - reads from environment variable (Docker) or local properties override (local dev)
gemini.api.key=${GEMINI_API_KEY}

# File upload limits
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

### Frontend Configuration
API URL is configured in the frontend code. For custom backend URL, update:
```typescript
// frontend/src/App.tsx
const API_URL = 'http://localhost:8080/api';
```

## 🤝 Contributing

This is a portfolio project, but suggestions and feedback are welcome!

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is open source and available for educational purposes.

## 🙏 Acknowledgments

- **Google Gemini** - AI-powered chat capabilities
- **Apache Tika** - Document text extraction
- **Spring Boot** - Backend framework
- **React** - Frontend library
- **Tailwind CSS** - Styling framework

## 📧 Contact

Created as a portfolio project by a junior developer passionate about full-stack development and AI integration.

---

**Built with ❤️ using Spring Boot, React, and Google Gemini**
