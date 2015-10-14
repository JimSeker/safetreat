package edu.cs4730.candypicker;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
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


public class MainActivity extends Activity implements PickerFragment.OnFragmentInteractionListener,
													  MatchFragment.OnFragmentInteractionListener {
	
	int buckets = 5;  //default value?  not sure what that should be.

	Random myRandom = new Random();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PickerFragment()).commit();
		}
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
		}
		return super.onOptionsItemSelected(item);
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
