package com.namvar.nederlandsles.ui.settings;

import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.namvar.nederlandsles.R;

public class PrivacyPolicyFragment extends Fragment {

    private TextView htmlView;

    public static PrivacyPolicyFragment newInstance() {
        return new PrivacyPolicyFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        var root = inflater.inflate(R.layout.fragment_privacy_policy, container, false);
        var viewModel = new ViewModelProvider(this).get(PrivacyPolicyViewModel.class);
        htmlView = root.findViewById(R.id.htmlView);

        htmlView.setMovementMethod(new ScrollingMovementMethod());
        viewModel.getHtml().observe(getViewLifecycleOwner(), s -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                htmlView.setText(Html.fromHtml(s, Html.FROM_HTML_MODE_COMPACT));
            } else {
                htmlView.setText(Html.fromHtml(s));
            }
        });

        return root;
    }
}
