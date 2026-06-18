import React, { useEffect, useState } from "react";

type BookTableProps<T> = {
  bookData: T[]; // This will have type Book; defined in MainPage.tsx
};

export function BookTable() {
  const [sortState, setSortState] = React.useState({
    // tracks the current sorted state of the books in the table
    column: "title",
    order: "asc",
  });
  const [safeImages, setSafeImages] = useState<Record<string, string>>({}); // loads image URLs asynchronously so they can be used in JSX

  const columnTitles = [
    "Image",
    "Title",
    "Last",
    "First",
    "Genre",
    "Number of Copies",
  ];

  function handleSort(column: string) {
    setSortState((prev) => ({
      column,
      order: prev.column === column && prev.order === "asc" ? "desc" : "asc",
    }));
  }

  // TODO: more yet to do on this function
  function handleDeleteClick(bookId: number) {
    fetch(`/api/books/delete/${bookId}`, {
      method: "DELETE",
    })
      .then((response) => {
        if (response.ok) {
          setBooks((prevBooks) =>
            prevBooks.filter((book) => book.id !== bookId),
          );
        } else {
          console.error("Failed to delete book: ", response.status);
        }
      })
      .catch((error) => {
        console.error("Network or server error during delete.", error);
      });
  }

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
        {books.map((book) => (
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
