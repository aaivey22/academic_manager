package angelaivey.example.academic_schedule_and_progress_tracker.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import angelaivey.example.academic_schedule_and_progress_tracker.entities.Course;

@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);
    @Update
    void update(Course course);
    @Delete
    void delete(Course course);
    @Query("SELECT * FROM COURSES ORDER BY courseID ASC")
    List<Course> getAllCourses();

}
