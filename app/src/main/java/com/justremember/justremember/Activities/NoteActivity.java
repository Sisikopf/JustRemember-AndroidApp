package com.justremember.justremember.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.justremember.justremember.Adapters.NoteAdapter;
import com.justremember.justremember.ApiProvider;
import com.justremember.justremember.Entities.Note;
import com.justremember.justremember.R;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoteActivity extends AppCompatActivity {
    private static final String TAG = "NoteActivity";
    public static final String NOTE_ID = "note_id";
    Note note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // toolbar.inflateMenu(R.menu.menu_note);
       // Menu menu = toolbar.getMenu();
       // final MenuItem deleteItem =  menu.findItem(R.id.deleteNote);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()== R.id.deleteNote) {
                    ApiProvider.getInstance().getApi().deleteNote(1,note.getId()).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            NoteActivity.this.finish();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.e(TAG, t.getMessage(),t);
                        }
                    });

                    return true;
                }
                return false;
            }
        });
        Bundle extras = getIntent().getExtras();
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final EditText noteNameEditText = (EditText) findViewById(R.id.noteNameEditText);
        final EditText noteEditText = (EditText) findViewById(R.id.noteEditText);
        if(extras!=null) {
            fab.hide();
            long noteId = extras.getLong(NOTE_ID);
            Call<Note> call = ApiProvider.getInstance().getApi().getNoteById(1, noteId);
            call.enqueue(new Callback<Note>() {
                @Override
                public void onResponse(Call<Note> call, Response<Note> response) {
                    note = response.body();
                    noteNameEditText.setText(note.getName());
                    noteEditText.setText(note.getContent());
                    fab.show();
                   // deleteItem.setVisible(true);
                }

                @Override
                public void onFailure(Call<Note> call, Throwable t) {
                    Log.e(TAG, t.getMessage(), t);
                }
            });
        }



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (note == null) {
                    note = new Note(0, null, noteNameEditText.getText().toString(), noteEditText.getText().toString(), 0);
                    Call<Void> call = ApiProvider.getInstance().getApi().postNote(1, note);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            NoteActivity.this.finish();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.e(TAG, t.getMessage(), t);
                        }
                    });
                } else {
                    note.setName(noteNameEditText.getText().toString());
                    note.setContent(noteEditText.getText().toString());
                    Call<Void> call = ApiProvider.getInstance().getApi().updateNote(1, note.getId(), note);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            NoteActivity.this.finish();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.e(TAG, t.getMessage(), t);
                        }
                    });
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
