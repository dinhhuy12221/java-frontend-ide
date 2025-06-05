# Java GUI App for Multi-language Code Formatter & Runner

## 🎯 Objective
Build a Java Swing GUI application that allows users to:
- Write source code in Java, Python, or JavaScript.
- Choose language and action (Format / Compile or Run).
- Send code to a backend server via socket.
- Display formatted code or output/errors in the GUI.

## 🧰 Technologies Used
- Java Swing (GUI)
- Java Sockets (TCP client)
- Backend server (handles formatting and execution)

## 🧱 Basic Structure
```plaintext
Frontend GUI (Java)
 ├── Language selector (Java, Python, JS)
 ├── Code editor (multi-line input)
 ├── Action buttons: Format / Run
 ├── Connects to backend via socket
 └── Displays response from server
