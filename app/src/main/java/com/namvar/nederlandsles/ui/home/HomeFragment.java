package com.namvar.nederlandsles.ui.home;

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

import java.util.List;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        final ListView chapters = root.findViewById(R.id.chapters);

        homeViewModel.getChapters().observe(getViewLifecycleOwner(), new Observer<>() {
            @Override
            public void onChanged(List<String> strings) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_list_item_1,
                        strings) {

                    @NonNull
                    @SuppressLint("SetTextI18n")
                    @Override
                    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView textView = view.findViewById(android.R.id.text1);
                        textView.setTextColor(Color.DKGRAY);
                        textView.setText((position + 1) + ". " + textView.getText());
                        return view;
                    }
                };
                chapters.setAdapter(adapter);
            }
        });

        chapters.setOnItemClickListener((parent, view, position, id) -> {
            Log.d("HomeFragment", "pos: " + position);
            Bundle args = new Bundle();
            args.putString("section", (String) chapters.getItemAtPosition(position));
            setArguments(args);
            Navigation.findNavController(view).navigate(R.id.navigation_hoofdstuk, args);
        });
        
        return root;
    }
}