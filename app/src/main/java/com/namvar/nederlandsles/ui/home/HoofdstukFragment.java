package com.namvar.nederlandsles.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.namvar.nederlandsles.R;

public class HoofdstukFragment extends Fragment {

    private HoofdstukViewModel viewModel;

    public static HoofdstukFragment newInstance() {
        return new HoofdstukFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_hoofdstuk, container, false);

        viewModel = ViewModelProviders.of(this).get(HoofdstukViewModel.class);
        viewModel.getText().observe(this, s -> {
            TextView title = root.findViewById(R.id.hoofdstukTitle);
            title.setText(s);
        });

        return root;
    }

}
