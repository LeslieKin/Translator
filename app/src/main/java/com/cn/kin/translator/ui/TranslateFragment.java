package com.cn.kin.translator.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.cn.kin.translator.R;
import com.cn.kin.translator.resource.resURL;

public class TranslateFragment extends Fragment {

    private EditText editText;
    private ImageButton clearButton;
    private ProgressBar progressBar;
    private AppCompatButton translateButton;
    public TranslateFragment(){}
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_translate_fragment,container,false);
        initViews(view);

        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequess(editText.getText().toString());
            }
        });
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
        editText=(EditText)view.findViewById(R.id.et_main_input);
        clearButton =(ImageButton)view.findViewById(R.id.ib_clear);
        clearButton.setVisibility(View.INVISIBLE);

        progressBar=(ProgressBar)view.findViewById(R.id.progress_bar);

        translateButton=(AppCompatButton)view.findViewById(R.id.buttonTranslate);
    }

    private void sendRequess(String req){
        progressBar.setVisibility(View.VISIBLE);
        //去掉输入文本中的回车
        req=req.replace("\n","");

        String url= resURL.BING_BASE+"?Word"+req+"&Samples=";


    }
}
