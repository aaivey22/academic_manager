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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import angelaivey.example.academic_schedule_and_progress_tracker.Database.Repository;
import angelaivey.example.academic_schedule_and_progress_tracker.R;
import angelaivey.example.academic_schedule_and_progress_tracker.entities.Course;
import angelaivey.example.academic_schedule_and_progress_tracker.entities.Term;

public class CourseDetails extends AppCompatActivity {
    EditText editTitle;
    EditText editStart;
    EditText editEnd;
    EditText editStatus;
    EditText editInstructor;
    EditText editNumber;
    EditText editEmail;

    String title;
    String start;
    String end;
    String status;
    String instructor;
    String number;
    String email;

    int id;
    int numCourses;
    Course course;
    Course currentCourse;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        editTitle = findViewById(R.id.textViewcoursetitle);
        editStart = findViewById(R.id.textViewcoursestart);
        editEnd = findViewById(R.id.textViewcourseend);
        editStatus = findViewById(R.id.textViewcoursestatus);
        editInstructor = findViewById(R.id.textViewcourseinstructor);
        editNumber = findViewById(R.id.textViewcourseinstructornumber);
        editEmail = findViewById(R.id.textViewcourseinstructoremail);

        id = getIntent().getIntExtra("id", -1);

        title = getIntent().getStringExtra("title");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        status = getIntent().getStringExtra("status");
        instructor = getIntent().getStringExtra("instructor");
        number = getIntent().getStringExtra("number");
        email = getIntent().getStringExtra("email");

        editTitle.setText(title);
        editStart.setText(start);
        editEnd.setText(end);
        editStatus.setText(status);
        editInstructor.setText(instructor);
        editNumber.setText(number);
        editEmail.setText(email);

        repository = new Repository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.assessmentrecyclerview);
        repository = new Repository(getApplication());
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessments(repository.getAllAssessments());

/*        List<Course> filteredCourses = new ArrayList<>();
        for (Course c : repository.getAllCourses()) {
            if (c.getCourseID() == id) filteredCourses.add(c);
        }
        courseAdapter.setCourses((filteredCourses));*/

        Button button = findViewById(R.id.savecourse);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id == -1) {
                    course = new Course(0, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(), editStatus.getText().toString(), editInstructor.getText().toString(), editNumber.getText().toString(), editEmail.getText().toString(), 0);
                    repository.insert(course);
                    Log.d("CourseLogging", "New Course Added");
                    //Toast.makeText(TermDetails.this, "Term is saved", Toast.LENGTH_LONG).show();
                } else {
                    course = new Course(id, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(), editStatus.getText().toString(), editInstructor.getText().toString(), editNumber.getText().toString(), editEmail.getText().toString(), 0);
                    repository.update(course);
                    Log.d("CourseLogging", "Course Updated");
                    //Toast.makeText(TermDetails.this, "Term is updated", Toast.LENGTH_LONG).show();
                }
            }
        });

        FloatingActionButton fab = findViewById(R.id.floatingActionButton3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseDetails.this, AssessmentList.class);
                intent.putExtra("courseID", id);
                startActivity(intent);
            }
        });

    }
}