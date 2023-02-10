package angelaivey.example.academic_schedule_and_progress_tracker.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import angelaivey.example.academic_schedule_and_progress_tracker.Database.Repository;
import angelaivey.example.academic_schedule_and_progress_tracker.R;
import angelaivey.example.academic_schedule_and_progress_tracker.entities.Assessment;
import angelaivey.example.academic_schedule_and_progress_tracker.entities.Course;
import angelaivey.example.academic_schedule_and_progress_tracker.entities.Term;

public class MainActivity extends AppCompatActivity {
    public static int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TermList.class);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addSampleData:
                Term term = new Term(0, "Term 7", "02/10/2023", "02/10/2023");
                Repository repository = new Repository(getApplication());
                repository.insert(term);
                Course course = new Course(0, "literature", "02/10/2023", "02/10/2023", "pass", "Ruby Sinke", "306-435-7957", "teacher@email.com", 1);
                repository.insert(course);
                Assessment assessment = new Assessment(0, "test", "objective", "02/10/2023", "02/10/2023", 1);
                repository.insert(assessment);

                Toast.makeText(MainActivity.this, "Sample Data Added", Toast.LENGTH_LONG).show();

                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}