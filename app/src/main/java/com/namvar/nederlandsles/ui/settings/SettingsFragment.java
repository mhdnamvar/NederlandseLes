package com.namvar.nederlandsles.ui.settings;

import android.annotation.SuppressLint;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.namvar.nederlandsles.R;
import com.namvar.nederlandsles.ui.letters.LettersViewModel;

import java.util.List;

public class SettingsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        final ListView settingsItems = root.findViewById(R.id.settingsItems);


        SettingsViewModel viewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        viewModel.getItems().observe(getViewLifecycleOwner(), new Observer<>() {
            @Override
            public void onChanged(List<String> strings) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_list_item_1,
                        strings) {

                    @NonNull
                    @SuppressLint("SetTextI18n")
                    @Override
                    public View getView(int position,
                                        View convertView,
                                        @NonNull ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView textView = view.findViewById(android.R.id.text1);
                        textView.setTextColor(Color.DKGRAY);
                        textView.setText(textView.getText());

                        return view;
                    }
                };
                settingsItems.setAdapter(adapter);
            }
        });

        settingsItems.setOnItemClickListener((parent, view, position, id) -> {
            Navigation.findNavController(view).navigate(R.id.navigation_privacy_policy);
        });

        return root;
    }
}