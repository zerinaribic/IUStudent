package ius.iustudent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import ius.iustudent.adapters.ExamAdapter;
import ius.iustudent.models.Exam;

public class ExamListFragment extends Fragment {
    private List<Exam> exams;
    private ListView examList;
    public ExamListFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.exam_list_view, container, false);
        examList = (ListView) rootView.findViewById(R.id.exam_list);
        try {
            exams = getExams();
        } catch (IOException e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            exams = new LinkedList<>();
        }
        examList.setAdapter(new ExamAdapter(getActivity(), R.layout.exam_list_item, exams));
        examList.setEmptyView(rootView.findViewById(R.id.empty));
        registerForContextMenu(examList);
        examList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Exam exam = (Exam) adapterView.getAdapter().getItem(i);
                Intent intent = new Intent(getActivity(), ShowExamActivity.class);
                intent.putExtra("exam", exam);
                startActivity(intent);
            }
        });
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), AddNewExamActivity.class),0);
            }
        });
        return rootView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v.getId() == R.id.exam_list){
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.class_list_context, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.delete:
                exams.remove(info.position);
                ExamAdapter adapter = (ExamAdapter) examList.getAdapter();
                adapter.notifyDataSetChanged();
                try {
                    saveExams();
                } catch (IOException e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        onResume();
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            exams.clear();
            exams.addAll(getExams());
            ExamAdapter adapter = (ExamAdapter) examList.getAdapter();
            adapter.notifyDataSetChanged();
        } catch (IOException e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private List<Exam> getExams() throws IOException {
        SharedPreferences preferences = getActivity().getSharedPreferences("data", 0);
        String examJson = preferences.getString("exams", "[]");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(examJson, new TypeReference<LinkedList<Exam>>(){});
    }

    private void saveExams() throws JsonProcessingException {
        SharedPreferences preferences = getActivity().getSharedPreferences("data", 0);
        ObjectMapper mapper = new ObjectMapper();
        String newExams = mapper.writeValueAsString(exams);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("exams", newExams);
        editor.apply();
    }
}
