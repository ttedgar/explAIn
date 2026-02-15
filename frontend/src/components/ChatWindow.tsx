import { useState, useEffect, useRef } from 'react';
import ChatMessage from './ChatMessage';
import ChatInput from './ChatInput';

interface Message {
  role: 'user' | 'model';
  content: string;
}

interface ChatWindowProps {
  sessionId: string;
  fileName: string;
}

export default function ChatWindow({ sessionId, fileName }: ChatWindowProps) {
  const [messages, setMessages] = useState<Message[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const messagesEndRef = useRef<HTMLDivElement>(null);

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  };

  useEffect(() => {
    scrollToBottom();
  }, [messages]);

  const sendMessage = async (userMessage: string) => {
    const userMsg: Message = { role: 'user', content: userMessage };
    setMessages((prev) => [...prev, userMsg]);
    setIsLoading(true);

    try {
      const response = await fetch(`http://localhost:8080/api/chat/${sessionId}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ message: userMessage }),
      });

      if (response.ok) {
        const data = await response.json();
        const aiMsg: Message = { role: 'model', content: data.response };
        setMessages((prev) => [...prev, aiMsg]);
      } else {
        const errorData = await response.json();
        const errorMsg: Message = {
          role: 'model',
          content: `Error: ${errorData.error || 'Failed to get response'}`,
        };
        setMessages((prev) => [...prev, errorMsg]);
      }
    } catch (error) {
      console.error('Error sending message:', error);
      const errorMsg: Message = {
        role: 'model',
        content: 'Sorry, I encountered a network error. Please try again.',
      };
      setMessages((prev) => [...prev, errorMsg]);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="w-full max-w-4xl h-[600px] bg-white rounded-lg shadow-lg flex flex-col">
      {/* Header */}
      <div className="border-b border-gray-300 p-4">
        <h2 className="text-lg font-medium text-gray-900">Chat about: {fileName}</h2>
        <p className="text-sm text-gray-500">Ask questions about your document</p>
      </div>

      <div className="flex-1 overflow-y-auto p-4">
        {messages.length === 0 ? (
          <div className="h-full flex items-center justify-center text-gray-500">
            <p>Start by asking a question about the document!</p>
          </div>
        ) : (
          <>
            {messages.map((msg, idx) => (
              <ChatMessage key={idx} role={msg.role} content={msg.content} />
            ))}
            {isLoading && (
              <div className="flex justify-start mb-4">
                <div className="bg-gray-200 text-gray-900 rounded-lg px-4 py-2">
                  <p className="text-gray-500">Thinking...</p>
                </div>
              </div>
            )}
            <div ref={messagesEndRef} />
          </>
        )}
      </div>

      <ChatInput onSendMessage={sendMessage} disabled={isLoading} />
    </div>
  );
}
