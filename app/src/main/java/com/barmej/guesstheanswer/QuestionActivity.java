package com.barmej.guesstheanswer;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class QuestionActivity extends AppCompatActivity {
    private static final boolean[] ANSWERS = {
            false,
            true,
            true,
            false,
            true,
            false,
            false,
            false,
            false,
            true,
            true,
            false,
            true
    };
    private TextView mTextViewQuestion;
    /* private static final String[] QUESTIONS = {
             "العملة الرسمية لدولة الكويت هي الريال الكويتي؟",
             "توبقال هي أعلى قمة جبلية في العالم العربي؟",
             "الجزائر هي أكبر دولة عربية من حيث المساحة؟",
             "الدار البيضاء هي عاصمة المغرب؟",
             "كابول هى عاصمة افغانستان؟",
             "اضخم الحيوانات اللافقرية هو القنديل؟",
             "الدولة العربية التي يمر بها خط الاستواء هى السودان؟",
             "القلب هو أكبر عضو في جسم الإنسان؟",
             "أول مسجد في الإسلام هو المسجد النبوي؟",
             "الخال الوحيد لأولاد عمتك هو والدك؟",
             "اولى دول العالم انتاجا للموز هى الاكوادور؟",
             "الأرجنتين عاصمتها باكو؟",
             "عملة فيتنام هى دونج؟",
     };*/
    /* we will write three array one for question , second for right or wrong answer boolean , three for display right sentense */
    private String[] QUESTIONS;
    private String[] ANSWERS_DETAILS;

   /* private static final String[] ANSWERS_DETAILS = {
            "العملة الرسمية لدولة الكويت هي الدينار الكويتي",
            "توبقال هي أعلى قمة جبلية في العالم العربي و تقع في المغرب",
            "الجزائر هي أكبر دولة عربية و إفريقية من حيث المساحة",
            "الرباط هي عاصمة المغرب",
            "كابول هى عاصمة افغانستان",
            "اضخم الحيوانات اللافقرية هو الحبار",
            "الدولة العربية التي يمر بها خط الاستواء هى الصومال",
            "الكبد هو أكبر عضو في جسم الإنسان",
            "أول مسجد في الإسلام هو مسجد قباء",
            "الخال الوحيد لأولاد عمتك هو والدك",
            "اولى دول العالم انتاجا للموز هى الاكوادور",
            "الأرجنتين عاصمتها بونس إيرس",
            "عملة فيتنام هى دونج"
    };*/

    private String mCurrentQuestion, mCurrentdetail;
    private Boolean mCurrentAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences(constans.APP_PREF,MODE_PRIVATE);
        String applang = sharedPreferences.getString(constans.APP_LANG,"");
        if(!applang.equals(""))
            LocaleHelper.setLocale(this,applang);


        setContentView(R.layout.activity_main);
        mTextViewQuestion = findViewById((R.id.text_view_question));
        /*   mTextViewQuestion.setText(); + the under rows we do not need if the item write in array but now we write in value string array )*/
        QUESTIONS = getResources().getStringArray(R.array.questions);
        ANSWERS_DETAILS = getResources().getStringArray(R.array.answers_details);
        showNewQuestion();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuChangeLang) {
            showLanguageDailog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

 private void showLanguageDailog (){
     AlertDialog alertDialog = new AlertDialog.Builder(this)
             .setTitle(R.string.change_Lang_text)
             .setItems(R.array.language,new DialogInterface.OnClickListener(){
                 public void onClick(DialogInterface dialogeInterface , int which) {
                     String language = "ar";
                     switch (which) {
                         case 0:
                             language = "ar";
                             break;
                         case 1:
                             language = "en";
                             break;
                         case 2:
                             language = "fr";
                             break;
                     }
                     saveLanguage (language);
                     LocaleHelper.setLocale(QuestionActivity.this, language);
                     Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                     intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                     intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     startActivity(intent);
                 }

             }).create();
    alertDialog.show();
 }

 private void saveLanguage (String lang){
     SharedPreferences sharedPreferences = getSharedPreferences("app_pref",MODE_PRIVATE);
     SharedPreferences.Editor editor = sharedPreferences.edit();
     editor.putString("app_lang",lang);
     editor.apply();
 }


    private void showNewQuestion() {
        Random random = new Random();
        int randomQuestionIndix = random.nextInt(QUESTIONS.length);
        mCurrentQuestion = QUESTIONS[randomQuestionIndix];
        mCurrentAnswer = ANSWERS[randomQuestionIndix];
        mCurrentdetail = ANSWERS_DETAILS[randomQuestionIndix];
        mTextViewQuestion.setText(mCurrentQuestion);
    }

    public void onChangeQuestionClicked(View view) {

        showNewQuestion();
    }

    public void onTrueClicked(View view) {
        if ((mCurrentAnswer == true)) {
            Toast.makeText(this, "أحسنت إجابة صحيحة ", Toast.LENGTH_SHORT).show();
            showNewQuestion();
        } else {
            Toast.makeText(this, "للاسف إجابة خاطئة ", Toast.LENGTH_SHORT).show();
            Intent intent;
            intent = new Intent(QuestionActivity.this ,AnswerActivity.class);
            intent.putExtra("question_answer",mCurrentdetail);
            startActivity(intent);
        }
    }

    public void onfalseeClicked(View view) {
        if (mCurrentAnswer == false) {
            Toast.makeText(this, "أحسنت إجابة صحيحة ", Toast.LENGTH_SHORT).show();
            showNewQuestion();
        } else {
            Toast.makeText(this, "للاسف إجابة خاطئة  ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(QuestionActivity.this ,AnswerActivity.class);
            intent.putExtra("question_answer",mCurrentdetail);
            startActivity(intent);
        }
    }

    public void onShareClicked (View view){
        Intent intent = new Intent(QuestionActivity.this ,ShareActivity.class);
        intent.putExtra("question_text_extra",mCurrentQuestion);
        startActivity(intent);
    }
}