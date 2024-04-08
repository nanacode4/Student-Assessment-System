package DAO;

// Import statements for necessary classes
import Model.Assessment;
import Model.*;
import Utility.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class AssessmentDAO {
    // Method to get assessment marks for a specific course and student
    public List<Assessment> getAssessmentsForCourse(int userId, int courseId) {
        List<Assessment> assessments = new ArrayList<>();
        // SQL query to select assessments based on userID and courseID
        String sql = "SELECT * FROM Assessment WHERE user_id = ? AND course_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();// Establish database connection
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {// Prepare the SQL query

            // Set parameters for user ID and course ID in the query
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Create Assessment objects and set their properties based on the result set
                Assessment assessment = new Assessment();
                assessment.setAssessmentId(resultSet.getInt("assessment_id"));
                assessment.setUserId(resultSet.getInt("user_id"));
                assessment.setCourseId(resultSet.getInt("course_id"));
                assessment.setQuizMarks(resultSet.getInt("quiz_marks"));
                assessment.setAssignmentMarks(resultSet.getInt("assignment_marks"));
                assessment.setFinalExamMarks(resultSet.getInt("final_exam_marks"));
                assessments.add(assessment);// Add the Assessment object to the list
            }
        } catch (SQLException e) {
            System.out.println("Error fetching assessments: " + e.getMessage());
            e.printStackTrace();
        }

        return assessments;// Return the list of assessments
    }

    // Saves a new Assessment record or updates an existing one based on whether assessmentId is set
    public boolean saveOrUpdateAssessment(Assessment assessment) {
        // Determine if the operation is an update or insert
        boolean isUpdate = assessment.getAssessmentId() > 0;
        // SQL query for updating or inserting based on isUpdate flag
        String sql = isUpdate ?
                "UPDATE Assessment SET quiz_marks = ?, assignment_marks = ?, final_exam_marks = ? WHERE Assessment_id = ? AND user_id = ? AND course_id = ?" :
                "INSERT INTO Assessment (user_id, course_id, quiz_marks, assignment_marks, final_exam_marks) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();// Establish database connection
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {// Prepare the SQL query

            // Set parameters for the prepared statement based on whether updating or inserting
            if (isUpdate) {
                // Set values for updating an existing record
                preparedStatement.setInt(1, assessment.getQuizMarks());
                preparedStatement.setInt(2, assessment.getAssignmentMarks());
                preparedStatement.setInt(3, assessment.getFinalExamMarks());
                preparedStatement.setInt(4, assessment.getAssessmentId());
                preparedStatement.setInt(5, assessment.getUserId());
                preparedStatement.setInt(6, assessment.getCourseId());
            } else {
                // Set values for inserting a new record
                preparedStatement.setInt(1, assessment.getUserId());
                preparedStatement.setInt(2, assessment.getCourseId());
                preparedStatement.setInt(3, assessment.getQuizMarks());
                preparedStatement.setInt(4, assessment.getAssignmentMarks());
                preparedStatement.setInt(5, assessment.getFinalExamMarks());
            }

            // Execute the update or insert
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;// Return true if the operation affected any rows
        } catch (SQLException e) {
            e.printStackTrace();
            return false;// Return false if there was an error during the operation
        }
    }

    // Retrieves a single Assessment object for a specific student and course
    public Assessment getAssessmentByStudentCourse(int studentId, int courseId) {
        // SQL query to select a specific assessment by student and course IDs
        String sql = "SELECT * FROM Assessment WHERE user_id = ? AND course_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();// Establish database connection
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {// Prepare the SQL query

            // Set parameters for the prepared statement
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, courseId);
            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Create an Assessment object and set its properties from the result set
                Assessment assessment = new Assessment();
                assessment.setAssessmentId(resultSet.getInt("assessment_id"));
                assessment.setUserId(resultSet.getInt("user_id"));
                assessment.setCourseId(resultSet.getInt("course_id"));
                assessment.setQuizMarks(resultSet.getInt("quiz_marks"));
                assessment.setAssignmentMarks(resultSet.getInt("assignment_marks"));
                assessment.setFinalExamMarks(resultSet.getInt("final_exam_marks"));
                // Return the found Assessment object
                return assessment;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;// Return null if no assessment was found or if there was an error
    }



}
