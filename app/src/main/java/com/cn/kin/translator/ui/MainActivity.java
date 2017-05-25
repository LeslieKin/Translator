package com.cn.kin.translator.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cn.kin.translator.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private  NavigationView navView;
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private TranslateFragment translateFragment;
    private NotebookFragment  notebookFragment;
    private DailyFragment dailyFragment;
    private VoiceTranslateFragment voiceTranslateFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        if(savedInstanceState!=null){
            FragmentManager manager=getSupportFragmentManager();
            translateFragment=(TranslateFragment) manager.getFragment(savedInstanceState,"translateFragment");
            notebookFragment=(NotebookFragment)manager.getFragment(savedInstanceState,"notebookFragment");
            dailyFragment=(DailyFragment)manager.getFragment(savedInstanceState,"dailyFragment");
            voiceTranslateFragment=(VoiceTranslateFragment)manager.getFragment(savedInstanceState,"voiceTranslateFragment");

        }else{
            translateFragment=new TranslateFragment();
            notebookFragment=new NotebookFragment();
            dailyFragment=new DailyFragment();
            voiceTranslateFragment=new VoiceTranslateFragment();
        }

        FragmentManager manager=getSupportFragmentManager();

        manager.beginTransaction().add(R.id.container_main,translateFragment,"translateFragment").commit();
        manager.beginTransaction().add(R.id.container_main,voiceTranslateFragment,"voiceTranslateFragment").commit();
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
        navView.setCheckedItem(R.id.nav_translate);
        navView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        if (id== R.id.action_search){
            startActivity(new Intent(MainActivity.this, SearchActivity.class));
        }else if(id==R.id.action_setting){

        }else if(id==R.id.action_about){

        }else if(id==R.id.action_exit){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();

        if(id==R.id.nav_translate){
            toolbar.setTitle(R.string.app_name);
            Toast.makeText(MainActivity.this,"Click the nav_translate",Toast.LENGTH_SHORT).show();
            showHideFragment(0);

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
            mDrawerLayout.closeDrawers();
        } else if (position==1){
            manager.beginTransaction().show(voiceTranslateFragment).commit();
            toolbar.setTitle(R.string.voice_input);
            mDrawerLayout.closeDrawers();
        }
        else if (position == 2) {
            toolbar.setTitle(R.string.daily_one);
            manager.beginTransaction().show(dailyFragment).commit();
            navView.setCheckedItem(R.id.nav_daily_one);
            mDrawerLayout.closeDrawers();
        } else if (position ==3) {
            toolbar.setTitle(R.string.notebook);
            manager.beginTransaction().show(notebookFragment).commit();
            navView.setCheckedItem(R.id.nav_notebook);
            mDrawerLayout.closeDrawers();
        }
    }
}
