package sg.edu.rp.c346.reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etName;
    EditText etTelephone;
    EditText etSize;
    CheckBox checkBox;
    EditText etDay;
    EditText etTime;
    Button btReserve;
    Button btReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.editTextName);
        etTelephone = findViewById(R.id.editTextTelephone);
        etSize = findViewById(R.id.editTextSize);
        checkBox = findViewById(R.id.checkBox);
        etDay = findViewById(R.id.editDay);
        etTime = findViewById(R.id.editTime);
        btReserve = findViewById(R.id.buttonReserve);
        btReset = findViewById(R.id.buttonReset);

        etDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etDay.setText(dayOfMonth + "/" + (month + 1) + "/" + (year));
                    }
                };

                Calendar cal1 = Calendar.getInstance();
                DatePickerDialog myDateDialog = new DatePickerDialog(MainActivity.this, myDateListener, cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH), cal1.get(Calendar.DAY_OF_MONTH));
                myDateDialog.show();
            }
        });

        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        etTime.setText(hourOfDay + ":" + minute);
                    }
                };

                Calendar cal1 = Calendar.getInstance();
                TimePickerDialog myTimeDialog = new TimePickerDialog(MainActivity.this, myTimeListener, cal1.get(Calendar.HOUR_OF_DAY), cal1.get(Calendar.MINUTE), true);
                myTimeDialog.show();
            }
        });




        btReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isSmoke = "";
                if (checkBox.isChecked()) {
                    isSmoke = "smoking";
                }
                else {
                    isSmoke = "non-smoking";
                }

                String name = "Name: " + etName.getText().toString();
                String smoking = "Smoking: " + isSmoke;
                String size = "Size: " + etSize.getText().toString();
                String date = etDay.getText().toString();
                String time = etTime.getText().toString();

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);

                myBuilder.setTitle("Confirm Your Order");
                myBuilder.setMessage(String.format("New Reservation\n%s\n%s\n%s\nDate: %s\nTime: %s", name, smoking, size, date, time));
                myBuilder.setCancelable(false);

                myBuilder.setPositiveButton("Confirm", null);


                myBuilder.setNeutralButton("Cancel", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etName.setText("");
                etTelephone.setText("");
                etSize.setText("");
                checkBox.setChecked(false);
                etDay.setText("");
                etDay.setHint(getString(R.string.DayH));
                etTime.setText("");
                etTime.setHint(getString(R.string.TimeH));
            }
        });

    }//end of OnCreate()


    @Override
    protected void onPause() {
        super.onPause();
        String sName = etName.getText().toString();
        String sTele = etTelephone.getText().toString();
        String sSize = etSize.getText().toString();
        boolean sCheckbox = checkBox.isChecked();
        String sDay = etDay.getText().toString();
        String sTime = etTime.getText().toString();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor prefsEdit = prefs.edit();
        prefsEdit.putString("name", sName);
        prefsEdit.putString("tele", sTele);
        prefsEdit.putString("size", sSize);
        prefsEdit.putBoolean("checkbox", sCheckbox);
        prefsEdit.putString("date", sDay);
        prefsEdit.putString("time", sTime);

        prefsEdit.commit();
    }//end of onPause()

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String name = prefs.getString("name", "");
        String tele = prefs.getString("tele", "");
        String size = prefs.getString("size", "");
        boolean checkbox = prefs.getBoolean("checkbox", false);
        String date = prefs.getString("date", getString(R.string.DayH));
        String time = prefs.getString("time", getString(R.string.TimeH));

        etName.setText(name);
        etTelephone.setText(tele);
        etSize.setText(size);
        checkBox.setChecked(checkbox);
        etDay.setText(date);
        etTime.setText(time);
    }//end of onResume()


}//end of class