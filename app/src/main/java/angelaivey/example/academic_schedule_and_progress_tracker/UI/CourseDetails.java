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
import android.widget.EditText;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.List;

import angelaivey.example.academic_schedule_and_progress_tracker.Database.Repository;
import angelaivey.example.academic_schedule_and_progress_tracker.R;
import angelaivey.example.academic_schedule_and_progress_tracker.entities.Assessment;
import angelaivey.example.academic_schedule_and_progress_tracker.entities.Course;
import angelaivey.example.academic_schedule_and_progress_tracker.entities.Note;
import angelaivey.example.academic_schedule_and_progress_tracker.entities.Term;

public class CourseDetails extends AppCompatActivity {
    EditText editTitle;
    EditText editStart;
    EditText editEnd;
    EditText editStatus;
    EditText editInstructor;
    EditText editNumber;
    EditText editEmail;
    EditText editNote;
    private DatePickerDialog startDatePicker;
    private DatePickerDialog endDatePicker;

    String title;
    String start;
    String end;
    String status;
    String instructor;
    String number;
    String email;
    String noteBody;

    int id;
    int termID;
    int noteID;
    Course course;
    Course currentCourse;
    Note courseNote;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        startDatePicker();
        endDatePicker();

        editTitle = findViewById(R.id.textViewcoursetitle);
        editStart = findViewById(R.id.textViewcoursestart);
        editEnd = findViewById(R.id.textViewcourseend);
        editStatus = findViewById(R.id.textViewcoursestatus);
        editInstructor = findViewById(R.id.textViewcourseinstructor);
        editNumber = findViewById(R.id.textViewcourseinstructornumber);
        editEmail = findViewById(R.id.textViewcourseinstructoremail);
        editNote = findViewById(R.id.textViewcoursenote);

        String dateFormat = "MM/dd/yy";
        id = getIntent().getIntExtra("id", -1);
        termID = getIntent().getIntExtra("termID", -1);
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
        if (!repository.getAllNotes().isEmpty()) {
            for (Note note : repository.getAllNotes()) {
                if (note.getCourseID() == id) {
                    noteBody = note.getNoteBody();
                    noteID = note.getNoteID();
                    editNote.setText(noteBody);
                }
            }
        }

        RecyclerView recyclerView = findViewById(R.id.assessmentrecyclerview);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> filteredAssessments = new ArrayList<>();
        for (Assessment a : repository.getAllAssessments()) {
            if (a.getCourseID() == id) {
                filteredAssessments.add(a);
                Log.d("AssessmentLogging", "CourseDetails ID: " + id + " " + "Assessment ID: " + a.getAssessmentID());
            }
            Log.d("AssessmentLogging", "CourseDetails ID: " + id + " " + "Assessment ID: " + a.getAssessmentID());
        }
        assessmentAdapter.setAssessments(filteredAssessments);
        assessmentAdapter.setCourseID(id);
        Button button = findViewById(R.id.savecourse);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editNote.getText().toString().length() > 0) {
                    if (noteID <= 0) {
                        courseNote = new Note(0, id, editNote.getText().toString());
                        Log.d("Note", "Note added: " + editNote.getText().toString() + " ID: " + noteID);
                        repository.insert(courseNote);
                    } else {
                        courseNote = new Note(noteID, id, editNote.getText().toString());
                        Log.d("Note", "Note updated: " + editNote.getText().toString() + " ID: " + noteID);
                        repository.update(courseNote);
                    }
                }

                if (id == -1) {
                    course = new Course(0, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(), editStatus.getText().toString(), editInstructor.getText().toString(), editNumber.getText().toString(), editEmail.getText().toString(), termID);
                    repository.insert(course);
                    Intent intent = new Intent(CourseDetails.this, TermList.class);
                    startActivity(intent);
                    //Toast.makeText(TermDetails.this, "Term is saved", Toast.LENGTH_LONG).show();
                } else {
                    course = new Course(id, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(), editStatus.getText().toString(), editInstructor.getText().toString(), editNumber.getText().toString(), editEmail.getText().toString(), termID);
                    repository.update(course);
                    Intent intent = new Intent(CourseDetails.this, TermList.class);
                    startActivity(intent);
                    //Toast.makeText(TermDetails.this, "Term is updated", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Loads the assessment list page and passes over the course id variable from the selected course
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

        endDatePicker = new DatePickerDialog(CourseDetails.this, endDateSetListener, year, month, day);

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

        startDatePicker = new DatePickerDialog(CourseDetails.this, startDateSetListener, year, month, day);

    }

    public void openStartDatePicker(View view) {
        startDatePicker.show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                this.finish();
                return true;
            case R.id.shareNotes:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, editNote.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Message Title");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                Log.d("Options Menu", "Share Clicked");
                return true;
            case R.id.notificationStart:
                String startDateFromScreen = editStart.getText().toString();
                String formatStartDate = "MM/dd/yy";
                SimpleDateFormat sdfstart = new SimpleDateFormat(formatStartDate, Locale.US);
                Date startDate = null;
                try {
                    startDate = sdfstart.parse(startDateFromScreen);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long triggerStart = startDate.getTime();
                Intent intentStart = new Intent(CourseDetails.this, MyReceiver.class);
                intentStart.putExtra("NotificationType", "CourseStart");
                String CourseNameStart = editTitle.getText().toString();
                intentStart.putExtra("courseStartNotify", "Course: " + CourseNameStart + " starts on " + startDateFromScreen);
                PendingIntent senderStart = PendingIntent.getBroadcast(CourseDetails.this, ++MainActivity.numAlert, intentStart, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManagerStart = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManagerStart.set(AlarmManager.RTC_WAKEUP, triggerStart, senderStart);
                return true;
            case R.id.notificationEnd:
                String endDateFromScreen = editEnd.getText().toString();
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdfend = new SimpleDateFormat(myFormat, Locale.US);
                Date endDate = null;
                try {
                    endDate = sdfend.parse(endDateFromScreen);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger = endDate.getTime();
                Intent intentEnd = new Intent(CourseDetails.this, MyReceiver.class);
                intentEnd.putExtra("NotificationType", "CourseEnd");
                String CourseName = editTitle.getText().toString();
                intentEnd.putExtra("courseEndNotify", "Course: " + CourseName + " ends on " + endDateFromScreen);
                PendingIntent sender = PendingIntent.getBroadcast(CourseDetails.this, ++MainActivity.numAlert, intentEnd, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManagerEnd = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManagerEnd.set(AlarmManager.RTC_WAKEUP, trigger, sender);

                Log.d("Options Menu", "End Notification Clicked");
                return true;
            case R.id.deleteCourse:
                for (Course course : repository.getAllCourses()) {
                    if (course.getCourseID() == id) currentCourse = course;
                }
                repository.delete(currentCourse);
                Toast.makeText(CourseDetails.this, currentCourse.getCourseTitle() + " was deleted", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(CourseDetails.this, TermList.class);
                startActivity(intent);
                Log.d("Options Menu", "Delete Course Clicked");
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.assessmentrecyclerview);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        assessmentAdapter.setCourseID(id);
        List<Assessment> assessmentList = new ArrayList<>();
        for (Assessment assessment : repository.getAllAssessments()) {
            if (assessment.getCourseID() == id) {
                assessmentList.add(assessment);
            }
        }
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessments(assessmentList);
    }

}