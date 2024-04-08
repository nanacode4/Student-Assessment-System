package Model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "Assessment")
public class Assessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assessment_id")
    private int assessmentId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "course_id")
    private int courseId;

    @Column(name = "quiz_marks")
    private int quizMarks;

    @Column(name = "assignment_marks")
    private int assignmentMarks;

    @Column(name = "final_exam_marks")
    private int finalExamMarks;

    // Constructor
    public Assessment() {}

    public Assessment(int userId, int courseId, int quizMarks, int assignmentMarks, int finalExamMarks) {
        this.userId = userId;
        this.courseId = courseId;
        this.quizMarks = quizMarks;
        this.assignmentMarks = assignmentMarks;
        this.finalExamMarks = finalExamMarks;
    }

    // Getters and setters
    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getQuizMarks() {
        return quizMarks;
    }

    public void setQuizMarks(int quizMarks) {
        this.quizMarks = quizMarks;
    }

    public int getAssignmentMarks() {
        return assignmentMarks;
    }

    public void setAssignmentMarks(int assignmentMarks) {
        this.assignmentMarks = assignmentMarks;
    }

    public int getFinalExamMarks() {
        return finalExamMarks;
    }

    public void setFinalExamMarks(int finalExamMarks) {
        this.finalExamMarks = finalExamMarks;
    }
}
