package angelaivey.example.academic_schedule_and_progress_tracker.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import angelaivey.example.academic_schedule_and_progress_tracker.Database.Repository;
import angelaivey.example.academic_schedule_and_progress_tracker.R;
import angelaivey.example.academic_schedule_and_progress_tracker.entities.Course;
import angelaivey.example.academic_schedule_and_progress_tracker.entities.Term;

public class TermDetails extends AppCompatActivity {
    EditText editTitle;
    EditText editStart;
    EditText editEnd;

    String title;
    String start;
    String end;

    int id;
    int numCourses;
    Term term;
    Term currentTerm;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        editTitle = findViewById(R.id.termtitle);
        editStart = findViewById(R.id.termstart);
        editEnd = findViewById(R.id.termend);
        id = getIntent().getIntExtra("id", -1);

        title = getIntent().getStringExtra("title");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");

        editTitle.setText(title);
        editStart.setText(start);
        editEnd.setText(end);

        repository = new Repository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.courserecyclerview);
        repository = new Repository(getApplication());
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Course> courseList = new ArrayList<>();
        for (Course course : repository.getAllCourses()) {
            if (course.getTermID() == id) {
                courseList.add(course);
            }
        }
        courseAdapter.setCourses(courseList);

        Button button = findViewById(R.id.saveterm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id == -1) {
                    term = new Term(0,editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
                    repository.insert(term);
                    //Toast.makeText(TermDetails.this, "Term is saved", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(TermDetails.this, TermList.class);
                    startActivity(intent);

                } else {
                    term = new Term(id, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
                    repository.update(term);
                    //Toast.makeText(TermDetails.this, "Term is updated", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(TermDetails.this, TermList.class);
                    startActivity(intent);

                }
            }
        });

        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermDetails.this, CourseList.class);
                intent.putExtra("termID", id);
                startActivity(intent);
            }
        });
    }
}