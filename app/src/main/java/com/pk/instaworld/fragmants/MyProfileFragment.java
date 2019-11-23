package com.pk.instaworld.fragmants;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;
import com.pk.instaworld.R;
import com.pk.instaworld.fragmants.child_fragments.GridChildFragment;
import com.pk.instaworld.fragmants.child_fragments.ListChildFragment;

public class MyProfileFragment extends Fragment {

    public static final String TAG = "MyProfileFragment";
    private OnFragmentInteractionListener mListener;
    TextView tvName;
    BottomNavigationView listGridNavBar;
    // DON'T DO THIS IF YOU ARE NESTING FRAGMENTS INSIDE FRAGMENTS
//    final FragmentManager transaction = getChildFragmentManager();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvName = view.findViewById(R.id.tvName);

        String name =  ParseUser.getCurrentUser().getString("firstName") + " " +  ParseUser.getCurrentUser().getString("lastName");
        tvName.setText(name);


        listGridNavBar = view.findViewById(R.id.listGridNavBar);
        listGridNavBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment childFragment;
                switch (menuItem.getItemId()) {
                    case R.id.view_list:
                        childFragment = new ListChildFragment();
                        break;
                    case R.id.view_grid:
                        childFragment = new GridChildFragment();
                        break;
                    default:
                        childFragment = new GridChildFragment();
                        break;

                }
                // solve the "Fragment is not attached.." error
                getChildFragmentManager().beginTransaction().replace(R.id.flMyPosts, childFragment).commit();
                return true;
            }
        });

        listGridNavBar.setSelectedItemId(R.id.view_grid);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void messageFromParentFragment(Uri uri);
    }

}
