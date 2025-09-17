const {app, BrowserWindow, ipcMain, dialog} = require('electron');
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

app.whenReady().then(createWindow);

// app.on('window-all-closed', function() {
//     if (process.platform !== 'darwin') app.quit();
// });

// app.on('activate', function() {
//     if (mainWindow === null) createWindow();
// });