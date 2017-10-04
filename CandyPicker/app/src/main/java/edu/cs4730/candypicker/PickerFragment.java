package edu.cs4730.candypicker;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link PickerFragment.OnFragmentInteractionListener}
 * interface to handle interaction events.
 * 
 */
public class PickerFragment extends Fragment {

	private OnFragmentInteractionListener mListener;
	public Context myContext;
	
	Random myRandom = new Random();
	int speaknum;
	int currentcard =0;
	
	public PickerFragment() {
		// Required empty public constructor
	}

	ViewSwitcher card1, card2, card3, card4, card5, card6;
	ImageView card1a, card2a, card3a, card4a, card5a, card6a;
	ImageView card1b, card2b, card3b, card4b, card5b, card6b;
	int board[];
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View myView = inflater.inflate(R.layout.fragment_picker2, container, false);
		
		//random covers for the cards.  1 to 6.
		board = new int[]{0,0,0,0,0,0,0}; //7, so I can use 1 to 6, instead of 0 to 5
		
		int pos = 0;
		int[] val = new int[]{1,2,3,4,5,6};
		for (int value: val) { // two pairs of numbers, between 1 and 3
		   pos = myRandom.nextInt(6) +1;
		   while (board[pos] != 0) {
			   pos = myRandom.nextInt(6) +1;
		   }
		   board[pos] = value;
		}
		//card set 1
		card1 = (ViewSwitcher) myView.findViewById(R.id.vs_card1);
		card1.setInAnimation(AnimationUtils.loadAnimation(myContext,android.R.anim.slide_in_left)); //or android.R.anim.fade_in
		card1.setOutAnimation(AnimationUtils.loadAnimation(myContext,android.R.anim.slide_out_right)); //or android.R.anim.fade_out
		card1b = (ImageView) myView.findViewById(R.id.iv_card1b);
		card1a = (ImageView) myView.findViewById(R.id.iv_card1a);
		card1a.setImageDrawable(getRandomBack(board[1]));
		card1.setOnClickListener(
			new View.OnClickListener(){
					@Override
					public void onClick(View v) {
					  if (card1b != card1.getCurrentView())  //don't change if is is already cardb showing.
						card1b.setImageDrawable(getRandomImage());
					  	fixcard(1); 
					    card1.showNext();
					}
		});
		
		card2 = (ViewSwitcher) myView.findViewById(R.id.vs_card2);
		card2.setInAnimation(AnimationUtils.loadAnimation(myContext,android.R.anim.slide_in_left)); //or android.R.anim.fade_in
		card2.setOutAnimation(AnimationUtils.loadAnimation(myContext,android.R.anim.slide_out_right)); //or android.R.anim.fade_out
		card2b = (ImageView) myView.findViewById(R.id.iv_card2b);
		card2a = (ImageView) myView.findViewById(R.id.iv_card2a);
		card2a.setImageDrawable(getRandomBack(board[2]));
		card2.setOnClickListener(
			new View.OnClickListener(){
					@Override
					public void onClick(View v) {
						if (card2b != card2.getCurrentView())  //don't change if is is already cardb showing.
						card2b.setImageDrawable(getRandomImage());
						fixcard(2); 
					    card2.showNext();
					}
		});
		
		//card set 3
		card3 = (ViewSwitcher) myView.findViewById(R.id.vs_card3);
		card3.setInAnimation(AnimationUtils.loadAnimation(myContext,android.R.anim.slide_in_left)); //or android.R.anim.fade_in
		card3.setOutAnimation(AnimationUtils.loadAnimation(myContext,android.R.anim.slide_out_right)); //or android.R.anim.fade_out
		card3b = (ImageView) myView.findViewById(R.id.iv_card3b);
		card3a = (ImageView) myView.findViewById(R.id.iv_card3a);
		card3a.setImageDrawable(getRandomBack(board[3]));
		
