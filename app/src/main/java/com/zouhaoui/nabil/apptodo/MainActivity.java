package com.zouhaoui.nabil.apptodo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.facebook.*;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity {

    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    private String firstName,lastName, email,birthday,gender;
    private URL profilePicture;
    private Albums albums = new Albums();
    private String userId;
    private String TAG = "MainActivity";
    private TextView info;
    private LoginButton loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callbackManager = CallbackManager.Factory.create();
        info = (TextView)findViewById(R.id.info);
        loginButton = (LoginButton)findViewById(R.id.login_button);


            try {
                userId = Profile.getCurrentProfile().getId();
                profilePicture = new URL("https://graph.facebook.com/" + userId + "/picture?width=500&height=500");
                firstName = Profile.getCurrentProfile().getFirstName();
                lastName = Profile.getCurrentProfile().getLastName();


                Log.d("First name",firstName);
                Log.d("Last name",lastName);
                new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/"+userId+"/albums",
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {
                                try {
                                    Log.e("Albums",response.getJSONObject().toString());
                                    JSONArray jsondata = response.getJSONObject().getJSONArray("data");
                                    for(int i=0;i<jsondata.length();i++)
                                    {
                                        JSONObject albums_object = jsondata.getJSONObject(i);
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                        Date date = sdf.parse(albums_object.getString("created_time"));
                                        albums.getAlbums().add(new album(albums_object.getInt("id"), albums_object.getString("name"),date));
                                        Log.e("name",albums_object.getString("name"));

                                    }
                                    Intent main = new Intent(MainActivity.this, FacebookActivity.class);
                                    main.putExtra("name", firstName);
                                    main.putExtra("albums",albums);
                                    main.putExtra("surname", lastName);
                                    main.putExtra("imageUrl", profilePicture.toString());
                                    startActivity(main);
                                    finish();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                ).executeAsync();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
             catch (Exception e) {
                e.printStackTrace();
            }
        FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.e(TAG, object.toString());
                        Log.e(TAG, response.toString());

                        try {
                            userId = object.getString("id");
                            profilePicture = new URL("https://graph.facebook.com/" + userId + "/picture?width=500&height=500");

                            if (object.has("first_name"))
                                firstName = object.getString("first_name");
                            if (object.has("last_name"))
                                lastName = object.getString("last_name");
                            if (object.has("email"))
                                email = object.getString("email");
                            if (object.has("birthday"))
                                birthday = object.getString("birthday");
                            if (object.has("gender"))
                                gender = object.getString("gender");
                            new GraphRequest(
                                    AccessToken.getCurrentAccessToken(),
                                    "/" + userId + "/albums",
                                    null,
                                    HttpMethod.GET,
                                    new GraphRequest.Callback() {
                                        public void onCompleted(GraphResponse response) {
                                            JSONArray jsondata = null;
                                            try {
                                                Log.e("Albums",response.getJSONObject().toString());
                                                jsondata = response.getJSONObject().getJSONArray("data");
                                                for(int i=0;i<jsondata.length();i++)
                                                {
                                                    JSONObject albums_object = jsondata.getJSONObject(i);
                                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                    Date date = sdf.parse(albums_object.getString("created_time"));
                                                    albums.getAlbums().add(new album(albums_object.getInt("id"), albums_object.getString("name"),date));
                                                    Log.e("name",albums_object.getString("name"));

                                                }
                                                Intent main = new Intent(MainActivity.this, FacebookActivity.class);
                                                main.putExtra("name", firstName);
                                                main.putExtra("albums",albums);
                                                main.putExtra("surname", lastName);
                                                main.putExtra("imageUrl", profilePicture.toString());
                                                startActivity(main);
                                                finish();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                            ).executeAsync();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                });
                //Here we put the requested fields to be returned from the JSONObject
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email, birthday, gender");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                info.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {
                info.setText("Login attempt failed." + e);
            }
        };
        loginButton.registerCallback(callbackManager, callback);
        loginButton.setReadPermissions("email", "user_birthday","user_posts","user_photos");
        loginButton.registerCallback(callbackManager, callback);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
