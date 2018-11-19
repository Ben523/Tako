package com.example.tako2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {
    private FirebaseAuth mAuth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        initLoginBtn(savedInstanceState);
        initRegisterBtn(savedInstanceState);
    }

    private void initLoginBtn(final Bundle savedInstanceState)
    {
        Button loginBtn = getView().findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userID = getView().findViewById(R.id.login_user_id);
                EditText passwordText = getView().findViewById(R.id.login_password);
                String username = userID.getText().toString();
                String password = passwordText.getText().toString();

                if(username.isEmpty()||password.isEmpty()){
                    Toast.makeText(getActivity(),
                            "USER OR PASSWORD IS EMPTY",
                            Toast.LENGTH_SHORT).show();
                    Log.d("USER","USER OR PASSWORD IS EMPTY");
                }else if(username.equals("admin") && password.equals("admin")){
                    Toast.makeText(getActivity(),
                            "“INVALID USER OR PASSWORD",
                            Toast.LENGTH_SHORT).show();
                    Log.d("USER","INVALID USER OR PASSWORD");
                }else{
                    Log.d("USER","GO TO BMI");
                    Log.d("USER",username);
                    mAuth.signInWithEmailAndPassword(username,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            if(mAuth.getCurrentUser().isEmailVerified()) {
                                if(savedInstanceState == null){
                                    getActivity().getSupportFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.main_view,new MenuFragment())
                                            .commit();
                                }
                            }
                            else
                            {
                                Toast.makeText(getActivity(), "Pleasse verify your email address then login again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "ERROR :"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }

    private void initRegisterBtn(final Bundle savedInstanceState)
    {
        TextView registerBtn = getView().findViewById(R.id.login_register_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("USER","GO TO REGISTER");
                if(savedInstanceState == null){
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view,new RegisterFragment())
                            .commit();
                }

            }
        });
    }
}
