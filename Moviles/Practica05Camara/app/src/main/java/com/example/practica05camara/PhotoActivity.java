package com.example.practica05camara;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class PhotoActivity extends AppCompatActivity {

    Button takePhoto;
    ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        photo = findViewById(R.id.imgPhoto);
        takePhoto = findViewById(R.id.btnFoto);

        takePhoto.setOnClickListener(view -> cameraLauncher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE)));

    }//onCreate

    ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

            if(result.getResultCode() == RESULT_OK){
                assert result.getData() != null;
                Bundle extras = result.getData().getExtras();
                Bitmap imgBitmap = (Bitmap) extras.get("data");
                photo.setImageBitmap(imgBitmap);
            }

        }
    });//cameraLauncher

}//photo