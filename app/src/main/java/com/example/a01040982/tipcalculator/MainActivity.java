package com.example.a01040982.tipcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
