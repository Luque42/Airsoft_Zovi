module es.Luque.AirsoftManager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.xml.bind;
    requires javafx.graphics;


    opens es.Luque.AirsoftManager.dataaccess to java.xml.bind;
    exports es.Luque.AirsoftManager.dataaccess;
    opens es.Luque.AirsoftManager to javafx.fxml;
    exports es.Luque.AirsoftManager;
    exports es.Luque.AirsoftManager.controllers;
    opens es.Luque.AirsoftManager.controllers to javafx.fxml;
    exports es.Luque.AirsoftManager.model;
    opens es.Luque.AirsoftManager.model to javafx.fxml;
    exports es.Luque.AirsoftManager.model.enums;
}