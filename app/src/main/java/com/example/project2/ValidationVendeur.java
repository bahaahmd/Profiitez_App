package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.DuplicateFormatFlagsException;

public class ValidationVendeur extends AppCompatActivity {
TextView open,close;
int toHour,toMinute,tcHour,tcMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation_vendeur);
        open=findViewById(R.id.HO);
        close=findViewById(R.id.HC);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog=new TimePickerDialog(ValidationVendeur.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hour, int minute) {
                                toHour=hour;
                                toMinute=minute;
                                String time=toHour+ ":"+toMinute;
                                SimpleDateFormat f24Hours =new SimpleDateFormat(
                                        "HH:mm"
                                );
                                try {
                                    Date date =f24Hours.parse(time);
                                    SimpleDateFormat f12Hours =new SimpleDateFormat(
                                            "hh:mm aa"
                                    );
                                    open.setText(f12Hours.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        },12,0,false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(toHour,toMinute);
                timePickerDialog.show();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog=new TimePickerDialog(ValidationVendeur.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hour, int minute) {
                                tcHour=hour;
                                tcMinute=minute;
                                String time=tcHour+ ":"+tcMinute;
                                SimpleDateFormat f24Hours =new SimpleDateFormat(
                                        "HH:mm"
                                );
                                try {
                                    Date date =f24Hours.parse(time);
                                    SimpleDateFormat f12Hours =new SimpleDateFormat(
                                            "hh:mm aa"
                                    );
                                    close.setText(f12Hours.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        },12,0,false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(tcHour,tcMinute);
                timePickerDialog.show();
            }
        });
    }
}