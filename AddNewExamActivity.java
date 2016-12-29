package ius.iustudent;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import ius.iustudent.models.Event;
import ius.iustudent.models.Exam;

public class AddNewExamActivity extends AppCompatActivity {
    Exam exam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_exam);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Add new exam");
        exam = new Exam();
        final Calendar calendar = new GregorianCalendar();
        calendar.clear();
        final EditText className = (EditText) findViewById(R.id.exam_name);
        final EditText examType = (EditText) findViewById(R.id.exam_type);
        final TextView date = (TextView) findViewById(R.id.exam_date);
        final TextView time = (TextView) findViewById(R.id.exam_time);
        LinearLayout datePicker = (LinearLayout) findViewById(R.id.date_picker);
        LinearLayout timePicker = (LinearLayout) findViewById(R.id.time_picker);
        Button btn = (Button) findViewById(R.id.add_exam_btn);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarDatePickerDialogFragment cdpdf = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, monthOfYear);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MMM.yyyy");
                                date.setText(dateFormat.format(calendar.getTime()));
                            }
                        });
                cdpdf.show(getSupportFragmentManager(), "datepicker");
            }
        });
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadialTimePickerDialogFragment rtpdf = new RadialTimePickerDialogFragment()
                        .setForced24hFormat()
                        .setOnTimeSetListener(new RadialTimePickerDialogFragment.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute) {
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);
                                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                                time.setText(dateFormat.format(calendar.getTime()));
                            }
                        });
                rtpdf.show(getSupportFragmentManager(), "timepicker");
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exam.setCourse(className.getText().toString());
                exam.setType(examType.getText().toString());
                exam.setDate(calendar.getTime());
                SharedPreferences sharedPreferences = getSharedPreferences("data", 0);
                ObjectMapper mapper = new ObjectMapper();
                try {
                    String eventJson = sharedPreferences.getString("exams", "[]");
                    ArrayList<Exam> exams = mapper.readValue(eventJson, new TypeReference<ArrayList<Exam>>(){});
                    exams.add(exam);
                    String newJson = mapper.writeValueAsString(exams);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("exams", newJson);
                    editor.apply();
                    setResult(0);
                    finish();
                } catch (IOException e) {
                    Toast.makeText(AddNewExamActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
