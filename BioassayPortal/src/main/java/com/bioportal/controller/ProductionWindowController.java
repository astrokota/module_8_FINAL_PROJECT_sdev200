package com.bioportal.controller;

import com.bioportal.model.AssayStorage;
import com.bioportal.model.Bioassay;
import com.bioportal.model.BioassayRepository;
import com.bioportal.model.BioassayStatus;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.UUID;

//controller for the Production window (displays/manages assays in production)
public class ProductionWindowController extends com.bioportal.controller.BioassayWindowController {

    @FXML private VBox assayListContainer;
    @FXML private TextField nameField;
    @FXML private TextField focalField;
    @FXML private TextField frequencyField;
    @FXML private TextField testLevelField;

    @Override
    public void loadBioassays() {
        repository = new BioassayRepository();

        //renders all previously saved production assays
        for (Bioassay assay : repository.getProductionBioassays()) {
            renderAssay(assay);
        }
    }

    //displays bioassay in the GUI with remove button
    private void renderAssay(Bioassay assay) {
        HBox row = new HBox();
        row.setSpacing(10);
        Label label = new Label(assay.getName() + " (Focal Point: " + assay.getFocalPoint() + ")" + " (Frequency: " + assay.getFrequency() + ")" + " (Test Level: " + assay.getTestLevel() + ")");
        Button removeBtn = new Button("Remove");
        removeBtn.setOnAction(e -> {
            repository.getProductionBioassays().remove(assay);
            assayListContainer.getChildren().remove(row);
        AssayStorage.save(repository.getProductionBioassays(), repository.getDevelopmentBioassays());
        });
        row.getChildren().addAll(label, removeBtn);
        assayListContainer.getChildren().add(row);
    }

    //when user clicks "Add Assay", new bioassay object is created and is rendered based on user input
    @FXML
    public void addAssay() {
        Bioassay newAssay = new Bioassay(
            UUID.randomUUID().toString(),
            nameField.getText(),
            focalField.getText(),
            frequencyField.getText(),
            testLevelField.getText(),
            BioassayStatus.PRODUCTION
        );
        repository.getProductionBioassays().add(newAssay);
        renderAssay(newAssay);
        AssayStorage.save(repository.getProductionBioassays(), repository.getDevelopmentBioassays());
        nameField.clear(); focalField.clear(); frequencyField.clear(); testLevelField.clear();
    }

    //triggers initial assay loading
    @FXML
    public void initialize() {
        loadBioassays();
    }

    //opens the development window in a new Stage
    @FXML
    public void openDevelopmentWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/development_window.fxml"));
            Parent root = loader.load();
            Stage devStage = new Stage();
            devStage.setTitle("Development Bioassays");
            devStage.setScene(new Scene(root, 800, 600));
            devStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}