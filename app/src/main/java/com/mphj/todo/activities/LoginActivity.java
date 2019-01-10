package com.mphj.todo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.mphj.todo.BaseActivity;
import com.mphj.todo.R;
import com.mphj.todo.repositories.Repository;
import com.mphj.todo.repositories.rest.models.requests.LoginRequest;
import com.mphj.todo.repositories.rest.models.requests.SignupRequest;
import com.mphj.todo.repositories.rest.models.responses.LoginResponse;
import com.mphj.todo.repositories.rest.models.responses.SignupResponse;
import com.mphj.todo.utils.Auth;
import com.mphj.todo.utils.ConstraintsUtils;
import com.mphj.todo.utils.DeviceUtils;
import com.mphj.todo.utils.FontUtils;
import com.mphj.todo.workers.DBSyncer;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.transition.TransitionManager;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.email)
    EditText email;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.password_repeat)
    EditText passwordRepeat;

    @BindView(R.id.password_layout)
    TextInputLayout passwordLayout;

    @BindView(R.id.password_repeat_layout)
    TextInputLayout passwordRepeatLayout;

    @BindView(R.id.submit)
    Button submit;

    @BindView(R.id.container)
    ConstraintLayout constraintLayout;

    @BindView(R.id.progress_line)
    ProgressBar progressBar;

    @BindView(R.id.switch_page)
    TextView switchPageLink;

    private boolean isSignupPage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initViews();
    }

    void initViews() {
        password.setTypeface(FontUtils.def(this));
        passwordLayout.setTypeface(FontUtils.def(this));

        passwordRepeat.setTypeface(FontUtils.def(this));
        passwordRepeatLayout.setTypeface(FontUtils.def(this));
    }

    @OnClick(R.id.submit)
    public void onSubmit() {
        showProgressBar();
        if (isSignupPage) {
            if (!validPassword()) {
                incorrectPassword();
                hideProgressBar();
                return;
            }
            doSignUp();
        } else {
            doLogin();
        }
    }


    void doSignUp() {
        final SignupRequest signupRequest = new SignupRequest();
        signupRequest.email = email.getText().toString();
        signupRequest.password = password.getText().toString();
        Repository.todoService().signUp(signupRequest).enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                SignupResponse signupResponse = response.body();
                if (signupResponse.status == 200) {
                    signUpSuccessful();
                    onSwitchPage();
                } else if (signupResponse.msg.contains("EMAIL_ALREADY_EXISTS")) {
                    errorEmailAlreadyExists();
                }
                hideProgressBar();
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                errorPasswordTooShort();
                hideProgressBar();
            }
        });
    }


    void doLogin() {
        final LoginRequest loginRequest = new LoginRequest();
        loginRequest.email = email.getText().toString().trim();
        loginRequest.password = password.getText().toString().trim();
        loginRequest.imei = DeviceUtils.getId(getApplicationContext());
        Repository.todoService().login(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if (loginResponse.status == 200) {
                    Auth.login(loginRequest.email, loginResponse.token, getApplicationContext());
                    WorkManager.getInstance().cancelUniqueWork(DBSyncer.NAME);
                    OneTimeWorkRequest dbSync = new OneTimeWorkRequest.Builder(DBSyncer.class)
                            .setConstraints(ConstraintsUtils.requireInternet())
                            .build();
                    WorkManager.getInstance().enqueueUniqueWork(DBSyncer.NAME, ExistingWorkPolicy.REPLACE.REPLACE, dbSync);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    cleanPasswordFields();
                    loginFailed();
                }
                hideProgressBar();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                connectionFailed();
                hideProgressBar();
            }
        });
    }

    boolean validPassword() {
        return password.getText().toString().trim().equals(passwordRepeat.getText().toString().trim());
    }

    void incorrectPassword() {
        Toasty.error(this, R.string.err_password_mismatch).show();
    }

    void showProgressBar() {
        submit.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
    }

    void loginFailed() {
        Toasty.error(this, R.string.err_login_failed).show();
    }

    void signUpSuccessful() {
        Toasty.success(this, R.string.signup_successful).show();
    }

    void errorPasswordTooShort() {
        Toasty.error(this, getString(R.string.err_password_must_be_gt_x).replace("x", "8")).show();
    }

    void errorEmailAlreadyExists() {
        Toasty.error(this, getString(R.string.err_email_already_exists).replace("x", "8")).show();
    }

    void connectionFailed() {
        Toasty.error(this, R.string.err_connection_failed).show();
    }

    void hideProgressBar() {
        submit.setEnabled(true);
        progressBar.setVisibility(View.GONE);
    }

    void cleanPasswordFields() {
        password.setText(null);
        passwordRepeat.setText(null);
    }

    @OnClick(R.id.switch_page)
    public void onSwitchPage() {
        TransitionManager.beginDelayedTransition(constraintLayout);
        if (isSignupPage) {
            passwordRepeatLayout.setVisibility(View.GONE);
            switchPageLink.setText(R.string.sign_up_note);
            submit.setText(R.string.login);
        } else {
            passwordRepeatLayout.setVisibility(View.VISIBLE);
            switchPageLink.setText(R.string.sign_in_note);
            submit.setText(R.string.register);
        }
        isSignupPage = !isSignupPage;
    }

}
