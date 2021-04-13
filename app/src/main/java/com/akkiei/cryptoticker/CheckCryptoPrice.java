package com.akkiei.cryptoticker;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;
import static com.akkiei.cryptoticker.CreateTriggers.crNameS;
import static com.akkiei.cryptoticker.CreateTriggers.index;
import static com.akkiei.cryptoticker.CreateTriggers.operatorS;
import static com.akkiei.cryptoticker.CreateTriggers.priceS;

public class CheckCryptoPrice extends Worker {

    public CheckCryptoPrice(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String url = "https://api.wazirx.com/api/v2/tickers";
        final boolean[] flag = {true};
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responseObj = new JSONObject(response);
                    for (int i = 0; i < index; i++) {
                        Log.d(TAG, "" + crNameS[i]);
                        JSONObject crypto = (JSONObject) responseObj.get(crNameS[i] + "inr");
                        double last = Double.parseDouble((String) crypto.get("last"));
                        Log.d(TAG, "" + crNameS[i]);
                        if (operatorS[i] != null && checkTriggerCondition(operatorS[i], priceS[i], last)) {
                            postNotificationClicked(crNameS[i] + " ALERT ! ", "The current price(INR) : " + last);
                        }
                    }
                    flag[0] = true;
                } catch (JSONException e) {
                    e.printStackTrace();
                    flag[0] = false;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error in doWorker");
                flag[0] = false;
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
        if (flag[0])
            return Result.success();
        else
            return Result.failure();
    }

    boolean checkTriggerCondition(String operator, double price, double currPrice) {
        if (operator.equalsIgnoreCase("less than")) {
            return currPrice < price;
        }
        if (operator.equalsIgnoreCase("less than equal to")) {
            return currPrice <= price;
        }
        if (operator.equalsIgnoreCase("greater than")) {
            return currPrice > price;
        }
        if (operator.equalsIgnoreCase("greater than equal to")) {
            return currPrice >= price;
        } else {
            return currPrice == price;
        }
    }

    public void postNotificationClicked(String title, String text) {
        String channelId = String.valueOf((int) Math.ceil(Math.random() * 1000));
        PostNotifications postNotifications = new PostNotifications(getApplicationContext());
        postNotifications.createNotification(title, text, channelId);
    }
}
