/* declares as global the Window interface used by the MainWindow.
  This ensures that the getFilePath function is recognized by React components
  when the 
*/
export {}; // ensures this file is treated as a module

declare global {
  interface Window {
    electronAPI: {
      openFileDialog: () => Promise<string | null>;
    };
  }
}