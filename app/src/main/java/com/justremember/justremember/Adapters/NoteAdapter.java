package com.justremember.justremember.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.justremember.justremember.API;
import com.justremember.justremember.Activities.NoteActivity;
import com.justremember.justremember.ApiProvider;
import com.justremember.justremember.Entities.Note;
import com.justremember.justremember.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dimko_000 on 03.04.2016.
 */
public class NoteAdapter extends BaseAdapter {
    private static final String TAG = "NoteAdapter";
    List<Note> notes;
    Context context;
    ProgressBar progressBar;
    public NoteAdapter(List<Note> notes, ProgressBar progressBar, Context context) {
        this.notes = notes;
        this.progressBar = progressBar;
        this.context = context;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_note, parent, false);
        TextView noteNameView = (TextView) convertView.findViewById(R.id.noteNameTextView);
        TextView noteTextView = (TextView) convertView.findViewById(R.id.noteTextView);
        TextView dateTextView = (TextView) convertView.findViewById(R.id.dateTextView);
        Button editButton = (Button) convertView.findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NoteActivity.class);
                intent.putExtra(NoteActivity.NOTE_ID, notes.get(position).getId());
                context.startActivity(intent);
            }
        });
        Button deleteButton = (Button) convertView.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiProvider.getInstance().getApi().deleteNote(1, notes.get(position).getId()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        notes.remove(position);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e(TAG, t.getMessage(), t);
                    }
                });
            }
        });
        Note note = notes.get(position);
        noteNameView.setText(note.getName());
        noteTextView.setText(note.getContent());
        //dateTextView.setText(new SimpleDateFormat("dd MMM HH:mm").format(new java.sql.Date(note.getTime().getTime())));
        return convertView;
    }
    public void fetchNotes(long userId) {
        Call<List<Note>> call = ApiProvider.getInstance().getApi().getNotesByUserId(userId);
        call.enqueue(new Callback<List<Note>>() {
            @Override
            public void onResponse(Call<List<Note>> call, Response<List<Note>> response) {
                if(notes.size()!=0)
                    notes.clear();
                if(response.body()!=null)
                    for(Note note : response.body()){
                        notes.add(note);
                    }
                progressBar.setVisibility(View.GONE);
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Note>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }
}