/* global.d.ts is meant to make types, interfaces, and variables globally accessible without
 * having to import them each time they're being used. So the Window interface has an electronAPI
 * that can be accessed whenever needed.
 */

export {}; // ensures this file is treated as a module

declare global {
  interface Window {
    electronAPI: {
      openFileDialog: () => Promise<string | null>;
      toSafeFile: (path: string) => string;
    };
  }
}
