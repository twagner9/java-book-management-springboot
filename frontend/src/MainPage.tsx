import React, { useEffect, useState } from "react";
import { BookModal } from "./BookModal";
import { BookInputs } from "./BookInputs";
import { useTableData } from "./useTableData";
import { BookTable } from "./BookTable";
import { ImageUpload } from "./ImageUpload";
import { EditableText } from "./EditableText";

// export type Book = {
//   id: number;
//   imagePath: string;
//   authorLast: string;
//   authorFirst: string;
//   title: string;
//   genre: string;
//   numCopies: number;
// };

// TODO: update to fetch the latest ID number from the database
let currentId: number = 1;

export function MainPage() {
  const { books, loading } = useTableData();
  const [modalOpen, setModalOpen] = React.useState(false); // tracks input modal state
  // const [loading, setLoading] = React.useState(true); // tracks the loading state of the application

  // Record<T, T> is akin to specifying a type of std::unordered_map in C++
  const [selectedImage, setSelectedImage] = useState<string | null>(null);

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
          mapping[book.id] = window.electronAPI.toSafeFile(book.imagePath);
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

  function editText(selectedText: string): void {}

  function updateDatabase(column: string): void {
    // TODO: update the appropriate column based on the name passed
    if (column == "title") {
    } else if (column === "author_last") {
    } else if (column === "author_first") {
    }
  }

  /**
   * Determine the current sort state of the table. Default is title sort.
   */
  useEffect(() => {
    let url = "";
    if (sortState.column === "title")
      url =
        sortState.order === "asc"
          ? "/api/books/titleSortAsc"
          : "/api/books/titleSortDesc";
    else if (sortState.column === "authorLast")
      url =
        sortState.order === "asc"
          ? "/api/books/authorLastSortAsc"
          : "/api/books/authorLastSortDesc";
    else if (sortState.column === "genre")
      url =
        sortState.order === "asc"
          ? "/api/books/genreSortAsc"
          : "/api/books/genreSortDesc";

    fetch(url)
      .then((response) => {
        if (!response.ok)
          throw new Error("Failed to return title sorted Book list.");
        return response.json();
      })
      .then((sortedList) => setBooks(sortedList))
      .catch((error) => {
        console.error("Error sorting list.", error);
      });
  }, [sortState]);

  /**
   * POST book to the SQL database.
   * @param newBook The Book object that will be added to the database.
   */
  function handleBookAdded(newBook: Book) {
    fetch("/api/books", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(newBook),
    })
      .then((response) => {
        if (!response.ok) throw new Error("Failed to add book to database.");
        return response.json();
      })
      .then((idNum) => {
        newBook.id = idNum;
        setBooks((prevBooks) => [...prevBooks, newBook]);
        setModalOpen(false);
      })
      .catch((error) => {
        alert("Error adding book to the database/GUI");
        console.error(error);
      });
  }

  function handleBookEdited(id: number, updatedText: string) {
    fetch("/api/books", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(updatedText),
    }).then((response) => {
      if (!response.ok) throw new Error("Failed to update book.");
      return response.json();
    });
  }

  function handleSort(column: string) {
    setSortState((prev) => ({
      column,
      order: prev.column === column && prev.order === "asc" ? "desc" : "asc",
    }));
  }

  return (
    <div className="main-content">
      <h1 className="main-text">Small Library Management</h1>
      {!loading && books.length === 0 && (
        <div>
          <p className="main-text main-description">
            This application is meant to give individuals and those with shared
            libraries such as classroom libraries a way to log and track their
            owned books. As of now, entry must be done manually, but in the
            future, the hope is to add features such as ISBN scanning and book
            cover image uploading. To get started adding books, click the button
            below!
          </p>
        </div>
      )}
      <button className="add-book-button" onClick={handleAddClick}>
        Add a book
      </button>
      <BookModal
        isOpen={modalOpen}
        onRequestClose={closeModal}
        className="prop-modal-content"
        overlayClassName="modal-overlay"
      >
        <h3 className="modal-heading">Add Book</h3>
        {<BookInputs currentId={currentId} onBookAdded={handleBookAdded} />}
      </BookModal>
      <BookTable bookData={books}></BookTable>
      {selectedImage && (
        <div className="modal" onClick={() => setSelectedImage(null)}>
          <img src={selectedImage}></img>
        </div>
      )}
      <EditableText></EditableText>
    </div>
  );
}
