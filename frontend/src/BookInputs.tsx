import {useState} from "react";

export function BookInputs({onBookAdded}: {onBookAdded?: () => void} ) {
    // UseState sets the initial default value of the state, and ties a hook
    // that change the state when users interact with the frontend
    const [title, setTitle] = useState('');
    const [author, setAuthor] = useState('');
    const [numCopies, setNumCopies] = useState(1);
    const isTitleValid = title.length >= 1 && title.length <= 99;
    const isAuthorValid = author.length > 1 && author.length <= 20;
    const isNumCopiesValid = numCopies >= 1 && numCopies <= 99;

    async function handleSubmitClick() {
        console.log("Function executing at least.")
        // Create book
        const newBook = {title, author, numCopies};
        console.log(newBook);

        // Generate a JSON transmission of the data from the frontend to the /api/books endpoint
        try {
            const response = await fetch('/api/books', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(newBook),
            });
            if (response.ok) {
                if (onBookAdded) onBookAdded();
            } else {
                alert("Failed to add book.");
            }
        } catch (error) {
            alert("Error adding book.");
            return;
        }
        
        // TODO: add logic for updating the table?
    }

    return (    
        <>
            <label htmlFor="bookTitle">Title: </label>
            <input id="bookTitle" placeholder="Enter a title..." onChange={(e) => setTitle(e.target.value)}></input>
            <label htmlFor="bookAuthor">Author: </label>
            <input id="bookAuthor" placeholder="Enter an author..." onChange={(e) => setAuthor(e.target.value)}></input>
            <label htmlFor="bookCopies">Number of Copies: </label>
            <input type="number" id="bookCopies" min="1" max="99" defaultValue="1" onChange={(e) => setNumCopies((e.target as HTMLInputElement).valueAsNumber)}></input>
            <button onClick={handleSubmitClick} disabled={!(isTitleValid && isAuthorValid && isNumCopiesValid)}>Submit</button>
        </>
    )    
}