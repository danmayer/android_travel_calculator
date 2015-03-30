package com.mayerdan.travel_app.tasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.mayerdan.travel_app.ASPApp;
import com.mayerdan.travel_app.FirstActivity;
import com.mayerdan.travel_app.model.AuthResponse;
import com.mayerdan.travel_app.model.User;
import com.mayerdan.travel_app.model.UserObject;

public class RegisterTask extends AsyncTask<String, Integer, AuthResponse> {

    private Context mContext;
    private SharedPreferences mPreferences;
    private String mUserEmail;
    private String mUserName;
    private String mUserPassword;
    private String mUserPasswordConfirmation;
    private Activity mActivity;

    /* todo ask Mike about a better way to pass all these fields around */
    public RegisterTask (Context context, SharedPreferences cPreferences, String cUserEmail, String cUserName, String cUserPassword, String cUserPasswordConfirmation, Activity activity) {
        mContext = context;
        mPreferences = cPreferences;
        mUserEmail = cUserEmail;
        mUserName = cUserName;
        mUserPassword = cUserPassword;
        mUserPasswordConfirmation = cUserPasswordConfirmation;
        mActivity = activity;
    }

    @Override
    protected AuthResponse doInBackground(String... params) {
        AuthResponse auth = null;

        try {
            UserObject
                user_object = new UserObject(new User(mUserEmail, mUserName, mUserPassword, mUserPasswordConfirmation));
            auth = ASPApp.service.registerUser(user_object);

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
            if (auth.success) {
                Log.e("login_success",
                        "setting token from json: "+auth.toString());
                // everything is ok
                SharedPreferences.Editor editor = mPreferences.edit();

                // save the returned auth_token into
                // the SharedPreferences
                String token = auth.userData.authToken;
                editor.putString("AuthToken", token);
                editor.commit();

                // launch the HomeActivity and close this one
                Intent intent = new Intent  (mContext.getApplicationContext(), FirstActivity.class);
                mContext.startActivity(intent);
                mActivity.finish();
            }
            Toast.makeText(mContext, auth.toString(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            // something went wrong: show a Toast
            // with the exception message
            Log.e("login_error",
                    "Error with json response result " + e.toString() + " json: "+auth.toString());
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
