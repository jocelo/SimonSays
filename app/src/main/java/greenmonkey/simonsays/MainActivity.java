package greenmonkey.simonsays;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends ActionBarActivity {
    String TAG = "SIMON_SAYS";
    LinearLayout buttonUL = null,
        buttonUR = null,
        buttonDL = null,
        buttonDR = null;
    float[] buttonULColor = new float[3],
        buttonURColor = new float[3],
        buttonDLColor = new float[3],
        buttonDRColor = new float[3];
    List<Integer> tokens = new ArrayList<Integer>();
    int difficulty = 3;
    Random generator = new Random();
    int position = 0;
    boolean foco = true;
    int token_id = -1;
    int step = 0;
    List<LinearLayout> buttons = new ArrayList<LinearLayout>();
    ArrayList<ArrayList<Float>> colors = new ArrayList<ArrayList<Float>>();
    ArrayList<Float> colorsDetails = new ArrayList<Float>();
    Timer updateTimer;

//    <item name="android:tag">#009930</item>
//    <item name="android:tag">#999900</item>
//    <item name="android:tag">#99000f</item>
//    <item name="android:background">#080594</item>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpInterface();
        //initiateGame();
        //bindLayoutsEvents();
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
                return true;
            case R.id.new_game:
                startNewGame();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void startNewGame() {
        tokens = new ArrayList<Integer>();
        DialogFragment new_game = new NewGameDialogFragment();
        new_game.show(getFragmentManager(), "new_game");
    }

    public void setUpInterface() {
        buttonUL = (LinearLayout) findViewById(R.id.up_left);
        buttonUR = (LinearLayout) findViewById(R.id.up_right);
        buttonDL = (LinearLayout) findViewById(R.id.down_left);
        buttonDR = (LinearLayout) findViewById(R.id.down_right);
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
        unbindLayoutsEvents();
    }

    public void startVariables() {
        // clean variables
        foco = true;
        position = 0;
        token_id = -1;
        step = 0;
        Log.d(TAG,"Variables initialized");
        Log.d(TAG,"+++++++++++++++++++++");
    }

    public boolean initiateGame() {
        startVariables();
        //updateTimer = new Timer();
        for (int i=1; i<= difficulty; i++) {
            insertNewToken();
        }
        Log.d(TAG,tokens.toString());
        playDemo();
        return true;
    }

    public void runDemoHandler() {
        step++;
        float[] color = new float[3];
        Integer tope = tokens.size()*2;
        if (foco) {
            Log.d(TAG,"  * foco prendido");
//            color[0] = buttonULColor[0];
//            color[0] = colors.get(0).get(0);
//            color[1] = buttonULColor[1];
            color[2] = 1.0f;
            foco = false;
            token_id++;
        } else {
            Log.d(TAG,"  - foco apagado");
//            color[0] = buttonULColor[0];
//            color[0] = colors.get(0).get(0);
//            color[1] = buttonULColor[1];
            color[2] = 0.6f;
//            Log.d(TAG, "color 1->"+ Float.toString(color[0]) );
//            Log.d(TAG, "color 2->"+ Float.toString(color[1]) );
//            Log.d(TAG, "color 3->"+ Float.toString(color[2]) );
            foco = true;
        }
        // ToDo: Check this values from the token_id
        //
        Log.d(TAG,"-----------");
        Log.d(TAG,"paso "+step);
        Log.d(TAG,"token id "+token_id);
        Log.d(TAG,"-----------");
        Integer tok = tokens.get(token_id);
        Log.d(TAG,"  -> Valor de token "+tok);

        color[0] = colors.get(tok).get(0);
        color[1] = colors.get(tok).get(1);
        buttons.get(tok).setBackgroundColor(Color.HSVToColor(color));
        //Log.d(TAG,"Color que sera seleccionado: "+color[0]);
//        String tokStr = tokens.get(token_id).toString();
//        Log.d(TAG,"Valor de token "+tok);
        if (step == tope) {
            updateTimer.cancel();
            Toast.makeText(getApplicationContext(), "Go now!!", Toast.LENGTH_SHORT).show();
            bindLayoutsEvents();
            Log.d(TAG,"Will exit immediately");
            return;
        }
    }

    public void checkTurn(int squarePressed) {
        Log.d(TAG,"pressed >"+squarePressed+" it should be a "+tokens.get(position).toString()+" at pos ["+position+"]");
        Log.d(TAG,tokens.toString());
        if (squarePressed == tokens.get(position)) {
            position += 1;
            Log.d(TAG,"YES!!!");
            validateNewToken();
        } else {
            Toast.makeText(getApplicationContext(), "Game Over!!!", Toast.LENGTH_SHORT).show();
            DialogFragment new_game = new NewGameDialogFragment();
            new_game.show(getFragmentManager(), "new_game");
        }
    }

    public void insertNewToken() {
        Integer lastToken, newToken;
        if (tokens.size() == 0) {
            lastToken = 0;
        } else {
            lastToken = tokens.get(tokens.size()-1);
        }
        newToken = lastToken;
        //Log.d(TAG,tokens.toString());
        //Log.d(TAG,"el ultimo insertado fue: "+lastToken);
        //Log.d(TAG,"el supuesto id es: "+tokens.size());

        while (lastToken==newToken) {
            newToken = generator.nextInt(3);
        }
        //Log.d(TAG,"se ba a insertar este nuevo: "+newToken);
        tokens.add(newToken);
    }

    public void validateNewToken() {
        if (position==tokens.size()) {
            // aumentar el nivel
            Toast.makeText(getApplicationContext(), "Next level", Toast.LENGTH_SHORT).show();
            insertNewToken();
            startVariables();
            playDemo();
        }
    }

    public void playDemo() {
        unbindLayoutsEvents();
        updateTimer = new Timer();
        updateTimer.schedule(new DemoTask(new Handler(),this),1000,800);
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
        @Override
        public Dialog onCreateDialog(Bundle savedInstance) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.game_over)
                    .setPositiveButton(R.string.new_game, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ((MainActivity) getActivity()).initiateGame();
                        }
                    });

            return builder.create();
        }
    }
}