		card3.setOnClickListener(
			new View.OnClickListener(){
					@Override
					public void onClick(View v) {
						if (card3b != card3.getCurrentView())  //don't change if is is already cardb showing.
						  card3b.setImageDrawable(getRandomImage());
						fixcard(3); 
					    card3.showNext();
					}
		});
		//card set 4
		card4 = (ViewSwitcher) myView.findViewById(R.id.vs_card4);
		card4.setInAnimation(AnimationUtils.loadAnimation(myContext,android.R.anim.slide_in_left)); //or android.R.anim.fade_in
		card4.setOutAnimation(AnimationUtils.loadAnimation(myContext,android.R.anim.slide_out_right)); //or android.R.anim.fade_out
		card4b = (ImageView) myView.findViewById(R.id.iv_card4b);
		card4a = (ImageView) myView.findViewById(R.id.iv_card4a);
		card4a.setImageDrawable(getRandomBack(board[4]));		
		card4.setOnClickListener(
			new View.OnClickListener(){
					@Override
					public void onClick(View v) {
						if (card4b != card4.getCurrentView())  //don't change if is is already cardb showing.
						  card4b.setImageDrawable(getRandomImage());
						fixcard(4);
					    card4.showNext();
					}
		});
		//card set 5
		card5 = (ViewSwitcher) myView.findViewById(R.id.vs_card5);
		card5.setInAnimation(AnimationUtils.loadAnimation(myContext,android.R.anim.slide_in_left)); //or android.R.anim.fade_in
		card5.setOutAnimation(AnimationUtils.loadAnimation(myContext,android.R.anim.slide_out_right)); //or android.R.anim.fade_out
		card5b = (ImageView) myView.findViewById(R.id.iv_card5b);
		card5a = (ImageView) myView.findViewById(R.id.iv_card5a);
		card5a.setImageDrawable(getRandomBack(board[5]));
		card5.setOnClickListener(
			new View.OnClickListener(){
					@Override
					public void onClick(View v) {
						if (card5b != card5.getCurrentView())  //don't change if is is already cardb showing.
						  card5b.setImageDrawable(getRandomImage());
						fixcard(5); 
					    card5.showNext();
					}
		});
		//card set 6
		card6 = (ViewSwitcher) myView.findViewById(R.id.vs_card6);
		card6.setInAnimation(AnimationUtils.loadAnimation(myContext,android.R.anim.slide_in_left)); //or android.R.anim.fade_in
		card6.setOutAnimation(AnimationUtils.loadAnimation(myContext,android.R.anim.slide_out_right)); //or android.R.anim.fade_out
		card6b = (ImageView) myView.findViewById(R.id.iv_card6b);
		card6a = (ImageView) myView.findViewById(R.id.iv_card6a);
		card6a.setImageDrawable(getRandomBack(board[6]));
		card6.setOnClickListener(
			new View.OnClickListener(){
					@Override
					public void onClick(View v) {
						if (card6b != card6.getCurrentView())  //don't change if is is already cardb showing.
						  card6b.setImageDrawable(getRandomImage());
						fixcard(6);
					    card6.showNext();
					}
		});
		//mListener.onFragmentInteraction(i);
		
		return myView;
	}

	public void fixcard(int i) {
		if (currentcard ==i) {
			currentcard =0;
			return;
		}
		switch (currentcard) {
		case 1: card1.showNext();
		break;
		case 2: card2.showNext();
		break;
		case 3: card3.showNext();
		break;
		case 4: card4.showNext();
		break;
		case 5: card5.showNext();
		break;
		case 6: card6.showNext();
		break;
		}
		currentcard = i;
        mListener.speaknum(speaknum);
		
	}
	public Drawable getRandomImage() {
		Drawable d = null;
		int i = mListener.GetNumber();
        speaknum  =i;
		switch (i) {
		case 1: d = getResources().getDrawable(R.drawable.one);
		break;
		case 2: d = getResources().getDrawable(R.drawable.two);
		break;
		case 3: d = getResources().getDrawable(R.drawable.three);
		break;
		case 4: d = getResources().getDrawable(R.drawable.four);
		break;
		case 5: d = getResources().getDrawable(R.drawable.five);
		break;
		default:
			d = getResources().getDrawable(R.drawable.pic1);
		}
		return d;
	}
	public Drawable getRandomBack(int i) {
		Drawable d = null;

		switch (i) {
		case 1: d = getResources().getDrawable(R.drawable.pic1);
		break;
		case 2: d = getResources().getDrawable(R.drawable.pic2);
		break;
		case 3: d = getResources().getDrawable(R.drawable.pic3);
		break;
		case 4: d = getResources().getDrawable(R.drawable.pic4);
		break;
		case 5: d = getResources().getDrawable(R.drawable.pic5);
		break;
		case 6: d = getResources().getDrawable(R.drawable.pic6);
		break;
		default:
			d = getResources().getDrawable(R.drawable.pic1);
		}
		return d;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		myContext = getActivity();
		try {
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onFragmentInteraction(int which);  //1 is Picker, 2 is Match
		public int GetNumber();
		public void setNumber(int num);
		public void speaknum(int num) ;
	}

}
