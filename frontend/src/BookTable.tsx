import React, { useEffect, useState } from "react";
import { Book } from "./MainPage";
import { SortState } from "./MainPage";

// Pass the state controlling function so it can be updated from this component and triggered
// for backend get
type Props = {
  bookData: Book[]; // READ/WRITE -- writeable for deletion
  sortState: SortState; // READ/WRITE
  onBookChange: (books: Book[]) => void;
  onSortChange: (s: SortState) => void;
};

export function BookTable({
  bookData,
  sortState,
  onBookChange,
  onSortChange,
}: Props) {
  const [safeImages, setSafeImages] = useState<Record<string, string>>({}); // loads image URLs asynchronously so they can be used in JSX

  const columnTitles = [
    "Image",
    "Title",
    "Last",
    "First",
    "Genre",
    "Number of Copies",
  ];

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
        onBookChange((prevBooks: Book[]) => [...prevBooks, newBook]);
        // setModalOpen(false);
      })
      .catch((error) => {
        alert("Error adding book to the database/GUI");
        console.error(error);
      });
  }

  // TODO: more yet to do on this function
  function handleDeleteClick(bookId: number) {
    fetch(`/api/books/delete/${bookId}`, {
      method: "DELETE",
    })
      .then((response) => {
        if (response.ok) {
          onBookChange((bookData) => {
            bookData.filter((book) => book.id !== bookId);
          }); // =>
          //   prevBooks.filter((book) => book.id !== bookId),
          // );
        } else {
          console.error("Failed to delete book: ", response.status);
        }
      })
      .catch((error) => {
        console.error("Network or server error during delete.", error);
      });
  }

  const handleSort = (column: string) => {
    if (column !== sortState.column) {
      onSortChange({ column, order: sortState.order });
    }
  };

  return (
    <table className="book-table">
      <thead>
        <tr>
          <th>Image</th>
          <th className="sortHeader" onClick={() => handleSort("title")}>
            Title
          </th>
          <th className="sortHeader" onClick={() => handleSort("authorLast")}>
            Last
          </th>
          <th>First</th>
          <th className="sortHeader" onClick={() => handleSort("genre")}>
            Genre
          </th>
          <th>Number of Copies</th>
        </tr>
      </thead>
      <tbody>
        {bookData.map((book) => (
          <tr key={book.id}>
            <td>
              {book.imagePath ? (
                <img
                  className="tableImage"
                  key={book.id}
                  src={safeImages[book.id] ? safeImages[book.id] : ""}
                  alt={`Book cover to: ${window.electronAPI.toSafeFile(book.imagePath)}`}
                  onClick={() => setSelectedImage(safeImages[book.id])}
                ></img>
              ) : (
                <p>No image uploaded</p>
              )}
            </td>
            <td>{book.title}</td>
            <td>{book.authorLast}</td>
            <td>{book.authorFirst}</td>
            <td>{book.genre}</td>
            <td>{book.numCopies}</td>
            <td>
              <button
                className="tableDeleteButton"
                onClick={() => handleDeleteClick(book.id)}
              >
                Delete
              </button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
