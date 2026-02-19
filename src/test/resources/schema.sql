CREATE TABLE IF NOT EXISTS all_books(
	    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, 
	    image TEXT, 
	    title TEXT, 
	    author_last TEXT, 
	    author_first TEXT, 
	    genre TEXT, 
	    num_copies INTEGER
    );
-- INSERT INTO all_books(title, author_last, author_first, genre, num_copies) VALUES('Test Book', 'lastname', 'firstname', 'Fantasy', 3);