package mx.uv.fei.sspger.GUI;


import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class SPGER extends Application{
    
    private static Scene scene;
    
    public static void main (String[] args){
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("DeliverAssignment.fxml"));
        
        scene = new Scene (root);
        
        stage.setScene(scene);
        stage.show();
    }

     public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SPGER.class.getResource(fxml));
        return fxmlLoader.load();
    }
    
}