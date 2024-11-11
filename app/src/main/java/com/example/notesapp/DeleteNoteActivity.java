package com.example.notesapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Map;

public class DeleteNoteActivity extends AppCompatActivity {

    private ArrayList<String> notesList;
    private ArrayAdapter<String> adapter;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);

        ListView listView = findViewById(R.id.listView);
        sharedPreferences = getSharedPreferences("Notes", MODE_PRIVATE);

        loadNotes();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notesList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                deleteNote(notesList.get(position));
            }
        });
    }

    private void loadNotes() {
        notesList = new ArrayList<>();
        Map<String, ?> allNotes = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allNotes.entrySet()) {
            notesList.add(entry.getKey());
        }
    }

    private void deleteNote(String noteName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(noteName);
        editor.apply();

        Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show();
        loadNotes();
        adapter.notifyDataSetChanged();
    }
}
