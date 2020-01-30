package com.namvar.nederlandsles.ui.speaking;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.namvar.nederlandsles.R;

import java.util.List;
import java.util.Objects;

public class SpeakingFragment extends Fragment {

    public static SpeakingFragment newInstance() {
        return new SpeakingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        SpeakingViewModel viewModel = ViewModelProviders.of(this).get(SpeakingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_speaking, container, false);
        ListView items = root.findViewById(R.id.speakingItems);
        items.setDivider(null);
        viewModel.getItems().observe(this, strings -> {
            ArrayAdapter<String> adapter = getStringArrayAdapter(strings);
            items.setAdapter(adapter);
        });


        return root;
    }

    private ArrayAdapter<String> getStringArrayAdapter(List<String> strings) {
        return new ArrayAdapter<String>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_list_item_1, strings) {

                    @SuppressLint("SetTextI18n")
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView textView= view.findViewById(android.R.id.text1);
                        textView.setTextColor(Color.DKGRAY);
                        textView.setText((position+1) + ". " + textView.getText());
                        textView.setHeight(250);
                        return view;
                    }
                };
    }

}
