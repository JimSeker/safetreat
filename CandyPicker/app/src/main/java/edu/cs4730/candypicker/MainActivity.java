package edu.cs4730.candypicker;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;


public class MainActivity extends Activity implements PickerFragment.OnFragmentInteractionListener,
													  MatchFragment.OnFragmentInteractionListener,
                                                        OnInitListener{
	
	int buckets = 4;  //default value?  not sure what that should be.

	Random myRandom = new Random();
    private static final int REQ_TTS_STATUS_CHECK = 0;
    private static final String TAG = "MainActivity";
    private TextToSpeech mTts;
    private boolean CanSpeak = false, WantSpeak =true;
    private String myUtteranceId = "txt2spk";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
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
			 FragmentManager fm = getFragmentManager();
			 numDialogFrag editNameDialog = new numDialogFrag();
			 editNameDialog.show(fm, "fragment_edit_name");
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
        if( status == TextToSpeech.SUCCESS) {
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
        }
        else {
            // Got something else
        }
    }


    @Override
	public void onFragmentInteraction(int which) {
		if (which ==1) { //picker
			getFragmentManager().beginTransaction()
			.replace(R.id.container, new PickerFragment()).commit();
		} else {
			getFragmentManager().beginTransaction()
			.replace(R.id.container, new MatchFragment()).commit();
		}
		
	}

	@Override
	public int GetNumber() {
		return (myRandom.nextInt(buckets)+1);
	}

	@Override
	public void setNumber(int num) {
		buckets = num;
	}

    @Override
    public void speaknum(int num) {
        if (!CanSpeak || !WantSpeak) return;
        //String stringnum = "Bucket " + String.valueOf(num);
        String stringnum =  String.valueOf(num);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //not sure what an utteranceId is supposed to be... we maybe able to setup a
            //listener for "utterances" and check to see if they completed or something.
            mTts.speak(stringnum, TextToSpeech.QUEUE_ADD, null, myUtteranceId);
        } else {  //below lollipop and use this method instead.
            mTts.speak(stringnum, TextToSpeech.QUEUE_ADD, null);
        }
    }

	public class numDialogFrag extends DialogFragment implements OnEditorActionListener {

		private EditText mEditText;
		public numDialogFrag() {
			// Empty constructor required for DialogFragment
		}
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.fragment_number, container);
			mEditText = (EditText) view.findViewById(R.id.txt_number);
			getDialog().setTitle("How many bins?");
			// Show soft keyboard automatically (except won't work in emulators correctly!!!, when physical keyboard input set. Also no done... )
			mEditText.requestFocus();
			getDialog().getWindow().setSoftInputMode(
					LayoutParams.SOFT_INPUT_STATE_VISIBLE);
			mEditText.setOnEditorActionListener(this);
			return view;
		}
		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			if (EditorInfo.IME_ACTION_DONE == actionId) {
				// Return input text to activity

				setNumber(Integer.valueOf(mEditText.getText().toString()));
				this.dismiss();
				return true;
			}
			return false;
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
		FragmentManager fm = getFragmentManager();
		AlertDialogFrag newFragment = new AlertDialogFrag(R.string.alert_dialog_two_buttons_title);
		newFragment.show(fm, "dialog");
	}
	
	
	public class AlertDialogFrag extends DialogFragment {
		int title;
		public  AlertDialogFrag (int t) {
			title = t;
		}
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			//int title = getArguments().getInt("title");
			return new AlertDialog.Builder(getActivity())
			//.setIcon(R.drawable.alert_dialog_icon)
			.setTitle(title)
			.setPositiveButton(R.string.alert_dialog_ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
								finish();
						}
					}
			)
			.setNegativeButton(R.string.alert_dialog_cancel,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							dialog.dismiss();
						}
					}
			)
			.create();
		}
	}
}
