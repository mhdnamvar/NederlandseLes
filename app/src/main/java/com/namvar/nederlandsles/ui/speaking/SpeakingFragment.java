package com.namvar.nederlandsles.ui.speaking;

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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.namvar.nederlandsles.R;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SpeakingFragment extends Fragment {

    private TextToSpeech tts;
    private final static String KEY_DUTCH = "nl_NL";
    private static final float speechRate = 0.6f;

    public static SpeakingFragment newInstance() {
        return new SpeakingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        SpeakingViewModel viewModel = new ViewModelProvider(this).get(SpeakingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_speaking, container, false);
        ListView items = root.findViewById(R.id.speakingItems);
        items.setDivider(null);
        viewModel.getItems().observe(getViewLifecycleOwner(), strings -> {
            ArrayAdapter<String> adapter = getStringArrayAdapter(strings);
            items.setAdapter(adapter);
        });

        setupTTS();

        items.setOnItemClickListener((parent, view, position, id) -> {
            String text = ((TextView) (view)).getText().toString();
            if (text.length() > 2) {
                speak(text.substring(text.indexOf(".")+1));
            }
        });

        return root;
    }

    private ArrayAdapter<String> getStringArrayAdapter(List<String> strings) {
        return new ArrayAdapter<String>(requireContext(),
                android.R.layout.simple_list_item_1, strings) {

                    @SuppressLint("SetTextI18n")
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView textView= view.findViewById(android.R.id.text1);
                        textView.setTextColor(Color.DKGRAY);
                        textView.setText("<b>"+ (position+1) + ". </b>" + textView.getText());
                        textView.setHeight(300);
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

    }

    private void speak(String text) {
        if(text.length() == 0) {
            Toast.makeText(getContext(),
                    "There is no text to convert to speech!",
                    Toast.LENGTH_SHORT).show();
        } else {
            HashMap<String, String> myHashAlarm = new HashMap<>();
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
