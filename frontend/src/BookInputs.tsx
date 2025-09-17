import {useState} from "react";
import type {Book} from "./MainPage";
import { ImageUpload } from "./ImageUpload";

export function BookInputs(props: {currentId: number; onBookAdded: (book: Omit<Book, "id">) => void}) {
    // UseState sets the initial default value of the state, and ties a hook
    // that change the state when users interact with the frontend
    const [newBook, setBook] = useState<Omit<Book, "id">>({
        title: "",
        authorLast: "",
        authorFirst: "",
        genre: "",
        numCopies: 1,
    });
    const isTitleValid = newBook.title.length >= 1 && newBook.title.length <= 99;
    const isAuthorValid = (newBook.authorLast.length > 1 && newBook.authorLast.length <= 20) && (newBook.authorFirst.length > 1 && newBook.authorFirst.length <= 20);
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
                    <label htmlFor="bookAuthorLast">Author last: </label>
                    <input id="bookAuthorLast" placeholder="Enter author last name..." onChange={(e) => setBook(b => ({...b, authorLast: e.target.value}))}></input>
                </div>
                <div className="form-label-and-input">
                    <label htmlFor="bookAuthorFirst">Author first: </label>
                    <input id="bookAuthorFirst" placeholder="Enter author first name..." onChange={(e) => setBook(b => ({...b, authorFirst: e.target.value}))}></input>
                </div>
                <div className="form-label-and-input">
                    <label htmlFor="bookGenre">Genre: </label>
                    <select name="genres" onChange={(e) => setBook(b => ({...b, genre: e.target.value}))}>
                        <option value="Action">Action</option>
                        <option value="Adventure">Adventure</option>
                        <option value="Autobiography">Autobiography</option>
                        <option value="Biography">Biography</option>
                        <option value="Children's">Children's</option>
                        <option value="Comic">Comic</option>
                        <option value="Cookbook">Cookbook</option>
                        <option value="Crime">Crime</option>
                        <option value="Dark fantasy">Dark Fantasy</option>
                        <option value="Dystopian">Dystopian</option>
                        <option value="Drama">Drama</option>
                        <option value="Erotica">Erotica</option>
                        <option value="Essay">Essay</option>
                        <option value="Fairy tale">Fairy Tale</option>
                        <option value="Fantasy">Fantasy</option>
                        <option value="Graphic novel">Graphic Novel</option>
                        <option value="Historical fiction">Historical Fiction</option>
                        <option value="Horror">Horror</option>
                        <option value="Lgbtq+">LGBTQ+</option>
                        <option value="Manga">Manga</option>
                        <option value="Memoir">Memoir</option>
                        <option value="Military fiction">Military Fiction</option>
                        <option value="Mystery">Mystery</option>
                        <option value="Mythology">Mythology</option>
                        <option value="Non-fiction">Non-ficiton</option>
                        <option value="Philosophy">Philosophy</option>
                        <option value="Picture book">Picture Book</option>
                        <option value="Poetry">Poetry</option>
                        <option value="Religious">Religious</option>
                        <option value="Romance">Romance</option>
                        <option value="Satire">Satire</option>
                        <option value="Science fiction">Science Fiction</option>
                        <option value="Self-help">Self-Help</option>
                        <option value="Short story">Short Story</option>"
                        <option value="Sports">Sports</option>
                        <option value="Tragedy">Tragedy</option>
                        <option value="Western">Western</option>
                        <option value="Young adult">Young Adult</option>
                    </select>
                </div>
                <div className="form-label-and-input">
                    <label htmlFor="bookCopies">Number of Copies: </label>
                    <input type="number" id="bookCopies" min="1" max="99" defaultValue="1" onChange={e => setBook(b => ({...b, numCopies: (e.target as HTMLInputElement).valueAsNumber}))}></input>
                </div>
                <div className="form-label-and-input">
                    <ImageUpload />
                </div>
                <button onClick={handleSubmitClick} disabled={!(isTitleValid && isAuthorValid && isNumCopiesValid)}>Submit</button>
            </div>
        </div>
    )    
}