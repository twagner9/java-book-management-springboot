import { useState, useEffect } from "react";
import {Book} from "./MainPage";
import {SortState} from "./MainPage"

type Props = {
  sortState: SortState
  books: Book[],
  updateBooks(newBooks: Book[]): () => void,
};

// TODO: sort state has to be passed in from the table itself; so MainPage must manage it

export function useTableData({sortState, books, updateBooks}: Props) {
  const [loading, setLoading] = useState<boolean>(true);

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
      .then((sortedList) => updateBooks(sortedList))
      .catch((error) => {
        console.error("Error sorting list.", error);
      });
  }, [sortState]);

  useEffect(() => {
    fetch("/api/books")
      .then((res) => res.json())
      .then((data) => {
        updateBooks(data);
        setLoading(false);
      })
      .catch((error) => console.error("Error fetching books:", error))
      .finally(() => setLoading(false));
  }, []);

  return { books, loading };
}
