package angelaivey.example.academic_schedule_and_progress_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import angelaivey.example.academic_schedule_and_progress_tracker.R;
import angelaivey.example.academic_schedule_and_progress_tracker.entities.Assessment;
import angelaivey.example.academic_schedule_and_progress_tracker.entities.Course;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {
    int courseID;

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentItemTitle;
        private final TextView assessmentItemType;
        private final TextView assessmentItemStartDate;
        private final TextView assessmentItemEndDate;

        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentItemTitle = itemView.findViewById(R.id.textViewassessmenttitle);
            assessmentItemType = itemView.findViewById(R.id.textViewassessmenttype);
            assessmentItemStartDate = itemView.findViewById(R.id.textViewassessmentstart);
            assessmentItemEndDate = itemView.findViewById(R.id.textViewassessmentend);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Assessment current = mAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentDetails.class);
                    intent.putExtra("id", current.getAssessmentID());
                    intent.putExtra("title", current.getAssessmentTitle());
                    intent.putExtra("type", current.getAssessmentType());
                    intent.putExtra("start", current.getAssessmentStartDate());
                    intent.putExtra("end", current.getAssessmentEndDate());
                    intent.putExtra("courseID", courseID);

                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;

    public AssessmentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentViewHolder((itemView));
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if (mAssessments != null) {
            Assessment current = mAssessments.get(position);
            String title = current.getAssessmentTitle();
            String type = current.getAssessmentType();
            String start = current.getAssessmentStartDate();
            String end = current.getAssessmentEndDate();

            holder.assessmentItemTitle.setText(title);
            holder.assessmentItemType.setText(type);
            holder.assessmentItemStartDate.setText(start);
            holder.assessmentItemEndDate.setText(end);

        } else {
            holder.assessmentItemTitle.setText("No Assessment Title");
            holder.assessmentItemType.setText("No Assessment Type");
            holder.assessmentItemStartDate.setText("No Assessment Start Date");
            holder.assessmentItemEndDate.setText("No Assessment End Date");
        }

    }

    public void setAssessments(List<Assessment> assessments) {
        mAssessments = assessments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mAssessments.size();
    }

    // Setting the value of the course ID when it is called in the app.
    public void setCourseID(int CourseID){
        courseID = CourseID;
    }
}

