package com.bioportal.controller;

import com.bioportal.model.BioassayRepository;
import javafx.scene.layout.VBox;

public abstract class BioassayWindowController {
    protected BioassayRepository repository;
    protected VBox assayListContainer;
    public abstract void loadBioassays();
}