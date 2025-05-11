package com.bioportal.model;

import java.time.LocalDateTime;

//represents user-submitted comment data tied to a development assay (comments have author, content, and timestamp metadata)
public class Comment {
    private String author;
    private String content;
    private LocalDateTime timestamp;

    //creates new comment using current timestamp
    public Comment(String author, String content) {
        this.author = author;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    //constructor used when loading a comment from local storage
    public Comment(String author, String content, LocalDateTime timestamp) {
        this.author = author;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getAuthor() { return author; }
    public String getContent() { return content; }
    public LocalDateTime getTimestamp() { return timestamp; }
}