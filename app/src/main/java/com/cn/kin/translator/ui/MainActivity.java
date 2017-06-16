package com.cn.kin.translator.ui;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.cn.kin.translator.R;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private  NavigationView navView;
    private Toolbar toolbar;

    private DrawerLayout mDrawerLayout;

    private TranslateFragment translateFragment;
    private NotebookFragment  notebookFragment;
    private DailyFragment dailyFragment;
    private VoiceTranslateFragment voiceTranslateFragment;

    private static final String ACTION_VOICE_TRANSLATE="con.cn.kin.translator.voicetranslate";
    private static final String ACTION_NOTEBOOK="com.cn.kin.translator.notebook";
    private static final String ACTION_DAILY_ONE="com.cn.kin.translator.dailyone";

    static String mp3Link;
    private MediaPlayer mediaPlayer;
    private ImageView imageViewMp3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        if(savedInstanceState!=null){
            FragmentManager manager=getSupportFragmentManager();
            notebookFragment=(NotebookFragment)manager.getFragment(savedInstanceState,"notebookFragment");
            dailyFragment=(DailyFragment)manager.getFragment(savedInstanceState,"dailyFragment");
            voiceTranslateFragment=(VoiceTranslateFragment)manager.getFragment(savedInstanceState,"voiceTranslateFragment");
            translateFragment=(TranslateFragment) manager.getFragment(savedInstanceState,"translateFragment");
        }else{
            notebookFragment=new NotebookFragment();
            dailyFragment=new DailyFragment();
            voiceTranslateFragment=new VoiceTranslateFragment();
            translateFragment=new TranslateFragment();
        }

        FragmentManager manager=getSupportFragmentManager();

        manager.beginTransaction().add(R.id.container_main,translateFragment,"translateFragment").commit();
        manager.beginTransaction().add(R.id.container_main,voiceTranslateFragment,"voiceTranslateFragment").commit();
        manager.beginTransaction().add(R.id.container_main,notebookFragment,"notebookFragment").commit();
        manager.beginTransaction().add(R.id.container_main,dailyFragment,"dailyFragment").commit();

        //用于隐藏多重界面叠加的情况
        Intent intent=getIntent();
        if(intent.getAction().equals(ACTION_VOICE_TRANSLATE)){
            showHideFragment(1);
        }else if(intent.getAction().equals(ACTION_DAILY_ONE)){
            showHideFragment(2);
        }else if(intent.getAction().equals(ACTION_NOTEBOOK)){
            showHideFragment(3);
        }else{
            showHideFragment(0);
        }

    }

    private void initViews(){
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navView =(NavigationView)findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);
        navView.setCheckedItem(R.id.nav_translate);

       imageViewMp3=(ImageView)findViewById(R.id.action_listen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        if (id == R.id.action_listen) {
            Toast.makeText(MainActivity.this, mp3Link, Toast.LENGTH_SHORT).show();
            //imageViewMp3.setColorFilter(getResources().getColor(R.color.colorPrimaryDark));
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (mp3Link == null) {
                        Toast.makeText(MainActivity.this, R.string.play_error, Toast.LENGTH_SHORT).show();
                    } else {
                        Uri uri = Uri.parse(mp3Link);
                        Log.w("MainActivity", "STEP_ONE");
                        try {
                            mediaPlayer=new MediaPlayer();
                            mediaPlayer.setDataSource(MainActivity.this, uri);
                            mediaPlayer.prepare();
                            Log.w("MainActivity", "STEP_TWO");
                            Toast.makeText(MainActivity.this, "Preparing!", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (!mediaPlayer.isPlaying()) {
                            mediaPlayer.start();
                            Log.w("MainActivity", "STEP_THREE");
                        }
                    }
                }

            }).start();
        }

        else if (id== R.id.action_search){
            startActivity(new Intent(MainActivity.this, SearchActivity.class));
        }

        else if(id==R.id.action_setting){

        }

        else if(id==R.id.action_about){

        }

        else if(id==R.id.action_exit){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();

        if(id==R.id.nav_translate){
            showHideFragment(0);
            Toast.makeText(MainActivity.this,"Click the nav_translate",Toast.LENGTH_SHORT).show();

        }else if(id==R.id.nav_voice){
            showHideFragment(1);
            Toast.makeText(MainActivity.this,"Click the nav_voice",Toast.LENGTH_SHORT).show();

        }else if(id==R.id.nav_daily_one){
            showHideFragment(2);
            Toast.makeText(MainActivity.this,"Click the nav_daily_one",Toast.LENGTH_SHORT).show();

        }else if(id==R.id.nav_notebook){
            showHideFragment(3);
            Toast.makeText(MainActivity.this,"Click the nav_notebook",Toast.LENGTH_SHORT).show();

        }else if(id==R.id.nav_setting){
            Toast.makeText(MainActivity.this,"Click the nav_setting",Toast.LENGTH_SHORT).show();

        }else if(id==R.id.nav_about){
            Toast.makeText(MainActivity.this,"Click the nav_about",Toast.LENGTH_SHORT).show();
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showHideFragment(@IntRange(from = 0, to = 3) int position) {

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().hide(translateFragment).commit();
        manager.beginTransaction().hide(voiceTranslateFragment).commit();
        manager.beginTransaction().hide(notebookFragment).commit();
        manager.beginTransaction().hide(dailyFragment).commit();

        if (position == 0) {
            manager.beginTransaction().show(translateFragment).commit();
            toolbar.setTitle(R.string.app_name);
            navView.setCheckedItem(R.id.nav_translate);

        } else if (position==1){
            manager.beginTransaction().show(voiceTranslateFragment).commit();
            toolbar.setTitle(R.string.voice_input);
            navView.setCheckedItem(R.id.nav_voice);

        }
        else if (position == 2) {
            toolbar.setTitle(R.string.daily_one);
            manager.beginTransaction().show(dailyFragment).commit();
            navView.setCheckedItem(R.id.nav_daily_one);

        } else if (position ==3) {
            toolbar.setTitle(R.string.notebook);
            manager.beginTransaction().show(notebookFragment).commit();
            navView.setCheckedItem(R.id.nav_notebook);

        }
    }

    public static void prepareSound(String mp3Link){

    }


}
