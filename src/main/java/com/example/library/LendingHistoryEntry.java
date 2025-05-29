package com.example.library;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LendingHistoryEntry implements Serializable {
    private String memberId;
    private String bookId;
    private String action; // "Issued" or "Returned"
    private LocalDateTime timestamp;

    public LendingHistoryEntry(String memberId, String bookId, String action, LocalDateTime timestamp) {
        this.memberId = memberId;
        this.bookId = bookId;
        this.action = action;
        this.timestamp = timestamp;
    }

    public String toString() {
        return String.format("[%s] Book %s by Member %s at %s",
                action,
                bookId,
                memberId,
                timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}