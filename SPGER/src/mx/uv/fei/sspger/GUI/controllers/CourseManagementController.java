package mx.uv.fei.sspger.GUI.controllers;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import mx.uv.fei.sspger.GUI.SPGER;
import static mx.uv.fei.sspger.GUI.controllers.ProfessorCourseManagerController.professorId;
import mx.uv.fei.sspger.logic.Course;
import mx.uv.fei.sspger.logic.CourseStates;
import mx.uv.fei.sspger.logic.DAO.CourseDAO;
import mx.uv.fei.sspger.logic.Project;
import mx.uv.fei.sspger.logic.Status;
import mx.uv.fei.sspger.logic.UserSession;

public class CourseManagementController implements Initializable{

    private int column = 0;
    private int row = 1;
    
    @FXML
    private ComboBox<String> cbxCourseFilter;

    @FXML
    private GridPane gpCourseTable;
    
    @FXML
    private Pane pnNavigationBar;

    @FXML
    void addCourseView(ActionEvent event) {
        SPGER.setRoot("/mx/uv/fei/sspger/GUI/AddCourse.fxml");
    }

    @FXML
    void filterCourses(ActionEvent event) {
        gpCourseTable.getChildren().clear();
        setCourses();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setGraphicElements();
    }

    private void setGraphicElements() {
        cbxCourseFilter.setItems(FXCollections.observableArrayList(getPosibleStates())); 
        setCourses();
        setNavigationBar();
    }
    
    private void setNavigationBar() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/NavigationBar.fxml"));
            Pane pnNavigationBarParent = fxmlLoader.load();
            NavigationBarController navigationBarController = fxmlLoader.getController();
            navigationBarController.setNavigationBar();
        
            pnNavigationBar.getChildren().add(pnNavigationBarParent);
        } catch (IOException ex) {
            Logger.getLogger(HomeProfessorController.class.getName()).log(Level.SEVERE, null, ex);
            DialogGenerator.getDialog(new AlertMessage (
                "Archivo FXML corrupto",
                Status.FATAL));
        }
    }
    
    private List<Course> getProfessorCourses (){
        CourseDAO courseDao = new CourseDAO();
        List<Course> courses = new ArrayList<>();
        
        try{
            if("Todos".equals(cbxCourseFilter.getValue()) || cbxCourseFilter == null){
                courses = courseDao.getAllCourses();
            } else {
                courses = courseDao.getCoursesPerState(cbxCourseFilter.getValue());
            }
            
        } catch (SQLException sqlException){
            Logger.getLogger(ProfessorCourseManagerController.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        
        return courses;
    }
    
    private List<String> getPosibleStates(){
        List<String> allStates = new ArrayList<>();
        
        for(CourseStates courseState: CourseStates.values()){
            allStates.add(courseState.getCourseState());
        }
        
        allStates.add("Todos");
        
        return allStates;
    }

    private void setCourses() {
        List<Course> professorCourses = getProfessorCourses();
        try{
            for (int card = 0; card < professorCourses.size(); card++){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/CourseCardAdmin.fxml"));
                AnchorPane courseCard = fxmlLoader.load();
                CourseCardAdminController courseCardControllerAdmin = fxmlLoader.getController();
                courseCardControllerAdmin.setCourseData(professorCourses.get(card));
                
                if(column == 2){
                    column = 0;
                    row++;
                }
                
                gpCourseTable.add(courseCard, column++, row);
                GridPane.setMargin(courseCard, new Insets(10));
                
            }
        } catch (IOException ioException){
            Logger.getLogger(ProfessorCourseManagerController.class.getName()).log(Level.SEVERE, null, ioException);
        }
    }
}
