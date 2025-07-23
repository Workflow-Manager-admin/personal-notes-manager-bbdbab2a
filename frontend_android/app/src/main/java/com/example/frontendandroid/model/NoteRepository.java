package com.example.frontendandroid.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

// PUBLIC_INTERFACE
public class NoteRepository {
    /** Singleton class for managing notes in-memory. */
    private static NoteRepository instance;
    private final List<Note> notes;

    private NoteRepository() {
        notes = new ArrayList<>();
    }

    public static NoteRepository getInstance() {
        if (instance == null) {
            instance = new NoteRepository();
        }
        return instance;
    }

    public List<Note> getNotes() {
        List<Note> copy = new ArrayList<>(notes);
        Collections.sort(copy, (a, b) -> Long.compare(b.getUpdatedAt(), a.getUpdatedAt()));
        return copy;
    }

    public void addNote(Note n) {
        notes.add(n);
    }

    public void updateNote(Note updated) {
        for (int i=0; i<notes.size(); i++) {
            if (notes.get(i).getId().equals(updated.getId())) {
                notes.set(i, updated);
                break;
            }
        }
    }

    public void deleteNote(String id) {
        for (int i=0; i<notes.size(); i++) {
            if (notes.get(i).getId().equals(id)) {
                notes.remove(i);
                break;
            }
        }
    }

    public Note getNoteById(String id) {
        for (Note n : notes) {
            if (n.getId().equals(id)) return n;
        }
        return null;
    }

    public List<Note> searchNotes(String query) {
        String lq = query.toLowerCase(Locale.getDefault());
        return getNotes().stream()
                .filter(n -> n.getTitle().toLowerCase().contains(lq) || n.getContent().toLowerCase().contains(lq))
                .collect(Collectors.toList());
    }
}
