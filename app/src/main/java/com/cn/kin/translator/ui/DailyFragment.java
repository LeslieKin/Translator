package com.cn.kin.translator.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.cn.kin.translator.R;
import com.cn.kin.translator.database.DBUtil;
import com.cn.kin.translator.database.NoteBook;
import com.cn.kin.translator.resource.RESURL;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.CLIPBOARD_SERVICE;

public class DailyFragment extends Fragment {

    private RequestQueue requestQueue;//请求队列

    private TextView cTextView;
    private TextView eTextView;
    private ImageView navImage;
    private ImageView ivLove;
    private ImageView ivCopy;
    private ImageView ivShare;

    boolean isMarked;

    private String imageURL=null;

    public DailyFragment(){

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue= Volley.newRequestQueue(getActivity().getApplicationContext());
    }

    @Nullable
    //通过LayoutInflater的inflate（）方法动态加载布局
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_daily_fragment,container,false);

        initViews(view);

        sendRequest();

        ivLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isMarked){
                    ivLove.setImageResource(R.drawable.ic_favorite_white_24dp);
                    Snackbar.make(ivLove, R.string.add_collection, Snackbar.LENGTH_SHORT).show();
                    isMarked=true;
                    NoteBook book=new NoteBook();
                    book.setInput(eTextView.getText().toString());
                    book.setOutput(cTextView.getText().toString());
                    DBUtil.insertValue(book);
                }else{
                    ivLove.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                    Snackbar.make(ivLove, R.string.removeFrom_collection, Snackbar.LENGTH_SHORT).show();
                    isMarked=false;
                    DBUtil.deleteValue(eTextView.getText().toString());
                }
            }
        });

        ivCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager manager = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text", String.valueOf(eTextView.getText() + "\n" + cTextView.getText()));
                manager.setPrimaryClip(clipData);

                Snackbar.make(ivCopy,R.string.copy_finish,Snackbar.LENGTH_SHORT)
                        .show();
            }
        });

        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_SEND).setType("text/plain");//分享文字
                intent.putExtra(Intent.EXTRA_TEXT,String.valueOf(eTextView.getText() + "\n" + cTextView.getText()));
                startActivity(Intent.createChooser(intent,getString(R.string.choose_app_share)));
            }
        });
        return view;
    }

    private void initViews(View view){

        cTextView=(TextView)view.findViewById(R.id.text_view_chi);
        eTextView=(TextView)view.findViewById(R.id.text_view_eng);
        navImage=(ImageView)view.findViewById(R.id.image_view_daily);

        ivLove=(ImageView)view.findViewById(R.id.image_view_mark_love);
        ivCopy=(ImageView)view.findViewById(R.id.image_view_copy);
        ivShare=(ImageView)view.findViewById(R.id.image_view_share);

    }

    private void sendRequest(){
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, RESURL.DAILY_SENTENCE, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                try {
                    imageURL=jsonObject.getString("picture2");

                    //需要添加闭包，Glide是一个图形加载库。注意其与Picasso的区别
                    Glide.with(getActivity())
                            .load(imageURL)
                            .asBitmap()
                            .centerCrop()
                            .into(navImage);

                    eTextView.setText(jsonObject.getString("content"));
                    cTextView.setText(jsonObject.getString("note"));

                    if(DBUtil.queryIfItemIsExist(eTextView.getText().toString())){
                        ivLove.setImageResource(R.drawable.ic_favorite_white_24dp);
                        isMarked=true;
                    }else{
                        ivLove.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                        isMarked=false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
       requestQueue.add(request);
    }
}
