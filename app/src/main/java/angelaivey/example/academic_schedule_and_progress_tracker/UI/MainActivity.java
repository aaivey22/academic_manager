package angelaivey.example.academic_schedule_and_progress_tracker.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import angelaivey.example.academic_schedule_and_progress_tracker.Database.Repository;
import angelaivey.example.academic_schedule_and_progress_tracker.R;
import angelaivey.example.academic_schedule_and_progress_tracker.entities.Course;
import angelaivey.example.academic_schedule_and_progress_tracker.entities.Term;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        /* Term term = new Term(0, "Term 1", "02/15/2018", "02/01/2023");
        //Repository repository = new Repository(getApplication());
        //repository.insert(term); */
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

    public boolean onOptionsItemsSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addSampleData:
            Term term = new Term(0, "Term 1", "02/15/2018", "02/01/2023");
            Repository repository = new Repository(getApplication());
            repository.insert(term);
            Course course = new Course(0, "Maths", "03/0/3029", "03/04/2039", "pass", "Reishi Grizwald", "306-435-7957", "reishi@email.com", 1);
            repository.insert(course);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}