package com.namvar.nederlandsles.ui.home;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.media.AudioManager;
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
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.namvar.nederlandsles.R;

import java.util.List;
import java.util.Locale;

public class PhrasesFragment extends Fragment {

    private String section;
    private TextToSpeech tts;
    private final static String KEY_SECTION = "section";
    private final static String KEY_DUTCH = "nl_NL";
    private TextView htmlView;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_phrases, container, false);
        final ListView list = root.findViewById(R.id.phrases);
        htmlView = root.findViewById(R.id.htmlView);
        PhrasesViewModel viewModel = new ViewModelProvider(this).get(PhrasesViewModel.class);

        Bundle args = getArguments();
        if (args != null) {
            section = args.getString(KEY_SECTION);
            setTitle(args.getString(KEY_SECTION));
        }

        viewModel.getList(section).observe(getViewLifecycleOwner(), strings -> list.setAdapter(getStringArrayAdapter(strings)));

        viewModel.getHtml().observe(getViewLifecycleOwner(), s -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                htmlView.setText(Html.fromHtml(s, Html.FROM_HTML_MODE_COMPACT));
            } else {
                htmlView.setText(Html.fromHtml(s));
            }
        });

        setupTTS();

        list.setOnItemClickListener((parent, view, position, id) -> {
            String text = ((TextView) (view)).getText().toString();
            if (text.length() > 2) {
                speak(text.substring(text.indexOf(".") + 1));
            }
        });

        return root;
    }

    private ArrayAdapter<String> getStringArrayAdapter(List<String> strings) {
        return new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_list_item_1, strings) {

            @SuppressLint("SetTextI18n")
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.DKGRAY);
                textView.setHeight(150);
                String source = textView.getText().toString();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    textView.setText(Html.fromHtml(source, Html.FROM_HTML_MODE_COMPACT));
                } else {
                    textView.setText(Html.fromHtml(source));
                }
                return view;
            }
        };
    }

    private void setupTTS() {
        tts = new TextToSpeech(getContext(), status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = tts.setLanguage(new Locale(KEY_DUTCH));
                if (result == TextToSpeech.LANG_MISSING_DATA ||
                        result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("PhrasesFragment", "This Language is not supported");
                }
            } else
                Log.e("PhrasesFragment", "Initialization Failed!");
        });

        tts.setSpeechRate(0.6f);
    }

    private void setTitle(String text) {
        if (getActivity() != null) {
            ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setTitle(text);
            }
        }
    }

    private void speak(String text) {
        if (text.isEmpty()) {
            Toast.makeText(getContext(),
                    "There is no text to convert to speech!",
                    Toast.LENGTH_SHORT).show();
        } else {
            Bundle params = new Bundle();
            params.putString(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(AudioManager.STREAM_MUSIC));
            params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "SOME MESSAGE");
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, params, "SOME MESSAGE");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("PhrasesFragment", "onStop called");
        tts.stop();
    }
}
