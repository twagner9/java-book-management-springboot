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
let currentId = 1;

export function ExistingDatabase() {
    const [books, setBooks] = React.useState<Book[]>([]);
    const [modalOpen, setModalOpen] = React.useState(false);

    useEffect(() => {
        fetch('/api/books')
            .then(response => response.json())
            .then(data => setBooks(data))
            .catch(error => console.error('Error fetching books:', error));
    }, []); // This empty "dependency array" in useEffect means this effect runs once after the initial render; if variables are added here,
            // the effect will run again when those variables change.


    function closeModal() {
        setModalOpen(false);
    }

    function handleAddClick() {
        setModalOpen(true);
    }

    function handleBookAdded(newBook: Book) {
        newBook.id = currentId;
        setBooks(prevBooks => [...prevBooks, newBook]);
        currentId++;

        // DEBUG:
        console.log("in handleBookAdded");
        console.log(newBook);

        // TODO: logic for adding the data to the table
        setModalOpen(false);
    }

    return (
        <div className="main-content">
            <h1 className="main-text">Small Library Management</h1>
            <button className="add-book-button" onClick={handleAddClick}>Add a book</button>
            <BookModal isOpen={modalOpen} onRequestClose={closeModal}>
                <h3>Add Book</h3>
                {
                    <BookInputs onBookAdded={handleBookAdded} />
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