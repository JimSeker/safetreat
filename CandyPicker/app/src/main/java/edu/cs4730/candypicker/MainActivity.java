package edu.cs4730.candypicker;

import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.EditText;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.OnBackPressedCallback;

import edu.cs4730.candypicker.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity implements PickerFragment.OnFragmentInteractionListener, MatchFragment.OnFragmentInteractionListener, OnInitListener {

    int buckets = 4;  //default value?  not sure what that should be.
    Random myRandom = new Random();
    private static final String TAG = "MainActivity";
    private TextToSpeech mTts;
    private boolean CanSpeak = false, WantSpeak = true;
    final private String myUtteranceId = "txt2spk";
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(binding.container.getId(), new PickerFragment()).commit();
        }

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Back is pressed... Finishing the activity
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.alert_dialog_two_buttons_title);
                builder.setPositiveButton(R.string.alert_dialog_ok, (dialog, whichButton) -> finish());
                builder.setNegativeButton(R.string.alert_dialog_cancel, (dialog, whichButton) -> dialog.dismiss());
                builder.show();
            }
        });


        //using the new startActivityForResult method.
        ActivityResultLauncher<Intent> myActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                    // TTS is up and running
                    mTts = new TextToSpeech(getApplicationContext(), MainActivity.this);
                    Log.v(TAG, "Pico is installed okay");
                } else Log.e(TAG, "Got a failure. TTS apparently not available");
            });
        // Check to be sure that TTS exists and is okay to use
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        myActivityResultLauncher.launch(checkIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            //show a dialog box to ask for the number.
            LayoutInflater inflater = LayoutInflater.from(this);
            View view = inflater.inflate(R.layout.fragment_number, null);
            final EditText mEditText = view.findViewById(R.id.txt_number);
            mEditText.requestFocus();
            final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.Theme_AppCompat));
            builder.setView(view).setTitle("How many bins?");
            builder.setPositiveButton("Set", (dialog, id2) -> {
                setNumber(Integer.parseInt(mEditText.getText().toString()));
                dialog.dismiss();
            }).setNegativeButton("Cancel", (dialog, id1) -> dialog.cancel());
            // Show soft keyboard automatically (except won't work in emulators correctly!!!, when physical keyboard input set. Also no done... )

            AlertDialog dialog = builder.create();
            dialog.getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            dialog.show();
            return true;
        } else if (id == R.id.Picker) {
            onFragmentInteraction(1);
            return true;
        } else if (id == R.id.matcher) {
            //Log.V("MainActivity", "menu called, should switch...");
            onFragmentInteraction(2);
            return true;
        } else if (id == R.id.Sound) {
            WantSpeak = !WantSpeak;
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onInit(int status) {
        // Now that the TTS engine is ready. We can read aloud and enable the button
        if (status == TextToSpeech.SUCCESS) {
            CanSpeak = true;
        }
    }

    @Override
    public void onFragmentInteraction(int which) {
        if (which == 1) { //picker
            getSupportFragmentManager().beginTransaction().replace(binding.container.getId(), new PickerFragment()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(binding.container.getId(), new MatchFragment()).commit();
        }
    }

    @Override
    public int GetNumber() {
        return (myRandom.nextInt(buckets) + 1);
    }

    @Override
    public void setNumber(int num) {
        buckets = num;
    }

    @Override
    public void speakNum(int num) {
        if (!CanSpeak || !WantSpeak) return;
        String stringNum = String.valueOf(num);
        mTts.speak(stringNum, TextToSpeech.QUEUE_ADD, null, myUtteranceId);
    }

}
