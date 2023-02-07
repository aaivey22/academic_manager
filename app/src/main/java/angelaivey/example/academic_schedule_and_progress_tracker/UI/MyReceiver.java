package angelaivey.example.academic_schedule_and_progress_tracker.UI;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.util.Objects;

import angelaivey.example.academic_schedule_and_progress_tracker.R;

public class MyReceiver extends BroadcastReceiver {
    String channel_id_course_start = "courseStartChannel";
    String channel_id_course_end = "courseEndChannel";
    String channel_id_assessment_start = "assessmentStartChannel";
    String channel_id_assessment_end = "assessmentEndChannel";

    static int notificationID;
    @Override
    public void onReceive(Context context, Intent intent) {
        String notificationType = intent.getStringExtra("NotificationType");
        if(Objects.equals(notificationType, "CourseStart")) {
            Toast.makeText(context, intent.getStringExtra("courseStartNotify"), Toast.LENGTH_LONG).show();
            createNotificationChannel(context, channel_id_course_start);
            Notification n = new NotificationCompat.Builder(context, channel_id_course_start)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText(intent.getStringExtra("courseStartNotify"))
                    .setContentTitle("Start Course Notification").build();
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(notificationID++, n);
        }
        if(Objects.equals(notificationType, "CourseEnd")) {
            Toast.makeText(context, intent.getStringExtra("courseEndNotify"), Toast.LENGTH_LONG).show();
            createNotificationChannel(context, channel_id_course_end);
            Notification n = new NotificationCompat.Builder(context, channel_id_course_end)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText(intent.getStringExtra("courseEndNotify"))
                    .setContentTitle("End Course Notification").build();
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(notificationID++, n);
        }
        if(Objects.equals(notificationType, "AssessmentStart")) {
            Toast.makeText(context, intent.getStringExtra("assessmentStartNotify"), Toast.LENGTH_LONG).show();
            createNotificationChannel(context, channel_id_assessment_start);
            Notification n = new NotificationCompat.Builder(context, channel_id_assessment_start)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText(intent.getStringExtra("assessmentStartNotify"))
                    .setContentTitle("Start Assessment Notification").build();
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(notificationID++, n);
        }
        if(Objects.equals(notificationType, "AssessmentEnd")) {
            Log.d("Options Menu", "if statement Clicked");

            Toast.makeText(context, intent.getStringExtra("assessmentEndNotify"), Toast.LENGTH_LONG).show();
            createNotificationChannel(context, channel_id_assessment_end);
            Notification n = new NotificationCompat.Builder(context, channel_id_assessment_end)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText(intent.getStringExtra("assessmentEndNotify"))
                    .setContentTitle("End Assessment Notification").build();
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(notificationID++, n);
        }

        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");
    }
    private void createNotificationChannel(Context context, String CHANNEL_ID) {
        CharSequence name = context.getResources().getString(R.string.channel_name);
        String description = context.getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);

        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}