package com.example.frontendandroid;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.frontendandroid.model.Note;
import com.example.frontendandroid.model.NoteRepository;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import android.view.View;
import android.widget.Toast;

// PUBLIC_INTERFACE
public class NoteDetailActivity extends AppCompatActivity {

    private Note note;
    private boolean editingExisting;
    private EditText editTitle, editContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_MaterialNotesApp);
        setContentView(R.layout.activity_note_detail);

        MaterialToolbar toolbar = findViewById(R.id.note_detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTitle = findViewById(R.id.edit_note_title);
        editContent = findViewById(R.id.edit_note_content);

        String noteId = getIntent().getStringExtra("noteId");
        editingExisting = (noteId != null);

        if (editingExisting) {
            Note n = NoteRepository.getInstance().getNoteById(noteId);
            if (n != null) {
                note = new Note(n.getId(), n.getTitle(), n.getContent(), n.getCreatedAt(), n.getUpdatedAt());
                editTitle.setText(note.getTitle());
                editContent.setText(note.getContent());
            }
        } else {
            note = new Note("", "");
        }

        MaterialButton saveButton = findViewById(R.id.save_note_button);
        saveButton.setOnClickListener(v -> saveNote());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveNote() {
        String title = editTitle.getText().toString().trim();
        String content = editContent.getText().toString().trim();
        if (title.isEmpty() && content.isEmpty()) {
            Toast.makeText(this, R.string.empty_note_error, Toast.LENGTH_SHORT).show();
            return;
        }
        note.setTitle(title);
        note.setContent(content);
        note.setUpdatedAt(System.currentTimeMillis());

        if (editingExisting) {
            NoteRepository.getInstance().updateNote(note);
        } else {
            NoteRepository.getInstance().addNote(note);
        }
        finish();
    }
}
