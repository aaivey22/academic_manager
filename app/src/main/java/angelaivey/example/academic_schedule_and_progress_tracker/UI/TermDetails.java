package angelaivey.example.academic_schedule_and_progress_tracker.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import angelaivey.example.academic_schedule_and_progress_tracker.Database.Repository;
import angelaivey.example.academic_schedule_and_progress_tracker.R;
import angelaivey.example.academic_schedule_and_progress_tracker.entities.Course;
import angelaivey.example.academic_schedule_and_progress_tracker.entities.Term;

public class TermDetails extends AppCompatActivity {
    EditText editTitle;
    EditText editStart;
    EditText editEnd;

    private DatePickerDialog startDatePicker;
    private DatePickerDialog endDatePicker;

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

        startDatePicker();
        endDatePicker();

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

        String dateFormat = "MM/dd/yy";

        repository = new Repository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.courserecyclerview);
        repository = new Repository(getApplication());
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        courseAdapter.setTermID(id);
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
                    term = new Term(0, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
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

    private void endDatePicker() {
        DatePickerDialog.OnDateSetListener endDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String formatMonth = null;
                String formatDay = null;
                if (month < 10) {
                    formatMonth = String.format("0" + String.valueOf(month));
                } else {
                    formatMonth = String.valueOf((month));
                }
                if (day < 10) {
                    formatDay = String.format("0" + String.valueOf(day));
                } else {
                    formatDay = String.valueOf((day));
                }
                end = formatMonth + "/" + formatDay + "/" + (year - 2000);
                editEnd.setText(end);
            }
        };

        Calendar endCalendar = Calendar.getInstance();

        int year = endCalendar.get(Calendar.YEAR);
        int month = endCalendar.get(Calendar.MONTH);
        int day = endCalendar.get(Calendar.DAY_OF_MONTH);

        endDatePicker = new DatePickerDialog(TermDetails.this, endDateSetListener, year, month, day);
    }

    public void openEndDatePicker(View view) {
        endDatePicker.show();
    }

    private void startDatePicker() {
        DatePickerDialog.OnDateSetListener startDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String formatMonth = null;
                String formatDay = null;
                if (month < 10) {
                    formatMonth = String.format("0" + String.valueOf(month));
                } else {
                    formatMonth = String.valueOf((month));
                }
                if (day < 10) {
                    formatDay = String.format("0" + String.valueOf(day));
                } else {
                    formatDay = String.valueOf((day));
                }
                start = formatMonth + "/" + formatDay + "/" + (year - 2000);
                editStart.setText(start);
            }
        };

        Calendar startCalendar = Calendar.getInstance();

        int year = startCalendar.get(Calendar.YEAR);
        int month = startCalendar.get(Calendar.MONTH);
        int day = startCalendar.get(Calendar.DAY_OF_MONTH);

        startDatePicker = new DatePickerDialog(TermDetails.this, startDateSetListener, year, month, day);
    }

    public void openStartDatePicker(View view) {
        startDatePicker.show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_term_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Intent intentTerm = new Intent(TermDetails.this, TermList.class);
                startActivity(intentTerm);
                return true;
            case R.id.deleteTerm:
                for (Term term : repository.getAllTerms()) {
                    if (term.getTermID() == id) currentTerm = term;
                }
                numCourses = 0;
                for (Course course : repository.getAllCourses()) {
                    if (course.getTermID() == id) ++numCourses;
                }
                if (numCourses == 0) {
                    repository.delete(currentTerm);
                    Intent intent = new Intent(TermDetails.this, TermList.class);
                    startActivity(intent);
                    Toast.makeText(TermDetails.this, currentTerm.getTermTitle() + " was deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(TermDetails.this, "A term with assigned courses cannot be deleted.", Toast.LENGTH_LONG).show();
                }
                Log.d("Options Menu", "Delete Course Clicked");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // the onResume function ensures the most updated data is displayed when using the back arrow
    @Override
    protected void onResume() {
        super.onResume();
        //List<Course> allCourses = repository.getAllCourses();
        RecyclerView recyclerView = findViewById(R.id.courserecyclerview);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        courseAdapter.setTermID(id);
        List<Course> courseList = new ArrayList<>();
        for (Course course : repository.getAllCourses()) {
            if (course.getTermID() == id) {
                courseList.add(course);
            }
        }
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourses(courseList);
    }
}