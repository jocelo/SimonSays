package greenmonkey.simonsays;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class leaderboardsActivity extends ActionBarActivity {
    LinearLayout scoresTable, scoresRow;
    String[] scoresArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboards);
        scoresArray = new String[10];
        scoresArray = getHighScores(this);

        scoresRow = new LinearLayout(this);
        scoresTable = (LinearLayout) findViewById(R.id.scores_table);
        TextView _place, _level, _score;

        scoresRow.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);

        _place = new TextView(this);
        _place.setText("Place");
        _place.setTypeface(Typeface.DEFAULT_BOLD);
        _place.setGravity(Gravity.CENTER);
        _place.setLayoutParams(lparams);

        _level = new TextView(this);
        _level.setText("Level");
        _level.setTypeface(Typeface.DEFAULT_BOLD);
        _level.setGravity(Gravity.CENTER);
        _level.setLayoutParams(lparams);

        _score = new TextView(this);
        _score.setText("Score");
        _score.setTypeface(Typeface.DEFAULT_BOLD);
        _score.setGravity(Gravity.CENTER);
        _score.setLayoutParams(lparams);

        scoresRow.addView(_place);
        scoresRow.addView(_level);
        scoresRow.addView(_score);
        scoresTable.addView(scoresRow,0);

        for (int i=0 ; i<scoresArray.length ; i++) {
            String[] score = new String[2];
            score = scoresArray[i].split(",");
            scoresRow = new LinearLayout(this);

            _place = new TextView(this);
            _place.setText(Integer.toString(i + 1));
            _place.setGravity(Gravity.CENTER);
            _place.setLayoutParams(lparams);
            _level = new TextView(this);
            _level.setText(score[0]);
            _level.setGravity(Gravity.CENTER);
            _level.setLayoutParams(lparams);
            _score = new TextView(this);
            _score.setText(score[1]);
            _score.setGravity(Gravity.CENTER);
            _score.setLayoutParams(lparams);

            scoresRow.addView(_place);
            scoresRow.addView(_level);
            scoresRow.addView(_score);
            scoresTable.addView(scoresRow);
        }
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

    public void removeValue(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences("SS_SH", Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.remove("top_scores");
        editor.commit();
    }
}
