package com.thepensionapp.sqlitedatabase;

import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
EditText edName,edSurname,edMarks;
    Button btnAdd;
    Button btnViewAll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb=new DatabaseHelper(this);

        edName=(EditText)findViewById(R.id.etname);
        edSurname=(EditText)findViewById(R.id.etSurname);
        edMarks=(EditText)findViewById(R.id.etMarks);
        btnAdd=(Button)findViewById(R.id.btnAdd);
        btnViewAll=(Button)findViewById(R.id.btnViewAll);
        addData();
        viewAll();

    }

    public void addData(){

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               boolean isInserted= myDb.insertData(edName.getText().toString(),edSurname.getText().toString(),edMarks.getText().toString());

                if (isInserted)
                    Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"Data Not Inserted",Toast.LENGTH_LONG).show();

            }
        });
    }

    public void viewAll(){
            btnViewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Cursor cursor=myDb.getAllData();
                    if(cursor.getCount()==0) {
                        showMessage("Error","No data found");
//                        Toast.makeText(MainActivity.this, "Data not available", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    StringBuffer stringBuffer=new StringBuffer();
                    while(cursor.moveToNext()){

                        stringBuffer.append("id :"+cursor.getString(0)+"\n");
                        stringBuffer.append("name :"+cursor.getString(1)+"\n");
                        stringBuffer.append("surname :"+cursor.getString(2)+"\n");
                        stringBuffer.append("marks :"+cursor.getString(3)+"\n\n");
                    }
                    showMessage("Data",stringBuffer.toString());


                }
            });

    }
    public void showMessage(String title,String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();


    }

}
