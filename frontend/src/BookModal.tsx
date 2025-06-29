import Modal from 'react-modal';

Modal.setAppElement("#root");

type BookModalProps = {
    isOpen: boolean;
    onRequestClose: () => void; // This is a function that takes no arguments and returns nothing
    children?: React.ReactNode; // React prop allowing passage of nested elements/content into the component
    // React.ReactNode indicates strings, elements, numbers, fragments can be passed, and the ? indicates optional
    // This is what will allow me to add text input fields for author, title, number of copies, etc.
    className?: string;
    overlayClassName?: string;
};

export function BookModal({ isOpen, onRequestClose, children, className, overlayClassName}: BookModalProps) {
    return(
        <Modal 
            isOpen={isOpen}
            onRequestClose={onRequestClose}
            className={className}
            overlayClassName={overlayClassName}
        >
        {children}
        </Modal>
    )
}