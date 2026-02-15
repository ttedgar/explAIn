import { useState } from 'react'
import FileUpload from './components/FileUpload'
import ChatWindow from './components/ChatWindow'
import './App.css'

function App() {
  const [sessionId, setSessionId] = useState<string | null>(null)
  const [fileName, setFileName] = useState<string>('')

  const handleSessionCreated = (id: string, name: string) => {
    setSessionId(id)
    setFileName(name)
  }

  const handleNewUpload = () => {
    setSessionId(null)
    setFileName('')
  }

  return (
    <div className="min-h-screen bg-gray-50 flex items-center justify-center p-4">
      {!sessionId ? (
        <FileUpload onSessionCreated={handleSessionCreated} />
      ) : (
        <div className="w-full max-w-4xl space-y-4">
          <button
            onClick={handleNewUpload}
            className="text-sm text-gray-600 hover:text-gray-900 underline"
          >
            ← Upload a new document
          </button>
          <ChatWindow sessionId={sessionId} fileName={fileName} />
        </div>
      )}
    </div>
  )
}

export default App
