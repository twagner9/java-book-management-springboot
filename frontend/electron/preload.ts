 /* 
  This file exists as a way to securely preload certain functionality that the application will require.
  In this case, because in electron/main.ts we've specified that nodeIntegration is false and
  contextIsolation is true to prevent unnecessary vulnerabilities, the GUI doesn't actually have
  access to the full path. By creating a function connected to the electron API that returns
  the absolute path that I want, this will allow me to use this API from the GUI to grab
  the full path. 

  The main reason to avoid giving complete access to the path this way to prevent embedding
  potentially sensitive info directly into the DOM (contextIsolation) and to prevent
  malicious actors from executing scripts that run commands via require("child_process").exec(...).
 */

const { contextBridge, ipcRenderer } = require('electron');

/**
 * 
 * @param path Absolute path to the image.
 * @returns The safe-file format of the path for processing in main.ts
 */
function toFileUrl(path: string): string {
  return `safe-file://${encodeURI(path)}`;
}

contextBridge.exposeInMainWorld('electronAPI', {
  isDarkMode: () => ipcRenderer.invoke("theme:get"),
  onThemeUpdated: (callback: (isDark: boolean) => void) => {
    ipcRenderer.on('theme:updated', (_: any, isDark: boolean) => callback(isDark));
  },
  openFileDialog: () => ipcRenderer.invoke("dialog:openFile"),
  toFileUrl,
});