package com.bioportal.model;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;
import org.json.*;

//handles reading/writing Comments (development) to JSON file storage. keyed by assay ID
public class CommentStorage {
    private static final String FILE_PATH = "comments.json";

    //loads all saved comments from the comments.json file
    public static Map<String, List<Comment>> loadComments() {
        Map<String, List<Comment>> commentsMap = new HashMap<>();
        try {
            //read and parse comments.json file
            String content = Files.readString(Paths.get(FILE_PATH));
            JSONObject root = new JSONObject(content);

            for (String assayId : root.keySet()) {
                JSONArray commentArray = root.getJSONArray(assayId);
                List<Comment> comments = new ArrayList<>();

                for (int i = 0; i < commentArray.length(); i++) {
                    JSONObject obj = commentArray.getJSONObject(i);
                    comments.add(new Comment(
                        obj.getString("author"),
                        obj.getString("content"),
                        LocalDateTime.parse(obj.getString("timestamp"))
                    ));
                }

                commentsMap.put(assayId, comments);
            }
        } catch (IOException | JSONException e) {
            //if file doesn't exist or some other issue with it, log and return empty map
            System.out.println("No comments file yet, or error parsing. Starting fresh.");
        }

        return commentsMap;
    }

    //saves all comments back to local storage, grouped by assay ID
    public static void saveComments(Map<String, List<Comment>> commentsMap) {
        JSONObject root = new JSONObject();

        for (String assayId : commentsMap.keySet()) {
            JSONArray commentArray = new JSONArray();
            for (Comment c : commentsMap.get(assayId)) {
                JSONObject obj = new JSONObject();
                obj.put("author", c.getAuthor());
                obj.put("content", c.getContent());
                obj.put("timestamp", c.getTimestamp().toString());
                commentArray.put(obj);
            }
            root.put(assayId, commentArray);
        }

        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            writer.write(root.toString(2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}