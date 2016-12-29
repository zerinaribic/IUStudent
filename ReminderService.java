package ius.iustudent;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;

import ius.iustudent.models.Event;
import ius.iustudent.models.Exam;



public class ReminderService extends IntentService {
    public ReminderService(){
        super("ReminderService");
    }

    @Override
    protected void onHandleIntent(Intent intent){
        Log.d("I", "Work");
        SharedPreferences preferences = getSharedPreferences("data", 0);
        ObjectMapper mapper = new ObjectMapper();
        String classJson = preferences.getString("events", "[]");
        try {
            LinkedList<Event> events = mapper.readValue(classJson, new TypeReference<LinkedList<Event>>(){});
            Calendar calendar = Calendar.getInstance();
            for (Event event : events) {
                if(((event.getStartTime() * 60) < ((calendar.get(Calendar.HOUR_OF_DAY) * 60) + calendar.get(Calendar.MINUTE) + 20)) && (calendar.get(Calendar.HOUR_OF_DAY) <= event.getStartTime())){
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                            .setSmallIcon(android.R.drawable.sym_def_app_icon)
                            .setContentTitle(event.getName())
                            .setContentText("Reminder for "+event.getName())
                            .setVibrate(new long[]{0, 1000})
                            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    manager.notify(0, builder.build());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
