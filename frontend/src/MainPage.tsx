import React, {useEffect, useState } from 'react';
import { BookModal } from './BookModal';
import { BookInputs } from './BookInputs';

export type Book = {
    id: number;
    author: string;
    title: string;
    numCopies: number;
};

// TODO: update to fetch the latest ID number from the database
let currentId: number = 1;

export function MainPage() {
    const [books, setBooks] = React.useState<Book[]>([]);
    const [modalOpen, setModalOpen] = React.useState(false);
    const [loading, setLoading] = React.useState(true);

    useEffect(() => {
        fetch('/api/books')
            .then(response => response.json())
            .then(data => setBooks(data))
            .catch(error => console.error('Error fetching books:', error))
            .finally(() => setLoading(false));
    }, []); // This empty "dependency array" in useEffect means this effect runs once after the initial render; if variables are added here,
            // the effect will run again when those variables change.


    function closeModal() {
        setModalOpen(false);
    }

    function handleAddClick() {
        setModalOpen(true);
    }

    function handleBookAdded(newBook: Omit<Book, "id">) {
        // DEBUG:
        console.log("in handleBookAdded");
        console.log(newBook);

        fetch('/api/books', {
            method: "POST",
            headers: {'Content-Type': 'application/json' },
            body: JSON.stringify(newBook),
        })
        .then(response => {
            if (!response.ok) throw new Error("Failed to add book to database.");
            return response.json();
        })
        .then(savedBook => {
            setBooks(prevBooks => [...prevBooks, savedBook]);
            setModalOpen(false);
        })
        .catch(error => {
            alert("Error adding book to the database/GUI");
        })
    }

    return (
        <div className="main-content">
            <h1 className="main-text">Small Library Management</h1>
            {!loading && books.length === 0 && (
                <div>
                    <p className="main-text main-description">
                        This application is meant to give individuals and those with shared libraries such as classroom libraries a way to 
                        log and track their owned books. As of now, entry must be done manually, but in the future, the hope is to add
                        features such as ISBN scanning and book cover image uploading. To get started adding books, click the button below!
                    </p>
                </div>
            )}
            <button className="add-book-button" onClick={handleAddClick}>Add a book</button>
            <BookModal isOpen={modalOpen} onRequestClose={closeModal}>
                <h3>Add Book</h3>
                {
                    <BookInputs currentId={currentId} onBookAdded={handleBookAdded} />
                }
            </BookModal>
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