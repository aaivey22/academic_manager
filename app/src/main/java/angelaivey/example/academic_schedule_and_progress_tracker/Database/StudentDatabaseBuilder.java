package angelaivey.example.academic_schedule_and_progress_tracker.Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import angelaivey.example.academic_schedule_and_progress_tracker.dao.AssessmentDAO;
import angelaivey.example.academic_schedule_and_progress_tracker.dao.CourseDAO;
import angelaivey.example.academic_schedule_and_progress_tracker.dao.TermDAO;
import angelaivey.example.academic_schedule_and_progress_tracker.entities.Assessment;
import angelaivey.example.academic_schedule_and_progress_tracker.entities.Course;
import angelaivey.example.academic_schedule_and_progress_tracker.entities.Term;

@Database(entities = {Term.class, Course.class, Assessment.class}, version=1, exportSchema = false)
public abstract class StudentDatabaseBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public  abstract AssessmentDAO assessmentDAO();

    private static volatile StudentDatabaseBuilder INSTANCE;

    static StudentDatabaseBuilder getDatabase(final Context context) {
        if(INSTANCE==null) {
            synchronized (StudentDatabaseBuilder.class) {
                if (INSTANCE==null) {
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),StudentDatabaseBuilder.class, "MyStudentDatabase.db")
                    .fallbackToDestructiveMigration()
                            .build();

                }
            }
        }
        return INSTANCE;
    }
}
