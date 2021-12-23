package ex2.maman14_ex2;


import javafx.beans.property.SimpleStringProperty;

public class TelephoneDictionary {
    private SimpleStringProperty name, phone;

    public TelephoneDictionary(String name, String phone) {
        this.name = new SimpleStringProperty(name);
        this.phone = new SimpleStringProperty(phone);
    }

    public String getName() {
        return name.get();
    }

    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }


    @Override
    public String toString() {
        return "Name: " + name.get() +
                ", Phone: " + phone.get();
    }
}
