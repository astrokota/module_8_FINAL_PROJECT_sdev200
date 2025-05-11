package com.bioportal.controller;

import com.bioportal.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.*;

//controller for Development window (renders assay information, new assay creation, handles user comments)
public class DevelopmentWindowController extends BioassayWindowController {

    @FXML private VBox assayListContainer;
    @FXML private TextField nameField;
    @FXML private TextField focalField;
    @FXML private TextField frequencyField;
    @FXML private TextField testLevelField;

    @Override
    public void loadBioassays() {
        repository = new BioassayRepository();

        //loop through saved development assays and render them
        for (Bioassay assay : repository.getDevelopmentBioassays()) {
            renderAssay(assay);
        }
    }

    //renders all individual development assay card information (details, comments, input boxes, removal button)
    private void renderAssay(Bioassay assay) {
        VBox assayBox = new VBox();
        assayBox.setSpacing(5);

        Label name = new Label("Assay: " + assay.getName());
        Label focal = new Label("Focal Point: " + assay.getFocalPoint());
        Label frequency = new Label("Frequency: " + assay.getFrequency());
        Label level = new Label("Test Level: " + assay.getTestLevel());

        VBox commentBox = new VBox();
        for (Comment comment : assay.getComments()) {
            commentBox.getChildren().add(new Label(comment.getAuthor() + ": " + comment.getContent()));
        }

        TextArea newComment = new TextArea();
        newComment.setPromptText("Enter new comment...");

        //add new comment to assay and save
        Button submitButton = new Button("Submit Comment");
        submitButton.setOnAction(e -> {
            String content = newComment.getText().trim();
            if (!content.isEmpty()) {
                Comment c = new Comment("You", content);
                assay.addComment(c);
                commentBox.getChildren().add(new Label("You: " + content));
                newComment.clear();
                CommentStorage.saveComments(buildCommentMap());
            }
        });

        //removes assay from the list and saves
        Button removeButton = new Button("Remove Assay");
        removeButton.setOnAction(e -> {
            repository.getDevelopmentBioassays().remove(assay);
            assayListContainer.getChildren().remove(assayBox);
            AssayStorage.save(repository.getProductionBioassays(), repository.getDevelopmentBioassays());
            CommentStorage.saveComments(buildCommentMap());
        });

        //add all elements to visual container
        assayBox.getChildren().addAll(name, focal, frequency, level, commentBox, newComment, submitButton, removeButton);
        assayListContainer.getChildren().add(assayBox);
    }

    //mapping of assay ID to associated comment list. used to have comments persist each session
    private Map<String, List<Comment>> buildCommentMap() {
        Map<String, List<Comment>> map = new HashMap<>();
        for (Bioassay assay : repository.getDevelopmentBioassays()) {
            map.put(assay.getId(), assay.getComments());
        }
        return map;
    }

    //adds new development assay based on user input and re-renders/saves to JSON
    @FXML
    public void addDevAssay() {
        Bioassay newAssay = new Bioassay(
            UUID.randomUUID().toString(),
            nameField.getText(),
            focalField.getText(),
            frequencyField.getText(),
            testLevelField.getText(),
            BioassayStatus.DEVELOPMENT
        );
        repository.getDevelopmentBioassays().add(newAssay);
        renderAssay(newAssay);
        AssayStorage.save(repository.getProductionBioassays(), repository.getDevelopmentBioassays());
        CommentStorage.saveComments(buildCommentMap());

        nameField.clear(); focalField.clear(); frequencyField.clear(); testLevelField.clear();
    }

    @FXML
    public void initialize() {
        loadBioassays();
    }
}