package souravapp.numberguessinggame;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class game extends AppCompatActivity {

    EditText Input;
    TextView results,lives,score,range,high_score;
    int xlive,xscore,no,xhighscore;
    Button rules, reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Input =(EditText)findViewById(R.id.input);

        results =(TextView)findViewById(R.id.results);

        lives =(TextView)findViewById(R.id.lives);
        score =(TextView)findViewById(R.id.score);
        range =(TextView)findViewById(R.id.range);
        high_score =(TextView)findViewById(R.id.high_score);
        rules =(Button) findViewById(R.id.rules);
        reset =(Button)findViewById(R.id.reset);


        /*Animate
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_left);
        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_right);
        lives.startAnimation(animation2);
        range.startAnimation(animation);

        score.startAnimation(animation2);
        high_score.startAnimation(animation);

        rules.startAnimation(animation2);
        reset.startAnimation(animation);*/


        rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder= new AlertDialog.Builder(v.getContext());
                builder.setMessage("Rules:\n1. This is a Simple Number Guessing Game.\n2. For Every Correct guess you will get 1 Score.\n3. For Every Incorrect guess you will lose 1 life.\n4. There is a total of 30 lives.\n5. In Results if X is low!!!. Guess Something Greater than X.\n6. In Results if X is high!!!. Guess Something Smaller than X.\n 7. Guess Correctly using less lives and Create new High Scores.");
                builder.setPositiveButton("Got it!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setCancelable(true);
                builder.create();
                builder.show();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_game();
            }
        });

        xlive=30;
        xscore=0;

        SharedPreferences preferences=getSharedPreferences("HSsp", Activity.MODE_PRIVATE);
        xhighscore=preferences.getInt("HS",0);
        high_score.setText("High Score : "+ xhighscore);

        setgame();

        Input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    if(xlive!=0){
                        if(Input.getText().toString().matches("")){
                            Input.setError("You need to enter some Number!");
                        }
                        else {
                            check(no);
                        }
                    }
                    return true;
                }
                return false;
            }
        });

    }

    private void check(int no) {
        int x=Integer.parseInt(Input.getText().toString());
        if(x == no){
            score.setText("Score : "+ ++xscore);
            Toast.makeText(this,"Number Guessed is Correct : "+no,Toast.LENGTH_LONG).show();
            if(xscore>xhighscore) {
                high_score.setText("High Score : " + ++xhighscore);
                Toast toast = Toast.makeText(this,"New High Score!",Toast.LENGTH_LONG);
                toast.setGravity((Gravity.CENTER|Gravity.TOP),0,0);
                toast.show();
                SharedPreferences preferences = getSharedPreferences("HSsp", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("HS", xhighscore);
                editor.commit();
            }
            setgame();
        }
        else if(x > no ){
            results.append("\n"+x+" is high!!!");
            lives.setText("Lives : "+ --xlive);
         }
        else if(x < no ){
            results.append("\n"+x+" is low!!!");
            lives.setText("Lives : "+ --xlive);
        }
        if(xlive==0)
        {
            Input.setEnabled(false);
            Toast.makeText(this,"Zero lives Remaining",Toast.LENGTH_LONG).show();
            new_game();
        }
        Input.setText("");
    }

    private void setgame() {
        no = new Random().nextInt(100);
        results.setText("Results :");
    }

    private void new_game() {
        final AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setMessage("New Game");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Input.setText("");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            recreate();
                        }
                    }
                });
        builder.setCancelable(true);
        builder.create();
        builder.show();
    }
}