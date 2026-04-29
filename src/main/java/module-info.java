module org.example.airsoft_zovi {
    requires javafx.controls;
    requires javafx.fxml;


    opens es.Luque.AirsoftManager to javafx.fxml;
    exports es.Luque.AirsoftManager;
    exports es.Luque.AirsoftManager.controllers;
    opens es.Luque.AirsoftManager.controllers to javafx.fxml;
}