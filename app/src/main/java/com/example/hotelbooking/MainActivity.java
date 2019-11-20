package com.example.hotelbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private EditText etadult,etchildren,etrooms;
    private TextView tvcheckin,tvcheckout,tvTotal,tvlocation,tvroomtype,tvtotalroom,tvserviceC,tvvat,tvoutputcheckin,tvoutputcheckout;
    private Button btncalculate;
    private Boolean d1,d2;

    private Spinner spinlocation,spinroom;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvcheckin = findViewById(R.id.tvcheckin);
        tvcheckout = findViewById(R.id.tvcheckout);
        etadult = findViewById(R.id.etadult);
        etchildren = findViewById(R.id.etchildren);
        etrooms = findViewById(R.id.etroom);
        spinlocation = findViewById(R.id.slocation);
        spinroom = findViewById(R.id.sroom);
        btncalculate = findViewById(R.id.btncalculate);
        tvTotal = findViewById(R.id.tvtotal);
        tvlocation = findViewById(R.id.tvlocation);
        tvroomtype = findViewById(R.id.tvroomT);
        tvtotalroom = findViewById(R.id.tvnoOfroom);
        tvserviceC = findViewById(R.id.tvservice);
        tvvat = findViewById(R.id.tvvat);
        tvoutputcheckin = findViewById(R.id.tvDcheckin);
        tvoutputcheckout = findViewById(R.id.tvDcheckout);

        String Location[] = {"Select Location","Bhaktapur","Lalitpur","Kathmandu"};
        ArrayAdapter adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                Location
        );
        spinlocation.setAdapter(adapter);

        final String Room[] = {"Select Room Type", "Delux" , "AC", "Platinum"};
        ArrayAdapter adapter1 = new ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                Room
        );
        spinroom.setAdapter(adapter1);

        tvcheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadDatePicker();
                d1 = true;
            }
        });

        tvcheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadDatePicker();
                d2 = true;
            }
        });

        btncalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(tvcheckin.getText())){
                    tvcheckin.setError("Enter CheckIn Date");
                    return;
                }

                if (TextUtils.isEmpty(tvcheckout.getText())){
                    tvcheckout.setError("Enter CheckOut Date");
                    return;
                }

                if (TextUtils.isEmpty(etadult.getText())){
                    etadult.setError("Enter No of Adults");
                    return;
                }

                if (TextUtils.isEmpty(etchildren.getText())){
                    etchildren.setError("Enter No of Children");
                    return;
                }

                if (TextUtils.isEmpty(etrooms.getText())){
                    etrooms.setError("Enter No of Rooms");
                    return;
                }

                String RoomType = spinroom.getSelectedItem().toString();
                String Place =spinlocation.getSelectedItem().toString();

                if (Place == "Select Location"){
                    Toast.makeText(MainActivity.this, "Please select your Location", Toast.LENGTH_SHORT).show();
                }

                String CheckIn = tvcheckin.getText().toString();
                String CheckOut = tvcheckout.getText().toString();
                int TotalRooms = Integer.parseInt(etrooms.getText().toString());
                int RoomValue = 0 ;
                int Price;
                int TotalPrice;
                int Vat;
                int ServiceCharge;

                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                try {
                    Date date1 = sdf.parse(CheckIn);
                    Date date2 = sdf.parse(CheckOut);
                    long diff = date2.getTime() - date1.getTime();
                    long diffDays = diff / (24 * 60 * 60 * 1000);
                    int days = (int) diffDays;

                    if (RoomType == "Delux"){
                        RoomValue = 2000;
                    }
                    else if (RoomType == "AC"){
                        RoomValue = 3000;
                    }
                    else if (RoomType == "Platinum"){
                        RoomValue = 4000;
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Please Select Room Type", Toast.LENGTH_SHORT).show();
                    }

                    Price = RoomValue * TotalRooms * days;
                    Vat = (Price * 13)/100;
                    ServiceCharge = (Price * 10)/100;
                    TotalPrice = Price + Vat + ServiceCharge;

                    tvlocation.setText("Location : ".concat(Place));
                    tvroomtype.setText("Room Type : ".concat(RoomType));
                    tvoutputcheckin.setText("CheckIn : ".concat(CheckIn));
                    tvoutputcheckout.setText("CheckOut : ".concat(CheckOut));
                    tvtotalroom.setText("Total Rooms: ".concat(Integer.toString(TotalRooms)));
                    tvserviceC.setText("Service Charge: ".concat(Integer.toString(ServiceCharge)));
                    tvvat.setText("Vat: ".concat(Integer.toString(Vat)));
                    tvTotal.setText("Total : " .concat(Integer.toString(TotalPrice)));
                } catch (ParseException e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void LoadDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(c.YEAR);
        int month = c.get(c.MONTH);
        int day = c.get(c.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = month + "/" + dayOfMonth + "/" + year;
                if (d1 == true){
                    tvcheckin.setText(date);
                    d1 = false;
                }
                else if (d2 == true)
                {
                    tvcheckout.setText(date);
                    d2 = true;
                }
            }
        },year,month,day);
        datePickerDialog.show();
    }
}
