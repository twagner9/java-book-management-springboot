import { useState, useRef, useEffect } from "react";
import { EditableColumns } from "./BookTable";

type EditableTextProps = {
  currentData: string;
  onFinishedEditing: (newValue: string) => boolean;
  // onUpdateText: () => string;
  // className?: string;
};

export function EditableText({
  currentData,
  onFinishedEditing,
}: EditableTextProps) {
  const [editing, setEditing] = useState<boolean>(false);
  const [savedText, setSavedText] = useState<string>(currentData);
  const [currentText, setCurrentText] = useState<string>(savedText);
  const inputRef = useRef<HTMLInputElement>(null); // Converting <p> to input, so need to have a way to reference the <input>

  let tempValue: string = "";
  /* Requirements for this button:

        1. It should allow the user to edit the text within the cell by simply clicking. It should convert the plain text into an input.
        2. The user will be able to click off, or press enter and set the editing to false; once this is done, the text should be checked
            against what was already present; if different make the change and send the update to the database.
        3. 
    */

  const keyPress = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key == "Enter") {
      triggerSaveAndUpdateDatabase();
    }
    if (e.key == "Escape") {
      setCurrentText(savedText);
      setEditing(false);
    }
  };

  const handleInputLosesFocus = (e: React.FocusEvent<HTMLInputElement>) => {
    triggerSaveAndUpdateDatabase();
  };

  const onEditableTextClick = () => {
    setEditing(true);
  };

  function triggerSaveAndUpdateDatabase() {
    const successfulDatabaseUpdate = onFinishedEditing(currentText);
    if (successfulDatabaseUpdate) setSavedText(currentText);
    else
      console.error(
        `Failed to transmit update to database so table will not be updated with ${currentText}`,
      );
    setEditing(false);
    // TODO: trigger DB update -- this should be passed from the parent as a prop, and triggered when save occurs
  }

  return (
    <div>
      {editing ? (
        <input
          autoFocus
          type="text"
          value={currentText}
          onChange={(e) => setCurrentText(e.target.value)}
          onKeyDown={keyPress}
          onBlur={handleInputLosesFocus}
        ></input>
      ) : (
        <p style={{ cursor: "pointer" }} onClick={onEditableTextClick}>
          {savedText}
        </p>
      )}
    </div>
  );
}
