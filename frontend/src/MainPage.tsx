import React, { useEffect, useState } from "react";
import { BookModal } from "./BookModal";
import { BookInputs } from "./BookInputs";
import { BookTable } from "./BookTable";
import { ImageUpload } from "./ImageUpload";
import { EditableText } from "./EditableText";
import * as ApiService from "./bookAPI";

export type Book = {
  id: number;
  imagePath: string;
  authorLast: string;
  authorFirst: string;
  title: string;
  genre: string;
  numCopies: number;
};

type SortColumn = "title" | "author_last" | "genre"; // For now, the only sortable columns are title, author's last name, and genre

export type SortState = {
  column: SortColumn;
  order: boolean;
};
export type TableOperation =
  | { type: "add"; row: Book }
  | { type: "edit"; row: Book }
  | { type: "delete"; row: Book };

export function MainPage() {
  const [books, setBooks] = useState<Book[]>([]);
  const [loading, setLoading] = useState<boolean>(false);
  const [modalOpen, setModalOpen] = React.useState(false); // tracks input modal state
  // const [loading, setLoading] = React.useState(true); // tracks the loading state of the application
  const [curSortState, setSortState] = useState<SortState>({
    // tracks the current sorted state of the books in the table
    column: "title",
    order: true, // true means ascending, false descending
  });

  // Record<T, T> is akin to specifying a type of std::unordered_map in C++
  const [selectedImage, setSelectedImage] = useState<string | null>(null);
  const [pendingOperation, setPendingOperation] =
    useState<TableOperation | null>(null);

  /**
   * Supposed to load images in safely by mapping the safe-file URLs to a React state so that it can be done async, which is required
   * when using the ipcMain handle method for the custom toSafeFile function in the exposed electron API.
   */
  function closeModal() {
    setModalOpen(false);
  }

  function handleAddClick() {
    setModalOpen(true);
  }

  useEffect(() => {
    const initialLoad = async () => {
      try {
        const initBooks = await ApiService.getBooks();
        setBooks(initBooks);
      } catch (error) {
        console.error("Cannot retrieve list of books from database.", error);
      }
    };

    initialLoad();
  }, []);

  /**
   * POST book to the SQL database.
   * @param newBook The Book object that will be added to the database.
   */
  const handleBookAdded = async (newBook: Book) => {
    try {
      const result = await ApiService.addBook(newBook);
      newBook.id = result;
      setBooks((prevBooks) => [...prevBooks, newBook]);
      console.log("Successfully updated books state");
      closeModal();
    } catch (error) {
      console.error(
        "Error attempting to add book data to the database:",
        error,
      );
    }
  };

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
        {<BookInputs onBookAdded={handleBookAdded} />}
      </BookModal>
      <BookTable
        bookData={books}
        sortState={curSortState}
        onBookChange={setBooks}
        onSortChange={setSortState}
        onImageClick={setSelectedImage}
      ></BookTable>
      {/* selectedImage and the accompanying modal are meant to display a full-size image of one of the thumbnails displayed in the
          table. This needs to present on MainPage.tsx, which means the state of the image must be updated by the BookTable */}
      {selectedImage && (
        <div className="modal" onClick={() => setSelectedImage(null)}>
          <img src={selectedImage}></img>
        </div>
      )}
      <EditableText></EditableText>
    </div>
  );
}
