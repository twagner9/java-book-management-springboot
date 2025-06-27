import {useState} from "react";
import type {Book} from "./MainPage";

export function BookInputs(props: {currentId: number; onBookAdded: (book: Omit<Book, "id">) => void}) {
    // UseState sets the initial default value of the state, and ties a hook
    // that change the state when users interact with the frontend
    const [newBook, setBook] = useState<Omit<Book, "id">>({
        title: "",
        author: "",
        numCopies: 1,
    });
    const isTitleValid = newBook.title.length >= 1 && newBook.title.length <= 99;
    const isAuthorValid = newBook.author.length > 1 && newBook.author.length <= 20;
    const isNumCopiesValid = newBook.numCopies >= 1 && newBook.numCopies <= 99;

    async function handleSubmitClick() {
        console.log("Function executing at least.")
        // Create book
        console.log(newBook);
        props.onBookAdded(newBook);
        // // Generate a JSON transmission of the data from the frontend to the /api/books endpoint
        // try {
        //     const response = await fetch('/api/books', {
        //         method: 'POST',
        //         headers: {'Content-Type': 'application/json'},
        //         body: JSON.stringify(newBook),
        //     });
        //     if (response.ok) {
        //         if (onBookAdded) onBookAdded(newBook);
        //     } else {
        //         alert("Failed to add book.");
        //     }
        // } catch (error) {
        //     alert("Error adding book.");
        //     return;
        // }
    }

    return (    
        <div className="modal-container">
            <div className="form-container">
                <div className="form-label-and-input">
                    <label htmlFor="bookTitle">Title: </label>
                    <input id="bookTitle" placeholder="Enter a title..." onChange={e => setBook(b => ({...b, title: e.target.value}))}></input>
                </div>
                <div className="form-label-and-input">
                    <label htmlFor="bookAuthor">Author: </label>
                    <input id="bookAuthor" placeholder="Enter an author..." onChange={(e) => setBook(b => ({...b, author: e.target.value}))}></input>
                </div>
                <div className="form-label-and-input">
                    <label htmlFor="bookCopies">Number of Copies: </label>
                    <input type="number" id="bookCopies" min="1" max="99" defaultValue="1" onChange={e => setBook(b => ({...b, numCopies: (e.target as HTMLInputElement).valueAsNumber}))}></input>
                </div>
                <button onClick={handleSubmitClick} disabled={!(isTitleValid && isAuthorValid && isNumCopiesValid)}>Submit</button>
            </div>
        </div>
    )    
}