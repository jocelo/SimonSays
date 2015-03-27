package greenmonkey.simonsays;

import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class leaderboardsActivity extends ActionBarActivity {
    TableLayout scoresTable;
    String HighScores;
    String[] scoresArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboards);
        scoresArray = new String[10];
        scoresArray = getHighScores(this);

        scoresTable = (TableLayout) findViewById(R.id.scores_table);

        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        TableRow tableRow;
        TextView _place, _level, _score;
        View _line;

        tableRow = new TableRow(this);
        _place = new TextView(this);
        _level = new TextView(this);
        _score = new TextView(this);

        _place.setText("Place");
        _level.setText("Level");
        _score.setText("Score");

        tableRow.addView(_place);
        tableRow.addView(_level);
        tableRow.addView(_score);

        scoresTable.addView(tableRow);

        for (int i=0 ; i<scoresArray.length ; i++) {
            String[] score = new String[2];
            score = scoresArray[i].split(",");
            Log.d("LOG",Integer.toString(i)+" -> ["+score[0]+":"+score[1]+"]");

            tableRow = new TableRow(this);
            _place = new TextView(this);
            _level = new TextView(this);
            _score = new TextView(this);

            _place.setText(Integer.toString(i+1));
            _level.setText(score[0]);
            _score.setText(score[1]);

            tableRow.addView(_place);
            tableRow.addView(_level);
            tableRow.addView(_score);

            scoresTable.addView(tableRow);
        }

//        for (int i=0 ; i<50 ; i++) {
//            tableRow = new TableRow(this);
//            _place = new TextView(this);
//            _level = new TextView(this);
//            _score = new TextView(this);
//
//            _place.setText(Integer.toString(i));
//            _level.setText("7");
//            _score.setText("19182");
//
//            tableRow.addView(_place);
//            tableRow.addView(_level);
//            tableRow.addView(_score);
//
//            scoresTable.addView(tableRow);
//        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_leaderboards, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String[] getHighScores(Context context) {
        String scores;
        SharedPreferences settings;

        settings = context.getSharedPreferences("SS_SH",Context.MODE_PRIVATE);
        scores = settings.getString("top_scores","0,0|0,0|0,0|0,0|0,0|0,0|0,0|0,0|0,0|0,0");

        return scores.split("\\|");
    }
}
