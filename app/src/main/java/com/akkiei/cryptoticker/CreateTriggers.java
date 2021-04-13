package com.akkiei.cryptoticker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CreateTriggers extends AppCompatActivity {
    public static int index = 0;
    public static String[] crNameS = new String[50];
    public static String[] operatorS = new String[50];
    public static double[] priceS = new double[50];

    private static final String[] operators = new String[]
            {
                    "less than",
                    "less than equal to",
                    "greater than",
                    "greater than equal to",
                    "equals to"
            };

    private static final String[] cryptoNames = new String[]{
            "btc", "xrp", "eth", "trx", "eos", "zil", "bat", "zrx", "omg", "hot", "usdt",
            "wrx", "matic", "bch", "bnb", "btt", "chz", "one", "yfi", "uni", "link",
            "sxp", "ada", "nano", "atom", "xlm", "fet", "xem", "zec", "busd", "yfii",
            "doge", "dot", "vet", "easy", "crv", "ren", "enj", "mana", "hbar", "uma",
            "chr", "paxg", "1inch", "etc", "uft", "dock", "fil", "win", "ltc", "dash"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_triggers);
        Button button = this.findViewById(R.id.submit_trigger_button);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, operators);
        Spinner textView = (Spinner)
                findViewById(R.id.operator_spinner);
        textView.setAdapter(adapter);

        ArrayAdapter<String> adapterCrypto = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, cryptoNames);
        AutoCompleteTextView textViewCrypto = (AutoCompleteTextView)
                findViewById(R.id.crypto_name_tv);
        textViewCrypto.setAdapter(adapterCrypto);

    }

    public void AddTrigger(View view) {
        AutoCompleteTextView cryptoNameTv = (AutoCompleteTextView) this.findViewById(R.id.crypto_name_tv);
        Spinner spinner = this.findViewById(R.id.operator_spinner);
        EditText priceET = this.findViewById(R.id.price_editText);
        String cryptoName = (String) cryptoNameTv.getText().toString();
        crNameS[index] = cryptoName;
        String operator = (String) spinner.getSelectedItem().toString();
        operatorS[index] = operator;
        double price = Double.parseDouble(String.valueOf(priceET.getText()));
        priceS[index] = price;
        index++;
        if (cryptoName.length() == 0 || operator.length() == 0 || price == 0 ||
                !Arrays.asList(cryptoNames).contains(cryptoName)) {

            Toast.makeText(this, "Please enter all values correctly", Toast.LENGTH_SHORT).show();

        } else {
            String tag = cryptoName + price;
            Constraints constraints = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build();
            PeriodicWorkRequest saveRequest =
                    new PeriodicWorkRequest.Builder(CheckCryptoPrice.class, 15, TimeUnit.MINUTES)
                            .addTag(tag)
                            .setConstraints(constraints)
                            .build();
            WorkManager.getInstance(this).enqueueUniquePeriodicWork(tag, ExistingPeriodicWorkPolicy.KEEP, saveRequest);// enqueue(saveRequest);
            Toast.makeText(this, "Trigger created successfully !", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
//            WorkRequest uploadWorkRequest =
//                    new OneTimeWorkRequest.Builder(CheckCryptoPrice.class)
//                            .build();
//            WorkManager.getInstance(this).enqueue(uploadWorkRequest);
        }
    }
}