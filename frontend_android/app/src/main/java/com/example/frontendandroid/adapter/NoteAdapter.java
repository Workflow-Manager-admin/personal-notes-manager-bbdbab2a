package com.example.frontendandroid.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.frontendandroid.R;
import com.example.frontendandroid.model.Note;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

// PUBLIC_INTERFACE
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    /** Adapter for the Notes RecyclerView. */
    public interface OnItemClickListener {
        void onItemClick(Note note);
        void onItemLongClick(Note note);
    }

    private List<Note> notes;
    private final OnItemClickListener listener;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault());

    public NoteAdapter(List<Note> notes, OnItemClickListener listener) {
        this.notes = notes;
        this.listener = listener;
    }

    public void updateList(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note n = notes.get(position);
        holder.title.setText(n.getTitle());
        holder.content.setText(n.getContent());
        holder.date.setText(dateFormat.format(n.getUpdatedAt()));

        holder.card.setOnClickListener(v -> listener.onItemClick(n));
        holder.card.setOnLongClickListener(v -> {
            listener.onItemLongClick(n);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return notes == null ? 0 : notes.size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        public final TextView content;
        public final TextView date;
        public final CardView card;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.note_card);
            title = itemView.findViewById(R.id.note_title);
            content = itemView.findViewById(R.id.note_content);
            date = itemView.findViewById(R.id.note_date);
        }
    }
}
