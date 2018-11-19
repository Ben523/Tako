package com.example.tako2;

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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterFragment extends Fragment {
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAuth  = FirebaseAuth.getInstance();
        initRegisterBtn2(savedInstanceState);

    }
    private void initRegisterBtn2(final Bundle savedInstanceState)
    {
        Button registerBtn = getView().findViewById(R.id.register_register_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userID = getView().findViewById(R.id.register_user_id);
                EditText passText = getView().findViewById(R.id.register_password);
                EditText pass2Text = getView().findViewById(R.id.register_re_password);

                String username = userID.getText().toString();
                String password = passText.getText().toString();
                String password2 = pass2Text.getText().toString();

                if(username.isEmpty() ||  password2.isEmpty() || password.isEmpty()){
                    Log.d("USER","FIELD NAME IS EMPTY");
                    Toast.makeText(getActivity(), "FIELD NAME IS EMPTY", Toast.LENGTH_SHORT).show();
                }else if(password.length() < 6 || password2.length() < 6) {
                    Log.d("USER", "PASSWORD LENGTH LESS THAN 6");
                    Toast.makeText(getActivity(), "PASSWORD LENGTH LESS THAN 6", Toast.LENGTH_SHORT).show();
                }else if((password.equals(password2))==false){
                    Log.d("USER", "PASSWORD DOESNT MAHT");
                    Toast.makeText(getActivity(), "PASSWORD DOESNT MATCH", Toast.LENGTH_SHORT).show();
                }else{
                    Log.d("USER","GO TO LOGIN");
                    mAuth.createUserWithEmailAndPassword(username, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            sentEmail(authResult.getUser());
                            if(savedInstanceState == null){
                                getActivity().getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.main_view,new LoginFragment())
                                        .commit();
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
    private void sentEmail(FirebaseUser _user)
    {
        _user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }}).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "ERROR :"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
