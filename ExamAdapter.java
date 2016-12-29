package ius.iustudent.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import ius.iustudent.R;
import ius.iustudent.models.Exam;


public class ExamAdapter extends ArrayAdapter<Exam> {
    public ExamAdapter(Context context, int resource, List<Exam> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.exam_list_item, null);
        }
        Exam exam = getItem(position);
        TextView examName = (TextView) convertView.findViewById(R.id.exam_name);
        TextView examType = (TextView) convertView.findViewById(R.id.exam_type);
        TextView examDate = (TextView) convertView.findViewById(R.id.exam_date);
        TextView examTime = (TextView) convertView.findViewById(R.id.exam_time);
        examName.setText(exam.getCourse());
        examType.setText(exam.getType());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MMM.yyyy");
        examDate.setText(dateFormat.format(exam.getDate()));
        dateFormat = new SimpleDateFormat("HH:mm");
        examTime.setText(dateFormat.format(exam.getDate()));
        return convertView;
    }
}
