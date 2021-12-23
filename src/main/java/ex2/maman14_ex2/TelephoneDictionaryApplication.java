package ex2.maman14_ex2;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Israel Heiblum
 */

public class TelephoneDictionaryApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private int selectedIndex = -1;
    private final String pathToFile = "src/main/resources/ex2/maman14_ex2/dictionary.csv";

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(new Group());
        stage.setTitle("Maman 14, EX 2");
        stage.setWidth(600);
        stage.setHeight(550);

        Label nameLabel = new Label("Name and Phone List");
        nameLabel.setFont(new Font("Arial", 20));

        TextField nameTxt = new TextField();
        nameTxt.setPromptText("Name");

        TextField phoneTxt = new TextField();
        phoneTxt.setPromptText("Phone");

        ListView myListView = new ListView();
        myListView.setItems(getDictionaryList);

        myListView.setOnMouseClicked(event -> {
            String selectedItemName = ((TelephoneDictionary) myListView.getSelectionModel().getSelectedItem()).getName();
            selectedIndex = myListView.getSelectionModel().getSelectedIndex();
            nameTxt.setText(selectedItemName);

            String selectedItemPhone = ((TelephoneDictionary) myListView.getSelectionModel().getSelectedItem()).getPhone();
            phoneTxt.setText(selectedItemPhone);
        });

        Button addButton = new Button("Add");
        addButton.setOnAction((ActionEvent e) -> {
            String[] message = new String[1];
            if (!validateFieldsInputs(nameTxt, phoneTxt, message) || !validateInputIsNotAlreadyExist(nameTxt, phoneTxt, message)) {
                Dialog d = new Alert(Alert.AlertType.ERROR, "Please fix: " + message[0]);
                d.show();
                return;
            }
            getDictionaryList.add(new TelephoneDictionary(nameTxt.getText(), phoneTxt.getText()));
            nameTxt.clear();
            phoneTxt.clear();
        });

        Button updateButton = new Button("Update");
        updateButton.setOnAction((ActionEvent e) -> {
            String[] message = new String[1];
            if (!validateFieldsInputs(nameTxt, phoneTxt, message)) {
                Dialog d = new Alert(Alert.AlertType.ERROR, "Please fix: " + message[0]);
                d.show();
                return;
            }
            Dialog d = new Alert(Alert.AlertType.INFORMATION, "Updated " + nameTxt.getText());
            d.show();
            getDictionaryList.remove(selectedIndex);
            getDictionaryList.add(selectedIndex, new TelephoneDictionary(nameTxt.getText(), phoneTxt.getText()));
            nameTxt.clear();
            phoneTxt.clear();
        });

        Button deleteBtn = new Button("Delete");
        deleteBtn.setOnAction((ActionEvent e) -> {
            getDictionaryList.remove(selectedIndex);
            nameTxt.clear();
            phoneTxt.clear();
        });

        Button clearBtn = new Button("Save");
        clearBtn.setOnAction((ActionEvent e) -> {
            try {
                writeListToFile();
                Dialog d = new Alert(Alert.AlertType.INFORMATION, "Saved succeeded");
                d.show();
            } catch (IOException ex) {
                Dialog d = new Alert(Alert.AlertType.ERROR, "Sorry: Can't save to file at this moment");
                d.show();
                ex.printStackTrace();
            }
        });
        HBox controlPanelCRUD = new HBox();
        controlPanelCRUD.getChildren().addAll(nameTxt, phoneTxt, addButton, updateButton, deleteBtn, clearBtn);
        controlPanelCRUD.setSpacing(3);

        TextField searchTxt = new TextField();
        searchTxt.setPromptText("Search by Name");

        Button searchButton = new Button("Search");
        searchButton.setOnAction((ActionEvent e) -> {
            if (searchTxt.getText().equals("")) {
                Dialog d = new Alert(Alert.AlertType.ERROR, "Please insert Name to search for");
                d.show();
                return;
            }
            ObservableList<TelephoneDictionary> sorted = getDictionaryList.sorted();
            for (int i = 0; i < getDictionaryList.size(); i++) {
                if (getDictionaryList.get(i).getName().toLowerCase().contains(searchTxt.getText().toLowerCase())) {
                    Dialog d = new Alert(Alert.AlertType.INFORMATION,
                            "Found - Name: " + getDictionaryList.get(i).getName() +
                                    ", Phone: " + getDictionaryList.get(i).getPhone());
                    d.show();
                    searchTxt.clear();
                    return;
                }
            }
            Dialog d = new Alert(Alert.AlertType.WARNING,
                    "Not found name: " + searchTxt.getText());
            d.show();
            searchTxt.clear();
        });

        HBox controlPanelSearch = new HBox();
        controlPanelSearch.getChildren().addAll(searchTxt, searchButton);
        controlPanelSearch.setSpacing(3);

        VBox myVBox = new VBox();
        myVBox.setSpacing(5);
        myVBox.setPadding(new Insets(10, 0, 0, 10));
        myVBox.getChildren().addAll(nameLabel, myListView, controlPanelCRUD, controlPanelSearch);

        ((Group) scene.getRoot()).getChildren().addAll(myVBox);
        stage.setScene(scene);
        stage.show();
    }

    private ObservableList<TelephoneDictionary> getDictionaryList;
    {
        try {
            getDictionaryList = FXCollections.observableArrayList(
                    readFromFile()
            );
        } catch (IOException e) {
            System.out.println("Can't get list from file error occurred");
            e.printStackTrace();
        }
    }


    private boolean validateFieldsInputs(TextField nameTxt, TextField phoneTxt, String[] message) {
        boolean valid = true;
        if (nameTxt.getText().equals("") ||
                phoneTxt.getText().equals("") ||
                nameTxt.getText().length() < 2 ||
                !phoneTxt.getText().matches("^[0-9\\-]*$") ||
                phoneTxt.getText().length() < 9) {
            message[0] = "Invalid input";
            valid = false;
        }
        return valid;
    }

    private boolean validateInputIsNotAlreadyExist(TextField nameTxt, TextField phoneTxt, String[] message) {
        boolean valid = true;
        for (int i = 0; i < getDictionaryList.size(); i++) {
            if (getDictionaryList.get(i).getName().toLowerCase().equals(nameTxt.getText().toLowerCase()) ||
                    getDictionaryList.get(i).getPhone().equals(nameTxt.getText())) {
                valid = false;
                message[0] = "Already exist in list";
                break;
            }
        }
        return valid;
    }

    private List<TelephoneDictionary> readFromFile() throws IOException {
        File file = new File(pathToFile);
        List<TelephoneDictionary> dataFile = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                String name = line.split(",")[0];
                String phone = line.split(",")[1];
                TelephoneDictionary dictionaryModel = new TelephoneDictionary(name, phone);
                dataFile.add(dictionaryModel);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Can't read file error occurred");
            Dialog d = new Alert(Alert.AlertType.ERROR, "Can't read file error occurred");
            d.show();
        }
        return dataFile;
    }

    private boolean writeListToFile() throws IOException {
        try {
            FileWriter file = new FileWriter(pathToFile);
            PrintWriter write = new PrintWriter(file);
            for (TelephoneDictionary model : getDictionaryList) {
                write.println(model.getName() + ","+model.getPhone());
            }
            write.close();
        } catch (IOException exe) {
            System.out.println("Cannot create file error occurred");
            return false;
        }
        return true;
    }

}