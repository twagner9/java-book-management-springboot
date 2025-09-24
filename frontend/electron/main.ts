import { decode } from "punycode";

const {app, BrowserWindow, ipcMain, dialog, protocol, net} = require('electron');
const path = require('path');
const {fileURLToPath} = require('url');
const {dirname} = require('path');


let mainWindow: Electron.BrowserWindow | null = null;
function createWindow () {
    mainWindow = new BrowserWindow({
    width: 1920,
    height: 1080,
    webPreferences: {
      preload: path.join(__dirname, 'preload.js'),
      nodeIntegration: false,
      contextIsolation: true,
      sandbox: true,
    },
  });
    if (mainWindow) {
            mainWindow.loadURL('http://localhost:8080');
            mainWindow.on('closed', function () {
            mainWindow = null;
        });
    }
}

ipcMain.handle('dialog:openFile', async () => {
  const result = await dialog.showOpenDialog({
    properties: ["openFile"],
  });
  if (result.canceled || result.filePaths.length === 0) {
    return null;
  }
  return result.filePaths[0];
});

ipcMain.handle("toSafeFile", (_, path) => {
  const {pathToFileURL} = require('url');

  if (!path) return;
  const fileUrl = pathToFileURL(path).href;
  return fileUrl.replace('file://', "safe-file://");
});

app.whenReady().then(() => {
  /* Use this protocol to prevent disabling webSecurity, which includes CORS and other
    protections that would open the possibility of the app being hijacked by malicious
    scripts or other content. safe-file is essentially a whitelisted protocol that 
    only I control, so anything lacking that won't be used. I am still not sure if there
    are ways around this, but it's better than fully disabling security features that are
    built in for a reason.
  */
  protocol.handle('safe-file', (request) => {
    try {
      const fileUrl = request.url.replace("safe-file://", "file://");
      console.log("intercepted:", fileUrl);
      return net.fetch(fileUrl);
    } catch (error) {
      console.log('Error loading image file: ' + error);
      return new Response('File not found', {status: 404});
    }
  });

  createWindow();
});

// app.on('window-all-closed', function() {
//     if (process.platform !== 'darwin') app.quit();
// });

// app.on('activate', function() {
//     if (mainWindow === null) createWindow();
// });