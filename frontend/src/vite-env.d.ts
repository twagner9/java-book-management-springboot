// Resolves: Cannot find module or type declarations for side-effect import of './App.css' from intellisense
// This occurs because typescript doesn't know to handle such imports by default with vite

declare module "*.css" {
  const content: Record<string, string>;
  export default content;
}
