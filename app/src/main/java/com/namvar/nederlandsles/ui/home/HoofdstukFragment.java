package com.namvar.nederlandsles.ui.home;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.namvar.nederlandsles.R;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class HoofdstukFragment extends Fragment {

    private HoofdstukViewModel viewModel;
    private int section = 1;
    TextToSpeech tts;

    public static HoofdstukFragment newInstance() {
        return new HoofdstukFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_hoofdstuk, container, false);
        final ListView list = root.findViewById(R.id.hoofdstukList);
        final TextView htmlView = root.findViewById(R.id.htmlView);
        viewModel = ViewModelProviders.of(this).get(HoofdstukViewModel.class);

        Bundle args = getArguments();
        if (args != null) {
            section = args.getInt("section");
            if (section >= 10) {
                list.setVisibility(View.INVISIBLE);
                htmlView.setVisibility(View.VISIBLE);
            } else {
                list.setVisibility(View.VISIBLE);
                htmlView.setVisibility(View.INVISIBLE);
            }
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

        viewModel.getHtml().observe(this, s -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                htmlView.setText(Html.fromHtml(s, Html.FROM_HTML_MODE_COMPACT));
            } else {
                htmlView.setText(Html.fromHtml(s));
            }
        });

        tts = new TextToSpeech(getContext(), status -> {
            // TODO Auto-generated method stub
            if(status == TextToSpeech.SUCCESS){
                int result = tts.setLanguage(new Locale("nl_NL"));
                if(result == TextToSpeech.LANG_MISSING_DATA ||
                        result == TextToSpeech.LANG_NOT_SUPPORTED){
                    Log.e("error", "This Language is not supported");
                }
            }
            else
                Log.e("error", "Initilization Failed!");
        });
        tts.setSpeechRate(0.7f);

        list.setOnItemClickListener((parent, view, position, id) -> {
            String text = ((TextView)(view)).getText().toString();
            if(text.length() == 0){
                Toast.makeText(getContext(), "There is no text to convert to speech!",
                        Toast.LENGTH_SHORT).show();
            } else {
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        return root;
    }

}
