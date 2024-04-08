package Servlet;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Model.*;
import DAO.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

// Annotation to declare the servlet configuration, specifying its name and the URL pattern it serves
@WebServlet(name = "UpdateAssessmentServlet", value = "/UpdateAssessmentServlet")
public class UpdateAssessmentServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Parse request parameters
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        int quizMarks = Integer.parseInt(request.getParameter("quizMarks"));
        int assignmentMarks = Integer.parseInt(request.getParameter("assignmentMarks"));
        int finalExamMarks = Integer.parseInt(request.getParameter("finalExamMarks"));

        // Perform the update operation
        boolean updateSuccess = updateAssessmentMarks(studentId, courseId, quizMarks, assignmentMarks, finalExamMarks);

        // Redirect based on the outcome of the update operation
        if (updateSuccess) {
            // Redirect to the teacher dashboard with a success message
            response.sendRedirect("teacherDashboard.jsp?updateSuccess=true");
        } else {
            // Redirect back to the marks entry form with an error message
            response.sendRedirect("enterAssessmentMarks.jsp?studentId=" + studentId + "&courseId=" + courseId + "&updateError=true");
        }
    }

    // A method to update or create assessment marks for a given student and course
    private boolean updateAssessmentMarks(int studentId, int courseId, int quizMarks, int assignmentMarks, int finalExamMarks) {

        // Initialize the DAO for interacting with the database
        AssessmentDAO assessmentDAO = new AssessmentDAO();
        // Attempt to retrieve an existing assessment record for the student and course
        Assessment existingAssessment = assessmentDAO.getAssessmentByStudentCourse(studentId, courseId);

        Assessment assessment;
        if (existingAssessment != null) {
            // If an existing record is found, prepare to update it
            assessment = existingAssessment;
        } else {
            // If no record exists, prepare a new Assessment object for creation
            assessment = new Assessment();
            assessment.setUserId(studentId);
            assessment.setCourseId(courseId);
        }
        // Set the assessment marks
        assessment.setQuizMarks(quizMarks);
        assessment.setAssignmentMarks(assignmentMarks);
        assessment.setFinalExamMarks(finalExamMarks);

        // Save the updated or new assessment record to the database
        return assessmentDAO.saveOrUpdateAssessment(assessment);
    }







}
