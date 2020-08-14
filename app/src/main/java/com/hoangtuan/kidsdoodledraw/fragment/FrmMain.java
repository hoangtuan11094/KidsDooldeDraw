package com.hoangtuan.kidsdoodledraw.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hoangtuan.kidsdoodledraw.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FrmMain extends Fragment {

    public FrmMain() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frm_main, container, false);
    }
}
