package com.example.library;

import java.io.*;
import java.util.*;

public class LibraryFileHandler {
    private static final String BOOKS_FILE = "books.txt";
    private static final String MEMBERS_FILE = "members.txt";

    public static void saveBooks(List<Book> books) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKS_FILE))) {
            for (Book b : books) {
                writer.write(b.getBookId() + "," + b.getTitle() + "," + b.getAuthor() + "," + b.isAvailable());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Failed to save books: " + e.getMessage());
        }
    }

    public static List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Book b = new Book(parts[0], parts[1], parts[2]);
                b.setAvailable(Boolean.parseBoolean(parts[3]));
                books.add(b);
            }
        } catch (IOException e) {
            System.err.println("Books file not found, skipping load.");
        }
        return books;
    }

    public static void saveMembers(List<Member> members) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MEMBERS_FILE))) {
            for (Member m : members) {
                writer.write(m.getMemberId() + "," + m.getName());
                for (Book b : m.getBorrowedBooks()) {
                    writer.write("," + b.getBookId());
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Failed to save members: " + e.getMessage());
        }
    }

    public static List<Member> loadMembers(List<Book> books) {
        List<Member> members = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(MEMBERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Member m = new Member(parts[0], parts[1]);
                for (int i = 2; i < parts.length; i++) {
                    for (Book b : books) {
                        if (b.getBookId().equals(parts[i])) {
                            m.getBorrowedBooks().add(b);
                            b.setAvailable(false);
                        }
                    }
                }
                members.add(m);
            }
        } catch (IOException e) {
            System.err.println("Members file not found, skipping load.");
        }
        return members;
    }
}