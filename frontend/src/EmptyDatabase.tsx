import React from 'react';
import { BookModal } from './BookModal';
import { BookInputs } from './BookInputs';
import type {Book} from './ExistingDatabase';

// TODO: pass this state up so it can be handed down to EmptyDatabase and ExistingDatabase
// Also may want to reconsider dividing the state this way; could instead conditionally render based
// on whether the database is empty or not
let currentId = 1;

export function EmptyDatabase() {
    // I see; so this creates a state called modalOpen
    // The state is managed by both the handleClick and closeModal functions so that
    // it is modified to match the necessary state at any given time
    const [modalOpen, setModalOpen] = React.useState(false);
    const [books, setBooks] = React.useState<Book[]>([]);

    function handleClick() {
        // alert("TODO: add logic for displaying a menu that allows the user to specify book details");
        setModalOpen(true);
    }

    function closeModal() {
        setModalOpen(false);
        // TODO?: add logic for wiping all input fields? 
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

    return(
        <div className="main-content">
            <h1 className="main-text">Small Library Management</h1>
            <h4 className="main-text">Welcome to Library Management!</h4>
            <p className="main-text main-description">This application is meant to give individuals and those with shared libraries such as classroom libraries a way to 
                log and track their owned books. As of now, entry must be done manually, but in the future, the is hope to add
                features such as ISBN scanning and book cover image uploading. To get started adding books, click the button below!
            </p>
            <button className="add-book-button" onClick={handleClick}>Add your first book</button>
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
    )
}
