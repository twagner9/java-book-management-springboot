import {useState} from "react";

export function BookInputs() {
    // UseState sets the initial default value of the state, and ties a hook
    // that change the state when users interact with the frontend
    const [title, setTitle] = useState('');
    const [author, setAuthor] = useState('');
    const [numCopies, setNumCopies] = useState(1);
    const isTitleValid = title.length >= 1 && title.length <= 99;
    const isAuthorValid = author.length > 1 && author.length <= 20;
    const isNumCopiesValid = numCopies >= 1 && numCopies <= 99;

    function handleSubmitClick() {
        alert("TODO: add logic for adding this data to the database, and for updating the table with the newly added input.");
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