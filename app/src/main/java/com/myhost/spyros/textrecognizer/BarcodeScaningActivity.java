package com.myhost.spyros.textrecognizer;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.util.List;

import dmax.dialog.SpotsDialog;

public class BarcodeScaningActivity extends AppCompatActivity {

    //    CameraView cameraView;
//    Button barcodeScan;
//    AlertDialog waitingDialog;
//    GraphicOverlay graphicOverlay;
    SurfaceView surfaceView;
    CameraSource cameraSource;
    TextView textView;
    BarcodeDetector barcodeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scaning);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        surfaceView = (SurfaceView) findViewById(R.id.camera_preview);
        textView = (TextView) findViewById(R.id.textView);

        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(640, 480)
                .setAutoFocusEnabled(true).build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(BarcodeScaningActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                try{
                    cameraSource.start(surfaceHolder);
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrCodes = detections.getDetectedItems();
                if(qrCodes.size() != 0){
                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            Vibrator vibrator = (Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(1000);
                            textView.setText(qrCodes.valueAt(0).displayValue);
                        }
                    });
                }
            }
        });

//        cameraView = (CameraView)findViewById(R.id.camera_view);
//        barcodeScan = (Button)findViewById(R.id.barcodeDetect);
//        graphicOverlay = (GraphicOverlay)findViewById(R.id.graphic_overlay);
//        waitingDialog = new SpotsDialog.Builder().setContext(this).setMessage("Please wait...").setCancelable(false).build();
//
//        barcodeScan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                cameraView.start();
//                cameraView.captureImage();
//                graphicOverlay.clear();
//            }
//        });
//
//        cameraView.addCameraKitListener(new CameraKitEventListener() {
//            @Override
//            public void onEvent(CameraKitEvent cameraKitEvent) {
//
//            }
//
//            @Override
//            public void onError(CameraKitError cameraKitError) {
//
//            }
//
//            @Override
//            public void onImage(CameraKitImage cameraKitImage) {
//                waitingDialog.show();
//                Bitmap bitmap = cameraKitImage.getBitmap();
//                bitmap = Bitmap.createScaledBitmap(bitmap,cameraView.getWidth(),cameraView.getHeight(),false);
//                cameraView.stop();
//                runDetector(bitmap);
//            }
//
//            @Override
//            public void onVideo(CameraKitVideo cameraKitVideo) {
//
//            }
//        });
    }

//    private void runDetector(Bitmap bitmap) {
//        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
//        FirebaseVisionBarcodeDetectorOptions options =
//                new FirebaseVisionBarcodeDetectorOptions.Builder()
//                        .setBarcodeFormats(
//                                FirebaseVisionBarcode.FORMAT_QR_CODE,
//                                FirebaseVisionBarcode.FORMAT_AZTEC)
//                        .build();
//        FirebaseVisionBarcodeDetector detector = FirebaseVision.getInstance().getVisionBarcodeDetector(options);
//
//        detector.detectInImage(image).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
//            }
//        }).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionBarcode>>() {
//            @Override
//            public void onSuccess(List<FirebaseVisionBarcode> firebaseVisionBarcodes) {
//                processResult(firebaseVisionBarcodes);
//            }
//        });
//
//    }
//
//    private void processResult(List<FirebaseVisionBarcode> firebaseVisionBarcodes) {
//        for(FirebaseVisionBarcode item: firebaseVisionBarcodes){
//
//            //Draw Rect
//            Rect rectBounds = item.getBoundingBox();
//            RectOverlay rectOverlay = new RectOverlay(graphicOverlay,rectBounds);
//            graphicOverlay.add(rectOverlay);
//
//
//            int value_type = item.getValueType();
//            switch (value_type){
//                case FirebaseVisionBarcode.TYPE_TEXT:{
//                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                    builder.setMessage(item.getRawValue());
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            dialogInterface.dismiss();
//                        }
//                    });
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//                    Log.i("gyj",item.getRawValue());
//                }
//                break;
//
//                case FirebaseVisionBarcode.TYPE_URL:{
//                    //start browse url
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getRawValue()));
//                    startActivity(intent);
//                }
//                break;
//
//                case FirebaseVisionBarcode.TYPE_CONTACT_INFO:{
//                    String info = new StringBuilder("Name: ").append(item.getContactInfo().getName().getFormattedName())
//                            .append("\n")
//                            .append("Adress: ")
//                            .append(item.getContactInfo().getAddresses().get(0).getAddressLines())
//                            .append("\n")
//                            .append("Email: ")
//                            .append(item.getContactInfo().getEmails().get(0).getAddress())
//                            .toString();
//                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                    builder.setMessage(info);
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            dialogInterface.dismiss();
//                        }
//                    });
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//                }
//                break;
//
//                default:
//                    break;
//            }
//        }
//        waitingDialog.dismiss();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        cameraView.start();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        cameraView.stop();
//    }
}
