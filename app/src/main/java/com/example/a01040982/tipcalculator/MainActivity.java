package com.example.a01040982.tipcalculator;

import android.annotation.SuppressLint;
import android.support.v4.view.MenuCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.NumberFormat;

import static android.support.constraint.ConstraintSet.INVISIBLE;
import static android.support.constraint.ConstraintSet.VISIBLE;

public class MainActivity extends AppCompatActivity {

    private Button plusBtn;
    private Button minusButton;
    private Button applyButton;

    private  TextView billAmount;
    private TextView defaultTipPercentage;
    private TextView tipAmount;
    private TextView totalAmount;
    private  TextView perPerson;
    private TextView perPersonAmount;
    private TextView errorMessage;

    private RadioGroup radioBtnGroup;
    private RadioButton noneRadioBtn;
    private RadioButton tipRadioBtn;
    private RadioButton totalRadioBtn;
    private RadioButton radiobtn;

    private Spinner spinner;
    private static final String[] items = {"No", "Divided between 2", "Divided between 3", "Divided between 4"};


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        plusBtn = (Button) findViewById(R.id.plusButton);
        minusButton = (Button) findViewById(R.id.minusButton);
        applyButton = (Button) findViewById(R.id.applyBtn);

        billAmount = (TextView)findViewById(R.id.inputBillAmount);
        defaultTipPercentage = (TextView)findViewById(R.id.defaultPercent);
        tipAmount = (TextView)findViewById(R.id.defaultTip);
        totalAmount = (TextView)findViewById(R.id.defaultTotal);
        perPerson = (TextView) findViewById(R.id.perPerson) ;
        perPersonAmount = (TextView)findViewById(R.id.perPersonAmount);
        errorMessage = (TextView) findViewById(R.id.displayError) ;

        radioBtnGroup = (RadioGroup) findViewById(R.id.radionBtnGroup) ;
        noneRadioBtn = (RadioButton) findViewById(R.id.noneRadioBtn);
        tipRadioBtn = (RadioButton) findViewById(R.id.tipRadioBtn);
        totalRadioBtn = (RadioButton) findViewById(R.id.totalRadioBtn);

        perPerson.setVisibility(INVISIBLE);
        perPersonAmount.setVisibility(INVISIBLE);

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

        radioBtnGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                String totalamount = totalAmount.getText().toString();
                String tipamount = tipAmount.getText().toString();
                int defaulPercentage = Integer.parseInt(defaultTipPercentage.getText().toString());
                float billamount = (float) Integer.parseInt(billAmount.getText().toString());
                float tipAmountOnNoneRadioBtn = (float)(billamount * (float)defaulPercentage / 100);
                float totalAmountOnNoneRadioBtn = (float)(billamount + tipAmountOnNoneRadioBtn);

                switch(checkedId){
                    case R.id.noneRadioBtn:
                        tipAmount.setText(String.format("%.2f",tipAmountOnNoneRadioBtn));
                        totalAmount.setText(String.format("%.2f",totalAmountOnNoneRadioBtn));
                        break;

                    case R.id.tipRadioBtn:
                        String tipAmountToRound = tipAmount.getText().toString();
                        double roundingTip = Math.round(Double.valueOf(tipAmountToRound));
                        if( roundingTip > Double.valueOf(tipamount)){
                            double incresedTipAmount = roundingTip - Double.valueOf(tipamount);
                            double newTipAmount = Double.valueOf(tipamount) + incresedTipAmount;
                            tipAmount.setText(String.format("%.2f", newTipAmount));
                            double newTotalAmount = incresedTipAmount + Double.valueOf(totalamount);
                            totalAmount.setText(String.format("%.2f", newTotalAmount));
                        }
                        double decreasedTipAmount = Double.valueOf(tipamount) - roundingTip;
                        double newTipAmount = Double.valueOf(tipamount) - decreasedTipAmount;
                        tipAmount.setText(String.format("%.2f",newTipAmount));
                        double newTotalAmount = Double.valueOf(totalamount) - decreasedTipAmount;
                        totalAmount.setText(String.format("%.2f",newTotalAmount));
                        break;

                    case R.id.totalRadioBtn:
                        String totalAmountToRound = totalAmount.getText().toString();
                        double roundingTotal = Math.round(Double.valueOf(totalAmountToRound));
                        if( roundingTotal > Double.valueOf(totalamount)){
                            double incresedTotalAmount = roundingTotal - Double.valueOf(totalamount);
                            double newTotalamount = Double.valueOf(totalamount) + incresedTotalAmount;
                            totalAmount.setText(String.format("%.2f",newTotalamount));
                            double newTipamount = incresedTotalAmount + Double.valueOf(tipamount);
                            tipAmount.setText(String.format("%.2f",newTipamount));
                        }
                        double decreasedTotalAmount = Double.valueOf(totalamount) - roundingTotal;
                        double newTotalamount = Double.valueOf(totalamount) - decreasedTotalAmount;
                        totalAmount.setText(String.format("%.2f",newTotalamount));
                        double newTipamount = Double.valueOf(tipamount) - decreasedTotalAmount;
                        tipAmount.setText(String.format("%.2f", newTipamount));
                        break;
                }
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

        applyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                MainActivity.this.onClickOnApplyBtn();
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

    public void onClickOnApplyBtn() {
        if(billAmount.getText().toString().equals("")){
            errorMessage.setText("Please enter a bill amount.");
        }
        else {
            errorMessage.setText("");
            NumberFormat currency = NumberFormat.getCurrencyInstance();

            if(tipAmount.getText().equals("0.00") || totalAmount.getText().equals("0.00")){
                float billamount = (float) Integer.parseInt(billAmount.getText().toString());
                int defaulPercentage = Integer.parseInt(defaultTipPercentage.getText().toString());

                float totalamount = (float)(billamount * (float)defaulPercentage/100);
                totalAmount.setText(String.valueOf(billamount + totalamount));
                perPersonAmount.setText(String.valueOf(billamount + totalamount));
                perPerson.setVisibility(VISIBLE);
                perPersonAmount.setVisibility(VISIBLE);
            }
            else {
                perPerson.setVisibility(VISIBLE);
                perPersonAmount.setVisibility(VISIBLE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tip_calculator_menu, menu);

        MenuCompat.setGroupDividerEnabled(menu, true);
        return super.onCreateOptionsMenu(menu);
    }
}
