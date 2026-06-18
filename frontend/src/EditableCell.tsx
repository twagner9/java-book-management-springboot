import React, { useState } from "react";

/**
 * The goal of this function will be to convert the text into an input
 * that will allow user editing. Once the user has pressed enter, it should save the new text
 * and submit an update to the database -- though this may be changed later
 * depending on whether we can just do a final commit of all changes by queuing db updates
 * instead of submitting right away.
 *
 * So it will enable the editing, and transmit the edited value back to the MainPage where the
 * cell is awaiting the updates. Esc key is how user can cancel changes, or by clicking outside
 * of the input. Until the user presses enter, the parent won't know anything about the changes.
 * Then, when there are changes, the parent can handle transmitting the updated data to the db.
 */

type EditMode = "click" | "dblClick";

interface EditableCellProps {
  curValue: string; // pass down the original text to the component
  onSave?: (newValue: string) => string;
  editMode?: EditMode;
  multiline?: boolean;
  className?: string;
  inputClassName?: string;
  // optional: keep cell focusable / keyboard open
  // allowKeyboardOpen?: boolean;
}

export default function EditableCell({
  curValue,
  onSave,
  editMode = "dblClick",
  multiline = false,
  className = "",
  inputClassName = "",
}: EditableCellProps) {
  const [editing, setEditing] = React.useState(false); // Need to know if this cell is being edited or not.
}
