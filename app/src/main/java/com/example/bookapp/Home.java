package com.example.bookapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import nl.joery.animatedbottombar.AnimatedBottomBar;
import profileuser.ProfileUser;

public class Home extends AppCompatActivity {
    FragmentManager fragmentManager;
    ImageButton imgButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imgButton = findViewById(R.id.imagebtn1);

        AnimatedBottomBar animatedBottomBar = (AnimatedBottomBar) findViewById(R.id.bottomnavi);
        //set bắt đầu là trang home đầu tiên
        if(savedInstanceState == null)
        {
            fragmentManager = getSupportFragmentManager();
            Homefragment homefragment = new Homefragment();
            fragmentManager.beginTransaction().replace(R.id.fragcontainer,homefragment).commit();
        }
        animatedBottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NonNull AnimatedBottomBar.Tab newTab) {
                Fragment fragment = null;
                switch (newTab.getId())
                {
                    case R.id.home:
                        fragment = new Homefragment();
                        break;
                    case  R.id.search:
                        fragment = new Searchfragment();
                        break;
                    case  R.id.library:
                        fragment = new Libraryfragment();
                        break;
                    case  R.id.bell:
                        fragment = new Notififragment();
                        break;
                }
                if(fragment != null)
                {
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragcontainer,fragment).commit();
                }else {
                }
            }

            @Override
            public void onTabReselected(int i, @NonNull AnimatedBottomBar.Tab tab) {

            }
        });
    }
    public void Profile(View view)
    {
        Intent intent = new Intent(Home.this, ProfileUser.class);
        startActivity(intent);
    }
}