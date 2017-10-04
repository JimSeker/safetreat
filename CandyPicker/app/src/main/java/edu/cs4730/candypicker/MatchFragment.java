package edu.cs4730.candypicker;

import java.util.Random;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.ViewSwitcher;


/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link MatchFragment.OnFragmentInteractionListener}
 * interface to handle interaction events.
 */
public class MatchFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    public Context myContext;

    Random myRandom = new Random();
    int board[];
    int speaknum = 0;
    int pickone = 0, picktwo = 0;

    ViewSwitcher card1, card2, card3, card4, card5, card6;
    ImageView card1a, card2a, card3a, card4a, card5a, card6a;
    ImageView card1b, card2b, card3b, card4b, card5b, card6b;

    public MatchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_match2, container, false);

        //get all the widgets and setup listeners.
        //then call the setup method to set the cards and data structures.

        //card set 1
        card1 = (ViewSwitcher) myView.findViewById(R.id.vs_card1);
        card1.setInAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_in_left)); //or android.R.anim.fade_in
        card1.setOutAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_out_right)); //or android.R.anim.fade_out
        card1b = (ImageView) myView.findViewById(R.id.iv_card1b);
        card1a = (ImageView) myView.findViewById(R.id.iv_card1a);
        //card1a.setImageDrawable(getRandomBack());
        card1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ismatch(1))
                            card1.showNext();
                    }
                });

        card2 = (ViewSwitcher) myView.findViewById(R.id.vs_card2);
        card2.setInAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_in_left)); //or android.R.anim.fade_in
        card2.setOutAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_out_right)); //or android.R.anim.fade_out
        card2b = (ImageView) myView.findViewById(R.id.iv_card2b);
        card2a = (ImageView) myView.findViewById(R.id.iv_card2a);
        card2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ismatch(2))
                            card2.showNext();
                    }
                });

        //card set 3
        card3 = (ViewSwitcher) myView.findViewById(R.id.vs_card3);
        card3.setInAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_in_left)); //or android.R.anim.fade_in
        card3.setOutAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_out_right)); //or android.R.anim.fade_out
        card3b = (ImageView) myView.findViewById(R.id.iv_card3b);
        card3a = (ImageView) myView.findViewById(R.id.iv_card3a);
        card3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ismatch(3))
                            card3.showNext();
                    }
                });
        //card set 4
        card4 = (ViewSwitcher) myView.findViewById(R.id.vs_card4);
        card4.setInAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_in_left)); //or android.R.anim.fade_in
        card4.setOutAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_out_right)); //or android.R.anim.fade_out
        card4b = (ImageView) myView.findViewById(R.id.iv_card4b);
        card4a = (ImageView) myView.findViewById(R.id.iv_card4a);
        card4.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ismatch(4))
                            card4.showNext();
                    }
                });
        //card set 5
        card5 = (ViewSwitcher) myView.findViewById(R.id.vs_card5);
        card5.setInAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_in_left)); //or android.R.anim.fade_in
        card5.setOutAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_out_right)); //or android.R.anim.fade_out
        card5b = (ImageView) myView.findViewById(R.id.iv_card5b);
        card5a = (ImageView) myView.findViewById(R.id.iv_card5a);
        card5.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ismatch(5))
                            card5.showNext();
                    }
                });
        //card set 6
        card6 = (ViewSwitcher) myView.findViewById(R.id.vs_card6);
        card6.setInAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_in_left)); //or android.R.anim.fade_in
        card6.setOutAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_out_right)); //or android.R.anim.fade_out
        card6b = (ImageView) myView.findViewById(R.id.iv_card6b);
        card6a = (ImageView) myView.findViewById(R.id.iv_card6a);
        card6.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ismatch(6))
                            card6.showNext();
                    }
                });
        //mListener.onFragmentInteraction(i);

        setup();
        return myView;
    }

    //this setups the game (or resets the game).
    public void setup() {
        //populate  the board
        board = new int[]{0, 0, 0, 0, 0, 0, 0}; //7, so I can use 1 to 6, instead of 0 to 5

        int pos = 0;
        int[] val = new int[]{1, 1, 2, 2, 3, 3};
        for (int value : val) { // two pairs of numbers, between 1 and 3
            pos = myRandom.nextInt(6) + 1;
            while (board[pos] != 0) {
                pos = myRandom.nextInt(6) + 1;
            }
            board[pos] = value;
        }
        //so now the board is setup, set the "b" card for each of them.
        card1b.setImageDrawable(getbcard(board[1]));
        card2b.setImageDrawable(getbcard(board[2]));
        card3b.setImageDrawable(getbcard(board[3]));
        card4b.setImageDrawable(getbcard(board[4]));
        card5b.setImageDrawable(getbcard(board[5]));
        card6b.setImageDrawable(getbcard(board[6]));
    }

    public boolean ismatch(int card) {

        if (card == pickone && picktwo == 0)
            return false;

        if (pickone == 0) { //first card flipped over
            pickone = card;
        } else if (picktwo == 0) { //is there a match
            picktwo = card;
            if (board[pickone] == board[picktwo]) {
                showDialog();
                //Winner!  call dialogFragment
                //reset, and flip over the cards as well.
                //FragmentManager fm = getFragmentManager();
                //	 winDialogFrag editNameDialog = new winDialogFrag();
                //	 editNameDialog.setCancelable(false);  //have to click on the dialog in order for it go away.
                //	 editNameDialog.show(fm, "stuff.");

            }
        } else {
            //flip them back over, no match before.
            //	Log.v("ismatch", "ff one is " + pickone+ " two is "+picktwo);
            if (pickone == 1 || picktwo == 1) {
                card1.showNext();
            }
            if (pickone == 2 || picktwo == 2) {
                card2.showNext();
            }
            if (pickone == 3 || picktwo == 3) {
                card3.showNext();
            }
            if (pickone == 4 || picktwo == 4) {
                card4.showNext();
            }
            if (pickone == 5 || picktwo == 5) {
                card5.showNext();
            }
            if (pickone == 6 || picktwo == 6) {
                card6.showNext();
            }
            pickone = 0;
            picktwo = 0;
            return false;

        }
        return true;

    }

    public Drawable getRandomImage() {
        Drawable d = null;
        int i = mListener.GetNumber();
        speaknum = i;
        switch (i) {
            case 1:
                d = ContextCompat.getDrawable(getActivity(), R.drawable.one);
                break;
            case 2:
                d = ContextCompat.getDrawable(getActivity(), R.drawable.two);
                break;
            case 3:
                d = getResources().getDrawable(R.drawable.three);
                break;
            case 4:
                d = getResources().getDrawable(R.drawable.four);
                break;
            case 5:
                d = getResources().getDrawable(R.drawable.five);
                break;
            default:
                d = getResources().getDrawable(R.drawable.one);
        }
        return d;
    }

    public Drawable getbcard(int i) {
        Drawable d = null;

        switch (i) {
            case 1:
                d = getResources().getDrawable(R.drawable.pic1);
                break;
            case 2:
                d = getResources().getDrawable(R.drawable.pic2);
                break;
            case 3:
                d = getResources().getDrawable(R.drawable.pic3);
                break;
            case 4:
                d = getResources().getDrawable(R.drawable.pic4);
                break;
            case 5:
                d = getResources().getDrawable(R.drawable.pic5);
                break;
            case 6:
                d = getResources().getDrawable(R.drawable.pic6);
                break;
            default:
                d = getResources().getDrawable(R.drawable.pic1);
        }
        return d;
    }

    void showDialog() {
        ImageButton ib;
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.match_winner, null);

        final AlertDialog dialog = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.Theme_AppCompat)).create();
        dialog.setTitle("Winner!!!!");


        ib = (ImageButton) view.findViewById(R.id.imageButton1);
        ib.setImageDrawable(getRandomImage());
        mListener.speaknum(speaknum);
        ib.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                if (pickone == 1 || picktwo == 1) {
                    card1.showNext();
                }
                if (pickone == 2 || picktwo == 2) {
                    card2.showNext();
                }
                if (pickone == 3 || picktwo == 3) {
                    card3.showNext();
                }
                if (pickone == 4 || picktwo == 4) {
                    card4.showNext();
                }
                if (pickone == 5 || picktwo == 5) {
                    card5.showNext();
                }
                if (pickone == 6 || picktwo == 6) {
                    card6.showNext();
                }
                pickone = 0;
                picktwo = 0;
                setup();

            }
        });

        dialog.setView(view);
        dialog.show();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myContext = context;
        try {
            mListener = (OnFragmentInteractionListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
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
     */
    public interface OnFragmentInteractionListener {

        public void onFragmentInteraction(int which);  //1 is Picker, 2 is Match

        public int GetNumber();

        public void setNumber(int num);

        public void speaknum(int num);
    }

}
