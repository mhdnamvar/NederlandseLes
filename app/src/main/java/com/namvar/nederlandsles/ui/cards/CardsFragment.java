package com.namvar.nederlandsles.ui.cards;

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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.namvar.nederlandsles.R;

public class CardsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        CardsViewModel viewModel = new ViewModelProvider(this).get(CardsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cards, container, false);

        final ListView cards = root.findViewById(R.id.cards);
        viewModel.getCards().observe(getViewLifecycleOwner(), strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    strings) {

                @NonNull
                @SuppressLint("SetTextI18n")
                @Override
                public View getView(
                        int position,
                        View convertView,
                        @NonNull ViewGroup parent) {

                    View view = super.getView(position, convertView, parent);
                    TextView textView = view.findViewById(android.R.id.text1);
                    textView.setTextColor(Color.DKGRAY);
                    textView.setText((position + 1) + ". " + textView.getText());

                    return view;
                }
            };
            cards.setAdapter(adapter);
        });

        cards.setOnItemClickListener((parent, view, position, id) -> {
            Log.d("CardsFragment", "pos: " + position);
            Bundle args = new Bundle();
            args.putString("cardTitle", (String) cards.getItemAtPosition(position));
            args.putInt("cardNo", position);
            setArguments(args);
            Navigation.findNavController(view).navigate(R.id.navigation_show_card, args);
        });

        return root;
    }
}