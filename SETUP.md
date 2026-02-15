# explAIn - Setup Guide

## Prerequisites
- Java 17+
- Node.js 18+
- Maven
- Google Gemini API Key

## Getting Your Gemini API Key

1. Visit [Google AI Studio](https://aistudio.google.com/app/apikey)
2. Sign in with your Google account
3. Click "Create API Key"
4. Copy the generated API key

## Backend Setup

1. Navigate to the backend directory:
   ```bash
   cd backend
   ```

2. Create a local configuration file:
   ```bash
   cp src/main/resources/application-local.properties.template src/main/resources/application-local.properties
   ```

3. Edit `application-local.properties` and add your Gemini API key:
   ```properties
   gemini.api.key=YOUR_ACTUAL_API_KEY_HERE
   ```

4. Install dependencies and run:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

   The backend will start on `http://localhost:8080`

## Frontend Setup

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Run the development server:
   ```bash
   npm run dev
   ```

   The frontend will start on `http://localhost:5173`

## Using the Application

1. Open your browser to `http://localhost:5173`
2. Drag and drop a document (PDF, DOCX, DOC, or TXT)
3. Wait for the file to process
4. Start chatting with the AI about your document!

## Gemini API Free Tier Limits

- 15 requests per minute
- 1 million tokens per day
- 1,500 requests per day

For most users, this is more than sufficient for document Q&A sessions.

## Troubleshooting

### Backend won't start
- Verify Java 17+ is installed: `java -version`
- Check that port 8080 is not in use
- Ensure your Gemini API key is correctly set in `application-local.properties`

### Frontend won't start
- Verify Node.js is installed: `node -version`
- Try deleting `node_modules` and running `npm install` again
- Check that port 5173 is available

### Chat not working
- Open browser console (F12) to check for errors
- Verify backend is running on port 8080
- Check that your Gemini API key is valid
- Review backend logs for error messages

### "Session not found" error
- Sessions are stored in memory and cleared when the backend restarts
- Upload the file again to create a new session

## Architecture

```
┌─────────────┐
│   Browser   │
│  (React UI) │
└──────┬──────┘
       │
       │ HTTP/JSON
       │
┌──────▼────────────┐
│  Spring Boot API  │
│  - FileController │
│  - ChatController │
└──────┬────────────┘
       │
       ├──► TextExtractionService (Apache Tika)
       │
       ├──► SessionService (In-memory sessions)
       │
       └──► GeminiService (REST API)
                  │
                  │ HTTPS
                  │
           ┌──────▼───────┐
           │ Google Gemini │
           │  1.5 Flash    │
           └──────────────┘
```

## Future Enhancements

- Session persistence (Redis/Database)
- Multi-file uploads
- Streaming responses (SSE/WebSocket)
- Chat history export
- User authentication
- Rate limiting
