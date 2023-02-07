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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import angelaivey.example.academic_schedule_and_progress_tracker.Database.Repository;
import angelaivey.example.academic_schedule_and_progress_tracker.R;
import angelaivey.example.academic_schedule_and_progress_tracker.entities.Assessment;
import angelaivey.example.academic_schedule_and_progress_tracker.entities.Course;
import angelaivey.example.academic_schedule_and_progress_tracker.entities.Term;

public class AssessmentDetails extends AppCompatActivity {
    EditText editTitle;
    EditText editType;
    EditText editStart;
    EditText editEnd;

    String title;
    String type;
    String start;
    String end;

    private DatePickerDialog startDatePicker;
    private DatePickerDialog endDatePicker;

    int id;
    int courseID;

    Assessment assessment;
    Assessment currentAssessment;

    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        startDatePicker();
        endDatePicker();

        editTitle = findViewById(R.id.textViewassessmenttitle);
        editType = findViewById(R.id.textViewassessmenttype);
        editStart = findViewById(R.id.textViewassessmentstart);
        editEnd = findViewById(R.id.textViewassessmentend);

        String dateFormat = "MM/dd/yy";
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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

        endDatePicker = new DatePickerDialog(AssessmentDetails.this, endDateSetListener, year, month, day);

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

        startDatePicker = new DatePickerDialog(AssessmentDetails.this, startDateSetListener, year, month, day);

    }

    public void openStartDatePicker(View view) {
        startDatePicker.show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessment_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                this.finish();
                return true;
            case R.id.notificationStart:
                String startDateFromScreen = editStart.getText().toString();
                String formatStartDate = "MM/dd/yy";
                SimpleDateFormat sdfstart = new SimpleDateFormat(formatStartDate, Locale.US);
                Date startDateAssessment = null;
                try {
                    startDateAssessment = sdfstart.parse(startDateFromScreen);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long triggerStart = startDateAssessment.getTime();
                Intent intentStart = new Intent(AssessmentDetails.this, MyReceiver.class);
                intentStart.putExtra("NotificationType", "AssessmentStart");
                String AssessmentNameStart = editTitle.getText().toString();
                intentStart.putExtra("assessmentStartNotify", "Assessment: " + AssessmentNameStart + " starts on " + startDateFromScreen);
                PendingIntent senderStart = PendingIntent.getBroadcast(AssessmentDetails.this, ++MainActivity.numAlert, intentStart, PendingIntent.FLAG_UPDATE_CURRENT);
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
                Intent intentEnd = new Intent(AssessmentDetails.this, MyReceiver.class);
                intentEnd.putExtra("NotificationType", "AssessmentEnd");
                String AssessmentNameEnd = editTitle.getText().toString();
                intentEnd.putExtra("assessmentEndNotify", "Assessment: " + AssessmentNameEnd + " ends on " + endDateFromScreen);
                PendingIntent senderEnd = PendingIntent.getBroadcast(AssessmentDetails.this, ++MainActivity.numAlert, intentEnd, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManagerEnd = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManagerEnd.set(AlarmManager.RTC_WAKEUP, trigger, senderEnd);

                Log.d("Options Menu", "End Notification Clicked");
                return true;
            case R.id.deleteAssessment:
                for (Assessment assessment : repository.getAllAssessments()) {
                    if (assessment.getAssessmentID() == id) currentAssessment = assessment;
                }
                repository.delete(currentAssessment);
                Toast.makeText(AssessmentDetails.this, currentAssessment.getAssessmentTitle() + " was deleted", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(AssessmentDetails.this, TermList.class);
                startActivity(intent);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

}