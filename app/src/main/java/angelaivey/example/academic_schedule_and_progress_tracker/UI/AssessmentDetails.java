package angelaivey.example.academic_schedule_and_progress_tracker.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import angelaivey.example.academic_schedule_and_progress_tracker.Database.Repository;
import angelaivey.example.academic_schedule_and_progress_tracker.R;
import angelaivey.example.academic_schedule_and_progress_tracker.entities.Assessment;
import angelaivey.example.academic_schedule_and_progress_tracker.entities.Course;

public class AssessmentDetails extends AppCompatActivity {
    EditText editTitle;
    EditText editType;
    EditText editStart;
    EditText editEnd;

    String title;
    String type;
    String start;
    String end;

    int id;
    int numAssessments;
    int courseID;
    Assessment assessment;
    Assessment currentAssessment;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);
        editTitle = findViewById(R.id.textViewassessmenttitle);
        editType = findViewById(R.id.textViewassessmenttype);
        editStart = findViewById(R.id.textViewassessmentstart);
        editEnd = findViewById(R.id.textViewassessmentend);

        id = getIntent().getIntExtra("id", -1);

        title = getIntent().getStringExtra("title");
        type = getIntent().getStringExtra("type");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        courseID = getIntent().getIntExtra("courseID", -1);

        editTitle.setText(title);
        editType.setText(type);
        editStart.setText(start);
        editEnd.setText(end);

        repository = new Repository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.assessmentrecyclerview);
        repository = new Repository(getApplication());
        //final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        //recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //assessmentAdapter.setAssessments(repository.getAllAssessments());

        Button button = findViewById(R.id.saveassessment);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id == -1) {
                    assessment = new Assessment(0, editTitle.getText().toString(), editType.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(), courseID);
                    repository.insert(assessment);
                    Intent intent = new Intent(AssessmentDetails.this, TermList.class);
                    startActivity(intent);

                    Log.d("AssessmentLogging", "New Assessment Added");
                    //Toast.makeText(TermDetails.this, "Term is saved", Toast.LENGTH_LONG).show();
                } else {
                    assessment = new Assessment(id, editTitle.getText().toString(), editType.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(), courseID);
                    repository.update(assessment);
                    Intent intent = new Intent(AssessmentDetails.this, TermList.class);
                    startActivity(intent);

                    Log.d("AssessmentLogging", "Assessment Updated");
                    //Toast.makeText(TermDetails.this, "Term is updated", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}