package edu.cs4730.candypicker;

import java.util.Random;

import android.os.Bundle;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import edu.cs4730.candypicker.databinding.FragmentPicker2Binding;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link PickerFragment.OnFragmentInteractionListener}
 * interface to handle interaction events.
 */
public class PickerFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    public Context myContext;
    FragmentPicker2Binding binding;

    Random myRandom = new Random();
    int speakNum;
    int currentCard = 0;

    public PickerFragment() {
        // Required empty public constructor
    }

    int[] board;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPicker2Binding.inflate(inflater, container, false);

        //random covers for the cards.  1 to 6.
        board = new int[]{0, 0, 0, 0, 0, 0, 0}; //7, so I can use 1 to 6, instead of 0 to 5

        int pos;
        int[] val = new int[]{1, 2, 3, 4, 5, 6};
        for (int value : val) { // two pairs of numbers, between 1 and 3
            do {
                pos = myRandom.nextInt(6) + 1;
            } while (board[pos] != 0);
            board[pos] = value;
        }
        //card set 1
        binding.vsCard1.setInAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_in_left)); //or android.R.anim.fade_in
        binding.vsCard1.setOutAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_out_right)); //or android.R.anim.fade_out
        binding.ivCard1a.setImageDrawable(getRandomBack(board[1]));
        binding.vsCard1.setOnClickListener(v -> {
            if (binding.ivCard1b != binding.vsCard1.getCurrentView())  //don't change if is already cardB showing.
                binding.ivCard1b.setImageDrawable(getRandomImage());
            fixCard(1);
            binding.vsCard1.showNext();
        });


        binding.vsCard2.setInAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_in_left)); //or android.R.anim.fade_in
        binding.vsCard2.setOutAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_out_right)); //or android.R.anim.fade_out
        binding.ivCard2a.setImageDrawable(getRandomBack(board[2]));
        binding.vsCard2.setOnClickListener(v -> {
            if (binding.ivCard2b != binding.vsCard2.getCurrentView())  //don't change if is already cardB showing.
                binding.ivCard2b.setImageDrawable(getRandomImage());
            fixCard(2);
            binding.vsCard2.showNext();
        });

        //card set 3
        binding.vsCard3.setInAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_in_left)); //or android.R.anim.fade_in
        binding.vsCard3.setOutAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_out_right)); //or android.R.anim.fade_out
        binding.ivCard3a.setImageDrawable(getRandomBack(board[3]));

        binding.vsCard3.setOnClickListener(v -> {
            if (binding.ivCard3b != binding.vsCard3.getCurrentView())  //don't change if is already cardB showing.
                binding.ivCard3b.setImageDrawable(getRandomImage());
            fixCard(3);
            binding.vsCard3.showNext();
        });
        //card set 4
        binding.vsCard4.setInAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_in_left)); //or android.R.anim.fade_in
        binding.vsCard4.setOutAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_out_right)); //or android.R.anim.fade_out
        binding.ivCard4a.setImageDrawable(getRandomBack(board[4]));
        binding.vsCard4.setOnClickListener(v -> {
            if (binding.ivCard4b != binding.vsCard4.getCurrentView())  //don't change if is already cardB showing.
                binding.ivCard4b.setImageDrawable(getRandomImage());
            fixCard(4);
            binding.vsCard4.showNext();
        });
        //card set 5
        binding.vsCard5.setInAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_in_left)); //or android.R.anim.fade_in
        binding.vsCard5.setOutAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_out_right)); //or android.R.anim.fade_out
        binding.ivCard5a.setImageDrawable(getRandomBack(board[5]));
        binding.vsCard5.setOnClickListener(v -> {
            if (binding.ivCard5b != binding.vsCard5.getCurrentView())  //don't change if is already cardB showing.
                binding.ivCard5b.setImageDrawable(getRandomImage());
            fixCard(5);
            binding.vsCard5.showNext();
        });
        //card set 6
        binding.vsCard6.setInAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_in_left)); //or android.R.anim.fade_in
        binding.vsCard6.setOutAnimation(AnimationUtils.loadAnimation(myContext, android.R.anim.slide_out_right)); //or android.R.anim.fade_out
        binding.ivCard6a.setImageDrawable(getRandomBack(board[6]));
        binding.vsCard6.setOnClickListener(v -> {
            if (binding.ivCard6b != binding.vsCard6.getCurrentView())  //don't change if is already cardB showing.
                binding.ivCard6b.setImageDrawable(getRandomImage());
            fixCard(6);
            binding.vsCard6.showNext();
        });
        return binding.getRoot();
    }

    public void fixCard(int i) {
        if (currentCard == i) {
            currentCard = 0;
            return;
        }
        switch (currentCard) {
            case 1:
                binding.vsCard1.showNext();
                break;
            case 2:
                binding.vsCard2.showNext();
                break;
            case 3:
                binding.vsCard3.showNext();
                break;
            case 4:
                binding.vsCard4.showNext();
                break;
            case 5:
                binding.vsCard5.showNext();
                break;
            case 6:
                binding.vsCard6.showNext();
                break;
        }
        currentCard = i;
        mListener.speakNum(speakNum);

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
                d = ContextCompat.getDrawable(requireActivity(), R.drawable.pic1);
        }
        return d;
    }

    public Drawable getRandomBack(int i) {
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        myContext = context;
        try {
            mListener = (OnFragmentInteractionListener) requireActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(requireActivity() + " must implement OnFragmentInteractionListener");
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
        void onFragmentInteraction(int which);  //1 is Picker, 2 is Match

        int GetNumber();

        void setNumber(int num);

        void speakNum(int num);
    }

}
