package yml.com.rbpoc;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by sagarsrao on 15-07-2017.
 */

public class ShareLicenseFragment extends Fragment {


    private RadioGroup radioGroup;

    private RadioButton radioButton0, radioButton1;

    private Button mShareButton;

    private TextView mhowToShare;

    private EditText meditSMS, meditEmail;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        final View view = inflater.inflate(R.layout.fragment_layout_sharelicense, container, false);
        mhowToShare = (TextView) view.findViewById(R.id.tv_how_to_share_license);
        meditSMS = (EditText) view.findViewById(R.id.ed_sms);
        meditEmail = (EditText) view.findViewById(R.id.ed_email_id);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        mShareButton = (Button) view.findViewById(R.id.share_button);
        radioButton0 = (RadioButton) view.findViewById(R.id.radio0);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "OFFISFB.ttf");
        radioButton0.setTypeface(font);
        radioButton1 = (RadioButton) view.findViewById(R.id.radio1);
        radioButton1.setTypeface(font);
        mhowToShare.setTypeface(font);
        mShareButton.getBackground().setAlpha(128);//Setting the opacity to the 50%value
        meditSMS.setVisibility(View.INVISIBLE);
        meditEmail.setVisibility(View.INVISIBLE);


        radioButton0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getActivity(), buttonView.getText().toString(), Toast.LENGTH_SHORT).show();
                    meditSMS.setEnabled(true);
                    meditSMS.setVisibility(View.VISIBLE);
                    Selection.setSelection(meditSMS.getText(), meditSMS.getText().length());
                    meditSMS.setText("+");

                    meditSMS.addTextChangedListener(new TextWatcher() {
                        final int[] blockLengths = new int[]{3, 2, 5, 4};
                        String mUnformatted = "";

                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

                            String unformattedSeq = s.toString().replace("-", "");
                            if (mUnformatted.length() == unformattedSeq.length()) {
                                return; //length of text has not changed
                            }

                            mUnformatted = unformattedSeq;
                            //formatting sequence
                            StringBuilder formatted = new StringBuilder();
                            int blockIndex = 0;
                            int currentBlock = 0;
                            for (int i = 0; i < mUnformatted.length(); ++i) {
                                if (currentBlock == blockLengths[blockIndex]) {
                                    formatted.append("-");
                                    currentBlock = 0;
                                    blockIndex++;
                                }
                                formatted.append(mUnformatted.charAt(i));
                                currentBlock++;
                            }

                            meditSMS.setText(formatted.toString());
                            meditSMS.setSelection(formatted.length());
                            //mShareButton.setVisibility(View.VISIBLE);


                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                            if (!s.toString().startsWith("+")) {
                                meditSMS.setText("+");
                                Selection.setSelection(meditSMS.getText(), meditSMS.getText().length());

                            }
                            enableShareButtonIfReady();
                        }
                    });

                }
            }
        });

        radioButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getActivity(), buttonView.getText().toString(), Toast.LENGTH_SHORT).show();

                }
            }
        });


        return view;
    }

    private void enableShareButtonIfReady() {

        boolean isReady = meditSMS.getText().toString().length()>10;
        if(isReady){
        mShareButton.setEnabled(isReady);
        mShareButton.setBackground(getActivity().getDrawable(R.drawable.round_radio_button));}
        else
        mShareButton.getBackground().setAlpha(128);//Setting the opacity to the 50%value




    }


}
