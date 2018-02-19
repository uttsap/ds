package com.afeastoffriends.doctorsaathi;

/**
 * Created by Acer-PC on 2/10/2017.
 */

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class AddContacts extends AppCompatActivity {
    // This references camefrom "activity_add_contacts.xml" layout
    EditText etName;
    EditText etPhone;
    EditText etEmail;
    TextView LastDonationDate;
    Spinner spinner;
    String s;
    String s1;
    String s2;
    String s3;
    int acheivedId;
    String lastDate ;
    ArrayList<String> bloodGroups= new ArrayList<String>();
    Calendar ca;
    TextView lastDonationTextView;
    Button savebtn;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String nameS ,phoneS, emailS , bloodGroupStr, lastDateS;
    //  "activity_add_contacts.xml" layout  refrences finished

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);
        etName= (EditText) findViewById(R.id.NameEditText);
        etPhone= (EditText) findViewById(R.id.PhoneEditText);
        etEmail= (EditText) findViewById(R.id.EmailEditText);
        LastDonationDate = (TextView) findViewById(R.id.lastDonationTextView);
        savebtn = (Button) findViewById(R.id.saveButton);
        lastDonationTextView = (TextView) findViewById(R.id.lastDonationTextView);

        String getTask = getIntent().getStringExtra("task");
        ca= Calendar.getInstance();

        bloodGroups.add("A+"); bloodGroups.add("A-"); bloodGroups.add("B+"); bloodGroups.add("B-");
        bloodGroups.add("AB+"); bloodGroups.add("AB-"); bloodGroups.add("O+"); bloodGroups.add("O-");




        lastDonationTextView.setText(ca.get(Calendar.DAY_OF_MONTH)+"/"+(ca.get(Calendar.MONTH)+1)+"/"+ca.get(Calendar.YEAR));


        spinner= (Spinner) findViewById(R.id.spinner2);

        spinner.setAdapter(new ArrayAdapter<String>(AddContacts.this,android.R.layout.simple_spinner_dropdown_item,bloodGroups));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem= spinner.getSelectedItem().toString().trim();


                s3 = spinner.getSelectedItem().toString().trim();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        if(getTask.equals("edit"))
        {

            acheivedId=  getIntent().getIntExtra("id",0);
            nameS = getIntent().getStringExtra("name");
            phoneS = getIntent().getStringExtra("phone");

            emailS = getIntent().getStringExtra("email");
            bloodGroupStr = getIntent().getStringExtra("bloodGroup");
            lastDateS = getIntent().getStringExtra("lastDate");

            etName.setText(nameS);
            etPhone.setText(phoneS);
            etEmail.setText(emailS);
            LastDonationDate.setText(lastDateS);

            for(int i=1;i<bloodGroups.size();i++)
            {

                if(bloodGroups.get(i).equals(bloodGroupStr))
                {
                    spinner.setSelection(i);
                    break;
                }
            }

            savebtn.setText("UPDATE");
        }

        //  Toast.makeText(this, savebtn.getText().toString() , Toast.LENGTH_LONG).show();




    }



    public boolean nameValidation(String s)
    {


        Toast.makeText(getApplicationContext(),"Please enter name correctly", Toast.LENGTH_SHORT).show();
        return false;


    }
    // Save Information Button Onclick
    public void saveInfo(View view) {

        s = etName.getText().toString().trim();
        s2=etEmail.getText().toString();
        lastDate = LastDonationDate.getText().toString().trim();
        if(s.length()== 0)
        {
            nameValidation(s);
        }
        else
        {
            s1 = etPhone.getText().toString();
            if(s1.trim().length() == 0)
            {
                Toast.makeText(getApplicationContext(),"Phone needed", Toast.LENGTH_SHORT).show();

            }
            else
            {

                if(savebtn.getText().toString().equals("save"))
                {

                    DonorInformation donorInfo = new DonorInformation(s,s1,s2,s3,lastDate);
                    DbHelper dbHelper = new DbHelper(AddContacts.this);
                    long result = dbHelper.insert(AddContacts.this,donorInfo);
                    if(result!= -1)
                    {
                        setResult(RESULT_OK);
                    }

                    finish();
                }

                else if(savebtn.getText().toString().equals("UPDATE"))
                {

                    DonorInformation donorInfo = new DonorInformation(acheivedId,s,s1,s2,s3,lastDate);
                    DbHelper dbHelper = new DbHelper(AddContacts.this);
                    //Toast.makeText(this,"ID : "+ acheivedId,Toast.LENGTH_LONG).show();
                    int re = dbHelper.update(this, donorInfo);
                    //  Toast.makeText(this,"Data : "+ re,Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AddContacts.this,MainActivity.class));
                    finish();
                }

            }






        }



    }

    public void Getcalendar(View view) {

        DatePickerDialog datePickerDialog=new DatePickerDialog(AddContacts.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                lastDonationTextView.setText(i2+"/"+(i1+1)+"/"+i);
            }
        },ca.get(Calendar.YEAR),ca.get(Calendar.MONTH),ca.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}
