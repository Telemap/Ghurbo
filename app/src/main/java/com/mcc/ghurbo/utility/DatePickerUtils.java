package com.mcc.ghurbo.utility;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DatePickerUtils implements DatePickerDialog.OnDateSetListener {

    private Calendar myCalendar;
    private DateListener dateListener;

    public DatePickerUtils(Activity activity, DateListener dateListener) {
        myCalendar = Calendar.getInstance();
        this.dateListener = dateListener;

        new DatePickerDialog(activity, this, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear,
                          int dayOfMonth) {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String date = sdf.format(myCalendar.getTime());

        if(dateListener != null) {
            dateListener.onDateSet(date);
        }
    }

    public interface DateListener {
        public void onDateSet(String finalDate);
    }


}
