package com.bioportal.model;

import java.util.*;

//handles all loading, access, and management of production and development bioassays. gateway between UI and JSON files
public class BioassayRepository {
    private List<Bioassay> productionBioassays;
    private List<Bioassay> developmentBioassays;

    //loads both production and development bioassays from local storage (as well as comments)
    public BioassayRepository() {
        Map<String, List<Bioassay>> loaded = AssayStorage.load();
        productionBioassays = loaded.getOrDefault("production", new ArrayList<>());
        developmentBioassays = loaded.getOrDefault("development", new ArrayList<>());

        //load and assign saved comments for each development assay
        Map<String, List<Comment>> savedComments = CommentStorage.loadComments();
        for (Bioassay assay : developmentBioassays) {
            List<Comment> saved = savedComments.get(assay.getId());
            if (saved != null) {
                assay.getComments().addAll(saved);
            }
        }
    }

    //getters for lists of production and development assays
    public List<Bioassay> getProductionBioassays() { return productionBioassays; }
    public List<Bioassay> getDevelopmentBioassays() { return developmentBioassays; }
}