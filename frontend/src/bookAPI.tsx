import { Book } from "./MainPage";
import { CellEditingData } from "./BookTable";
import { SortState } from "./MainPage";

export async function getBooks(): Promise<Book[]> {
  console.log("Triggered fetch of all books.");
  const response = await fetch("/api/books");

  if (!response.ok) {
    throw new Error("Failed to retrieve all books.");
  }

  return response.json();
}

export async function getSortedBooks(sorting: SortState): Promise<Book[]> {
  const endpoints = {
    title: "titleSort",
    author_last: "authorLastSort",
    genre: "genreSort",
  };

  const endpoint = endpoints[sorting.column];
  if (!endpoint) {
    throw new Error("Column to sort is unavailable.");
  }

  const response = await fetch(`/api/books/${endpoint}/${sorting.order}`);
  if (!response.ok) {
    throw new Error("Failed to retrieve books in sorted order.");
  }
  return response.json();
}

export async function addBook(book: Book): Promise<number> {
  console.log("Triggered async function addBook");
  const response = await fetch("/api/books", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(book),
  });

  if (!response.ok) {
    throw new Error("Failed to add new book to database.");
  }

  const id = await response.json();
  return id;
}

export async function editBook(data: CellEditingData) {
  const editingEndpoints = {
    title: "updateTitle",
    author_last: "updateAuthorLast",
    author_first: "updateAuthorFirst",
  };
  const endpoint = editingEndpoints[data.column];
  const response = await fetch(`/api/books/${endpoint}/${data.id}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ value: data.newData }),
  });

  if (!response.ok) {
    throw new Error("Failed to update book in database.");
  }
}

export async function deleteBook(id: number) {
  console.log("Triggered deletion of book.");
  const response = await fetch(`/api/books/delete/${id}`, {
    method: "DELETE",
  });
  if (!response.ok) {
    throw new Error("Error deleting book from database.");
  }
}
