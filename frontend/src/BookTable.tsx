import React, { useEffect, useState } from "react";
import { Book } from "./MainPage";
import { SortableColumn } from "./MainPage";
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

  /**
   * Load images from the user's filesystem using the file path saved in the database.
   */
  useEffect(() => {
    async function loadSafeImages() {
      // Creates hashmap associating the book ID with the safe URL; then, in JSX below, I can directly access the correct
      // image path based on the book ID number
      const mapping: Record<string, string> = {};
      for (const book of bookData) {
        if (book.imagePath !== null) {
          mapping[book.id] = await window.electronAPI.toSafeFile(
            book.imagePath,
          );
        }
      }
      setSafeImages(mapping);
    }
    loadSafeImages();
  }, [bookData]); // the books state is in the dependency array, so this effect will execute each time books is updated

  /**
   * Perform a database deletion of the book and update the state of the books array.
   * @param bookId ID number of book being deleted.
   */
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

  /**
   * Sorts all books in the table by the selected column. By default, the sort will occur in ascending
   * order on first click, and the second click will sort by descending.
   * @param columnToSort The specific column that all books will be sorted by.
   */
  const handleSort = async (columnToSort: SortableColumn) => {
    // 1. See if a new column was selected; if so, by default sort by ascending. Otherwise, invert current sort order.
    const updatedSortState: SortState = {
      column: columnToSort,
      order: columnToSort === sortState.column ? !sortState.order : true,
    };

    // 2. Use the frontend API service to get the sorted books.
    try {
      const result = await ApiService.getSortedBooks(updatedSortState);
      onBookChange(result);
      onSortChange(updatedSortState);
    } catch (error) {
      console.error(
        `Sorting of books based on column ${sortState.column} failed:`,
        error,
      );
    }
  };

  /**
   *
   * @param bookId ID of book being updated.
   * @param columnToEdit The specific Book field being updated.
   * @param newValue The value to which the columnToEdit will be changed.
   * @returns True when the edit succeeds, false when it fails.
   */
  const handleEditedData = async (
    bookId: number,
    columnToEdit: EditableColumns,
    newValue: string,
  ): Promise<boolean> => {
    try {
      await ApiService.editBook({
        id: bookId,
        column: columnToEdit,
        newData: newValue,
      });

      onBookChange((prevBooks) =>
        prevBooks.map((book) =>
          book.id === bookId
            ? {
                ...book,
                [columnToEdit]: newValue,
              }
            : book,
        ),
      );
      return true;
    } catch (error) {
      console.error(
        `Failed to update column ${columnToEdit} to ${newValue}:`,
        error,
      );
    }
    return false;
  };

  return (
    <table className="book-table">
      <thead>
        <tr>
          <th>Image</th>
          <th className="sortHeader" onClick={() => handleSort("title")}>
            Title
          </th>
          <th className="sortHeader" onClick={() => handleSort("author_last")}>
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
                  src={safeImages[book.id] ? safeImages[book.id] : undefined}
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
                onFinishedEditing={(newValue) =>
                  handleEditedData(book.id, "title", newValue)
                }
              />
            </td>
            <td>
              <EditableText
                currentData={book.authorLast}
                onFinishedEditing={(newValue) =>
                  handleEditedData(book.id, "author_last", newValue)
                }
              />
            </td>
            <td>
              <EditableText
                currentData={book.authorFirst}
                onFinishedEditing={(newValue) =>
                  handleEditedData(book.id, "author_first", newValue)
                }
              />
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
