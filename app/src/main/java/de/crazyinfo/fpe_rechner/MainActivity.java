package de.crazyinfo.fpe_rechner;

import java.util.Arrays;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends Activity

        implements OnClickListener {
    /**
     * Called when the activity is first created.
     */

    /* Variablen für Button, EditText und TextView */
    private Button buttonCalc;                                                                      // Button "Berechnung"
    private EditText editTextCho;                                                                   // Kohlenhydrate
    private EditText editTextKcal;                                                                  // KCAL
    private EditText editTextFactor;                                                                // Faktor
    private TextView textViewResult;                                                                // Ergebnis

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

        /* Definition der Error Buttons */
        editTextCho.setError(getString(R.string.choNote));
        editTextKcal.setError(getString(R.string.kcalNote));
        editTextFactor.setError(getString(R.string.factorNote));
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    // OnClick wird aufgerufen, wenn geklickt wird

    @Override
    public void onClick(View v) {

        if (v == buttonCalc) {

            /* Variablen deklarieren */
            double cho;         // Kohlenhydrate
            double kcal;
            double factor;
            double insulin;
            double fpu;         // Fett-Protein-Einheiten

            /* Diese erhält den Wert des Feldes als Zahl (Kohlenhydrate & KCAL & Faktor) */
            cho = Float.valueOf(editTextCho.getText().toString());
            kcal = Float.valueOf(editTextKcal.getText().toString());
            factor = Float.valueOf(editTextFactor.getText().toString());

            /* Berechnungen */
            cho = (double) Math.round(cho * 4);                 // Kohlenhydrate * 4
            fpu = (double) Math.round(kcal - cho) / 100;        // FPE-Gehalt gerundet
            insulin = (double) Math.round(fpu * factor);        // Insulinmenge gerundet

            /*  Stundenvergleich für die Insulinabgabe */
            int[] arrayCheckHours = {3, 4, 5, 7};

            int checkHours = 0;
            // string checkHours = "";

            if (kcal >= 100 && kcal < 200)
            {
                System.out.println(arrayCheckHours[0]); // 3 Stunden
                checkHours = arrayCheckHours[0];
                // checkHours = arrayCheckHours[0].toString();
            }
            if (kcal >= 200 && kcal < 300)
            {
                System.out.println(arrayCheckHours[1]); // 4 Stunden
                checkHours = arrayCheckHours[1];
                // checkHours = arrayCheckHours[1].toString();
            }
            if (kcal >= 300 && kcal < 400)
            {
                System.out.println(arrayCheckHours[2]); // 5 Stunden
                checkHours = arrayCheckHours[2];
                // checkHours = arrayCheckHours[2].toString();
            }
            if (kcal >= 400) {
                System.out.println(arrayCheckHours[3]); // 7 Stunden
                checkHours = arrayCheckHours[3];
                // checkHours = arrayCheckHours[3].toString();
            }

            /* Ergebnisausgabe */
            textViewResult.setText(getString(R.string.calcResult) + fpu + getString(R.string.textFpu) + insulin + getString(R.string.amountOfInsulin) + checkHours + getString(R.string.hoursText));
        }
    }
}
