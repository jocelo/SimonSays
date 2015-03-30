package greenmonkey.simonsays;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;


public class settingsActivity extends ActionBarActivity {
    Button saveBtn, cancelBtn;
    SeekBar demoSpeedBar, gameSpeedBar;
    Switch vibrationMode;
    SharedPreferences game_settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        demoSpeedBar = (SeekBar) findViewById(R.id.opt_demo_speed);
        gameSpeedBar = (SeekBar) findViewById(R.id.opt_game_speed);
        vibrationMode = (Switch) findViewById(R.id.opt_vibration);

        bindEvents();
        readStoredSettings();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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

    private void bindEvents() {
        saveBtn = (Button) findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
            }
        });

        cancelBtn = (Button) findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void readStoredSettings() {
        float demoSpeedF;
        float gameSpeedF;
        Integer demoSpeed;
        Integer gameSpeed;
        Boolean vibe;

        game_settings = getApplicationContext().getSharedPreferences("SS_SH", Context.MODE_PRIVATE);

        demoSpeed = game_settings.getInt("demo_speed",700);
        gameSpeed = game_settings.getInt("game_speed",1000);
        vibe = game_settings.getBoolean("vibrate", true);

        demoSpeed = demoSpeed-100;
        gameSpeed = gameSpeed-250;

        demoSpeedF = demoSpeed/900.0f*100.0f;
        gameSpeedF = gameSpeed/1250.0f*100.0f;

        demoSpeed = 100-(int)demoSpeedF;
        gameSpeed = 100-(int)gameSpeedF;

        demoSpeedBar.setProgress(demoSpeed);
        gameSpeedBar.setProgress(gameSpeed);
        vibrationMode.setChecked(vibe);
    }

    private void saveSettings() {
        SharedPreferences.Editor settingsEditor;
        int demoSpeed = 0;
        int gameSpeed = 0;
        float demoSpeedD;
        float gameSpeedD;
        boolean vibe;

        demoSpeedD = (100-demoSpeedBar.getProgress())/100.0f * 900;
        gameSpeedD = (100-gameSpeedBar.getProgress())/100.0f * 1250;
        vibe = vibrationMode.isChecked();
        demoSpeed = (int)demoSpeedD+100;
        gameSpeed = (int)gameSpeedD+250;

        game_settings = getApplicationContext().getSharedPreferences("SS_SH", Context.MODE_PRIVATE);
        settingsEditor = game_settings.edit();
        settingsEditor.putBoolean("vibrate",vibe);
        settingsEditor.putInt("demo_speed",demoSpeed);
        settingsEditor.putInt("game_speed",gameSpeed);
        settingsEditor.commit();
        finish();
    }
}
