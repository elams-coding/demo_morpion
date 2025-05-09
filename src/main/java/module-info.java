module com.elams.demo_morpion {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.elams.demo_morpion to javafx.fxml;
    exports com.elams.demo_morpion;
}