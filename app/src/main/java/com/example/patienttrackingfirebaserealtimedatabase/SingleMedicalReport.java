package com.example.patienttrackingfirebaserealtimedatabase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SingleMedicalReport extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 10;
    private static final String TAG = "Patient Tracking System";

    int pdfHeight = 1080;
    int pdfWidth = 720;
    private PdfDocument pdfDocument;

    String name,id,phone,diagnosis,drName,date,sys,recom,comm;

    TextView txtName,txtIdNo,txtPhone,txtDiagnosis,txtDoctorName,txtDate,txtSys,txtRecomm,txtCom,txtFaculty,txtDrName2,txtSign,lblPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_medical_report);

        txtName=findViewById(R.id.txtName);
        txtIdNo=findViewById(R.id.txtId);
        txtPhone=findViewById(R.id.txtPhone);
        lblPhone=findViewById(R.id.lblPhone);
        txtDiagnosis=findViewById(R.id.txtPossibleDisease);
        txtDoctorName=findViewById(R.id.txtDoctorName);
        txtDate=findViewById(R.id.txtDate);
        txtSys=findViewById(R.id.txtSymptoms);
        txtRecomm=findViewById(R.id.txtRecom);
        txtCom=findViewById(R.id.txtComments);
        txtDrName2=findViewById(R.id.txtDoctorName2);
        txtFaculty=findViewById(R.id.txtFaculty);
        txtSign=findViewById(R.id.txtSign);

        txtPhone.setVisibility(View.GONE);
        lblPhone.setVisibility(View.GONE);



        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            txtName.setText(bundle.getString("name"));
            txtIdNo.setText(bundle.getString("idNo"));
            txtPhone.setText(bundle.getString("phone"));
            txtDiagnosis.setText(bundle.getString("diagnosis"));
            txtDoctorName.setText(bundle.getString("doctorName"));
            txtDate.setText(bundle.getString("date"));
            txtSys.setText(bundle.getString("symptoms"));
            txtRecomm.setText(bundle.getString("recommendations"));
            txtCom.setText(bundle.getString("comments"));
            txtDrName2.setText(bundle.getString("drName"));
            txtFaculty.setText(bundle.getString("faculty"));
            txtSign.setText(bundle.getString("drName"));
            
        }else{
            Toast.makeText(this, "Nothing was passed", Toast.LENGTH_SHORT).show();
        }



        findViewById(R.id.floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(SingleMedicalReport.this, "Printing", Toast.LENGTH_SHORT).show();
                generatePdfFromView(findViewById(R.id.linearLayout));
            }
        });
    }


    private void generatePdfFromView(View viewById) {
        Bitmap bitmap = getBitmapFromView(viewById);
        pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        canvas.drawBitmap(bitmap, 0, 0, null);
        pdfDocument.finishPage(page);
        createFile();

    }

    private Bitmap getBitmapFromView(View view) {

        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return returnedBitmap;
    }

    private static final int CREATE_FILE = 1;

    void createFile() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_TITLE, "patient_report.pdf");
        startActivityForResult(intent, CREATE_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CREATE_FILE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (data != null) {
                uri = data.getData();

                if (pdfDocument != null) {
                    ParcelFileDescriptor parcelFileDescriptor = null;

                    try {
                        parcelFileDescriptor = getContentResolver().
                                openFileDescriptor(uri, "w");
                        FileOutputStream fileOutputStream = new FileOutputStream(parcelFileDescriptor.getFileDescriptor());
                        pdfDocument.writeTo(fileOutputStream);
                        pdfDocument.close();
                        Toast.makeText(this, "PDF Saved Successfully", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        try {
                            DocumentsContract.deleteDocument(getContentResolver(), uri);
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }

        }
    }

}