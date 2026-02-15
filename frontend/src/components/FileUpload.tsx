import { useCallback, useState } from 'react';
import { useDropzone } from 'react-dropzone';
import { FileText } from 'lucide-react';

export default function FileUpload() {
  const [selectedFile, setSelectedFile] = useState<File | null>(null);

  const onDrop = useCallback(async (acceptedFiles: File[]) => {
    if (acceptedFiles.length > 0) {
      const file = acceptedFiles[0];
      setSelectedFile(file);

      // Upload file to backend
      const formData = new FormData();
      formData.append('file', file);

      try {
        const response = await fetch('http://localhost:8080/api/upload', {
          method: 'POST',
          body: formData,
        });

        if (response.ok) {
          const data = await response.json();
          console.log('Upload successful:', data);
        } else {
          console.error('Upload failed');
        }
      } catch (error) {
        console.error('Error uploading file:', error);
      }
    }
  }, []);

  const { getRootProps, getInputProps, isDragActive } = useDropzone({
    onDrop,
    accept: {
      'text/plain': ['.txt'],
      'application/pdf': ['.pdf'],
      'application/vnd.openxmlformats-officedocument.wordprocessingml.document': ['.docx'],
      'application/msword': ['.doc'],
    },
    maxFiles: 1,
  });

  const formatFileSize = (bytes: number) => {
    if (bytes === 0) return '0 Bytes';
    const k = 1024;
    const sizes = ['Bytes', 'KB', 'MB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
  };

  return (
    <div className="w-full max-w-2xl">
      <div
        {...getRootProps()}
        className={`border-2 border-dashed rounded-lg p-16 text-center transition-all cursor-pointer ${
          isDragActive
            ? 'border-gray-500 bg-gray-100'
            : 'border-gray-300 hover:border-gray-400'
        }`}
      >
        <input {...getInputProps()} />

        {selectedFile ? (
          <div className="space-y-4">
            <FileText className="w-16 h-16 text-gray-600 mx-auto" />
            <div>
              <p className="text-lg font-medium text-gray-900">{selectedFile.name}</p>
              <p className="text-sm text-gray-500 mt-1">{formatFileSize(selectedFile.size)}</p>
            </div>
            <p className="text-gray-600">Starting chat...</p>
          </div>
        ) : (
          <div className="space-y-4">
            <p className="text-xl text-gray-700 leading-relaxed">
              Have an AI help you understand difficult texts — be it a legal document or a research paper.
            </p>
            <p className="text-lg text-gray-500">
              {isDragActive ? 'Drop your file here...' : 'Drag and drop files to start AI chat about it!'}
            </p>
            <p className="text-sm text-gray-400 mt-6">
              Supports: PDF, DOCX, DOC, TXT
            </p>
          </div>
        )}
      </div>
    </div>
  );
}
