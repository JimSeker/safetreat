package edu.cs4730.candypicker;

import java.util.Random;

import android.os.Bundle;
import android.content.Context;
import android.graphics.drawable.Drawable;

import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import edu.cs4730.candypicker.databinding.FragmentMatch2Binding;
import edu.cs4730.candypicker.databinding.MatchWinnerBinding;


/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link MatchFragment.OnFragmentInteractionListener}
 * interface to handle interaction events.
 */
public class MatchFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    public Context myContext;

    Random myRandom = new Random();
    int[] board;
    int speakNum = 0;
    int pickOne = 0, pickTwo = 0;

    FragmentMatch2Binding binding;

    public MatchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMatch2Binding.inflate(inflater, container, false);
        View myView = binding.getRoot();

        //get all the widgets and setup listeners.
        //then call the setup method to set the cards and data structures.

        //card set 1
        //card1 = myView.findViewById(R.id.vs_card1);
        binding.vsCard1.setInAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_in_left)); //or android.R.anim.fade_in
        binding.vsCard1.setOutAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_out_right)); //or android.R.anim.fade_out
        binding.vsCard1.setOnClickListener(
            v -> {
                if (isMatch(1))
                    binding.vsCard1.showNext();
            });

        binding.vsCard2.setInAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_in_left)); //or android.R.anim.fade_in
        binding.vsCard2.setOutAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_out_right)); //or android.R.anim.fade_out
        binding.vsCard2.setOnClickListener(
            v -> {
                if (isMatch(2))
                    binding.vsCard2.showNext();
            });

        //card set 3
        binding.vsCard3.setInAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_in_left)); //or android.R.anim.fade_in
        binding.vsCard3.setOutAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_out_right)); //or android.R.anim.fade_out
        binding.vsCard3.setOnClickListener(
            v -> {
                if (isMatch(3))
                    binding.vsCard3.showNext();
            });
        //card set 4
        binding.vsCard4.setInAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_in_left)); //or android.R.anim.fade_in
        binding.vsCard4.setOutAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_out_right)); //or android.R.anim.fade_out
        binding.vsCard4.setOnClickListener(
            v -> {
                if (isMatch(4))
                    binding.vsCard4.showNext();
            });
        //card set 5
        binding.vsCard5.setInAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_in_left)); //or android.R.anim.fade_in
        binding.vsCard5.setOutAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_out_right)); //or android.R.anim.fade_out
        binding.vsCard5.setOnClickListener(
            v -> {
                if (isMatch(5))
                    binding.vsCard5.showNext();
            });
        //card set 6
        binding.vsCard6.setInAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_in_left)); //or android.R.anim.fade_in
        binding.vsCard6.setOutAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_out_right)); //or android.R.anim.fade_out
        binding.vsCard6.setOnClickListener(
            v -> {
                if (isMatch(6))
                    binding.vsCard6.showNext();
            });
        setup();
        return myView;
    }

    //this sets up the board for the game (or resets the game).
    public void setup() {
        //populate  the board
        board = new int[]{0, 0, 0, 0, 0, 0, 0}; //7, so I can use 1 to 6, instead of 0 to 5

        int pos;
        int[] val = new int[]{1, 1, 2, 2, 3, 3};
        for (int value : val) { // two pairs of numbers, between 1 and 3
            do {
                pos = myRandom.nextInt(6) + 1;
            } while (board[pos] != 0);
            board[pos] = value;
        }
        //so now the board is setup, set the "b" card for each of them.
        binding.ivCard1b.setImageDrawable(getBCard(board[1]));
        binding.ivCard2b.setImageDrawable(getBCard(board[2]));
        binding.ivCard3b.setImageDrawable(getBCard(board[3]));
        binding.ivCard4b.setImageDrawable(getBCard(board[4]));
        binding.ivCard5b.setImageDrawable(getBCard(board[5]));
        binding.ivCard6b.setImageDrawable(getBCard(board[6]));
    }

    public boolean isMatch(int card) {

        if (card == pickOne && pickTwo == 0)
            return false;

        if (pickOne == 0) { //first card flipped over
            pickOne = card;
        } else if (pickTwo == 0) { //is there a match
            pickTwo = card;
            if (board[pickOne] == board[pickTwo]) {
                showDialog();
            }
        } else {
            //flip them back over, no match before.
            if (pickOne == 1 || pickTwo == 1) {
                binding.vsCard1.showNext();
            }
            if (pickOne == 2 || pickTwo == 2) {
                binding.vsCard2.showNext();
            }
            if (pickOne == 3 || pickTwo == 3) {
                binding.vsCard3.showNext();
            }
            if (pickOne == 4 || pickTwo == 4) {
                binding.vsCard4.showNext();
            }
            if (pickOne == 5 || pickTwo == 5) {
                binding.vsCard5.showNext();
            }
            if (pickOne == 6 || pickTwo == 6) {
                binding.vsCard6.showNext();
            }
            pickOne = 0;
            pickTwo = 0;
            return false;

        }
        return true;

    }

    public Drawable getRandomImage() {
        Drawable d;
        int i = mListener.GetNumber();
        speakNum = i;
        switch (i) {
            case 1:
                d = ContextCompat.getDrawable(requireActivity(), R.drawable.one);
                break;
            case 2:
                d = ContextCompat.getDrawable(requireActivity(), R.drawable.two);
                break;
            case 3:
                d = ContextCompat.getDrawable(requireActivity(), R.drawable.three);
                break;
            case 4:
                d = ContextCompat.getDrawable(requireActivity(), R.drawable.four);
                break;
            case 5:
                d = ContextCompat.getDrawable(requireActivity(), R.drawable.five);
                break;
            default:
                d = ContextCompat.getDrawable(requireActivity(), R.drawable.one);
        }
        return d;
    }

    public Drawable getBCard(int i) {
        Drawable d;

        switch (i) {
            case 1:
                d = ContextCompat.getDrawable(requireActivity(), R.drawable.pic1);
                break;
            case 2:
                d = ContextCompat.getDrawable(requireActivity(), R.drawable.pic2);
                break;
            case 3:
                d = ContextCompat.getDrawable(requireActivity(), R.drawable.pic3);
                break;
            case 4:
                d = ContextCompat.getDrawable(requireActivity(), R.drawable.pic4);
                break;
            case 5:
                d = ContextCompat.getDrawable(requireActivity(), R.drawable.pic5);
                break;
            case 6:
                d = ContextCompat.getDrawable(requireActivity(), R.drawable.pic6);
                break;
            default:
                d = ContextCompat.getDrawable(requireActivity(), R.drawable.pic1);
        }
        return d;
    }

    void showDialog() {
        //ImageButton ib;
        LayoutInflater inflater = getLayoutInflater();
        MatchWinnerBinding bindingD = MatchWinnerBinding.inflate(inflater);
//        View view = inflater.inflate(R.layout.match_winner, null);

        final AlertDialog dialog = new AlertDialog.Builder(new ContextThemeWrapper(requireActivity(), R.style.Theme_AppCompat)).create();
        dialog.setTitle("Winner!!!!");


        //ib = view.findViewById(R.id.imageButton1);
        bindingD.imageButton1.setImageDrawable(getRandomImage());
        mListener.speakNum(speakNum);
        bindingD.imageButton1.setOnClickListener(view -> {
            dialog.dismiss();

            if (pickOne == 1 || pickTwo == 1) {
                binding.vsCard1.showNext();
            }
            if (pickOne == 2 || pickTwo == 2) {
                binding.vsCard2.showNext();
            }
            if (pickOne == 3 || pickTwo == 3) {
                binding.vsCard3.showNext();
            }
            if (pickOne == 4 || pickTwo == 4) {
                binding.vsCard4.showNext();
            }
            if (pickOne == 5 || pickTwo == 5) {
                binding.vsCard5.showNext();
            }
            if (pickOne == 6 || pickTwo == 6) {
                binding.vsCard6.showNext();
            }
            pickOne = 0;
            pickTwo = 0;
            setup();

        });

        dialog.setView(bindingD.getRoot());
        dialog.show();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        myContext = context;
        try {
            mListener = (OnFragmentInteractionListener) requireActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(requireActivity()
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

        void onFragmentInteraction(int which);  //1 is Picker, 2 is Match

        int GetNumber();

        void setNumber(int num);

        void speakNum(int num);
    }

}
