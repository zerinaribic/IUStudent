package ius.iustudent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import ius.iustudent.models.Event;

public class ShowClassActivity extends AppCompatActivity {
    private final String[] days = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Event event = getIntent().getParcelableExtra("event");
        setTitle(event.getName() + " class");
        TextView nameView = (TextView) findViewById(R.id.name_view);
        TextView dayView = (TextView) findViewById(R.id.day_view);
        TextView startView = (TextView) findViewById(R.id.start_view);
        TextView endView = (TextView) findViewById(R.id.end_view);
        nameView.setText(event.getName());
        dayView.setText(days[event.getDay()]);
        startView.setText(String.valueOf(event.getStartTime()));
        endView.setText(String.valueOf(event.getEndTime()));
    }
}
