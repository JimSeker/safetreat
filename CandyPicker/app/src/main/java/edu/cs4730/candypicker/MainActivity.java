package edu.cs4730.candypicker;

import java.util.Random;

import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.EditText;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;


public class MainActivity extends AppCompatActivity implements PickerFragment.OnFragmentInteractionListener,
        MatchFragment.OnFragmentInteractionListener,
        OnInitListener {

    int buckets = 4;  //default value?  not sure what that should be.

    Random myRandom = new Random();
    private static final int REQ_TTS_STATUS_CHECK = 0;
    private static final String TAG = "MainActivity";
    private TextToSpeech mTts;
    private boolean CanSpeak = false, WantSpeak = true;
    private String myUtteranceId = "txt2spk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PickerFragment()).commit();
        }

        // Check to be sure that TTS exists and is okay to use
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        //The result will come back in onActivityResult with our REQ_TTS_STATUS_CHECK number
        startActivityForResult(checkIntent, REQ_TTS_STATUS_CHECK);
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
            //show a dialogbox to ask for the number.
            LayoutInflater inflater = LayoutInflater.from(this);
            View view = inflater.inflate(R.layout.fragment_number, null);
            final EditText mEditText = (EditText) view.findViewById(R.id.txt_number);
            mEditText.requestFocus();
            final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.Theme_AppCompat));
            builder.setView(view).setTitle("How many bins?");
            builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    setNumber(Integer.valueOf(mEditText.getText().toString()));
                    dialog.dismiss();
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();

                }
            });
            // Show soft keyboard automatically (except won't work in emulators correctly!!!, when physical keyboard input set. Also no done... )

            AlertDialog dialog = builder.create();
            dialog.getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            dialog.show();
            /*            FragmentManager fm = getSupportFragmentManager();
            numDialogFrag editNameDialog = new numDialogFrag();
            editNameDialog.show(fm, "fragment_edit_name");
            */
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
        // Now that the TTS engine is ready, we enable the button
        if (status == TextToSpeech.SUCCESS) {
            CanSpeak = true;
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_TTS_STATUS_CHECK) {
            switch (resultCode) {
                case TextToSpeech.Engine.CHECK_VOICE_DATA_PASS:
                    // TTS is up and running
                    mTts = new TextToSpeech(getApplicationContext(), this);
                    Log.v(TAG, "Pico is installed okay");
                    break;
                case TextToSpeech.Engine.CHECK_VOICE_DATA_FAIL:
                default:
                    Log.e(TAG, "Got a failure. TTS apparently not available");
            }
        } else {
            // Got something else
        }
    }


    @Override
    public void onFragmentInteraction(int which) {
        if (which == 1) { //picker
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new PickerFragment()).commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new MatchFragment()).commit();
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
    public void speaknum(int num) {
        if (!CanSpeak || !WantSpeak) return;
        //String stringnum = "Bucket " + String.valueOf(num);
        String stringnum = String.valueOf(num);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //not sure what an utteranceId is supposed to be... we maybe able to setup a
            //listener for "utterances" and check to see if they completed or something.
            mTts.speak(stringnum, TextToSpeech.QUEUE_ADD, null, myUtteranceId);
        } else {  //below lollipop and use this method instead.
            mTts.speak(stringnum, TextToSpeech.QUEUE_ADD, null);
        }
    }


    /*
     *
	 * If we wanted to deal with the back button, this is the method for it.
	 * (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onBackPressed()
	 */
    @Override
    public void onBackPressed() {
//		super.onBackPressed();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.alert_dialog_two_buttons_title);
        builder.setPositiveButton(R.string.alert_dialog_ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        finish();
                    }
                }
        );
        builder.setNegativeButton(R.string.alert_dialog_cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                }
        );
        builder.show();

    }

}
