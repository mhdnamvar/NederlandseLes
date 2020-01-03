package com.namvar.nederlandsles.ui.letters;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.namvar.nederlandsles.R;

import java.util.HashMap;
import java.util.Locale;

public class ShowLetterFragment extends Fragment {

    private final static String KEY_LETTER_TITLE = "letterTitle";
    private final static String KEY_LETTER_NO = "letterNo";
    private final static String KEY_DUTCH = "nl_NL";
    private static final float speechRate = 0.6f;
    private TextToSpeech tts;
    private TextView htmlView;
    private ImageView playImageView;

    public static ShowLetterFragment newInstance() {
        return new ShowLetterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_show_letter, container, false);
        ShowLetterViewModel viewModel = ViewModelProviders.of(this).get(ShowLetterViewModel.class);
        playImageView = root.findViewById(R.id.playImageView);
        htmlView = root.findViewById(R.id.htmlView);

        Bundle arguments = getArguments();
        if (arguments != null) {
            int number = arguments.getInt(KEY_LETTER_NO);
            viewModel.setHtml(number);

            String title = arguments.getString(KEY_LETTER_TITLE);
            setTitle(title);
        }

        viewModel.getHtml().observe(this, s -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                htmlView.setText(Html.fromHtml(s, Html.FROM_HTML_MODE_COMPACT));
            } else {
                htmlView.setText(Html.fromHtml(s));
            }
        });

        setupTTS();

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

        tts.setSpeechRate(speechRate);

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
            myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(AudioManager.STREAM_MUSIC));
            myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "SOME MESSAGE");
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, myHashAlarm);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (tts != null) {
            tts.stop();
        }
    }
}
