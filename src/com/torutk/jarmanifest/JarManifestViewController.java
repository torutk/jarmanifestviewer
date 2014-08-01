/*
 *
 */
package com.torutk.jarmanifest;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.jar.JarFile;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author TAKAHASHI,Toru
 */
public class JarManifestViewController implements Initializable {

    private static final Logger logger = Logger.getLogger(JarManifestViewController.class.getName());

    @FXML
    private Label label;
    @FXML
    private TextField filePathField;
    @FXML
    private TableView<ManifestAttribute> table;
    @FXML
    private TableColumn<ManifestAttribute, String> headerColumn;
    @FXML
    private TableColumn<ManifestAttribute, String> valueColumn;

    private FileChooser jarChooser;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        selectJarFile();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        jarChooser = new FileChooser();
        jarChooser.setTitle("JARファイルを選択してください");
        jarChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JAR", "*.jar")
        );
        jarChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        headerColumn.setCellValueFactory(cellData -> cellData.getValue().headerProperty());
        valueColumn.setCellValueFactory(cellData -> cellData.getValue().valueProperty());
    }

    private void selectJarFile() {
        File selected = jarChooser.showOpenDialog(getStage());
        if (selected != null) {
            try {
                filePathField.setText(selected.getCanonicalPath());
            } catch (IOException ex) {
                logger.warning(() -> String.format("selected file '%s' cannot be converted to canonical path", selected.getName()));
            }
            readManifest();
        }
    }

    private void readManifest() {
        try (JarFile jar = new JarFile(filePathField.getText())) {
            table.getItems().clear();
            List<ManifestAttribute> attrs = jar.getManifest().getMainAttributes().entrySet().stream()
                    .map(entry -> new ManifestAttribute((String) entry.getKey().toString(), (String) entry.getValue()))
                    .collect(toList());
            table.getItems().addAll(attrs);
        } catch (IOException ex) {
            logger.warning(() -> String.format("%s", ex.getLocalizedMessage()));
        }
    }

    private Stage getStage() {
        return (Stage) label.getScene().getWindow();
    }

}
