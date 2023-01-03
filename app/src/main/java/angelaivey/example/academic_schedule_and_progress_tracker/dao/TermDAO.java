package angelaivey.example.academic_schedule_and_progress_tracker.dao;

import angelaivey.example.academic_schedule_and_progress_tracker.entities.Term;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface TermDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Term term);
    @Update
    void update(Term term);
    @Delete
    void delete(Term term);
    @Query("SELECT * FROM TERMS ORDER BY termID ASC")
    List<Term> getAllTerms();
}
