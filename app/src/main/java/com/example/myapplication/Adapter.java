package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Adapter extends AppCompatActivity {

    Spinner spinner;
    Spinner spinner_1;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner_1 = (Spinner) findViewById(R.id.spinner_1);

        final ArrayList<String>  items=getContries("countries.json", "departement");
        final ArrayList<String>  items_1=getContries("countries.json", "departement");

        adapter=new ArrayAdapter<String>(this,R.layout.spinner_layout, R.id.txt,items);
        adapter_1=new ArrayAdapter<String>(this,R.layout.spinner_layout_1, R.id.txt_1,items_1);

        spinner.setAdapter(adapter);
        spinner_1.setAdapter(adapter_1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String test = items.get((int)id);
                adapter_1.clear();

                ArrayList<String> item=getContries(position + ".json", "commune");
                adapter_1=new ArrayAdapter<String>(Adapter.this,R.layout.spinner_layout_1, R.id.txt_1,item);
                spinner_1.setAdapter(adapter_1);

                Toast.makeText(Adapter.this, test, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Adapter.this, "Nothing", Toast.LENGTH_SHORT).show();

            }
        });

    }




    public ArrayList<String> getContries(String fileName, String attribut){
        JSONArray jsonArray = null;
        ArrayList<String> cList = new ArrayList<String>();
        try {
            InputStream is = getResources().getAssets().open(fileName);
            int size= is.available();
            byte[] data = new byte[size];
            is.read(data);
            is.close();
            String json=new String(data,"UTF-8");
            jsonArray = new JSONArray(json);
            if (jsonArray!=null){
                for (int i=0; i<jsonArray.length(); i++){
                    cList.add(jsonArray.getJSONObject(i).getString(attribut));
                }
            }
        }
        catch (IOException e) {e.printStackTrace();}
        catch (JSONException js) {js.printStackTrace();}
        return cList;
    }


    public void populate(int position){

        Spinner spin = (Spinner) findViewById(R.id.spinner_1);
        spin.setAdapter(null);
        ArrayList<String> item=getContries(position + ".json", "commune");

        ArrayAdapter<String> adapte=new ArrayAdapter<String>(this,R.layout.spinner_layout_1, R.id.txt_1,item);
        spin.setAdapter(adapte);
        adapte.notifyDataSetChanged();
    }

    public boolean onCreateOtionsMenu(Menu menu){

        // inflate the menu; this adds items to the action bqr if it is present
        getMenuInflater().inflate(R.menu.menu_main,menu);

        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}

