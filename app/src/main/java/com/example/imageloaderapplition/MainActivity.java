package com.example.imageloaderapplition;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.dailyyoga.image.ImageLoader;
import com.facebook.drawee.backends.pipeline.Fresco;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        setContentView(R.layout.activity_main);

        final ImageView imageView = findViewById(R.id.sdv_open_screen);

        ImageLoader.with(MainActivity.this)
                .load(R.mipmap.ic_launcher)
                .into(imageView);
    }

    private void initialize() {
        ImageLoader.registerInitializationLoader(new ImageLoader.InitializationLoader() {
            @Override
            public void initialize() {
                if (Fresco.hasBeenInitialized()) return;
                Fresco.initialize(getApplicationContext());
            }
        });
    }
}
