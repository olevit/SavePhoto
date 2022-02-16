package com.example.savephoto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Intent.ACTION_GET_CONTENT;

public class MainActivity extends AppCompatActivity {

    private TextView infoTextView;
    private ImageView imageView;
    File file;

    final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        infoTextView = findViewById(R.id.text_info);

imageView = findViewById(R.id.imageView);
        Button saveButton;
        saveButton = findViewById(R.id.button_save);
        saveButton.setOnClickListener(btnOnClickListener);
    }

    public File getPublicAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (file.mkdirs()) {
            //if directory not exist
            Toast.makeText(getApplicationContext(),
                    file.getAbsolutePath() + " created",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Directory not created", Toast.LENGTH_LONG).show();
        }
        return file;
    }

    Button.OnClickListener btnOnClickListener = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
            /*Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(ACTION_GET_CONTENT);
            //infoTextView.setText("");*/

            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    //call requestPermissions again
                    Toast.makeText(getApplicationContext(),
                            "should Show Request Permission",
                            Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_WRITE_EXTERNAL_STORAGE);
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_WRITE_EXTERNAL_STORAGE);
                }
            } else {
                // permission granted*/
                writeImage();
            }
        }
            /*startActivityForResult(
                    intent.createChooser(intent, "Выберите картинку"),
                    2);*/

    };

   /* @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted.
                Toast.makeText(getApplicationContext(),
                        "permission was granted, thx:)",
                        Toast.LENGTH_LONG).show();
            } else {
                // permission denied.
                Toast.makeText(getApplicationContext(),
                        "permission denied! Oh:(",
                        Toast.LENGTH_LONG).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }*/

    private void writeImage() {
        //generate a unique file name from timestamp
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyMMdd-hhmmss-SSS");
        String fileName = "img" + simpleDateFormat.format(new Date()) + ".png";

        File dir = getPublicAlbumStorageDir("Cats"); // Album name, save in /Pictures/
        file = new File(dir, fileName);

        FileOutputStream fileOutputStream;
        try{
            fileOutputStream = new FileOutputStream(file);

            imageView.setDrawingCacheEnabled(true);
            Bitmap bitmap = imageView.getDrawingCache();

            bitmap.compress(Bitmap.CompressFormat.PNG, 100,
                    fileOutputStream);

            infoTextView.setText("File saved: " + file.getAbsolutePath());
        }catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),
                    "FileNotFoundException: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }


        //infoTextView.setText("File saved: " + file.getAbsolutePath());


    /*protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 2) {

                writeImage();
                infoTextView.setText("File saved: " + file.getAbsolutePath());
            }
        }
    }*/
}