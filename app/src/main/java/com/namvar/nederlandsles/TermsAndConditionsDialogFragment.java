package com.namvar.nederlandsles;

import static com.namvar.nederlandsles.MainActivity.TERMS_AND_CONDITIONS_KEY;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.DialogFragment;


public class TermsAndConditionsDialogFragment extends DialogFragment {

    public static final String PRIVACY_POLICY = """                   
             <h1>Privacy Policy for "Nederlandse Les" App</h1>
                            
             <p>This Privacy Policy outlines how personal information is collected, used, and shared when you use the "Nederlandse Les" mobile application (the "App").</p>
                            
                 <h2>Information We Collect</h2>
                 <p>While using the App, we may ask you to provide certain personally identifiable information that can be used to contact or identify you. This may include your name, email address, and any other information you choose to provide.</p>
                 <p>We may collect information about your mobile device, including the device model, operating system, unique device identifiers, and mobile network information.</p>
                 <p>We collect information about how you interact with the App, such as the features you use and the buttons you click.</p>
                            
                 <h2>How We Use Your Information</h2>
                 <p>We use the information collected to deliver and maintain the App's functionality.</p>
                 <p>We use the information to understand how users interact with the App and to enhance and personalize your experience.</p>
                 <p>We may use your contact information to communicate with you about updates, announcements, and important information related to the App.</p>
                            
                 <h2>Data Security</h2>
                 <p>We prioritize the security of your personal information and employ industry-standard measures to protect it from unauthorized access, disclosure, alteration, and destruction.</p>
                            
                 <h2>Third-Party Services</h2>
                 <p>The App may use third-party services for analytics, advertising, or other purposes. These services may collect and use information in accordance with their own privacy policies. We recommend reviewing the privacy policies of these third-party services.</p>
                            
                 <h2>Your Choices</h2>
                 <p>You can choose to opt-out of certain data collection by adjusting your device settings or discontinuing use of the App.</p>
                            
                 <h2>Changes to This Privacy Policy</h2>
                 <p>We may update this Privacy Policy to reflect changes in our practices or for other operational, legal, or regulatory reasons.</p>
                            
                 <h2>Contact Us</h2>
                 <p>If you have any questions or concerns about this Privacy Policy, please contact us at <a href="mailto:namvar@gmail.com">namvar@gmail.com</a>.</p>
                            
                 <h2>Effective Date</h2>
                 <p>November 10, 2023</p>
                           
            """;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_terms_and_conditions, null);

        // Set up the TextView with HTML content
        TextView textViewContent = view.findViewById(R.id.textViewContent);
        CharSequence styledText = HtmlCompat.fromHtml(PRIVACY_POLICY, HtmlCompat.FROM_HTML_MODE_LEGACY);
        textViewContent.setText(styledText);
        textViewContent.setMovementMethod(LinkMovementMethod.getInstance());

        builder.setView(view);

        // Set up buttons
        Button buttonAgree = view.findViewById(R.id.buttonAgree);
        Button buttonDisagree = view.findViewById(R.id.buttonDisagree);

        buttonAgree.setOnClickListener(v -> {
            Log.d(TERMS_AND_CONDITIONS_KEY, "Handle positive button click");
            setTermsAndConditionsAccepted();
            dismiss();
        });

        buttonDisagree.setOnClickListener(v -> {
            Log.d(TERMS_AND_CONDITIONS_KEY, "Handle negative button click");
            requireActivity().finish();
        });

        return builder.create();
    }

    private void setTermsAndConditionsAccepted() {
        if (getActivity() != null) {
            SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(TERMS_AND_CONDITIONS_KEY, true);
            editor.apply();
        } else {
            Log.d(TERMS_AND_CONDITIONS_KEY, "getActivity is null");
        }
    }

}