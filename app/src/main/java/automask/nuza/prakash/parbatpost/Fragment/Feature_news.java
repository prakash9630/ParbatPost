package automask.nuza.prakash.parbatpost.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import automask.nuza.prakash.parbatpost.Activity.News_deatil;
import automask.nuza.prakash.parbatpost.Pojo.FeatureData;
import automask.nuza.prakash.parbatpost.Public_URL;
import automask.nuza.prakash.parbatpost.R;


public class Feature_news extends Fragment {
    View mainView;
    ProgressDialog pDilog;
    FeatureData data;
    ArrayList<FeatureData> datalist;
    String url= Public_URL.featurenews;
    RecyclerView recyclerView;
    FeatureAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView=inflater.inflate(R.layout.feature_layout,container,false);
        pDilog=new ProgressDialog(getContext());
        pDilog.setCancelable(false);
        pDilog.setMessage("loading....");
        pDilog.show();

        recyclerView=(RecyclerView)mainView.findViewById(R.id.feature_list);

        getData();
        return mainView;
    }


    void getData()
    {
        final JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {



            @Override
            public void onResponse(JSONArray response) {

                pDilog.dismiss();
                datalist=new ArrayList<>();
                for (int i=0;i<response.length();i++)
                {
                    try {
                        JSONObject object=response.getJSONObject(i);

                        String title=object.getString("title");
                        String nid=object.getString("nid");
                        String imgurl=object.getString("field_mg_image");

                        data= new FeatureData(title,nid,imgurl);
                        datalist.add(data);

                        adapter=new FeatureAdapter(getContext(),datalist);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDilog.dismiss();

                if (isOnline()) {
                    Toast.makeText(getContext(), "Error"+ error, Toast.LENGTH_SHORT).show();


                }
                else
                {
                    Toast.makeText(getContext(),"No internet connection", Toast.LENGTH_SHORT).show();

                }
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);

    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}




class FeatureAdapter extends RecyclerView.Adapter<FeatureHolder>
{
    Context contex;
    LayoutInflater layoutinflater;
    ArrayList<FeatureData> data;

    public FeatureAdapter(Context contex, ArrayList<FeatureData> data) {
        this.contex = contex;
        this.layoutinflater = LayoutInflater.from(contex);
        this.data = data;
    }

    @Override
    public FeatureHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=layoutinflater.inflate(R.layout.news_display_design,parent,false);
        FeatureHolder holder=new FeatureHolder(view,contex,data);
        return holder;
    }

    @Override
    public void onBindViewHolder(FeatureHolder holder, int position) {
        FeatureData current=data.get(position);
        holder.textview.setText(current.getTitle());
        Picasso.with(contex)
                .load("http://parbatpost.com/"+current.getImage())
                .placeholder(R.drawable.defult)   // optional
                .error(R.drawable.defult)      // optional
                .resize(400, 190)
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

class FeatureHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    ImageView image;
    TextView textview;

    Context context;
    ArrayList<FeatureData> data;


    public FeatureHolder(View itemView, Context context, ArrayList<FeatureData> data) {
        super(itemView);
        this.context=context;
        this.data=data;
        itemView.setOnClickListener((View.OnClickListener) this);

        image=(ImageView)itemView.findViewById(R.id.recycler_img_travel);
        textview=(TextView)itemView.findViewById(R.id.name_placedynamic);
    }

    @Override
    public void onClick(View view) {

        final Animation animScale = AnimationUtils.loadAnimation(context, R.anim.button_animation);
        view.startAnimation(animScale);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                int positon=getAdapterPosition();

                FeatureData current=data.get(positon);



                Intent i=new Intent(context, News_deatil.class);
                i.putExtra("nid",current.getNid());
                i.putExtra("name",current.getTitle());
                i.putExtra("image",current.getImage());
                context.startActivity(i);



            }
        }, 200);





    }

}
