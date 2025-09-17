 /* 
  This file exists as a way to securely preload certain functionality that the application will require.
  In this case, because in electron/main.ts we've specified that nodeIntegration is false and
  contextIsolation is true to prevent unnecessary vulnerabilities, the GUI doesn't actually have
  access to the full path. By creating a function connected to the electron API that returns
  the absolute path that I want, this will allow me to use this API from the GUI to grab
  the full path. 
 */

import { contextBridge, ipcRenderer } from 'electron';

contextBridge.exposeInMainWorld('electronAPI', {
  isDarkMode: () => ipcRenderer.invoke("theme:get"),
  onThemeUpdated: (callback: (isDark: boolean) => void) => {
    ipcRenderer.on('theme:updated', (_, isDark) => callback(isDark));
  },
  openFileDialog: () => ipcRenderer.invoke("dialog:openFile"),
});