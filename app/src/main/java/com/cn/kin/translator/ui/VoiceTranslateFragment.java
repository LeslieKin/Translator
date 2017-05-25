package com.cn.kin.translator.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.cn.kin.translator.R;

public class VoiceTranslateFragment extends Fragment {
    private EditText editText;
    private ImageButton clearButton;
    public VoiceTranslateFragment(){}
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_voice_translate_fragment,container,false);
        initViews(view);

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(editText.getText().toString().length()!=0){
                    clearButton.setVisibility(View.VISIBLE);
                }else{
                    clearButton.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    private void initViews(View view){
        editText=(EditText)view.findViewById(R.id.vet_main_input);
        clearButton =(ImageButton)view.findViewById(R.id.vib_clear);
        clearButton.setVisibility(View.INVISIBLE);
    }
}
