package com.up.up_opportunity.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.up.up_opportunity.R;

/**
 * Created by adao1 on 7/9/2016.
 */
public class HelpFragment extends android.support.v4.app.Fragment {
    
    private static final String TAG = "HELP_FRAGMENT";
    private Button foodBankButton;
    private Button forumButton;
    private Button housingButton;
    OnFoodBankClickListener onFoodBankClickListener;
    OnForumClickListener onForumClickListener;
    OnHousingClickListener onHousingClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help,container,false);
        setRetainInstance(true);
        foodBankButton = (Button)view.findViewById(R.id.help_foodbank_button_id);
        forumButton = (Button)view.findViewById(R.id.help_forum_button_id);
        housingButton = (Button)view.findViewById(R.id.help_housing_id);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // Log.i(TAG, "onViewCreated: ");
        setClickListeners();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onFoodBankClickListener = (OnFoodBankClickListener) getActivity();
        onForumClickListener = (OnForumClickListener)getActivity();
        onHousingClickListener = (OnHousingClickListener)getActivity();
    }

    private void setClickListeners(){
        foodBankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFoodBankClickListener.onFoodBankClicked();
            }
        });
        forumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onForumClickListener.onForumClicked();
            }
        });
        housingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onHousingClickListener.onHousingClicked();
            }
        });
    }

    public interface OnFoodBankClickListener{
        void onFoodBankClicked();
    }

    public interface OnForumClickListener{
        void onForumClicked();
    }

    public interface OnHousingClickListener{
        void onHousingClicked();
    }
}
