📚 Library Book Lending System

A Java Swing-based application for managing a small library system. This system allows users to manage books, register members, issue and return books, search, and view lending history with timestamps.

![img.png](images/img.png)
🚀 How to Run

https://github.com/R3trox/Library_Management

Requirements:

Java Development Kit (JDK) 8 or above

An IDE like IntelliJ IDEA, Eclipse, or any text editor

Optionally, terminal/command prompt for compilation and execution

Steps:

1. Using IDE:

Open your IDE and import the project folder containing the .java files.

Set the MainGUI class as the main class.

Run the application.

2. Using Terminal (Command Line):

Navigate to the directory containing the source files, e.g., src/com/example/library.

cd path/to/src
javac com/example/library/*.java
java com.example.library.MainGUI

Make sure you are at the root of the src folder when running the javac and java commands.

📋 Overview of the System

This library system supports the following features:

✅ Core Features:

Add Book – Input book ID, title, and author to add a new book to the system.

![img_1.png](images/img_1.png)

Register Member – Register a member by entering a unique ID and name.

![img_2.png](images/img_2.png)

View Available Books – Display a list of all books currently available for borrowing.

![img_3.png](images/img_3.png)

Issue Book – Issue a book to a registered member (limit: 3 books per member).

![img_4.png](images/img_4.png)

Return Book – Allow members to return borrowed books.

![img_5.png](images/img_5.png)

Search Book – Search books by title or author (case-insensitive).

![img_6.png](images/img_6.png)

Search Member – Search members by name or member ID.

![img_7.png](images/img_7.png)

View Member Info – View a member's current borrowed books.

![img_8.png](images/img_8.png)

View Lending History – Shows a timestamped log of all book issue and return events.

![img_9.png](images/img_9.png)

💾 Data Handling:

All library data (books, members, transactions) is stored in memory and saved upon application closure.

Lending history is tracked and displayed via a scrollable GUI component.

🖥️ User Interface:

Developed using Java Swing.
