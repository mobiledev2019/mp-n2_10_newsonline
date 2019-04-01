package com.garenalnews.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.garenalnews.R;
import com.garenalnews.common.Config;
import com.garenalnews.common.Untils;
import com.garenalnews.model.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.Arrays;

import io.karim.Utils;

/**
 * Created by ducth on 4/4/2018.
 */

public class MainLogin extends BaseActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private static final int RC_SIGN_IN = 9001;
    public static int TYPE_LOGIN;
    private static final int APP_REQUEST_CODE = 99;
    private LinearLayout btnLoginPhone;
    private LinearLayout btnLoginEmail;
    private LinearLayout btnLoginFacebook;
    private ProgressDialog dialog;
    /*login facebook*/
    private CallbackManager callbackManager = null;
    //google
    private GoogleApiClient mGoogleApiClient;
    //    private FirebaseAuth mAuth;
    private GoogleSignInAccount account;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login);
        getKeyHash();
        checkUser();
        initView();
        initData();
        initControl();
    }

    private void checkUser() {
        User user = Untils.getUser(this);
        if (user != null && !user.getTypeLogin().equals(Config.LOGIN_FB)) {
            startMainActivity(user);
        }
    }

    private void initView() {
        btnLoginPhone = findViewById(R.id.btnLoginPhone);
        btnLoginEmail = findViewById(R.id.btnLoginEmail);
        btnLoginFacebook = findViewById(R.id.btnLoginFacebook);
    }

    private void initData() {
        initGoogle();
        dialog = new ProgressDialog(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(final JSONObject object, GraphResponse response) {
                                if (object != null) {
                                    String email = object.optString("email") == null
                                            || object.optString("email").equals("")
                                            ? object.optString("id") : object.optString("email");
                                    String name = object.optString("name");
                                    String fbid = object.optString("id");
                                    String profilePicUrl = "";
                                    try {
                                        StrictMode.ThreadPolicy policy = new StrictMode
                                                .ThreadPolicy.Builder().permitAll().build();
                                        StrictMode.setThreadPolicy(policy);
                                        profilePicUrl = object.getJSONObject("picture")
                                                .getJSONObject("data").getString("url");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    User user = new User(fbid, Config.LOGIN_FB, name, "",
                                            email, profilePicUrl);
                                    startMainActivity(user);
                                } else {

                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,birthday,picture,email,gender");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ;
                        Toast.makeText(MainLogin.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onError(FacebookException error) {
                if (error instanceof FacebookAuthorizationException) {
                    if (com.facebook.AccessToken.getCurrentAccessToken() != null) {
                        LoginManager.getInstance().logOut();
                        loginFaceBook();
                    }
                }
            }
        });
    }

    private void initGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void initControl() {
        btnLoginPhone.setOnClickListener(this);
        btnLoginEmail.setOnClickListener(this);
        btnLoginFacebook.setOnClickListener(this);
    }

    public void loginPhone() {
        AccessToken accessToken = AccountKit.getCurrentAccessToken();
//        if (accessToken != null) {
//            loginAccountKitPhone(accessToken.getToken());
//        } else {
        final Intent intent = new Intent(MainLogin.this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN);
        configurationBuilder.setSMSWhitelist(new String[]{"VN"});
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, APP_REQUEST_CODE);
//        }
    }

    private void loginAccountKitPhone(final String token) {

        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(final Account account) {

                PhoneNumber phoneNumber = account.getPhoneNumber();
                String strPhone = phoneNumber.toString();
//                strPhone = strPhone.replace("+840", "0");
                strPhone = strPhone.replace("+84", "0");
                // Call API login
                User user = new User(Config.LOGIN_PHONE, strPhone);
                startMainActivity(user);
            }

            @Override
            public void onError(final AccountKitError error) {

            }
        });
    }

    private void startMainActivity(User user) {
        Intent intent = new Intent(MainLogin.this, MainActivity.class);
        intent.putExtra(Config.SEND_USER, user);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        this.finish();
    }

    public void getKeyHash() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo("com.garenalnews", PackageManager.GET_SIGNATURES);
            for (Signature signature : packageInfo.signatures) {
                MessageDigest digest = MessageDigest.getInstance("SHA");
                digest.update(signature.toByteArray());
                Log.d("KEY_HASH", Base64.encodeToString(digest.digest(), Base64.DEFAULT));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        dialog.dismiss();
        switch (TYPE_LOGIN) {
            case 0:
                if (requestCode == APP_REQUEST_CODE) {
                    final AccountKitLoginResult loginResult = data.getParcelableExtra(
                            AccountKitLoginResult.RESULT_KEY);
                    String toastMessage;
                    if (loginResult.getError() != null) {
                        toastMessage = loginResult.getError().getErrorType().getMessage();
                    } else if (loginResult.wasCancelled()) {

                    } else {
                        if (loginResult.getAccessToken() != null) {
                            loginAccountKitPhone(loginResult.getAccessToken().getToken());
                        } else {
                            AccessToken accessToken = AccountKit.getCurrentAccessToken();
                            if (accessToken == null) {
                                //loginAccountKitPhone("123");
                            } else {
                                loginAccountKitPhone(accessToken.getToken());
                            }
                        }
                    }
                }
                break;
            case 1:
                if (requestCode == RC_SIGN_IN) {
                    GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                    if (result.isSuccess()) {
                        GoogleSignInAccount acct = result.getSignInAccount();
                        //TO USE
                        String personName = acct.getDisplayName();
                        String personEmail = acct.getEmail();
                        String personId = acct.getId();
                        Uri personPhoto = acct.getPhotoUrl();
                        String personPhoneURL = "";
                        if (personPhoto != null) {
                            personPhoneURL = personPhoto.toString();
                        }
                        User user = new User(personId, Config.LOGIN_EMAIL, personName, "",
                                personEmail, personPhoneURL);
                        startMainActivity(user);
                    } else {

                    }
                }
                break;
            case 2:
                callbackManager.onActivityResult(requestCode, resultCode, data);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void loginFaceBook() {
        if (Profile.getCurrentProfile() != null) {
            LoginManager.getInstance().logOut();
        } else {
            dialog.setMessage("Connect with Facebook...");
            dialog.setCancelable(false);
            dialog.show();
        }
        LoginManager.getInstance().logInWithReadPermissions(this,
                Arrays.asList("public_profile", "user_friends", "email"));
    }

    public void loginEmail() {
        dialog.setMessage("Connect with Google...");
        dialog.setCancelable(false);
        dialog.show();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onBackPressed() {
        dialog.dismiss();
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLoginPhone:
                TYPE_LOGIN = 0;
                loginPhone();
                break;
            case R.id.btnLoginEmail:
                TYPE_LOGIN = 1;
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                loginEmail();
                break;
            case R.id.btnLoginFacebook:
                TYPE_LOGIN = 2;
                loginFaceBook();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
