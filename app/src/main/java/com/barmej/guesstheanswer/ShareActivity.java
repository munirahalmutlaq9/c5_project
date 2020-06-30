package com.barmej.guesstheanswer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ShareActivity extends AppCompatActivity {
    private String mQuestionText;
    public EditText mEditTextShareTiltle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        mEditTextShareTiltle = findViewById(R.id.editText_share_title);
        mQuestionText= getIntent().getStringExtra("question_text_extra");

        SharedPreferences sharedPreferences = getSharedPreferences("app_pref", MODE_PRIVATE);
        String mQuestionTitle = sharedPreferences.getString("share_title", "");
        mEditTextShareTiltle.setText(mQuestionTitle);


    }
    public void onShareQuestionClicked(View view)
    {
        String mQuestionTitle = mEditTextShareTiltle.getText().toString();
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT,mQuestionTitle+ "\n" + mQuestionText);
        shareIntent.setType("text/plain");
        startActivity(shareIntent);

        SharedPreferences sharedPreferences = getSharedPreferences("app_pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("share_title", mQuestionTitle);
        editor.apply();

    }}
