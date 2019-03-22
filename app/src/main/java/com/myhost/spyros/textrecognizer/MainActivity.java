package com.myhost.spyros.textrecognizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE_FOR_TEXT = 1;
    static final int REQUEST_FACE_DETECTION = 2;
    static final int REQUEST_IMAGE_LABELING = 3;
    static final int REQUEST_BARCODE_DETECTOR = 4;

    private Button txtBtn;
    private Button faceBtn;
    private Button imgLblBtn;
    private Button barcodeBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtBtn = findViewById(R.id.txtBtn);
        faceBtn = findViewById(R.id.faceBtn);
        imgLblBtn = findViewById(R.id.imgLblBtn);
        barcodeBtn = findViewById(R.id.barcodeBtn);

        txtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,TextRecognition.class);
                startActivityForResult(intent,REQUEST_IMAGE_CAPTURE_FOR_TEXT);
            }
        });

        faceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,FaceDetection.class);
                startActivityForResult(intent,REQUEST_FACE_DETECTION);
            }
        });

        imgLblBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ImageLabelingActivity.class);
                startActivityForResult(intent,REQUEST_IMAGE_LABELING);
            }
        });

        barcodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,BarcodeScaningActivity.class);
                startActivityForResult(intent,REQUEST_BARCODE_DETECTOR);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(requestCode == REQUEST_IMAGE_CAPTURE_FOR_TEXT && requestCode == RESULT_OK){
            startActivity(intent);
        }
        else if(requestCode == REQUEST_FACE_DETECTION && requestCode == RESULT_OK){
            startActivity(intent);
        }
        else if(requestCode == REQUEST_IMAGE_LABELING && requestCode == RESULT_OK){
            startActivity(intent);
        }
        else if(requestCode == REQUEST_BARCODE_DETECTOR && requestCode == RESULT_OK){
            startActivity(intent);
        }
    }
}
