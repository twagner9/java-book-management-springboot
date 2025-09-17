import React from 'react';
import logo from './logo.svg';
import { MainPage } from './MainPage';
import './App.css';

function App() {
  // Create state variable called "books" to hold the list of books
  const [books, setBooks] = React.useState([]);

  // Fetch any books from the backend
  React.useEffect(() => {
    fetch('/api/books')
      .then(response => response.json())
      .then(data => setBooks(data))
      .catch(error => console.error('Error fetching books:', error));
    }, []);

  // If books is null, display a loading message
  if (books === null) {
    return (<div>Loading...</div>);
  }
  
  return <MainPage />
}

export default App;
