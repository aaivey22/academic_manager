package angelaivey.example.academic_schedule_and_progress_tracker.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import angelaivey.example.academic_schedule_and_progress_tracker.entities.Note;

@Dao
public interface NoteDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Note note);
    @Update
    void update(Note note);
    @Delete
    void delete(Note note);
    @Query("SELECT * FROM notes ORDER BY noteID ASC")
    List<Note> getAllNotes();
}
