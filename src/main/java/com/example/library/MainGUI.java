package com.example.library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Collectors;

// ... package and imports remain the same

public class MainGUI {
    private LibrarySystem library;

    public MainGUI() {
        library = new LibrarySystem();
        library.loadData();
        createUI();
    }

    private void createUI() {
        JFrame frame = new JFrame("📚 Library Book Lending System");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] actions = {
                "➕ Add Book",
                "🧍 Register Member",
                "📖 View Available Books",
                "📗 Issue Book",
                "📘 Return Book",
                "🔍 Search Book",
                "🔎 Search Member",
                "👤 View Member Info",
                "📜 View Lending History"
        };

        JButton[] buttons = new JButton[actions.length];

        for (int i = 0; i < actions.length; i++) {
            buttons[i] = new JButton(actions[i]);
            buttonPanel.add(buttons[i]);
        }

        buttons[0].addActionListener(e -> addBookDialog());
        buttons[1].addActionListener(e -> addMemberDialog());
        buttons[2].addActionListener(e -> showAvailableBooks());
        buttons[3].addActionListener(e -> issueBookDialog());
        buttons[4].addActionListener(e -> returnBookDialog());
        buttons[5].addActionListener(e -> searchBookDialog());
        buttons[6].addActionListener(e -> searchMemberDialog());
        buttons[7].addActionListener(e -> viewMemberInfoDialog());
        buttons[8].addActionListener(e -> viewLendingHistoryDialog());

        frame.add(buttonPanel, BorderLayout.WEST);
        frame.add(new JLabel("  Welcome to the Library System!"), BorderLayout.CENTER);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                library.saveData();
            }
        });

        frame.setVisible(true);
    }

    private void addBookDialog() {
        JTextField idField = new JTextField();
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        Object[] message = {
                "Book ID:", idField,
                "Title:", titleField,
                "Author:", authorField
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Add Book", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Book book = new Book(idField.getText(), titleField.getText(), authorField.getText());
            library.addBook(book);
            library.saveData();  // Save immediately
            JOptionPane.showMessageDialog(null, "Book added!");
        }
    }

    private void addMemberDialog() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        Object[] message = {
                "Member ID:", idField,
                "Name:", nameField
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Register Member", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Member member = new Member(idField.getText(), nameField.getText());
            library.addMember(member);
            library.saveData();  // Save immediately
            JOptionPane.showMessageDialog(null, "Member registered!");
        }
    }

    private void showAvailableBooks() {
        List<Book> books = library.getAvailableBooks();
        StringBuilder sb = new StringBuilder();
        for (Book b : books) {
            sb.append(b.getBookId()).append(" - ").append(b.getTitle())
                    .append(" by ").append(b.getAuthor()).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.length() > 0 ? sb.toString() : "No available books.");
    }

    private void issueBookDialog() {
        JTextField bookIdField = new JTextField();
        JTextField memberIdField = new JTextField();
        Object[] message = {
                "Book ID:", bookIdField,
                "Member ID:", memberIdField
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Issue Book", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                library.issueBook(bookIdField.getText(), memberIdField.getText());
                library.saveData();  // Save immediately
                JOptionPane.showMessageDialog(null, "Book issued!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void returnBookDialog() {
        JTextField bookIdField = new JTextField();
        JTextField memberIdField = new JTextField();
        Object[] message = {
                "Book ID:", bookIdField,
                "Member ID:", memberIdField
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Return Book", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                library.returnBook(bookIdField.getText(), memberIdField.getText());
                library.saveData();  // Save immediately
                JOptionPane.showMessageDialog(null, "Book returned!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void searchBookDialog() {
        String keyword = JOptionPane.showInputDialog("Enter title or author to search:");
        if (keyword == null || keyword.trim().isEmpty()) return;

        List<Book> matches = library.getAllBooks().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(keyword.toLowerCase())
                        || b.getAuthor().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());

        if (matches.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No books found.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Book b : matches) {
                sb.append(b.getBookId()).append(" - ").append(b.getTitle())
                        .append(" by ").append(b.getAuthor())
                        .append(" [").append(b.isAvailable() ? "Available" : "Not Available").append("]\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString(), "Search Results", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void searchMemberDialog() {
        String keyword = JOptionPane.showInputDialog("Enter member name or ID:");
        if (keyword == null || keyword.trim().isEmpty()) return;

        List<Member> matches = library.getAllMembers().stream()
                .filter(m -> m.getName().toLowerCase().contains(keyword.toLowerCase())
                        || m.getMemberId().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());

        if (matches.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No members found.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Member m : matches) {
                sb.append(m.getMemberId()).append(" - ").append(m.getName()).append("\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString(), "Search Results", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void viewMemberInfoDialog() {
        String memberId = JOptionPane.showInputDialog("Enter Member ID:");
        if (memberId == null || memberId.trim().isEmpty()) return;

        Member member = library.findMemberById(memberId);
        if (member == null) {
            JOptionPane.showMessageDialog(null, "Member not found.");
            return;
        }

        StringBuilder sb = new StringBuilder("Name: ").append(member.getName()).append("\n")
                .append("Borrowed Books:\n");

        if (member.getBorrowedBooks().isEmpty()) {
            sb.append("  None");
        } else {
            for (Book b : member.getBorrowedBooks()) {
                sb.append("  ").append(b.getBookId()).append(" - ").append(b.getTitle()).append("\n");
            }
        }

        JOptionPane.showMessageDialog(null, sb.toString(), "Member Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void viewLendingHistoryDialog() {
        List<String> logs = library.getLendingLog();
        if (logs.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No lending history available.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (String log : logs) {
            sb.append(log).append("\n");
        }
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        JOptionPane.showMessageDialog(null, scrollPane, "Lending History", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new MainGUI();
    }
}