import {useState} from "react";

type EditableTextProps = {
    onUpdateText: () => string,
    className?: string,
};

export function EditableText({onUpdateText, className}: EditableTextProps) {
    const [editing, setEditing] = useState<boolean>(false);
    const [currentText, setCurrentText] = useState<string>("");

    /* Requirements for this button:

        1. It should allow the user to edit the text within the cell by simply clicking. It should convert the plain text into an input.
        2. The user will be able to click off, or press enter and set the editing to false; once this is done, the text should be checked
            against what was already present; if different make the change and send the update to the database.
        3. 
    */

};
