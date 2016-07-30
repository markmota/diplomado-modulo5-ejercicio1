package modulo5.ddam.markmota.tk.space;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import retrofit2.Call;

import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import modulo5.ddam.markmota.tk.space.data.ApodService;
import modulo5.ddam.markmota.tk.space.data.Data;
import modulo5.ddam.markmota.tk.space.model.Apod;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ImageView appImg;
    private TextView titleImg;
    private TextView descImg;
    private TextView dateImg;
    private TextView copyImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appImg= (ImageView) findViewById(R.id.activity_main_image);
        titleImg=(TextView) findViewById(R.id.activity_main_title);
        descImg=(TextView) findViewById(R.id.activity_main_desc);
        dateImg=(TextView) findViewById(R.id.activity_main_date);
        copyImg=(TextView) findViewById(R.id.activity_main_copy);

        ApodService apodService= Data.getRetrofitInstance().create(ApodService.class);
        Call<Apod> callApodService =apodService.getTodayPod(BuildConfig.NasaApiKey,"2016-06-28");

        callApodService.enqueue(new Callback<Apod>(){
            @Override
            public  void onResponse(Call<Apod> call, Response<Apod> response){
                Log.d("APODTitle",response.body().getTitle());
                Log.d("APODUrl",response.body().getUrl());
                String imageToUse=response.body().getUrl();
                String titleImage=response.body().getTitle();
                String descImage=response.body().getExplanation();
                String dateImage=response.body().getDate();
                String copyImage=response.body().getCopyright();
                titleImg.setText(titleImage);
                descImg.setText(descImage);
                dateImg.setText(dateImage);
                if(TextUtils.isEmpty(copyImage))
                    copyImg.setText("by Anonymous");
                else
                    copyImg.setText("by "+copyImage);
                Picasso.with(getApplicationContext())
                        .load(imageToUse)
                        .resize(500, 300)
                        .centerCrop()
                        .placeholder(android.R.drawable.ic_input_get)
                        .error(android.R.drawable.ic_dialog_alert)
                        .into(appImg);


            }
            @Override
            public void onFailure(Call<Apod> call,Throwable t){

            }
        });




    }

}
