package hr.fer.android.jmbag1191227025.mathland;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 *Activity class used for show the calculus available: addition, subtraction, multiplication,
 * division. When the calculus is selected, pressing the button {@code btnCalculate}, chosen
 * calculus is calculated over two
 * numeric operands written in {@code etFirstNumber} and {@code etSecondNumber}.
 * If the operands aren't numerical values, error is provided.
 * The result is sent to the {@link DisplayActivity}.
 */
public class CalculusActivity extends AppCompatActivity {
    /**String on which the operation chosen will be mapped.
     * */
    public static final String OPERATION = "operation";
    /**String on which the operation chosen will be mapped.
     * In case of error, the mapped value will be error description.
     * */
    public static final String RESULT = "result";
    /**Indicator if error occurs during the calculation.*/
    private boolean error = false;
    /**String on which the first operand of the chosen operation
     * will be mapped.
     * */
    public static final String FIRSTNUMBER = "firstNumber";
    /**String on which the second operand of the chosen operation
     * will be mapped.
     * */
    public static final String SECONDNUMBER = "secondNumber";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculus);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        error = false;

        final EditText etFirstNumber = (EditText)findViewById(R.id.etFirstNumber);
        final EditText etSecondNumber = (EditText)findViewById(R.id.etSecondNumber);
        final RadioGroup rgCheckBox = (RadioGroup) findViewById(R.id.rgCheckBox);


        Button btnCalculate = (Button)findViewById(R.id.btnCalculate);


        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String operationName = DisplayActivity.ERROR;
                int operation = rgCheckBox.getCheckedRadioButtonId();
                if(operation == -1) {
                    Toast.makeText(CalculusActivity.this, "Operacija nije odabrana!", Toast.LENGTH_LONG).show();
                    return;
                }

                double firstNumber;
                double secondNumber;
                try{
                    firstNumber = Double.parseDouble(etFirstNumber.getText().toString());
                    secondNumber = Double.parseDouble(etSecondNumber.getText().toString());
                }catch (Exception e) {
                    Toast.makeText(CalculusActivity.this, "Operandi nisu brojevi!", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent i = new Intent(CalculusActivity.this,DisplayActivity.class);
                i.putExtra(FIRSTNUMBER,firstNumber);
                i.putExtra(SECONDNUMBER,secondNumber);
                i.putExtra(OPERATION,Calculus.getCaluclusSign(operation));
                i.putExtra(RESULT,Calculus.calculate(operation,firstNumber,secondNumber,CalculusActivity.this));

                i.putExtra(DisplayActivity.ERROR,error);
                startActivity(i);
                finish();

            }
        });
    }

    /**Setting the flag {@link #error}
     * @param flag flag indicating the error
     * */
    public void setError(boolean flag){
        error = flag;
    }
}
