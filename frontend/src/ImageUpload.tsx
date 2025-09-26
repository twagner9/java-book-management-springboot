import {useState} from 'react';
// import React, {useRef, ChangeEvent} from 'react';
export function ImageUpload({imagePath, setImagePath}: {imagePath: string; setImagePath: (val: string) => void}) {
    // useRef hook takes generic, which must have type specified so compiler knows what to expect
    const [filename, setFilename] = useState('');
    const [uploadError, setUploadError] = useState('');
    // const uploadRef = useRef<HTMLInputElement>(null);

    const handleSelectFile = async () => {
        const filePath: string | null = await window.electronAPI.openFileDialog();
        if (filePath) {
            if (!filePath.endsWith(".jpg") && !filePath.endsWith(".jpeg") && !filePath.endsWith(".png")) {
                setUploadError("Must upload an image as .jpg, .jpeg, or .png.");
                setFilename("");
            } else {
                setFilename(filePath);
                setImagePath(filePath);
                setUploadError("");
            }
        } else {
            setUploadError("The image could not be uploaded; try again.");
            setFilename("");
        }
        // TODO: now have to manually check the file extension and type
        console.log("Select file path: ", filePath);
    };

    return (
        <>
            <button type="button" className="imageUploadButton" onClick={handleSelectFile}>Upload Image</button>
            {uploadError ? <p className="uploadError">{uploadError}</p> : null}
            {filename ? <p className="filename">{filename}</p> : null}
        </>
    );
}