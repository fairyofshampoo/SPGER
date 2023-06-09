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
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import mx.uv.fei.sspger.logic.AcademicBody;
import mx.uv.fei.sspger.logic.DAO.AcademicBodyDAO;
import mx.uv.fei.sspger.logic.DAO.LgacDAO;
import mx.uv.fei.sspger.logic.DAO.ProjectDAO;
import mx.uv.fei.sspger.logic.DAO.UserManagerDAO;
import mx.uv.fei.sspger.logic.Lgac;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.Project;

public class ProjectRegisterController implements Initializable {

    @FXML
    private Button btnAccept;

    @FXML
    private Button btnCancel;

    @FXML
    private ChoiceBox<String> cbxAcademicBody;

    @FXML
    private ChoiceBox<Integer> cbxDuration;

    @FXML
    private Label lblAddProject;

    @FXML
    private Label lblInstruction;

    @FXML
    private Label lblTitleSystem;

    @FXML
    private TableColumn<ProfessorsTable, CheckBox> tblCCodirector;

    @FXML
    private TableColumn<ProfessorsTable, CheckBox> tblCDirector;

    @FXML
    private TableColumn<LGACTable, CheckBox> tblCIdLGAC;

    @FXML
    private TableColumn<LGACTable, String> tblCNameLGAC;

    @FXML
    private TableColumn<ProfessorsTable, String> tblCProfessorEMail;

    @FXML
    private TableColumn<ProfessorsTable, String> tblCProfessorName;

    @FXML
    private TableView<LGACTable> tblLGAC;

    @FXML
    private TableView<ProfessorsTable> tblProfessors;

    @FXML
    private TextArea txtBibliography;

    @FXML
    private TextArea txtExpectedResults;

    @FXML
    private TextField txtInvestigationLine;

    @FXML
    private TextArea txtNotes;

    @FXML
    private TextField txtPartcipantsNumber;

    @FXML
    private TextField txtPladeaProjectTitle;

    @FXML
    private TextField txtProjectModality;

    @FXML
    private TextArea txtProjectRequeriments;

    @FXML
    private TextArea txtProyectDescription;

    @FXML
    private TextArea txtReceptionalWorkDescription;

    @FXML
    private TextField txtReceptionalWorkName;

    @FXML
    void acceptButtonClick(ActionEvent event) {
        projectRegister();
    }
    
    @FXML
    void cancelButtonClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/sspger/GUI/DirectorProjectsManager.fxml"));
        Parent root;
        try {
            root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            Stage myStage = (Stage) this.btnCancel.getScene().getWindow();
            myStage.close();
            
        } catch (IOException ex) {
            Logger.getLogger(ProjectRegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    ObservableList<ProfessorsTable> list1 = FXCollections.observableArrayList();
    ObservableList<LGACTable> list2 = FXCollections.observableArrayList();
    ArrayList<Integer> durationList = new ArrayList<>(List.of(6, 12, 18, 24));
    
    
    
    void fillProfessorsTable(){
        try {
            UserManagerDAO professorUserDAO = new UserManagerDAO();
            List<Professor> professorList = professorUserDAO.getActiveProfessors();
            for (int i =0; i< professorList.size(); i++){
                Professor professor = professorList.get(i);
                CheckBox cbkDirector = new CheckBox("" + professorList.get(i));
                CheckBox cbkCodirector = new CheckBox("" + professorList.get(i));
                
                cbkDirector.setOnAction(event -> {
                if (cbkDirector.isSelected()) {
                    for (ProfessorsTable director : list1) {
                        if (director.getCbkDirector() != cbkDirector) {
                            director.getCbkDirector().setSelected(false);
                        }
                    }
                }
                });
                list1.add(new ProfessorsTable(professor.getEMail(), (professor.getHonorificTitle() + " " + professor.getName() + " " + professor.getLastName()), cbkCodirector, cbkDirector));
            }
        tblProfessors.setItems(list1);
        tblCProfessorEMail.setCellValueFactory(new PropertyValueFactory<ProfessorsTable, String>("emailProfessor"));
        tblCProfessorName.setCellValueFactory(new PropertyValueFactory<ProfessorsTable, String>("nameProfessor"));
        tblCCodirector.setCellValueFactory(new PropertyValueFactory<ProfessorsTable, CheckBox>("cbkCodirector"));
        tblCDirector.setCellValueFactory(new PropertyValueFactory<ProfessorsTable, CheckBox>("cbkDirector"));
        } catch (SQLException ex) {
            Logger.getLogger(ProjectRegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void fillLgacTable(){
        try {
            LgacDAO lgacDAO = new LgacDAO();
            List<Lgac> lgacList = lgacDAO.getAllLgac();
            for (int i =0; i< lgacList.size(); i++){
                Lgac lgac = lgacList.get(i);
                CheckBox cbkLgac = new CheckBox(""+ lgacList.get(i));
                list2.add(new LGACTable(lgac.getName(), cbkLgac));
            }
        tblLGAC.setItems(list2);
        tblCIdLGAC.setCellValueFactory(new PropertyValueFactory<LGACTable, CheckBox>("cbkLGAC"));
        tblCNameLGAC.setCellValueFactory(new PropertyValueFactory<LGACTable, String>("nameLGAC"));
        } catch (SQLException ex) {
            Logger.getLogger(ProjectRegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setAcademicBodyComboBox(){
        try {
            AcademicBodyDAO academicBodyDAO = new AcademicBodyDAO();
            List<AcademicBody> academicBodies = null;
            ObservableList<String> academicBodiesObservableList = FXCollections.observableArrayList();
            academicBodies = academicBodyDAO.getAllAcademicBody();
            
            if(academicBodies != null){
                for(AcademicBody academicBody : academicBodies){
                    academicBodiesObservableList.add(academicBody.toString());
                }
            }
            cbxAcademicBody.setItems(academicBodiesObservableList);
        } catch (SQLException ex) {
            Logger.getLogger(ProjectRegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void setDurationComboBox(){
        for (int i =0; i< durationList.size(); i++) {
        cbxDuration.getItems().add(durationList.get(i));
        }
    }
    
    private void projectRegister(){
        Project project = new Project();
        ProjectDAO projectDAO = new ProjectDAO();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillProfessorsTable();
        fillLgacTable();
        setAcademicBodyComboBox();
        setDurationComboBox();
    }    
    
}
