import {useState, useRef} from "react";
import type {Book} from "./MainPage";
import { ImageUpload } from "./ImageUpload";

export function BookInputs(props: {currentId: number; onBookAdded: (book: Omit<Book, "id">) => void}) {
    // UseState sets the initial default value of the state, and ties a hook
    // that change the state when users interact with the frontend
    const [newBook, setBook] = useState<Omit<Book, "id">>({
        imagePath: "",
        title: "",
        authorLast: "",
        authorFirst: "",
        genre: "",
        numCopies: 1,
    });
    const [value, setValue] = useState(1);
    const intervalRef = useRef<number | null>(null);

    const isTitleValid = newBook.title.length >= 1 && newBook.title.length <= 99;
    const isAuthorValid = (newBook.authorLast.length > 1 && newBook.authorLast.length <= 20) && (newBook.authorFirst.length > 1 && newBook.authorFirst.length <= 20);
    const isNumCopiesValid = newBook.numCopies >= 1 && newBook.numCopies <= 99;

    const setImagePath = (imagePath: string) => {
        setBook(prev => ({...prev, imagePath}));
    };

    /**
     * Adjusts the value of numCopies stored in the book object's state.
     * @param numCopiesInterval Holds 1 or -1 depending on whether the increment or decrement button was pressed
     */
    const changeNumCopies = (numCopiesInterval: number) => {
        setBook(b => {
            console.log('There is an attempt to update the book state');
            const updatedCopies = Math.min(99, Math.max(1, b.numCopies + numCopiesInterval));
            console.log("updatedCopies:", updatedCopies);
            return {...b, numCopies: updatedCopies};
        })
    };

    /**
     * Registers that the mouse is being clicked/held; will automatically increment/decrement once
     * immediately, and then runs on an interval after that for faster adjustment.
     * @param num 1 or -1, based on whether the increment or decrement button is clicked, respectively.
     */
    const handleMouseDown = (num: number) => {
        console.log('handleMouseDown is being called.');
        changeNumCopies(num);
        intervalRef.current = window.setInterval(() => {
            changeNumCopies(num);
        }, 100);
    };

    /**
     * Sets the interval reference to 0; still a bit unclear about what refererences do in React.
     */
    const clearIntervalRef = () => {
        console.log('clearIntervalRef is being called on mouseUp/mouseLeave');
        if (intervalRef.current !== null) {
            clearInterval(intervalRef.current);
            intervalRef.current = null;
        }
    };

    async function handleSubmitClick() {
        // First get the image name, then 
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
                    <label className="modalInputContent" htmlFor="bookTitle">Title: </label>
                    <input className="modalInputContent" id="bookTitle" placeholder="Enter a title..." onChange={e => setBook(b => ({...b, title: e.target.value}))}></input>
                </div>
                <div className="form-label-and-input">
                    <label className="modalInputContent" htmlFor="bookAuthorLast">Author last: </label>
                    <input className="modalInputContent" id="bookAuthorLast" placeholder="Enter author last name..." onChange={(e) => setBook(b => ({...b, authorLast: e.target.value}))}></input>
                </div>
                <div className="form-label-and-input">
                    <label className="modalInputContent" htmlFor="bookAuthorFirst">Author first: </label>
                    <input className="modalInputContent" id="bookAuthorFirst" placeholder="Enter author first name..." onChange={(e) => setBook(b => ({...b, authorFirst: e.target.value}))}></input>
                </div>
                <div className="form-label-and-input">
                    <label className="modalInputContent" htmlFor="bookGenre">Genre: </label>
                    <select className="modalInputContent" name="genres" onChange={(e) => setBook(b => ({...b, genre: e.target.value}))}>
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
                    <label className="modalInputContent" htmlFor="bookCopies">Number of Copies: </label>
                    <button className="valueControl" 
                            onMouseDown={() => handleMouseDown(-1)}
                            onMouseUp={clearIntervalRef}
                            onMouseLeave={clearIntervalRef}
                            title="Decrease value" 
                            aria-label="Decrease value">
                                -
                    </button>
                    <input 
                        className="modalInputContent" 
                        type="number" 
                        id="bookCopiesInput" 
                        name="bookCopiesInput" 
                        min="1" 
                        max="99" 
                        value={newBook.numCopies} 
                        onChange={e => {
                            const newValue = e.target.valueAsNumber;
                            if (!isNaN(newValue) && newValue >= 1 && newValue <= 99) {
                                setBook(b => ({...b, numCopies: (e.target as HTMLInputElement).valueAsNumber}));
                            } else if (e.target.value === '') {
                                setBook(b => ({...b, numCopies: 1}));
                            }
                        }}>
                    </input>
                    <button className="valueControl" 
                            onMouseDown={() => handleMouseDown(1)}
                            onMouseUp={clearIntervalRef}
                            onMouseLeave={clearIntervalRef}
                            title="Decrease value" 
                            aria-label="Decrease value">
                                +
                    </button>
                </div>
                <div className="form-label-and-input">
                    <ImageUpload setImagePath={setImagePath} imagePath={newBook.imagePath} />
                </div>
                <button className="modalInputContent" onClick={handleSubmitClick} disabled={!(isTitleValid && isAuthorValid && isNumCopiesValid)}>Submit</button>
            </div>
        </div>
    )    
}