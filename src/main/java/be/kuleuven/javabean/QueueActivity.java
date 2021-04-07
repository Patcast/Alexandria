package be.kuleuven.javabean;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class QueueActivity extends AppCompatActivity {
    private TextView txtInfo;
    private RequestQueue requestQueue;
    private static final String SUBMIT_URL = "https://studev.groept.be/api/ptdemo/order/";
    private static final String QUEUE_URL = "https://studev.groept.be/api/ptdemo/queue";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);
        txtInfo = (TextView) findViewById(R.id.txtInfo);
        requestQueue = Volley.newRequestQueue(this);
        Bundle extras = getIntent().getExtras();
        String toppings = (extras.getBoolean("Sugar")? "+sugar" : "") + (extras.getBoolean("WhipCream")? "+cream" : "");
        String requestURL = SUBMIT_URL + extras.get("Name") + "/" +
                extras.get("Coffee") + "/" +
                ((toppings.length() == 0)? "-" : toppings) + "/" +
                extras.get("Quantity");
        JsonArrayRequest queueRequest = new JsonArrayRequest(Request.Method.GET,QUEUE_URL,null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String info = "";
                for (int i=0; i<response.length(); ++i) {
                    JSONObject o = null;
                    try {
                        o = response.getJSONObject(i);
                        info += o.get("customer") + ": " + o.get("coffee") + " x " + o.get("quantity") + " " +
                                o.get("toppings") + " will be ready at " + o.get("date_due") + "\n";
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                txtInfo.setText(info);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(QueueActivity.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
            }
        });
        StringRequest submitRequest = new StringRequest(Request.Method.GET, requestURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(QueueActivity.this, "Order placed", Toast.LENGTH_SHORT).show();

                requestQueue.add(queueRequest);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(QueueActivity.this, "Unable to place the order", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(submitRequest);
    }
}
