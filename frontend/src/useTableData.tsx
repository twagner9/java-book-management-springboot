import { useState, useEffect } from "react";

export type Book = {
  id: number;
  imagePath: string;
  authorLast: string;
  authorFirst: string;
  title: string;
  genre: string;
  numCopies: number;
};

export function useTableData() {
  const [books, setBooks] = useState<Book[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [sortState, setSortState] = useState({
    // tracks the current sorted state of the books in the table
    column: "title",
    order: "asc",
  });

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

  useEffect(() => {
    fetch("/api/books")
      .then((res) => res.json())
      .then((data) => {
        setBooks(data);
        setLoading(false);
      })
      .catch((error) => console.error("Error fetching books:", error))
      .finally(() => setLoading(false));
  }, []);

  return { books, loading };
}
