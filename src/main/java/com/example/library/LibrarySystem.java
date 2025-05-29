package com.example.library;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class LibrarySystem {
    private List<Book> books;
    private List<Member> members;
    private List<String> lendingLog; // To store history with timestamps

    private static final String BOOKS_FILE = "books.dat";
    private static final String MEMBERS_FILE = "members.dat";
    private static final String LOG_FILE = "lending_log.txt";

    public LibrarySystem() {
        books = new ArrayList<>();
        members = new ArrayList<>();
        lendingLog = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public List<Book> getAvailableBooks() {
        List<Book> available = new ArrayList<>();
        for (Book b : books) {
            if (b.isAvailable()) available.add(b);
        }
        return available;
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public List<Member> getAllMembers() {
        return members;
    }

    public Member findMemberById(String memberId) {
        for (Member m : members) {
            if (m.getMemberId().equalsIgnoreCase(memberId)) return m;
        }
        return null;
    }

    public void issueBook(String bookId, String memberId) throws Exception {
        Book book = findBookById(bookId);
        Member member = findMemberById(memberId);

        if (book == null) throw new Exception("Book not found.");
        if (member == null) throw new Exception("Member not found.");
        if (!book.isAvailable()) throw new BookNotAvailableException("Book is not available.");
        if (member.getBorrowedBooks().size() >= 3) throw new MaxLimitReachedException("Member has reached the max limit.");

        book.setAvailable(false);
        member.getBorrowedBooks().add(book);

        String logEntry = "[" + LocalDateTime.now() + "] " + member.getName() + " issued book: " + book.getTitle();
        lendingLog.add(logEntry);
        saveLogEntry(logEntry);
    }

    public void returnBook(String bookId, String memberId) throws Exception {
        Book book = findBookById(bookId);
        Member member = findMemberById(memberId);

        if (book == null) throw new Exception("Book not found.");
        if (member == null) throw new Exception("Member not found.");
        if (!member.getBorrowedBooks().contains(book)) throw new Exception("This member did not borrow this book.");

        book.setAvailable(true);
        member.getBorrowedBooks().remove(book);

        String logEntry = "[" + LocalDateTime.now() + "] " + member.getName() + " returned book: " + book.getTitle();
        lendingLog.add(logEntry);
        saveLogEntry(logEntry);
    }

    private Book findBookById(String bookId) {
        for (Book b : books) {
            if (b.getBookId().equalsIgnoreCase(bookId)) return b;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public void loadData() {
        books = (List<Book>) readObjectFromFile(BOOKS_FILE, new ArrayList<>());
        members = (List<Member>) readObjectFromFile(MEMBERS_FILE, new ArrayList<>());
        lendingLog = readLogFromFile();

        // ✅ Debug logs to confirm data loading
        System.out.println("📘 Books loaded: " + books.size());
        System.out.println("🧍 Members loaded: " + members.size());
        System.out.println("📜 Lending log entries: " + lendingLog.size());
    }


    public void saveData() {
        writeObjectToFile(BOOKS_FILE, books);
        writeObjectToFile(MEMBERS_FILE, members);
    }

    private void writeObjectToFile(String filename, Object obj) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(obj);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private Object readObjectFromFile(String filename, Object defaultObj) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return defaultObj;
        }
    }

    private void saveLogEntry(String entry) {
        try (PrintWriter out = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            out.println(entry);
        } catch (IOException e) {
            System.out.println("Failed to write to log.");
        }
    }

    private List<String> readLogFromFile() {
        List<String> logs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logs.add(line);
            }
        } catch (IOException e) {
            // No log file yet
        }
        return logs;
    }

    public List<String> getLendingLog() {
        return lendingLog;
    }
}