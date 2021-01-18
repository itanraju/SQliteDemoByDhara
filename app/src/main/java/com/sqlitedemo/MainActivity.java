package com.sqlitedemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.LongDef;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sqlitedemo.DBHelper.DataBase;
import com.sqlitedemo.Model.ThemelModel;
import com.sqlitedemo.activity.AllDataListActivity;
import com.sqlitedemo.retrofit.APIClient;
import com.sqlitedemo.retrofit.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RelativeLayout rlThemeLoading;
    private ArrayList<ThemelModel> ThemeListCategoryWise = new ArrayList<>();
    private DataBase dataBase;
    private Button btn_submit;
    private ThemelModel themeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataBase = new DataBase(MainActivity.this);

        rlThemeLoading = findViewById(R.id.rl_loading_pager);
        btn_submit = findViewById(R.id.btn_submit);

        findViewById(R.id.btn_display).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AllDataListActivity.class);
                startActivity(intent);
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetCategoryOfTheme();

            }
        });
    }

    private void GetCategoryOfTheme() {
        APIClient.getRetrofit().create(ApiInterface.class).GetAllTheme("aciativtyksdfhal5215ajal", "11").enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                Log.e("TAG", "Response Data" + response.body());
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObj = new JSONObject(new Gson().toJson(response.body()));
                        JSONArray jsonArray = jsonObj.getJSONArray("category");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject themeJSONObject = jsonArray.getJSONObject(i);
                            JSONArray jSONArray4 = themeJSONObject.getJSONArray("themes");
                            for (int j = 0; j < jSONArray4.length(); j++) {
                                JSONObject jsonobjecttheme = jSONArray4.getJSONObject(j);
                                themeModel = new ThemelModel();
                                themeModel.setThemeid(jsonobjecttheme.getString("id"));
                                themeModel.setThemeName(jsonobjecttheme.getString("theme_name"));
                                themeModel.setImage(jsonobjecttheme.getString("theme_thumbnail"));
                                ThemeListCategoryWise.add(themeModel);


                                if (dataBase.Isidexists(themeModel)) {
                                    Log.e("TAG", "Is Exist" + themeModel.getThemeName());
                                    Toast.makeText(MainActivity.this, "Already Exists", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.e("TAG", "Insert New Data" + themeModel.getThemeName());
                                    SaveThumbnailIntoDb saveIntoDatabase = new SaveThumbnailIntoDb();
                                    saveIntoDatabase.execute(themeModel);
                                }
                            }
                        }

                        rlThemeLoading.setVisibility(View.GONE);
                    } catch (final JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private byte[] profileImage(Bitmap b) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();

    }


    public class SaveThumbnailIntoDb extends AsyncTask<ThemelModel, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(ThemelModel... params) {
            ThemelModel dataModel = params[0];
            //ThemelModel dataModel = new ThemelModel();
            try {
                InputStream inputStream = new URL(dataModel.getImage()).openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                dataModel.setPic(profileImage(bitmap));
                Log.e("TAG", "Save Img" + dataModel.getThemeName());
                dataBase.InsertImage(dataModel);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
