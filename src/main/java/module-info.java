module ex2.maman14_ex2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens ex2.maman14_ex2 to javafx.fxml;
    exports ex2.maman14_ex2;
    exports ex2.maman14_ex2.business_logic;
    opens ex2.maman14_ex2.business_logic to javafx.fxml;
}