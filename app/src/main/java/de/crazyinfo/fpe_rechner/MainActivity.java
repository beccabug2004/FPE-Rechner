package de.crazyinfo.fpe_rechner;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends Activity

        implements OnClickListener {

    /**
     * Wird aufgerufen, wenn die Aktivität erstellt wird.
     */

    /* Variablen für Button, EditText und TextView */
    private Button buttonCalc;                                                                      // "Calculation" button
    private EditText editTextCho;                                                                   // carbohydrates
    private EditText editTextKcal;                                                                  // KCAL
    private EditText editTextFactor;                                                                // factor
    private TextView textViewResult;                                                                // Result

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    private GoogleApiClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Definition App (Button, Text etc. mit Hinweisausgabe)
        buttonCalc = (Button) findViewById(R.id.buttonCalc);
        editTextCho = (EditText) findViewById(R.id.editTextCho);
        editTextKcal = (EditText) findViewById(R.id.editTextKcal);
        editTextFactor = (EditText) findViewById(R.id.editTextFactor);
        textViewResult = (TextView) findViewById(R.id.textViewResult);

        buttonCalc.setOnClickListener(this);

            // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    // OnClick wird aufgerufen, wenn geklickt wird

    @Override
    public void onClick(View v) {

        /* Check whether there is an empty entry and output it as a toast message */
        if (("".equals(editTextCho.getText().toString().trim()) || "".equals(editTextKcal.getText().toString().trim()) || "".equals(editTextFactor.getText().toString().trim()))){
            Toast.makeText(this, getString(R.string.noValue), Toast.LENGTH_LONG).show();
            return;
        }

        if (v == buttonCalc) {

            /* Declare variables */
            double cho;                 // Carbohydrate
            double kcal;                // Kcal
            double factor;              // Factor
            double insulin;             // Insulin
            double fpu;                 // Rounded fat protein units

            /* This receives the value of the field as a number (carbohydrates & KCAL & factor) */
            cho = Float.valueOf(editTextCho.getText().toString());
            kcal = Float.valueOf(editTextKcal.getText().toString());
            factor = Float.valueOf(editTextFactor.getText().toString());

            /* Calculations */
            cho = Math.abs(cho * 4);                                                                // Carbohydrates * 4
            fpu = (double) Math.round(((kcal - cho) / 100 * 10) * 1) / 10;                          // FPE content rounded to one decimal place
            insulin = (double) Math.round(((kcal - cho) / 100 * factor * 10) * 1) / 10;             // Amount of insulin rounded to one decimal place

            /*  Hour comparison for insulin delivery */
            String[] arrayCheckHours = {"3", "4", "5", "7 - 8", "0"};
            String checkHours = "0";

            if (kcal >= 100 && kcal < 200) {
                System.out.println(arrayCheckHours[0]);         // 3 Hours
                checkHours = arrayCheckHours[0].toString();
            }
            if (kcal >= 200 && kcal < 300) {
                System.out.println(arrayCheckHours[1]);         // 4 Hours
                checkHours = arrayCheckHours[1].toString();
            }
            if (kcal >= 300 && kcal < 400) {
                System.out.println(arrayCheckHours[2]);         // 5 Hours
                checkHours = arrayCheckHours[2].toString();
            }
            if (kcal >= 400) {
                System.out.println(arrayCheckHours[3]);         // 7 - 8 Hours
                checkHours = arrayCheckHours[3].toString();
            }
            if (insulin <= 0) {
                System.out.println(arrayCheckHours[4]);         // If insulin is less than or equal to 0, then also 0 hours
                checkHours = arrayCheckHours[4].toString();
            }
            if (fpu <= 0) {
                System.out.println(arrayCheckHours[4]);
                checkHours = arrayCheckHours[4].toString();     // If fat-protein units are less than or equal to 0, then also 0 hours
            }

            /*  Check insulin delivery*/
            double[] arrayInsulin = {0};
            if (insulin < 0) {
                insulin = arrayInsulin[0];                      // If insulin is less than 0, then also insulin 0
            }
            if (kcal < 100) {
                insulin = arrayInsulin[0];                      // If Kcal is less than 0, then also insulin 0
            }

            /*  Check the FPE content*/
            double[] arrayFpu = {0};
            if (fpu < 0) {
                fpu = arrayFpu[0];                              // If FPE is less than 0, then also FPE 0
            }
            if (insulin <= 0) {
                fpu = arrayFpu[0];                              // If insulin is less than or equal to 0, then FPE 0 as well
            }

            /* Results output */
            textViewResult.setText(getString(R.string.calcResult) + fpu + getString(R.string.textFpu) + insulin + getString(R.string.amountOfInsulin) + checkHours + getString(R.string.hoursText));
        }
    }
}
