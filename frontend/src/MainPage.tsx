import React, {useEffect, useState } from 'react';
import { BookModal } from './BookModal';
import { BookInputs } from './BookInputs';
import { ImageUpload } from './ImageUpload';

export type Book = {
    id: number;
    imagePath: string,
    authorLast: string;
    authorFirst: string;
    title: string;
    genre: string;
    numCopies: number;
};

// TODO: update to fetch the latest ID number from the database
let currentId: number = 1;

export function MainPage() {
    const [books, setBooks] = React.useState<Book[]>([]); // tracks Book objects
    const [modalOpen, setModalOpen] = React.useState(false); // tracks input modal state
    const [loading, setLoading] = React.useState(true); // tracks the loading state of the application
    const [sortState, setSortState] = React.useState({ // tracks the current sorted state of the books in the table
        column: "title",
        order: "asc",
    });
    // Record<T, T> is akin to specifying a type of std::unordered_map in C++
    const [safeImages, setSafeImages] = useState<Record<string, string>>({}); // loads image URLs asynchronously so they can be used in JSX
    const [selectedImage, setSelectedImage] = useState<string | null>(null);

    useEffect(() => {
        fetch('/api/books')
            .then(response => response.json())
            .then(data => setBooks(data))
            .catch(error => console.error('Error fetching books:', error))
            .finally(() => setLoading(false));
    }, []); // This empty "dependency array" in useEffect means this effect runs once after the initial render; if variables are added here,
            // the effect will run again when those variables change.
    
    /**
     * Supposed to load images in safely by mapping the safe-file URLs to a React state so that it can be done async, which is required
     * when using the ipcMain handle method for the custom toSafeFile function in the exposed electron API. 
     */
    useEffect(() => {
        async function loadSafeImages() {
            // Creates hashmap associating the book ID with the safe URL; then, in JSX below, I can directly access the correct
            // image path based on the book ID number
            const mapping: Record<string, string> = {};
            for (const book of books) {
                if (book.imagePath !== "")
                    mapping[book.id] = await window.electronAPI.toSafeFile(book.imagePath);
            }
            setSafeImages(mapping);
        }
        loadSafeImages();
    }, [books]); // the books state is in the dependency array, so this effect will execute each time books is updated
    function closeModal() {
        setModalOpen(false);
    }

    function handleAddClick() {
        setModalOpen(true);
    }

    useEffect(() => {
        let url = '';
        if (sortState.column === 'title') url = sortState.order === 'asc' ? '/api/books/titleSortAsc' : '/api/books/titleSortDesc';
        else if (sortState.column === 'authorLast') url = sortState.order === 'asc' ? '/api/books/authorLastSortAsc' : '/api/books/authorLastSortDesc';
        else if (sortState.column === 'genre') url = sortState.order === 'asc' ? '/api/books/genreSortAsc' : '/api/books/genreSortDesc';

        fetch(url)
            .then(response => {
                if (!response.ok) throw new Error("Failed to return title sorted Book list.");
                return response.json();
            })
            .then((sortedList) => setBooks(sortedList))
            .catch(error => {
                console.error("Error sorting list.", error);
            });
    }, [sortState]);

    function handleBookAdded(newBook: Omit<Book, "id">) {
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

    
    // TODO: more yet to do on this function
    function handleDeleteClick(bookId: number) {
        fetch(`/api/books/${bookId}`, {
            method: "DELETE"
        })
        .then(response => {
            if (response.ok) {
                setBooks(prevBooks => prevBooks.filter(book => book.id !== bookId));
            }
            else {
                console.error("Failed to delete book: ", response.status);
            }
        })
        .catch(error => {
            console.error("Network or server error during delete.", error);
        })
    }

    function handleSort(column: string) {
        setSortState(prev => ({
            column,
            order: prev.column === column && prev.order === 'asc' ? 'desc' : 'asc'
        }));
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
            <BookModal isOpen={modalOpen} onRequestClose={closeModal} className="prop-modal-content" overlayClassName="modal-overlay">
                <h3 className='modal-heading'>Add Book</h3>
                {
                    <BookInputs currentId={currentId} onBookAdded={handleBookAdded} />
                }
            </BookModal>
            <table className="book-table">
                <thead>
                    <tr>
                        <th>Image</th>
                        <th className="sortHeader" onClick={() => handleSort('title')}>Title</th>
                        <th className="sortHeader" onClick={() => handleSort('authorLast')}>Last</th>
                        <th>First</th>
                        <th className="sortHeader" onClick={() => handleSort('genre')}>Genre</th>
                        <th>Number of Copies</th>
                    </tr>
                </thead>
                <tbody>
                    {books.map((book) => (
                        <tr key={book.id}>
                            <td>
                                {book.imagePath ? (<img 
                                                    className="tableImage" 
                                                    key={book.id}
                                                    src={(safeImages[book.id]) ? safeImages[book.id] : ""} 
                                                    alt={`Book cover to: ${window.electronAPI.toSafeFile(book.imagePath)}`}
                                                    onClick={() => setSelectedImage(safeImages[book.id])}>
                                                </img>) : (<p>No image uploaded</p>)}
                            </td>
                            <td>{book.title}</td>
                            <td>{book.authorLast}</td>
                            <td>{book.authorFirst}</td>
                            <td>{book.genre}</td>
                            <td>{book.numCopies}</td>
                            <td>
                                <button className="tableDeleteButton" onClick={() => handleDeleteClick(book.id)}>Delete</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
            {selectedImage && (
                <div className="modal" onClick={() => setSelectedImage(null)}>
                    <img src={selectedImage}></img>
                </div>
            )}
        </div>
    );
}