import { useState, useRef, useEffect } from "react";

type EditableTextProps = {
  // onUpdateText: () => string;
  className?: string;
};

export function EditableText({ className }: EditableTextProps) {
  const [editing, setEditing] = useState<boolean>(false);
  const [savedText, setSavedText] = useState<string>("hello");
  const [currentText, setCurrentText] = useState<string>(savedText);
  const inputRef = useRef<HTMLInputElement>(null); // Converting <p> to input, so need to have a way to reference the <input>

  let tempValue: string = "";
  /* Requirements for this button:

        1. It should allow the user to edit the text within the cell by simply clicking. It should convert the plain text into an input.
        2. The user will be able to click off, or press enter and set the editing to false; once this is done, the text should be checked
            against what was already present; if different make the change and send the update to the database.
        3. 
    */

  // For now just fuck around. Make A button. Convert it into an input. Then just log that input. Literally just one step at a time
  // to get back into the frontend bullshit.

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
    setSavedText(currentText);
    setEditing(false);
    // TODO: trigger DB update
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
