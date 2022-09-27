module com.grafikacafe.grafikacafe {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.prefs;
    requires org.xerial.sqlitejdbc;


    opens com.grafikacafe.grafikacafe to javafx.fxml;
    exports com.grafikacafe.grafikacafe;
    opens com.grafikacafe.grafikacafe.Models to javafx.fxml;
    exports com.grafikacafe.grafikacafe.Models;
    opens com.grafikacafe.grafikacafe.Helper to javafx.fxml;
    exports com.grafikacafe.grafikacafe.Helper;
}