package com.justremember.justremember.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final EditText noteNameEditText = (EditText) findViewById(R.id.noteNameEditText);
        final EditText noteEditText = (EditText) findViewById(R.id.noteEditText);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Note note = new Note(0,null,noteNameEditText.getText().toString(), noteEditText.getText().toString(), 0);
                Call<Void> call = ApiProvider.getInstance().getApi().postNote(1,note);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        NoteActivity.this.finish();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e(TAG, t.getMessage(),t);
                    }
                });
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
