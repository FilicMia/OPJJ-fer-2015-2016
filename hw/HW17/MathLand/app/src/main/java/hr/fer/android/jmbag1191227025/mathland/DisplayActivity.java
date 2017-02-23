package hr.fer.android.jmbag1191227025.mathland;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity displays the number gotten from {@link CalculusActivity} as result of
 * calculated operation.
 * It also displays 2 buttons under the result.
 * First one {@code btnStart}, proceeds to the {@link CalculusActivity}, the new
 * calculus can be done.
 * Second one sends the mail to the {@code RECIPIENT} which is predefined as
 * static class property.
 * The mail content is number gotten from {@link CalculusActivity} as result of
 * calculated operation, or error message if an error occurs.
 *
 */
public class DisplayActivity extends AppCompatActivity {
    /**Defines the recipient of the mail sent when the {@code btnPosaljiIzvjesce} is clicked.*/
    private static final String RECIPIENT = "ana.baotic@infinum.hr";
    /**Defines the cc recipient of the mail sent when the {@code btnPosaljiIzvjesce} is clicked.*/
    private static final String CC_RECIPIENT = "filicmia@gmail.com";
    /**JMBAG of the student who has done this homework; me*/
    private static final String JMBAG = "1191227025";
    /**String indication error in operation calculated in {@link CalculusActivity}*/
    public static final String ERROR = "error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Intent i = new Intent(DisplayActivity.this,CalculusActivity.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        final String result = getIntent().getExtras().getString(CalculusActivity.RESULT);
        final String operation = getIntent().getExtras().getString(CalculusActivity.OPERATION);
        final boolean error = getIntent().getExtras().getBoolean(ERROR);
        final double firstNumber = getIntent().getExtras().getDouble(CalculusActivity.FIRSTNUMBER);
        final double secondNumber = getIntent().getExtras().getDouble(CalculusActivity.SECONDNUMBER);

        TextView tvResult = (TextView) findViewById(R.id.tvResult);
        final String body;
        if(error){
            body = "Prilikom obavljanje operacije \""+operation+"\" nad unosima "
                    +firstNumber+" i "+secondNumber+
                    " je došlo do sljedeće greške:"+result;
        } else{
            body = "Rezultat operacije \""+operation+"\" je: "+result;
        }
        tvResult.setText(body);

        Button btnStart = (Button)findViewById(R.id.btnOK);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(i);
                finish();
            }
        });

        Button btnPosaljiIzvjestaj = (Button)findViewById(R.id.btnPosaljiIzvjestaj);
        btnPosaljiIzvjestaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                //appi for mail sending
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{RECIPIENT});
                i.putExtra(Intent.EXTRA_CC, new String[]{CC_RECIPIENT});
                i.putExtra(Intent.EXTRA_SUBJECT, JMBAG +  ": dz report");

                i.putExtra(Intent.EXTRA_TEXT   , body);
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(DisplayActivity.this, "E-mail klijent nije instaliran.", Toast.LENGTH_SHORT).show();
                }
                startActivity(i);
                finish();
            }
        });
    }
}
