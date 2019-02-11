package com.example.testcropper;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yalantis.ucrop.UCrop;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private final int CODE_IMG_GALLERY = 1;
    private final String SAMPLE_CROPPED_IMAGE_NAME = "SampleCrop";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.image);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent().setAction(Intent.ACTION_GET_CONTENT).
                        setType("image/*"), CODE_IMG_GALLERY);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == CODE_IMG_GALLERY) {
            Uri uri = data.getData();
            if (uri != null) {
                startCrop(uri);
            }



        } else if (requestCode == UCrop.REQUEST_CROP) {
            Uri uri = UCrop.getOutput(data);
            if (uri != null) {
                imageView.setImageURI(uri);
                uri = null;
            }
        }
    }


    private void startCrop(@NonNull Uri uri) {
        String destinationFile = SAMPLE_CROPPED_IMAGE_NAME;
        destinationFile += ".jpg";

        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), destinationFile)));

        uCrop.withAspectRatio(1, 1);
        uCrop.withMaxResultSize(250, 250);


        uCrop.withOptions(getCropOptions());
        uCrop.start(MainActivity.this);

    }

    private UCrop.Options getCropOptions() {
        UCrop.Options options = new UCrop.Options();
        options.setCompressionQuality(70);
        options.setHideBottomControls(false);
        options.setFreeStyleCropEnabled(false);

//        options.setStatusBarColor();
//    options.setToolbarColor();
        return options;

    }

}
