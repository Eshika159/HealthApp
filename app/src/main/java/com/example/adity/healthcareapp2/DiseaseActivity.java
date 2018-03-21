package com.example.adity.healthcareapp2;

import android.app.AlertDialog;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class DiseaseActivity extends AppCompatActivity {


    String a;
    String b;
    String c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease);
        final Spinner mspinner = findViewById(R.id.spinner1);
        final Spinner mspinner2 = findViewById(R.id.spinner2);
        final Spinner mspinner3 = findViewById(R.id.spinner3);
        ArrayAdapter<String> myada = new ArrayAdapter<String>(DiseaseActivity.this,android.R.layout.simple_expandable_list_item_1,getResources().getStringArray(R.array.sympton));
        myada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mspinner.setAdapter(myada);
        mspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(
                    AdapterView<?> adapterView, View view,
                    int i, long l) {
                a = mspinner.getOnItemSelectedListener().toString();

                //Toast.makeText(DiseaseActivity.this,a, Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(
                    AdapterView<?> adapterView) {

            }
        });

        mspinner2.setAdapter(myada);
        mspinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(
                    AdapterView<?> adapterView, View view,
                    int i, long l) {
                b = mspinner2.getOnItemSelectedListener().toString();

                // Toast.makeText(DiseaseActivity.this,b, Toast.LENGTH_SHORT).show();

            }
            public void onNothingSelected(
                    AdapterView<?> adapterView) {

            }
        });
        mspinner3.setAdapter(myada);
        mspinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(
                    AdapterView<?> adapterView, View view,
                    int i, long l) {
                c = mspinner3.getOnItemSelectedListener().toString();
                //Toast.makeText(DiseaseActivity.this,c, Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(
                    AdapterView<?> adapterView) {
            }
        });

    }
    public void Disease(View view)
    {
        ConnectivityManager connec
                =(ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        AlertDialog alertDialog = new AlertDialog.Builder(DiseaseActivity.this).create();
        alertDialog.setTitle("Connection Status");
        if ( connec.getNetworkInfo(0).getState() ==
                android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
            //Toast.makeText(this, s, Toast.LENGTH_LONG).show();
            String type = "Dis";
            BackgroundWorkerDis backgroundWorkerWithdraw = new BackgroundWorkerDis(this);
             backgroundWorkerWithdraw.execute(type,a,b,c);
        }else if (
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() ==
                                android.net.NetworkInfo.State.DISCONNECTED  ) {
            alertDialog.setMessage("Not Connected, Please check your Internet Connection");
            alertDialog.show();
        }
    }
    }

