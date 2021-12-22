package ex2.maman14_ex2.business_logic;


import javafx.beans.property.SimpleStringProperty;

public class TelephoneDictionaryModel {
    public SimpleStringProperty name, phone;

    public TelephoneDictionaryModel(String name, String phone) {
        this.name = new SimpleStringProperty(name);
        this.phone = new SimpleStringProperty(phone);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    @Override
    public String toString() {
        return "Name: " + name.get() +
                ", Phone: " + phone.get();
    }
}
