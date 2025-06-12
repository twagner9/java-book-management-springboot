import React from 'react';
import { BookModal } from './BookInput';

export function EmptyDatabase() {
    // I see; so this creates a state called modalOpen
    // The state is managed by both the handleClick and closeModal functions so that
    // it is modified to match the necessary state at any given time
    const [modalOpen, setModalOpen] = React.useState(false);

    function handleClick() {
        // alert("TODO: add logic for displaying a menu that allows the user to specify book details");
        setModalOpen(true);
    }

    function closeModal() {
        setModalOpen(false);
        // TODO?: add logic for wiping all input fields? 
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
                    <>
                        <label htmlFor="bookTitle">Title: </label>
                        <input id="bookTitle" placeholder="Enter a title..."></input>
                        <label htmlFor="bookAuthor">Author: </label>
                        <input id="bookAuthor" placeholder="Enter an author..."></input>
                        <label htmlFor="bookCopies">Number of Copies: </label>
                        <input type="number" id="bookCopies" min="1" max="99"></input>
                    </>
                }
            </BookModal>
        </div>
    )
}
