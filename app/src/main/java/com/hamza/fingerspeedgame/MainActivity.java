package com.hamza.fingerspeedgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView timerTextView;    // its a text view where timer value shows
    private TextView aThousandTextView; // its a text view where counting shows
    private Button tapTapBtn;   // its a button
    private CountDownTimer mCountDownTimer;// mCoundownTimer variable of type CountDownTimer for creating an object
    private long initialCountDownValue;// initial countdown value is 60000mseconds(60seconds)
    private int timerInterval;  // its a interval of 1000miliseconds(i.e 1 second)
    private long remainingTime; // its a time that shows on Countdown timer in seconds after converted
    private int tapCounter = 200; // counting start from 200 initially decrement after pressing button


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = (TextView) findViewById(R.id.txtTimer);//get timer text view using id
        aThousandTextView = (TextView) findViewById(R.id.txtAThousand);//get thousand text view using id
        tapTapBtn = (Button) findViewById(R.id.btnTap);//get button from xml  using id
        initialCountDownValue = 60000l;    // set value
        aThousandTextView.setText(tapCounter + ""); // set text
        timerInterval = 1000;
        remainingTime = 60l;

        tapTapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tapCounter--;   // decrement value on each click
                aThousandTextView.setText(Integer.toString(tapCounter));
                if (tapCounter <= 0 && remainingTime > 0) { //condition
//                    Toast.makeText(MainActivity.this, "You Won!", Toast.LENGTH_SHORT).show();
                    showAlert("You Won!", " Would you like to play again?");
                    mCountDownTimer.cancel();   // cancel timer for starting again after click on alert
                }
            }
        });

        mCountDownTimer = new CountDownTimer(initialCountDownValue, timerInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime = millisUntilFinished / 1000;     // 60000/1000 = 6000(60seconds)
                timerTextView.setText(Long.toString(remainingTime));      //set value 60 seconds
            }

            @Override
            public void onFinish() {        // when countdown timer is finished
//                Toast.makeText(MainActivity.this, "Countdown Finished!", Toast.LENGTH_SHORT).show();
                showAlert("You Lost!", "You lost from " + tapCounter + " clicks..\nWould you like to try again?"); // from showAlert method

            }
        };
        mCountDownTimer.start();        // start timer
    }

    private void resetGame() {  // reset game after clicking on alert dialog
        tapCounter = 200;
        aThousandTextView.setText(tapCounter + "");
        remainingTime = 60;
        mCountDownTimer.start();


    }

    private void showAlert(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                .setCancelable(false)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                                    finish();

                        resetGame();    // reseting the fame
                    }
                }).show(); // show dialog

    }

}