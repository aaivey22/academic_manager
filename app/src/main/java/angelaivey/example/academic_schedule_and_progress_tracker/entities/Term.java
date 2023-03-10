package angelaivey.example.academic_schedule_and_progress_tracker.entities;

//import kotlin.experimental.ExperimentalTypeInference;
import android.text.Editable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="terms")

public class Term {
    @PrimaryKey(autoGenerate = true)
    private int termID;
    private String termTitle;
    private String termStartDate;
    private String termEndDate;

    public Term(int termID, String termTitle, String termStartDate, String termEndDate) {
        this.termID = termID;
        this.termTitle = termTitle;
        this.termStartDate = termStartDate;
        this.termEndDate = termEndDate;
    }

    public Term() {
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public String getTermTitle() {
        return termTitle;
    }

    public void setTermTitle(String termTitle) {
        this.termTitle = termTitle;
    }

    public String getTermStartDate() {
        return termStartDate;
    }

    public void setTermStartDate(String termStartDate) {
        this.termStartDate = termStartDate;
    }

    public String getTermEndDate() {
        return termEndDate;
    }

    public void setTermEndDate(String termEndDate) {
        this.termEndDate = termEndDate;
    }
}

