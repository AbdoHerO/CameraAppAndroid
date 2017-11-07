package com.example.abdohero.camera;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public ArrayList<ImageView> images= new ArrayList<>(11);
    private ImageView galerie ;
    private Button capture;
    private int index=0;
    private String mCurrentPhotoPath;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private AlertDialog dialog;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        capture = (Button)findViewById(R.id.camera);
        galerie = (ImageView) findViewById(R.id.galerie);
        images.add((ImageView) findViewById(R.id.b1));
        images.add((ImageView) findViewById(R.id.b2));
        images.add((ImageView) findViewById(R.id.b3));
        images.add((ImageView) findViewById(R.id.b4));
        images.add((ImageView) findViewById(R.id.b5));
        images.add((ImageView) findViewById(R.id.b6));
        images.add((ImageView) findViewById(R.id.b7));
        images.add((ImageView) findViewById(R.id.b8));
        images.add((ImageView) findViewById(R.id.b9));
        images.add((ImageView) findViewById(R.id.b10));
        images.add((ImageView) findViewById(R.id.b11));


        capture.setOnClickListener(this);
        for(ImageView images : images){
            images.setOnClickListener(this);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(index==11){
            index=0;
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            images.get(index).setImageBitmap(imageBitmap);
            index++;
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }



    /*public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }*/

    @Override
    public void onClick(View view) {
        if(view.getId()==capture.getId()){
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }

        for(final ImageView images : images){
            if(images.getId()== view.getId()){
                dialog=new AlertDialog.Builder(this).create();
                dialog.setTitle("Save Image");
                dialog.setMessage("You Are Sure save your image?");
                dialog.setButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       // startSave(images);
                        Intent intent2 = new Intent(MainActivity.this,Touch.class);
                        startActivity(intent2);

                    }
                });
                dialog.setButton2("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        }
    }

    public void startSave(ImageView img) {
            FileOutputStream fileOutputStream = null;
            File file = getDisc();
            if (!file.exists() && !file.mkdirs()) {
                Toast.makeText(this, "Can't create disctory to save your image", Toast.LENGTH_LONG).show();
                return;
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String date = simpleDateFormat.format(new Date());
            String name = "Img" + date + ".jpg";
            String filaName = file.getAbsolutePath() + "/" + name;
            File newFile = new File(filaName);
            try {
                fileOutputStream = new FileOutputStream(newFile);
                Bitmap bitmap = ViewToBitmap(img, img.getWidth(), img.getHeight());
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
                Toast.makeText(this, "Save image Success", Toast.LENGTH_LONG).show();
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            refreshGallery(newFile);
    }
    public void refreshGallery(File file){
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(file));
        sendBroadcast(intent);
    }
    private File getDisc(){
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        return new File(file,"Image Demo ");
    }
    public static Bitmap ViewToBitmap(View v,int width,int height) {
        Bitmap b = Bitmap.createBitmap( width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
        return b;
    }

    /*private void galleryAddPic(View view) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }*/

    //---------------------------------------------------------------------------------
    /*
    OutputStream output;
    // Find the SD Card path
    File filepath = Environment.getExternalStorageDirectory();

    // Create a new folder in SD Card
    File dir = new File(filepath.getAbsolutePath()
            + "/WhatSappIMG/");
        dir.mkdirs();

    // Retrieve the image from the res folder
    BitmapDrawable drawable = (BitmapDrawable) principal.getDrawable();
    Bitmap bitmap1 = drawable.getBitmap();

    // Create a name for the saved image
    File file = new File(dir, "Wallpaper.jpg" );

        try {

        output = new FileOutputStream(file);

        // Compress into png format image from 0% - 100%
        bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, output);
        output.flush();
        output.close();

    }

        catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }*/




}
