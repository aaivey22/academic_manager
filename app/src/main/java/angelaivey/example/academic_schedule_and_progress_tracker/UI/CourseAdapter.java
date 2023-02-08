package angelaivey.example.academic_schedule_and_progress_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import angelaivey.example.academic_schedule_and_progress_tracker.R;
import angelaivey.example.academic_schedule_and_progress_tracker.entities.Course;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    int termID;

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemTitle;
        private final TextView courseItemStartDate;
        private final TextView courseItemEndDate;
        private final TextView courseItemStatus;
        private final TextView courseItemInstructor;
        private final TextView courseItemInstructorNumber;
        private final TextView courseItemInstructorEmail;

        private CourseViewHolder(View itemView) {
            super(itemView);
            courseItemTitle = itemView.findViewById(R.id.textViewcoursetitle);
            courseItemStartDate = itemView.findViewById(R.id.textViewcoursestart);
            courseItemEndDate = itemView.findViewById(R.id.textViewcourseend);
            courseItemStatus = itemView.findViewById(R.id.textViewcoursestatus);
            courseItemInstructor = itemView.findViewById(R.id.textViewcourseinstructor);
            courseItemInstructorNumber = itemView.findViewById(R.id.textViewcourseinstructornumber);
            courseItemInstructorEmail = itemView.findViewById(R.id.textViewcourseinstructoremail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Course current = mCourses.get(position);
                    Intent intent = new Intent(context, CourseDetails.class);
                    intent.putExtra("id", current.getCourseID());
                    intent.putExtra("title", current.getCourseTitle());
                    intent.putExtra("start", current.getCourseStartDate());
                    intent.putExtra("end", current.getCourseEndDate());
                    intent.putExtra("status", current.getCourseStatus());
                    intent.putExtra("instructor", current.getCourseInstructor());
                    intent.putExtra("number", current.getCourseInstructorNumber());
                    intent.putExtra("email", current.getCourseInstructorEmail());
                    intent.putExtra("termID", termID);
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;

    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseViewHolder((itemView));
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if (mCourses != null) {
            Course current = mCourses.get(position);
            String title = current.getCourseTitle();
            String start = current.getCourseStartDate();
            String end = current.getCourseEndDate();
            String status = current.getCourseStatus();
            String instructor = current.getCourseInstructor();
            String number = current.getCourseInstructorNumber();
            String email = current.getCourseInstructorEmail();

            holder.courseItemTitle.setText(title);
            holder.courseItemStartDate.setText(start);
            holder.courseItemEndDate.setText(end);
            holder.courseItemStatus.setText(status);
            holder.courseItemInstructor.setText(instructor);
            holder.courseItemInstructorNumber.setText(number);
            holder.courseItemInstructorEmail.setText(email);

        } else {
            holder.courseItemTitle.setText("No Course Title");
            holder.courseItemStartDate.setText("No Course Start Date");
            holder.courseItemEndDate.setText("No Course End Date");
            holder.courseItemStatus.setText("No Course Status");
            holder.courseItemInstructor.setText("No Course Instructor");
            holder.courseItemInstructorNumber.setText("No Course Instructor Number");
            holder.courseItemInstructorEmail.setText("No Course Instructor Email");
        }
    }

    public void setCourses(List<Course> courses) {
        mCourses = courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public void setTermID(int TermID) {
        termID = TermID;
    }
}