package com.bioportal.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

//handles reading/writing Bioassay data (production and development) to JSON file storage
public class AssayStorage {
    private static final String FILE_PATH = "assays.json";

    //saves lists of production and development bioassays to local storage
    public static void save(List<Bioassay> production, List<Bioassay> development) {
        JSONObject root = new JSONObject();
        root.put("production", toJsonArray(production));
        root.put("development", toJsonArray(development));

        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            writer.write(root.toString(2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //loads all assays from the assays.json file
    public static Map<String, List<Bioassay>> load() {
        Map<String, List<Bioassay>> map = new HashMap<>();
        map.put("production", new ArrayList<>());
        map.put("development", new ArrayList<>());

        try {
            String content = Files.readString(Paths.get(FILE_PATH));
            JSONObject root = new JSONObject(content);

            for (String key : root.keySet()) {
                JSONArray array = root.getJSONArray(key);
                List<Bioassay> assays = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    Bioassay assay = new Bioassay(
                        obj.getString("id"),
                        obj.getString("name"),
                        obj.getString("focalPoint"),
                        obj.getString("frequency"),
                        obj.getString("testLevel"),
                        BioassayStatus.valueOf(key.toUpperCase())
                    );
                    assays.add(assay);
                }
                map.put(key, assays);
            }
        } catch (IOException e) {
            System.out.println("No assay file yet or error reading.");
        }

        return map;
    }

    //helper method to convert list of bioassay objects to JSON array
    private static JSONArray toJsonArray(List<Bioassay> list) {
        JSONArray array = new JSONArray();
        for (Bioassay a : list) {
            JSONObject obj = new JSONObject();
            obj.put("id", a.getId());
            obj.put("name", a.getName());
            obj.put("focalPoint", a.getFocalPoint());
            obj.put("frequency", a.getFrequency());
            obj.put("testLevel", a.getTestLevel());
            array.put(obj);
        }
        return array;
    }
}