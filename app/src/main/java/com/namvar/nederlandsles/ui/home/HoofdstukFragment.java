package com.namvar.nederlandsles.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.namvar.nederlandsles.R;

import java.util.List;
import java.util.Objects;

public class HoofdstukFragment extends Fragment {

    private HoofdstukViewModel viewModel;
    private int section = 1;

    public static HoofdstukFragment newInstance() {
        return new HoofdstukFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_hoofdstuk, container, false);
        final ListView list = root.findViewById(R.id.hoofdstukList);
        viewModel = ViewModelProviders.of(this).get(HoofdstukViewModel.class);


        Bundle args = getArguments();
        if (args != null) {
            section = args.getInt("section");
        }

        viewModel.getList(section).observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        Objects.requireNonNull(getContext()),
                        android.R.layout.simple_list_item_1,
                        strings) {

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView textView= view.findViewById(android.R.id.text1);
                        textView.setTextColor(Color.DKGRAY);
                        return view;
                    }
                };
                list.setAdapter(adapter);
            }
        });

        return root;
    }

}
