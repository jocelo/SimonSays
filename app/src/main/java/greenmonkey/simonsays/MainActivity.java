package greenmonkey.simonsays;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends ActionBarActivity {
    final String H_SCORES = "SS_HS";
    final String H_SCORES_TOKEN = "top_scores";
    final String TAG = "SIMON_SAYS";
    SharedPreferences game_settings;
    static Integer topScore;
    static long timeScore;
    static long timeScoreFinal;
    static int DEMO_SPEED;
    static int GAME_SPEED;
    static boolean VIBRATE;
    Vibrator vib;
    View buttonUL, buttonUR, buttonDL, buttonDR;
    TextView levelLabel,timeLabel;
    float[] buttonULColor = new float[3],
        buttonURColor = new float[3],
        buttonDLColor = new float[3],
        buttonDRColor = new float[3];
    List<Integer> tokens = new ArrayList<Integer>();
    Random generator = new Random();
    int position = 0;
    boolean foco = true;
    int token_id = -1;
    int step = 0;
    List<View> buttons = new ArrayList<View>();
    ArrayList<ArrayList<Float>> colors = new ArrayList<ArrayList<Float>>();
    ArrayList<Float> colorsDetails = new ArrayList<Float>();
    Timer updateTimer;

    // TODO: add timer to stop executing when not pressed anything
    // after 2 seconds
    // http://stackoverflow.com/questions/24991572/finish-activity-after-timeout-if-user-dont-touch-screen

    // TODO: add sounds

    // done: add leaderboards

    // TODO: remove multitouch

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpInterface();
        playMode();
        //initiateGame();
        //bindLayoutsEvents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id) {
            case R.id.action_settings:
                startActivity(new Intent( getApplicationContext(), settingsActivity.class));
                return true;
            case R.id.new_game:
                startNewGame();
                return true;
            case R.id.leaderboards:
                showLeaderBoards();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void bindLayoutsEvents() {
        buttonUL.setOnTouchListener(new View.OnTouchListener() {
            float temp;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    temp = buttonULColor[2];
                    buttonULColor[2] = 1.0f;
                    buttonUL.setBackgroundColor(Color.HSVToColor(buttonULColor));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    buttonULColor[2] = temp;
                    buttonUL.setBackgroundColor( Color.HSVToColor(buttonULColor) );
                    checkTurn(0);
                }
                return true;
            }
        });
        buttonUR.setOnTouchListener(new View.OnTouchListener() {
            float temp;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    temp = buttonURColor[2];
                    buttonURColor[2] = 1.0f;
                    buttonUR.setBackgroundColor(Color.HSVToColor(buttonURColor));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    buttonURColor[2] = temp;
                    buttonUR.setBackgroundColor( Color.HSVToColor(buttonURColor) );
                    checkTurn(1);
                }
                return true;
            }
        });
        buttonDL.setOnTouchListener(new View.OnTouchListener() {
            float temp;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    temp = buttonDLColor[2];
                    buttonDLColor[2] = 1.0f;
                    buttonDL.setBackgroundColor(Color.HSVToColor(buttonDLColor));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    buttonDLColor[2] = temp;
                    buttonDL.setBackgroundColor( Color.HSVToColor(buttonDLColor) );
                    checkTurn(2);
                }
                return true;
            }
        });
        buttonDR.setOnTouchListener(new View.OnTouchListener() {
            float temp;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    temp = buttonDRColor[2];
                    buttonDRColor[2] = 1.0f;
                    buttonDR.setBackgroundColor(Color.HSVToColor(buttonDRColor));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    buttonDRColor[2] = temp;
                    buttonDR.setBackgroundColor( Color.HSVToColor(buttonDRColor) );
                    checkTurn(3);
                }
                return true;
            }
        });
    }

    public void unbindLayoutsEvents() {
        buttonUL.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        buttonUR.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        buttonDL.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        buttonDR.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    public void playMode() {
        buttonUL.setOnTouchListener(new View.OnTouchListener() {
            float temp;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    temp = buttonULColor[2];
                    buttonULColor[2] = 1.0f;
                    buttonUL.setBackgroundColor(Color.HSVToColor(buttonULColor));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    buttonULColor[2] = temp;
                    buttonUL.setBackgroundColor( Color.HSVToColor(buttonULColor) );
                }
                return true;
            }
        });
        buttonUR.setOnTouchListener(new View.OnTouchListener() {
            float temp;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    temp = buttonURColor[2];
                    buttonURColor[2] = 1.0f;
                    buttonUR.setBackgroundColor(Color.HSVToColor(buttonURColor));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    buttonURColor[2] = temp;
                    buttonUR.setBackgroundColor( Color.HSVToColor(buttonURColor) );
                }
                return true;
            }
        });
        buttonDL.setOnTouchListener(new View.OnTouchListener() {
            float temp;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    temp = buttonDLColor[2];
                    buttonDLColor[2] = 1.0f;
                    buttonDL.setBackgroundColor(Color.HSVToColor(buttonDLColor));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    buttonDLColor[2] = temp;
                    buttonDL.setBackgroundColor( Color.HSVToColor(buttonDLColor) );
                }
                return true;
            }
        });
        buttonDR.setOnTouchListener(new View.OnTouchListener() {
            float temp;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    temp = buttonDRColor[2];
                    buttonDRColor[2] = 1.0f;
                    buttonDR.setBackgroundColor(Color.HSVToColor(buttonDRColor));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    buttonDRColor[2] = temp;
                    buttonDR.setBackgroundColor( Color.HSVToColor(buttonDRColor) );
                }
                return true;
            }
        });
    }

    public void startNewGame() {
        tokens = new ArrayList<Integer>();
        DialogFragment new_game = new NewGameDialogFragment();
        new_game.show(getFragmentManager(), "new_game");
    }

    public void showLeaderBoards() {
        Intent intent = new Intent(this, leaderboardsActivity.class);
        startActivity(intent);
    }

    public void setUpInterface() {
        levelLabel = (TextView) findViewById(R.id.level_label);
        timeLabel = (TextView) findViewById(R.id.time_label);
        buttonUL = (View) findViewById(R.id.up_left);
        buttonUR = (View) findViewById(R.id.up_right);
        buttonDL = (View) findViewById(R.id.down_left);
        buttonDR = (View) findViewById(R.id.down_right);
        buttons.add(buttonUL);
        buttons.add(buttonUR);
        buttons.add(buttonDL);
        buttons.add(buttonDR);

        Color.RGBToHSV(
            Color.red(Color.parseColor(buttonUL.getTag().toString())),
            Color.green(Color.parseColor(buttonUL.getTag().toString())),
            Color.blue(Color.parseColor(buttonUL.getTag().toString())), buttonULColor);
        Color.RGBToHSV(
            Color.red(Color.parseColor( buttonUR.getTag().toString() )),
            Color.green(Color.parseColor( buttonUR.getTag().toString() )),
            Color.blue(Color.parseColor( buttonUR.getTag().toString() )), buttonURColor);
        Color.RGBToHSV(
            Color.red(Color.parseColor( buttonDL.getTag().toString() )),
            Color.green(Color.parseColor( buttonDL.getTag().toString() )),
            Color.blue(Color.parseColor( buttonDL.getTag().toString() )), buttonDLColor);
        Color.RGBToHSV(
            Color.red(Color.parseColor( buttonDR.getTag().toString() )),
            Color.green(Color.parseColor( buttonDR.getTag().toString() )),
            Color.blue(Color.parseColor( buttonDR.getTag().toString() )), buttonDRColor);

        colorsDetails = new ArrayList<Float>();
        colorsDetails.add(buttonULColor[0]);
        colorsDetails.add(buttonULColor[1]);
        colorsDetails.add(buttonULColor[2]);
        colors.add(colorsDetails);

        colorsDetails = new ArrayList<Float>();
        colorsDetails.add(buttonURColor[0]);
        colorsDetails.add(buttonURColor[1]);
        colorsDetails.add(buttonURColor[2]);
        colors.add(colorsDetails);

        colorsDetails = new ArrayList<Float>();
        colorsDetails.add(buttonDLColor[0]);
        colorsDetails.add(buttonDLColor[1]);
        colorsDetails.add(buttonDLColor[2]);
        colors.add(colorsDetails);

        colorsDetails = new ArrayList<Float>();
        colorsDetails.add(buttonDRColor[0]);
        colorsDetails.add(buttonDRColor[1]);
        colorsDetails.add(buttonDRColor[2]);
        colors.add(colorsDetails);

        game_settings = getApplicationContext().getSharedPreferences("SS_SH",Context.MODE_PRIVATE);
        vib = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        unbindLayoutsEvents();
    }

    public void startVariables() {
        // clean variables
        DEMO_SPEED = game_settings.getInt("demo_speed",700);
        GAME_SPEED = game_settings.getInt("game_speed", 1000);
        VIBRATE = game_settings.getBoolean("vibrate", true);
        foco = true;
        position = 0;
        token_id = -1;
        step = 0;
    }

    public boolean initiateGame() {
        tokens = new ArrayList<Integer>();

        startVariables();
        insertNewToken();
        playDemo();
        return true;
    }

    public void runDemoHandler() {
        step++;
        float[] color = new float[3];
        Integer tope = tokens.size()*2;
        if (foco) {
            color[2] = 1.0f;
            foco = false;
            token_id++;
        } else {
            color[2] = 0.6f;
            foco = true;
        }
        Integer tok = tokens.get(token_id);

        color[0] = colors.get(tok).get(0);
        color[1] = colors.get(tok).get(1);
        buttons.get(tok).setBackgroundColor(Color.HSVToColor(color));
        if (step == tope) {

            updateTimer.cancel();
            Toast.makeText(getApplicationContext(), "Go now!!", Toast.LENGTH_SHORT).show();
            bindLayoutsEvents();
            if (VIBRATE) { vib.vibrate(50); }
            timeScore = System.currentTimeMillis();
            return;
        }
    }

    public void checkTurn(int squarePressed) {
        if (squarePressed == tokens.get(position)) {
            position += 1;
            validateNewToken();
        } else {
            // TODO: Save high score
            saveHighScore(this, topScore-1, timeScoreFinal);
            Toast.makeText(getApplicationContext(), "Game Over!!!", Toast.LENGTH_SHORT).show();
            DialogFragment new_game = new GameOverDialogFragment();
            new_game.show(getFragmentManager(), "game_over");
        }
        timeLabel.setText("0099");
    }

    public void insertNewToken() {
        Integer lastToken, newToken;
        if (tokens.size() == 0) {
            lastToken = 0;
        } else {
            lastToken = tokens.get(tokens.size()-1);
        }
        newToken = lastToken;

        while (lastToken==newToken) {
            newToken = generator.nextInt(4);
        }
        tokens.add(newToken);
    }

    public void validateNewToken() {
        if (position==tokens.size()) {
            timeScoreFinal = System.currentTimeMillis()-timeScore;
            topScore = tokens.size();
            // Raising the level
            levelLabel.setText("Level "+topScore.toString());
            //Toast.makeText(getApplicationContext(), "Level "+topScore.toString(), Toast.LENGTH_SHORT).show();
            insertNewToken();
            startVariables();
            playDemo();
        }
    }

    public void playDemo() {
        unbindLayoutsEvents();
        updateTimer = new Timer();
        updateTimer.schedule(new DemoTask(new Handler(),this),1000,DEMO_SPEED);
    }

    public void saveHighScore(Context context, Integer level, Long score ) {
        SharedPreferences shpScores;
        SharedPreferences.Editor shpEditor;
        String[] highScores = new String[10];
        String[] currentScore = new String[2];
        Long longScore;
        String finalScores="";
        String stringScores;

        shpScores = context.getSharedPreferences("SS_SH", Context.MODE_PRIVATE);
        stringScores = shpScores.getString("top_scores","0,0|0,0|0,0|0,0|0,0|0,0|0,0|0,0|0,0|0,0");

        highScores = stringScores.split("\\|");

        for (int i=0 ; i<highScores.length ; i++) {
            currentScore = highScores[i].split(",");
            // if high score in level achieved
            if ( level > Integer.parseInt(currentScore[0]) ) {
                highScores[i] = level.toString()+","+Long.toString(score);
                level = Integer.parseInt(currentScore[0]);
                score = Long.parseLong(currentScore[1]);
            }

            if ( level == Integer.parseInt(currentScore[0]) && score<Long.parseLong(currentScore[1]) ) {
                highScores[i] = level.toString()+","+Long.toString(score);
                level = Integer.parseInt(currentScore[0]);
                score = Long.parseLong(currentScore[1]);
            }

            finalScores += highScores[i]+(i<9 ? "|" : "");
        }

        shpEditor = shpScores.edit();
        shpEditor.putString("top_scores",finalScores);
        shpEditor.commit();
    }

    private class DemoTask extends TimerTask {
        Handler handler;
        MainActivity ref;

        public DemoTask(Handler handler, MainActivity ref) {
            super();
            this.handler = handler;
            this.ref = ref;
        }

        @Override
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    runDemoHandler();
                }
            });
        }
    }

    public static class NewGameDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstance) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.confirm_new_game)
                    .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ((MainActivity)getActivity()).initiateGame();
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // cancel the new game
                        }
                    });

            return builder.create();
        }
    }

    public static class GameOverDialogFragment extends DialogFragment {
        String scores;
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();

            final View inflator = inflater.inflate(R.layout.game_over, null);
            TextView textSelector;
            String finalScore;
            finalScore = "Level: "+Integer.toString(topScore - 1);
            finalScore += "\nTime: "+Long.toString(timeScoreFinal);
            textSelector = (TextView) inflator.findViewById(R.id.scoring);
            ((MainActivity) getActivity()).playMode();

            builder.setView(inflator)
                    .setTitle(R.string.game_over)
                    .setPositiveButton(R.string.new_game, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            ((MainActivity) getActivity()).initiateGame();
                        }
                    })
                    .setNegativeButton(R.string.leaderboards, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            ((MainActivity) getActivity()).showLeaderBoards();
                        }
                    });
            textSelector.setText(finalScore);
            return builder.create();
        }
    }
}
