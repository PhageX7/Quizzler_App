package com.londonappbrewery.quizzler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends Activity {

    // TODO: Declare member variables here:
    Button mTrueButton;
    Button mFalseButton;
    TextView mQuestion_TextView;
    TextView mScore_TextView;
    TrueFalse mCurrentQuestion;
    ProgressBar mProgressBar;
    int mQuestion;
    int mIndex;
    int mScore;

    // TODO: Uncomment to create question bank
    private TrueFalse[] mQuestionBank = new TrueFalse[] {
            new TrueFalse(R.string.question_1, true),
            new TrueFalse(R.string.question_2, true),
            new TrueFalse(R.string.question_3, true),
            new TrueFalse(R.string.question_4, true),
            new TrueFalse(R.string.question_5, true),
            new TrueFalse(R.string.question_6, false),
            new TrueFalse(R.string.question_7, true),
            new TrueFalse(R.string.question_8, false),
            new TrueFalse(R.string.question_9, true),
            new TrueFalse(R.string.question_10, true),
            new TrueFalse(R.string.question_11, false),
            new TrueFalse(R.string.question_12, false),
            new TrueFalse(R.string.question_13,true)
    };

    // TODO: Declare constants here
    final int NUM_QUESTIONS_DATA = mQuestionBank.length;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mQuestion_TextView = findViewById(R.id.question_text_view);
        mProgressBar = findViewById(R.id.progress_bar);
        mScore_TextView = findViewById(R.id.score);

        mCurrentQuestion = mQuestionBank[mIndex];
        mQuestion = (mCurrentQuestion.getQuestionID());
        mQuestion_TextView.setText(mQuestion);

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Quizzler", "True button was clicked");
                checkAnswer(true);
                nextQuestion();
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Quizzler", "False button was clicked");
                checkAnswer(false);
                nextQuestion();
            }
        });

    }

    private void nextQuestion() {
        mIndex = (mIndex + 1) % mQuestionBank.length;

        if (mIndex == 0) {

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("GAME OVER!");
            alert.setCancelable(false);
            alert.setMessage("You have scored " + mScore + " points!");
            alert.setPositiveButton("Close Application", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    finish();
                }
            });

            alert.show();
        }

        mQuestion = mQuestionBank[mIndex].getQuestionID();
        mQuestion_TextView.setText(mQuestion);
    }

    private void checkAnswer(boolean userSelection) {

        mCurrentQuestion = mQuestionBank[mIndex];
        boolean correctAnswer = mCurrentQuestion.isAnswer();

        if (userSelection == correctAnswer) {

            mScore++;
            mScore_TextView.setText("Score " +
                    mScore + "/" + NUM_QUESTIONS_DATA);
            mProgressBar.incrementProgressBy((int)(Math.ceil(100.0/ NUM_QUESTIONS_DATA)));


            Toast.makeText(getApplicationContext(),
                    R.string.correct_toast,
                    Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(getApplicationContext(),
                    R.string.incorrect_toast,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
