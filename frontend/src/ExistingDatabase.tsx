import React, {useEffect, useState } from 'react';

type Book = {
    id: number;
    author: string;
    title: string;
    numCopies: number;
};

export function ExistingDatabase() {
    const [books, setBooks] = React.useState<Book[]>([]);
    useEffect(() => {
        fetch('/api/books')
            .then(response => response.json())
            .then(data => setBooks(data))
            .catch(error => console.error('Error fetching books:', error));
    }, []); // This empty "dependency array" in useEffect means this effect runs once after the initial render; if variables are added here,
            // the effect will run again when those variables change.

    function handleClick() {
        alert("TODO: add logic for displaying a menu that allows the user to specify book details, and then send to backend");
    }

    return (
        <div className="main-content">
            <h1 className="main-text">Small Library Management</h1>
            <button className="add-book-button" onClick={handleClick}>Add a book</button>
            <table>
                <thead>
                    <tr>
                        <th>Author</th>
                        <th>Title</th>
                        <th>Number of Copies</th>
                    </tr>
                </thead>
                <tbody>
                    {books.map((book) => (
                        <tr key={book.id}>
                            <td>{book.author}</td>
                            <td>{book.title}</td>
                            <td>{book.numCopies}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}