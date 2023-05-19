package mx.uv.fei.sspger.logic.DAO;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mx.uv.fei.sspger.dataaccess.DataBaseManager;
import mx.uv.fei.sspger.logic.contracts.IStudent;
import mx.uv.fei.sspger.logic.Student;


public class StudentDAO implements IStudent{
    
    private final String GET_COURSE_STUDENT = "SELECT * FROM  estudiante NATURAL JOIN cursa " +
        "INNER JOIN estudiante_trabajo_recepcional ON estudiante_trabajo_recepcional.idEstudiante = idUsuarioEstudiante " +
        "WHERE idCurso = ?";
    private final String GET_STUDENT_BY_ID = "SELECT * FROM estudiante WHERE idUsuarioEstudiante = ?";

    @Override
    public int register(Student student) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Student> getStudentList() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Student getStudent(int idStudent) throws SQLException {
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_STUDENT_BY_ID);

        statement.setInt(1,idStudent);

        ResultSet studentResult = statement.executeQuery();
        Student student = new Student();
        
        if(studentResult.next()){
        student.setEMail(studentResult.getString("correo_institucional"));
        student.setName(studentResult.getString("nombre"));
        student.setLastName(studentResult.getString("apellido"));
        student.setRegistrationTag(studentResult.getString("matricula"));
        student.setId(studentResult.getInt("idUsuarioEstudiante"));
        }
        
        DataBaseManager.closeConnection();

        return student;
    }
    
    @Override
    public List<Student> getAvailableStudents() throws SQLException {
        String query = "Select idUsuarioEstudiante, nombre, apellido, matricula From estudiante";
        DataBaseManager.getConnection();
        Statement statement = DataBaseManager.getConnection().createStatement();
        ResultSet studentResult = statement.executeQuery(query);
        List<Student> studentList = new ArrayList<>();
        
        while(studentResult.next()){
            Student student = new Student();
            
            student.setId(studentResult.getInt("idUsuarioEstudiante"));
            student.setName(studentResult.getString("nombre"));
            student.setLastName(studentResult.getString("apellido"));
            student.setRegistrationTag(studentResult.getString("matricula"));
            studentList.add(student);
        } 
        DataBaseManager.closeConnection();
        
        return studentList;
    }

    @Override
    public List<Student> getStudentsPerCourse(String courseId) throws SQLException {
        String query = "SELECT idUsuarioEstudiante, nombre, apellido, matricula, correo_institucional "
                + "FROM estudiante NATURAL JOIN cursa WHERE cursa.idCurso = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setString(1, courseId);
        
        ResultSet studentResult = statement.executeQuery();
        List<Student> studentList = new ArrayList<>();
        
        while(studentResult.next()){
            Student student = new Student();
            
            student.setId(studentResult.getInt("idUsuarioEstudiante"));
            student.setName(studentResult.getString("nombre"));
            student.setLastName(studentResult.getString("apellido"));
            student.setRegistrationTag(studentResult.getString("matricula"));
            student.setEMail(studentResult.getString("correo_institucional"));
            studentList.add(student);
        } 
        DataBaseManager.closeConnection();
        
        return studentList;
    }
    
     @Override
    public List<Student> getAvailableStudentsNotInCourse(String courseId) throws SQLException {
        String query = "SELECT idUsuarioEstudiante, nombre, apellido, matricula, correo_institucional FROM estudiante" +
        " WHERE idUsuarioEstudiante NOT IN (SELECT idUsuarioEstudiante FROM estudiante NATURAL JOIN cursa WHERE idCurso = ?)";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setString(1, courseId);
        
        ResultSet studentResult = statement.executeQuery();
        List<Student> studentList = new ArrayList<>();
        
        while(studentResult.next()){
            Student student = new Student();
            
            student.setId(studentResult.getInt("idUsuarioEstudiante"));
            student.setName(studentResult.getString("nombre"));
            student.setLastName(studentResult.getString("apellido"));
            student.setRegistrationTag(studentResult.getString("matricula"));
            student.setEMail(studentResult.getString("correo_institucional"));
            studentList.add(student);
        } 
        DataBaseManager.closeConnection();
        
        return studentList;
    }
}
