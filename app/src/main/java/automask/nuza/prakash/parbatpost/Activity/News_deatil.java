package automask.nuza.prakash.parbatpost.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import automask.nuza.prakash.parbatpost.R;

public class News_deatil extends AppCompatActivity {
    ProgressDialog pDilog;
    Toolbar toolbar;
    String nid,image,name;
    Intent i;
    ImageView newsimg;
    TextView newstxt;
    TextView newstitle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Toggle_color));
        }



        newsimg=(ImageView)findViewById(R.id.news_image);
        newstxt=(TextView) findViewById(R.id.news_detail_txt);
        newstitle=(TextView)findViewById(R.id.news_title);
        i=getIntent();

        nid=i.getStringExtra("nid");
        image=i.getStringExtra("image");
        name=i.getStringExtra("name");

        pDilog=new ProgressDialog(this);
        pDilog.setCancelable(false);
        pDilog.setMessage("loading....");
        pDilog.show();

        toolbar=(Toolbar)findViewById(R.id.news_detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setTitle(name);


        getData();
    }


    void getData()
    {
        final JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, "http://parbatpost.com/ne/node/"+nid+"?_format=json&fbclid=IwAR0ORvTDZscwheztUzfo0b2ZjBWO3BT3lvOoL4tCbk7oGTzi4U1u9fjrXEk", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pDilog.dismiss();

                try {
                    JSONArray arr=response.getJSONArray("field_mg_summary");
//                    for (int i=0;i<arr.length();i++)
//                    {
//
//
//                    }
                    JSONObject object=arr.getJSONObject(0);
                    String detail=object.getString("value");


                    Picasso.with(News_deatil.this)
                            .load("http://parbatpost.com/"+image)
                            .placeholder(R.drawable.defult)   // optional
                            .error(R.drawable.defult)      // optional
                            .resize(600, 340)
                            .into(newsimg);
                    newstxt.setText(detail);
                    newstitle.setText(name);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDilog.dismiss();
                if (isOnline())
                {
                    Toast.makeText(News_deatil.this, "Error"+error, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(News_deatil.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                }

            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
