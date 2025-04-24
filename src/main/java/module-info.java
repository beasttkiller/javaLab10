module org.example.javalab10 {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.compiler;


    opens org.example.javalab10 to javafx.fxml;
    exports org.example.javalab10;
}