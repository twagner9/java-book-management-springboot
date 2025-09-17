import {useState} from 'react';
// import React, {useRef, ChangeEvent} from 'react';
export function ImageUpload() {
    // useRef hook takes generic, which must have type specified so compiler knows what to expect
    const [filename, setFilename] = useState('');
    const [uploadError, setUploadError] = useState('');
    // const uploadRef = useRef<HTMLInputElement>(null);

    const handleSelectFile = async () => {
        const filePath: string | null = await window.electronAPI.openFileDialog();
        if (filePath) {
            if (!filePath.endsWith(".jpg") && !filePath.endsWith(".jpeg") && !filePath.endsWith(".png")) {
                setUploadError("Must upload an image as .jpg, .jpeg, or .png.");
            } else {
                setFilename(filePath);
            }
        } else {
            setUploadError("The image could not be uploaded; try again.");
        }
        // TODO: now have to manually check the file extension and type
        console.log("Select file path: ", filePath);
    };

    // const handleUpload = (e: ChangeEvent<HTMLInputElement>) => {
    //     if (e.target.files === null) {
    //         return;
    //     }
    //     // The event automatically comes packed with an array for holding files;
    //     // there is no built-in extension checking, so it will have to be enforced manually.
    //     // It does, however, have 4 useful properties (lastModified, name, size, and type) that
    //     // can be used for various purposes -- type, of course, being the extension.
    //     const file = e.target.files[0];

    //     if (file) {
    //         // TEST: see if the absolute path to the image is stored...
    //         const filePath = window.electronAPI.getFilePath(file);
    //         console.log(filePath);
    //         if (file.type !== 'image/jpeg' && file.type !== 'image/png' && file.type !== '.jpg') {
    //             setUploadError("Must upload an image as a jpeg or png.");
    //             return;
    //         }
            
    //         // Now load the file:
    //         const fileReader = new FileReader();
    //         // FIXME: this may change if the upload is an image (in my case it is)
    //         fileReader.onload = (event) => {
    //             const contents = event?.target?.result;
    //             // TODO: do something with contents
    //             setFilename(file.name);
    //             console.log(file.name);
    //             console.log('filename: ' + filename);
    //         }
    //         e.target.value = '';
    //         fileReader.readAsDataURL(file);
    //     } else {
    //         setUploadError("File could not be uploaded; try again.");
    //     }
    // };

    return (
        <>
            <button type="button" className="modalButton" onClick={handleSelectFile}>Upload Image</button>
            {uploadError ? <p className="filename">{uploadError}</p> : null}
            {filename ? <p>{filename}</p> : null}
        </>
    );
}