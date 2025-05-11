package com.bioportal.model;

import java.util.ArrayList;
import java.util.List;

//represents single bioassay (with each assay having metadata and optional list of comments)
public class Bioassay {
    private String id;
    private String name;
    private String focalPoint;
    private String frequency;
    private String testLevel;
    private BioassayStatus status;
    private List<Comment> comments = new ArrayList<>();

    //constructs a bioassay with all the main properties
    public Bioassay(String id, String name, String focalPoint, String frequency, String testLevel, BioassayStatus status) {
        this.id = id;
        this.name = name;
        this.focalPoint = focalPoint;
        this.frequency = frequency;
        this.testLevel = testLevel;
        this.status = status;
    }

    //getters for all fields
    public String getId() { return id; }
    public String getName() { return name; }
    public String getFocalPoint() { return focalPoint; }
    public String getFrequency() { return frequency; }
    public String getTestLevel() { return testLevel; }
    public BioassayStatus getStatus() { return status; }
    public List<Comment> getComments() { return comments; }

    //adds new user comment to development assay
    public void addComment(Comment comment) {
        this.comments.add(comment);
    }
}