package com.cn.kin.translator.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cn.kin.translator.R;
import com.cn.kin.translator.adapter.SampleAdapter;
import com.cn.kin.translator.database.DBUtil;
import com.cn.kin.translator.database.NoteBook;
import com.cn.kin.translator.model.BingModel;
import com.cn.kin.translator.resource.RESURL;
import com.cn.kin.translator.util.NetworkUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.litepal.LitePal;

import java.util.ArrayList;

import static android.content.Context.CLIPBOARD_SERVICE;
import static com.cn.kin.translator.ui.MainActivity.mp3Link;

public class TranslateFragment extends Fragment {

    private EditText editText;
    private ImageButton clearButton;

    private ImageView imageViewMark;
    private ImageView imageViewCopy;
    private ImageView imageViewShare;

    private ProgressBar progressBar;
    private AppCompatButton translateButton;
    private View viewResult;
    private TextView textViewResult;

    private ArrayList<BingModel.Sample> samples;
    private RecyclerView recyclerView;
    private SampleAdapter sampleAdapter;
    private boolean showSamples;

    private BingModel bingModel;
    private String result;

    private Boolean isMarked = false;

    private RequestQueue queue;

//    static String mp3Link=null;
//    static MediaPlayer mediaPlayer;

    public TranslateFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        LitePal.getDatabase();//创建数据库
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_translate_fragment, container, false);
        initViews(view);

        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // LitePal.getDatabase();
                if (!NetworkUtil.isNetworkConnected(getActivity())) {
                    showNoNetwork();
                }else if (editText.getText() == null || editText.getText().length() == 0) {
                    Snackbar.make(translateButton, getString(R.string.no_input), Snackbar.LENGTH_SHORT)
                            .show();
                } else {
                   // Toast.makeText(getActivity(),editText.getText().toString()+"begin translating",Toast.LENGTH_SHORT);
                    Snackbar.make(translateButton, editText.getText().toString()+" begin translating", Snackbar.LENGTH_SHORT)
                            .show();
                    sendRequest(editText.getText().toString());
                }
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

                if (editText.getText().toString().length() != 0) {
                    clearButton.setVisibility(View.VISIBLE);
                } else {
                    clearButton.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imageViewMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isMarked){
                    imageViewMark.setImageResource(R.drawable.ic_favorite_white_24dp);
                    Snackbar.make(translateButton, R.string.add_collection, Snackbar.LENGTH_SHORT).show();
                    isMarked=true;
                    NoteBook book=new NoteBook();
                    book.setInput(bingModel.getWord());
                    book.setOutput(result);
                    DBUtil.insertValue(book);
                }else{
                    imageViewMark.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                    Snackbar.make(translateButton, R.string.removeFrom_collection, Snackbar.LENGTH_SHORT).show();
                    isMarked=false;
                    DBUtil.deleteValue(bingModel.getWord());
                }
            }
        });

        imageViewCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager manager = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text", result);
                manager.setPrimaryClip(clipData);

                Snackbar.make(translateButton,R.string.copy_finish,Snackbar.LENGTH_SHORT)
                        .show();
            }
        });

        imageViewShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_SEND).setType("text/plain");//分享文字
                intent.putExtra(Intent.EXTRA_TEXT,result);
                startActivity(Intent.createChooser(intent,getString(R.string.choose_app_share)));
            }
        });
        return view;
    }

    private void initViews(View view) {
        editText = (EditText) view.findViewById(R.id.et_main_input);
        clearButton = (ImageButton) view.findViewById(R.id.ib_clear);
        clearButton.setVisibility(View.INVISIBLE);

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        imageViewMark = (ImageView) view.findViewById(R.id.image_view_mark_star);
        translateButton = (AppCompatButton) view.findViewById(R.id.buttonTranslate);

        //获取RecyclerView实例，指定RecyclerView的布局为线性布局
        /*recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);*/
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);

        //下栏 收藏 复制 分享
        imageViewMark=(ImageView)view.findViewById(R.id.image_view_mark_star);
        imageViewCopy=(ImageView)view.findViewById(R.id.image_view_copy);
        imageViewShare=(ImageView)view.findViewById(R.id.image_view_share);

        viewResult = view.findViewById(R.id.include);
        textViewResult = (TextView) view.findViewById(R.id.text_view_output);

    }


    private void sendRequest(String req) {

        Log.w("TranslateFragment", "Begin Translateing!");
        progressBar.setVisibility(View.VISIBLE);//设置进度条为可见
        viewResult.setVisibility(View.INVISIBLE);//设置例子不可见

        //去掉输入文本中的回车
        req = req.replace("\n", "");

        // 监听输入面板的情况，如果激活则隐藏,用于控制显示或隐藏输入法面板的类
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(translateButton.getWindowToken(), 0);
        }


        //http://xtk.azurewebsites.net/BingDictService.aspx?Word=Hello&Samples=true
        String url = RESURL.BING_BASE + "?Word=" + req + "&Samples=";
        //Log.w("TranslateFragment", "Your url1 is "+url);
        
        if (showSamples) {
            url += "true";
        } else {
            url += "false";
        }

       // Log.w("TranslateFragment", "Your url2 is "+url);

       /* textViewResult.setText("begint to translate");
        textViewResult.setVisibility(View.VISIBLE);*/
        //Log.w("TranslateFragment","Begin translateing!!!!");
        StringRequest request = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                      //  Log.w("TranslateFragment","Begin translateing1111!!!!");
                        try {
                            Gson gson = new Gson();
                            bingModel = gson.fromJson(s, BingModel.class);
                            
                            if (bingModel != null) {

                                result = bingModel.getWord() + "\n";
                              //  Log.w("TranslateFragment", "The word is "+result);
                                
                                if (DBUtil.queryIfItemIsExist(bingModel.getWord())) {
                                    isMarked = true;
                                } else {
                                    isMarked = false;
                                }

                              //  Log.w("TranslateFragment", "Enter the Database step");

                                if (bingModel.getPronunciation() != null) {
                                    BingModel.Pronunciation p = bingModel.getPronunciation();
                                    result = result + "\nAmE:" + p.getAmE() + "\nBrE:" + p.getBrE() + "\n";
                                    mp3Link=p.getAmEmp3();
                                }


                              //  Log.w("TranslateFragment", "STEP_ONE");

                                for (BingModel.Definition def : bingModel.getDefs()) {
                                    result = result + def.getPos() + "\n" + def.getDef() + "\n";
                                }

                               // Log.w("TranslateFragment", "STEP_TWO");

                                result = result.substring(0, result.length() - 1);

                                if (bingModel.getSams() != null && bingModel.getSams().size() != 0) {

                                    if (samples == null) {
                                        samples = new ArrayList<>();
                                    }

                                    samples.clear();

                                    for (BingModel.Sample sample : bingModel.getSams()) {
                                        samples.add(sample);
                                    }

                                    if (sampleAdapter == null) {
                                        sampleAdapter = new SampleAdapter(getActivity(), samples);
                                        recyclerView.setAdapter(sampleAdapter);
                                    } else {
                                        sampleAdapter.notifyDataSetChanged();
                                    }

                                   // Log.w("TranslateFragment", "STEP_THREE");
                                    progressBar.setVisibility(View.INVISIBLE);
                                    viewResult.setVisibility(View.VISIBLE);

                                    textViewResult.setText(result);

//                                    new Thread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                           if(mp3Link!=null){
//                                               Uri uri = Uri.parse(mp3Link);
//                                               try {
//                                                   MainActivity.mediaPlayer=new MediaPlayer();
//                                                   MainActivity.mediaPlayer.setDataSource(getActivity(), uri);
//                                                   MainActivity.mediaPlayer.prepare();
//                                               } catch (IOException e) {
//                                                   e.printStackTrace();
//                                               }
//                                               Log.w("TranslateFragment", "STEP_ONE");
//                                           }
//                                        }
//                                    }).start();

                                }
                            }
                        } catch (JsonSyntaxException ex) {
                            showTranErrorMsg();
                        }

                        progressBar.setVisibility(View.GONE);
                    }


                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressBar.setVisibility(View.GONE);
                showTranErrorMsg();
            }
        });

            queue.add(request);
    }

    private void showNoNetwork() {
        Snackbar.make(translateButton, R.string.no_network, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.settings, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Settings.ACTION_SETTINGS));
                    }
                }).show();
    }


    private void showTranErrorMsg() {
        Snackbar.make(translateButton, R.string.tran_error, Snackbar.LENGTH_SHORT).show();
    }


    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
    //    completeWithEnter = sp.getBoolean("enter_key", false);
        showSamples = sp.getBoolean("samples", true);
        if (samples != null) {
            samples.clear();
        }
        if (sampleAdapter != null) {
            sampleAdapter.notifyDataSetChanged();
        }
    }
}
