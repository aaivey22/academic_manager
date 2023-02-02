package angelaivey.example.academic_schedule_and_progress_tracker.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="notes")

public class Note {
    @PrimaryKey(autoGenerate = true)
    private int noteID;
    private int courseID;
    private String noteBody;

    public Note(int noteid, int courseid, String note) {
        this.noteID = noteid;
        this.courseID = courseid;
        this.noteBody = note;
    }

    public Note() {
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getNoteID() {
        return noteID;
    }

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public String getNoteBody() {
        return noteBody;
    }

    public void setNoteBody(String noteBody) {
        this.noteBody = noteBody;
    }

}
