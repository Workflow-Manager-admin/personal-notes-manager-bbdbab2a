package com.example.frontendandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.example.frontendandroid.adapter.NoteAdapter;
import com.example.frontendandroid.model.Note;
import com.example.frontendandroid.model.NoteRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

// PUBLIC_INTERFACE
public class MainActivity extends AppCompatActivity implements NoteAdapter.OnItemClickListener {

    private NoteAdapter noteAdapter;
    private RecyclerView recyclerView;
    private EditText searchEditText;
    private List<Note> currentNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_MaterialNotesApp);
        setContentView(R.layout.activity_main);

        // Recycler view & Adapter
        recyclerView = findViewById(R.id.notes_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        currentNotes = NoteRepository.getInstance().getNotes();
        noteAdapter = new NoteAdapter(currentNotes, this);
        recyclerView.setAdapter(noteAdapter);

        // FAB (Add)
        FloatingActionButton fab = findViewById(R.id.add_note_fab);
        fab.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, NoteDetailActivity.class);
            startActivity(i);
        });

        // Search
        searchEditText = findViewById(R.id.search_box);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence c, int s, int c2, int t) {}
            @Override public void onTextChanged(CharSequence c, int s, int b, int e) {
                performSearch(c.toString());
            }
            @Override public void afterTextChanged(Editable e) {}
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentNotes = NoteRepository.getInstance().getNotes();
        noteAdapter.updateList(currentNotes);
    }

    private void performSearch(String query) {
        if (query.isEmpty()) {
            noteAdapter.updateList(NoteRepository.getInstance().getNotes());
        } else {
            noteAdapter.updateList(NoteRepository.getInstance().searchNotes(query));
        }
    }

    @Override
    public void onItemClick(Note note) {
        // Open detail/edit
        Intent i = new Intent(this, NoteDetailActivity.class);
        i.putExtra("noteId", note.getId());
        startActivity(i);
    }

    @Override
    public void onItemLongClick(Note note) {
        // Confirm delete
        new AlertDialog.Builder(this)
            .setTitle(R.string.delete_note)
            .setMessage(R.string.delete_note_confirm)
            .setPositiveButton(R.string.delete_note, (dialog, which) -> {
                NoteRepository.getInstance().deleteNote(note.getId());
                noteAdapter.updateList(NoteRepository.getInstance().getNotes());
                Toast.makeText(this, R.string.deleted, Toast.LENGTH_SHORT).show();
            })
            .setNegativeButton(R.string.cancel, null)
            .show();
    }
}
