package ex2.maman14_ex2;

import ex2.maman14_ex2.business_logic.TelephoneDictionaryModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TelephoneDictionaryController implements Initializable {
    @FXML
    private TableView<TelephoneDictionaryModel> dictionaryTable;

    @FXML
    private TableColumn<TelephoneDictionaryModel, String> name;

    @FXML
    private TableColumn<TelephoneDictionaryModel, String> phone;

//    @FXML
//    private TableColumn<TelephoneDictionaryModel, Button> delete;
//
//    @FXML
//    private TableColumn<TelephoneDictionaryModel, Button> edit;


    public ObservableList<TelephoneDictionaryModel> getDictionaryRecord() {
        ObservableList<TelephoneDictionaryModel> phones = FXCollections.observableArrayList();
        phones.add(new TelephoneDictionaryModel("Israel Heiblum","0525322169"));
        phones.add(new TelephoneDictionaryModel("Inbal Heiblum","0525322167"));
        phones.add(new TelephoneDictionaryModel("Leo Igel","0525322165"));
        return phones;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        dictionaryTable.setItems(getDictionaryRecord());
    }
}
