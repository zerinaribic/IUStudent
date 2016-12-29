package ius.iustudent.overview;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import ius.iustudent.R;
import ius.iustudent.adapters.ExamAdapter;
import ius.iustudent.models.Exam;

public class ExamScheduleFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.overview_exam_fragment, container, false);
        List<Exam> exams;
        try {
            exams = getExams();
        } catch (IOException e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            exams = new LinkedList<>();
        }
        ListView examList = (ListView) view.findViewById(R.id.exam_list);
        examList.setEmptyView(view.findViewById(R.id.empty));
        examList.setAdapter(new ExamAdapter(getActivity(), R.layout.exam_list_view, exams));
        return view;
    }

    private List<Exam> getExams() throws IOException {
        SharedPreferences preferences = getActivity().getSharedPreferences("data", 0);
        ObjectMapper mapper = new ObjectMapper();
        String examJson = preferences.getString("exams", "[]");
        LinkedList<Exam> exams = mapper.readValue(examJson, new TypeReference<LinkedList<Exam>>(){});
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        long time = calendar.getTime().getTime();
        LinkedList<Exam> upcomingExams = new LinkedList<>();
        for (Exam exam: exams){
            if(exam.getDate().getTime() <= time && exam.getDate().getTime() >= Calendar.getInstance().getTime().getTime()){
                upcomingExams.add(exam);
            }
        }
        return upcomingExams;
    }
}
