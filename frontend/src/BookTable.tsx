import React, { useEffect, useState } from "react";
import { Book } from "./MainPage";
import { SortState } from "./MainPage";
import * as ApiService from "./bookAPI";
import { EditableText } from "./EditableText";

// Pass the state controlling function so it can be updated from this component and triggered
// for backend get
type Props = {
  bookData: Book[]; // READ/WRITE -- writeable for deletion
  sortState: SortState; // READ/WRITE
  onBookChange: React.Dispatch<React.SetStateAction<Book[]>>;
  onSortChange: React.Dispatch<React.SetStateAction<SortState>>;
  onImageClick: React.Dispatch<React.SetStateAction<string | null>>;
};

export type EditableColumns = "title" | "author_last" | "author_first";
export type CellEditingData = {
  id: number;
  column: EditableColumns;
  newData: string;
};

export function BookTable({
  bookData,
  sortState,
  onBookChange,
  onSortChange, // TODO: must update the sortState each time a sort option is selected.
  onImageClick,
}: Props) {
  const [safeImages, setSafeImages] = useState<Record<string, string>>({}); // loads image URLs asynchronously so they can be used in JSX

  useEffect(() => {
    async function loadSafeImages() {
      // Creates hashmap associating the book ID with the safe URL; then, in JSX below, I can directly access the correct
      // image path based on the book ID number
      const mapping: Record<string, string> = {};
      for (const book of bookData) {
        if (book.imagePath !== "")
          mapping[book.id] = window.electronAPI.toSafeFile(book.imagePath);
      }
      setSafeImages(mapping);
    }
    loadSafeImages();
  }, [bookData]); // the books state is in the dependency array, so this effect will execute each time books is updated
  // TODO: more yet to do on this function
  const handleDeleteClick = async (bookId: number) => {
    // Delete in backend
    try {
      await ApiService.deleteBook(bookId);
    } catch (error) {
      console.error("Error deleting book from database:", error);
    }

    // Delete the row from the user's view
    onBookChange((prevBooks) => prevBooks.filter((book) => book.id !== bookId));
  };

  const handleSort = async (sortingInfo: SortState) => {
    try {
      const result = await ApiService.getSortedBooks(sortingInfo);
      onBookChange(result);
    } catch (error) {
      console.error(
        `Sorting of books based on column ${sortingInfo.column} failed:`,
        error,
      );
    }
  };

  const handleEditedData = async (newValue: string): Promise<boolean> => {
    try {
      const result = await ApiService.editBook({
        id: bookId,
        column: columnToEdit,
        newData: newValue,
      });
    } catch (error) {}
  };

  return (
    <table className="book-table">
      <thead>
        <tr>
          <th>Image</th>
          <th
            className="sortHeader"
            onClick={() =>
              handleSort({
                column: "title",
                order: !sortState.order, // Negate to toggle order between asc and desc
              })
            }
          >
            Title
          </th>
          <th
            className="sortHeader"
            onClick={() =>
              handleSort({
                column: "author_last",
                order: !sortState.order,
              })
            }
          >
            Last
          </th>
          <th>First</th>
          <th
            className="sortHeader"
            onClick={() =>
              handleSort({
                column: "genre",
                order: !sortState.order,
              })
            }
          >
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
                  onClick={() => onImageClick(safeImages[book.id])}
                ></img>
              ) : (
                <p>No image uploaded</p>
              )}
            </td>
            <td>
              <EditableText
                currentData={book.title}
                onFinishedEditing={handleEditedData}
              ></EditableText>
            </td>
            <td>
              <EditableText currentData={book.authorLast} />
            </td>
            <td>
              <EditableText currentData={book.authorFirst} />
            </td>
            <td>
              <p>{book.genre}</p>
            </td>
            <td>
              <p>{book.numCopies} </p>
            </td>
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
