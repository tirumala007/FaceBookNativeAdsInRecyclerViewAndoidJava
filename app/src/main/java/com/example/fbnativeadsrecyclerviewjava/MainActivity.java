package com.example.fbnativeadsrecyclerviewjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    //add these(below steps) in manifest file application tag

    //step 1-> set android:largeHeap="true"
    //step 2-> set android:hardwareAccelerated="true"
    //step 3-> create application class, extends application, initialize facebook ads there
    //android:name=".MyAppliation"


    //this step is for version 24, mine  phone's is  23,so i don't need it
    //step 4->  create a xml file in RES
    // android:networkSecurityConfig="@xml/network_security_config"

    //counter to add a ad
    int adCounter=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         RecyclerView rv = findViewById(R.id.rv);


        //set layout to linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rv.setLayoutManager(linearLayoutManager);



        //making the model list to pass to the adapter
        List<MainModel> modelList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            modelList.add(new MainModel("This contains Native Ads ", "From Facebook Audience Network " ,"After every 5th item",
                    "Current item "+ i));

            ///check if the current item is 5th, add a null object, so that we can inflate native ad in adapter
            if(adCounter%5==0)
            {


                //we will check this, later in get item vew type to add native ads to recycler view
                modelList.add(null);
            }
            //increase the counter after every model item
            adCounter++;

        }

        // create the adapter instance
        MyCustomAdapter adapter = new MyCustomAdapter(MainActivity.this, modelList );

        //set the adapter to reycler view
        rv.setAdapter(adapter);
    }
}