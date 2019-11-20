package com.namvar.nederlandsles.ui.home;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.namvar.nederlandsles.R;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class HoofdstukFragment extends Fragment {

    private String section;
    private TextToSpeech tts;
//    private final static String KEY_SELECTED = "selected";
    private final static String KEY_SECTION = "sectionText";
    private final static String KEY_DUTCH = "nl_NL";
    private TextView htmlView;
    private ImageView playImageView;

    public static HoofdstukFragment newInstance() {
        return new HoofdstukFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_hoofdstuk, container, false);
        final ListView list = root.findViewById(R.id.hoofdstukList);
        htmlView = root.findViewById(R.id.htmlView);
        playImageView = root.findViewById(R.id.playImageView);
        HoofdstukViewModel viewModel = ViewModelProviders.of(this).get(HoofdstukViewModel.class);

        Bundle args = getArguments();
        if (args != null) {
            section = args.getString(KEY_SECTION);
//            if (section >= 10) {
//                list.setVisibility(View.INVISIBLE);
//                htmlView.setVisibility(View.VISIBLE);
//                playImageView.setVisibility(View.VISIBLE);
//            } else {
//                list.setVisibility(View.VISIBLE);
//                htmlView.setVisibility(View.INVISIBLE);
                playImageView.setVisibility(View.INVISIBLE);
//            }
            setTitle(args.getString(KEY_SECTION));
        }

        viewModel.getList(section).observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        Objects.requireNonNull(getContext()),
                        android.R.layout.simple_list_item_1,
                        strings) {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView textView= view.findViewById(android.R.id.text1);
                        textView.setTextColor(Color.DKGRAY);
                        textView.setText((position+1) + ". " + textView.getText());
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

        setupTTS();

        list.setOnItemClickListener((parent, view, position, id) -> {
            String text = ((TextView) (view)).getText().toString();
            if (text.length() > 2) {
                speak(text.substring(text.indexOf(".")+1));
            }
        });

        playImageView.setOnClickListener(view -> {
            if (tts.isSpeaking()){
                tts.stop();
            } else {
                speak(htmlView.getText().toString());
            }
        });

        return root;
    }

    private void setupTTS() {
        tts = new TextToSpeech(getContext(), status -> {
            if(status == TextToSpeech.SUCCESS){
                int result = tts.setLanguage(new Locale(KEY_DUTCH));
                if(result == TextToSpeech.LANG_MISSING_DATA ||
                        result == TextToSpeech.LANG_NOT_SUPPORTED){
                    Log.e("Voice", "This Language is not supported");
                }
            }
            else
                Log.e("Voice", "Initilization Failed!");
        });

        tts.setSpeechRate(0.7f);

        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {
                Log.d("Voice", utteranceId + ", started" );
                playImageView.setImageResource(R.drawable.ic_stop_24px);
            }

            @Override
            public void onDone(String utteranceId) {
                Log.d("Voice", utteranceId + ", done" );
                playImageView.setImageResource(R.drawable.ic_play_circle_outline_24px);
            }

            @Override
            public void onError(String utteranceId) {
                Log.d("Voice", utteranceId);
            }

            @Override
            public void onStop(String utteranceId, boolean interrupted) {
                Log.d("Voice", utteranceId + ", stopped" );
                playImageView.setImageResource(R.drawable.ic_play_circle_outline_24px);
            }

        });
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
        if(text.length() == 0) {
            Toast.makeText(getContext(),
                    "There is no text to convert to speech!",
                    Toast.LENGTH_SHORT).show();
        } else {
            HashMap<String, String> myHashAlarm = new HashMap<String, String>();
            myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(AudioManager.STREAM_ALARM));
            myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "SOME MESSAGE");
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, myHashAlarm);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("Voice", "onStop called" );
        tts.stop();
    }
}
