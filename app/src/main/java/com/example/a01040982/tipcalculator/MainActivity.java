package com.example.a01040982.tipcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private Button plusBtn;
    private Button minusButton;

    private  TextView billAmount;
    private TextView defaultTipPercentage;
    private TextView tipAmount;
    private TextView totalAmount;
    private TextView perPersonAmount;

    private Spinner spinner;
    private static final String[] items = {"No", "Divided between 2", "Divided between 3", "Divided between 4"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        plusBtn = (Button) findViewById(R.id.plusButton);
        minusButton = (Button) findViewById(R.id.minusButton);

        billAmount = (TextView)findViewById(R.id.inputBillAmount);
        defaultTipPercentage = (TextView)findViewById(R.id.defaultPercent);
        tipAmount = (TextView)findViewById(R.id.defaultTip);
        totalAmount = (TextView)findViewById(R.id.defaultTotal);
        perPersonAmount = (TextView)findViewById(R.id.perPersonAmount);

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                MainActivity.this.onPercentageIncrease();
            }

        });

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                MainActivity.this.onPercentageDecrease();
            }

        });

        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item,items);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                String totalamount = totalAmount.getText().toString();
                NumberFormat currency = NumberFormat.getCurrencyInstance();
                switch (position) {
                    case 0:
                        perPersonAmount.setText(currency.format(Double.valueOf(totalamount)));
                        break;
                    case 1:
                        double divideByTwo = Double.valueOf(totalamount);
                        perPersonAmount.setText(currency.format(divideByTwo / 2  ));
                        break;
                    case 2:
                        double divideByThree = Double.valueOf(totalamount);
                        perPersonAmount.setText(String.format(currency.format(divideByThree / 3)));
                        break;
                    case 3:
                        double divideByFour = Double.valueOf(totalamount);
                        perPersonAmount.setText(String.format(currency.format(divideByFour / 4)));
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                String totalamount = totalAmount.getText().toString();
                perPersonAmount.setText(totalamount);
            }
        });


    }
    public void onPercentageIncrease(){

        NumberFormat currency = NumberFormat.getCurrencyInstance();
        float billamount = (float) Integer.parseInt(billAmount.getText().toString());
        int defaulPercentage = Integer.parseInt(defaultTipPercentage.getText().toString()) + 1;
        defaultTipPercentage.setText(String.valueOf(defaulPercentage));

        float tipamount = (float)(billamount * (float)defaulPercentage / 100);
        tipAmount.setText(String.valueOf(tipamount));

        float totalamount = (float)(billamount + tipamount);
        totalAmount.setText(String.valueOf(totalamount));

        perPersonAmount.setText(currency.format(totalamount));
    }

    public void onPercentageDecrease(){

        NumberFormat currency = NumberFormat.getCurrencyInstance();
        float billamount = (float) Integer.parseInt(billAmount.getText().toString());
        int defaulPercentage = Integer.parseInt(defaultTipPercentage.getText().toString()) - 1;
        defaultTipPercentage.setText(String.valueOf(defaulPercentage));

        float tipamount = (float)(billamount * (float)defaulPercentage / 100);
        tipAmount.setText(String.valueOf(tipamount));

        float totalamount = (float)(billamount + tipamount);
        totalAmount.setText(String.valueOf(totalamount));

        perPersonAmount.setText(currency.format(totalamount));
    }
}
