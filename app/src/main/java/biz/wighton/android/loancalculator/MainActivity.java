package biz.wighton.android.loancalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Local variables / objects?
    private double calculationAmount;
    private EditText loanAmountEditText;
    private EditText depositAmountEditText;
    private TextView depositPercentTextView;
    private EditText percentInterestEditText;
    private EditText loanDurationEditText;
    private TextView repaymentAmountTextView;
    private RadioButton monthlyRadioButton;
    private RadioButton fortnightlyRadioButton;
    private RadioButton weeklyRadioButton;
    private Button calculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assigning all my 'objects'? to pull in data from app widgets and fields in to java
        loanAmountEditText = (EditText) findViewById(R.id.editTextLoanAmount);
        depositAmountEditText = (EditText) findViewById(R.id.editTextDepositAmount);
        depositPercentTextView = (TextView) findViewById(R.id.textViewDepositPercent);
        percentInterestEditText = (EditText) findViewById(R.id.editTextInterestRate);
        loanDurationEditText = (EditText) findViewById(R.id.editTextLoanDuration);
        repaymentAmountTextView = (TextView) findViewById(R.id.textViewRepaymentValue);
        monthlyRadioButton = (RadioButton) findViewById(R.id.radioButtonMonthly);
        fortnightlyRadioButton = (RadioButton) findViewById(R.id.radioButtonFortnightly);
        weeklyRadioButton = (RadioButton) findViewById(R.id.radioButtonWeekly);
        calculateButton = (Button) findViewById(R.id.buttonCalculate);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Error check for empty input fields required for calculation.
                if (loanAmountEditText.getText().toString().matches("") || percentInterestEditText.getText().toString().matches("") || loanDurationEditText.getText().toString().matches("")) {
                    // Prompt user all fields need to be entered, future dev is to
                    // identify which input field are missing and highlight
                    Toast.makeText(getApplicationContext(), "Enter required * fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Perform calculations then format to 2dp
                    // interestRate = % / 12 (months) / 100 (fractions to decimal)
                    double interestRate = (Double.parseDouble(percentInterestEditText.getText().toString()) / 12 / 100);
                    // principle is the amount
                    double principleAmount = Double.parseDouble(loanAmountEditText.getText().toString());
                    // loanDuration (years) * 12 (months)
                    double loanDuration = (Double.parseDouble(loanDurationEditText.getText().toString()) * 12);
                    calculationAmount = ((interestRate * principleAmount) / (1 - (Math.pow(1 + interestRate, -loanDuration))));
                    repaymentAmountTextView.setText(String.format("$%.2f", calculationAmount));
                }
            }
        });

        monthlyRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When monthly radio button is checked, confirm amount has been calculated,
                // perform standard calculation and adjust for monthly payments, then format to 2dp
                if (repaymentAmountTextView.getText() != "") {
                    repaymentAmountTextView.setText(String.format("$%.2f", (calculationAmount)));
                }
            }

        });

        fortnightlyRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When fortnightly radio button is checked, confirm amount has been calculated,
                // perform standard calculation and adjust for fortnightly payments, then format to 2dp
                if (repaymentAmountTextView.getText() != "") {
                    repaymentAmountTextView.setText(String.format("$%.2f", calculationAmount * 12 / 26));
                }
            }
        });

        weeklyRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When weekly radio but button checked, confirm amount has been calculated,
                // perform standard calculation and adjust for weekly payments, then format to 2dp
                if (repaymentAmountTextView.getText() != "") {
                    repaymentAmountTextView.setText(String.format("$%.2f", calculationAmount * 12 / 52));
                }
            }
        });
    }
}
