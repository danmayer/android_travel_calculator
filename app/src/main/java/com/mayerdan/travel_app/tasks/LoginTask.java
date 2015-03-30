package com.mayerdan.travel_app.tasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.mayerdan.travel_app.ASPApp;
import com.mayerdan.travel_app.FirstActivity;
import com.mayerdan.travel_app.model.AuthResponse;
import com.mayerdan.travel_app.model.User;
import com.mayerdan.travel_app.model.UserObject;

/**
 * Created by danmayer on 2/23/15.
 */
public class LoginTask extends AsyncTask<String, Integer, AuthResponse> {

    private Context mContext;
    private String mUserEmail;
    private String mUserPassword;
    private Activity mActivity;

    public LoginTask (Context context, String email, String pass, Activity activity) {
        mContext = context;
        mUserEmail = email;
        mUserPassword = pass;
        mActivity = activity;
    }

    @Override
    protected AuthResponse doInBackground(String... params) {
        AuthResponse auth = null;
        try {
            UserObject user_object = new UserObject(new User(mUserEmail, mUserPassword));
            auth = ASPApp.service.loginUser(user_object);

            return auth;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("JSON", "" + auth);
        }

        return auth;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(AuthResponse auth) {
        super.onPostExecute(auth);
        try {
            if (auth.authToken!=null) {
                Log.i("login_success", "setting token from json: "+auth.authToken);
                ((ASPApp) mContext.getApplicationContext()).setToken(auth.authToken);

                Intent intent = new Intent(mContext.getApplicationContext(), FirstActivity.class);
                mContext.startActivity(intent);
                mActivity.finish();
            }
            Toast.makeText(mContext, auth.error, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            // something went wrong: show a Toast
            // with the exception message
            Log.e("login_error",
                    "Error with json response result " + e.toString() + " json: "+auth);
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            super.onPostExecute(auth);
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }
}
