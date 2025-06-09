import React from 'react';

export function EmptyDatabase() {
    function handleClick() {
        alert("TODO: add logic for displaying a menu that allows the user to specify book details");
    }


    return(
        <div className="main-content">
            <h1 className="main-text">Small Library Management</h1>
            <h4 className="main-text">Welcome to Library Management!</h4>
            <p className="main-text main-description">This application is meant to give individuals and those with shared libraries such as classroom libraries a way to 
                log and track their owned books. As of now, entry must be done manually, but in the future, the is hope to add
                features such as ISBN scanning and book cover image uploading. To get started adding books, click the button below!
            </p>
            <button className="add-book-button" onClick={handleClick}>Add your first book</button>
        </div>
    )
}
