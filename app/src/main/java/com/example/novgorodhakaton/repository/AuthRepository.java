package com.example.novgorodhakaton.repository;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.novgorodhakaton.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthRepository {
    private Application application;
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private FirebaseAuth firebaseAuth;


    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public void setFirebaseUserMutableLiveData(MutableLiveData<FirebaseUser> firebaseUserMutableLiveData) {
        this.firebaseUserMutableLiveData = firebaseUserMutableLiveData;
    }

    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }



    public AuthRepository(Application application) {
        this.application=application;
        firebaseUserMutableLiveData= new MutableLiveData<>();
        firebaseAuth=FirebaseAuth.getInstance();
    }

    //Регистрация пользователей
    public void signUp (String email, String pass) {
        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(application, application.getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                    firebaseUserMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                } else{
                    Toast.makeText(application, application.getResources().getString(R.string.error) + ": " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    //Вход пользователей
    public void signIn(String email, String pass){
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(application, application.getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                    firebaseUserMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                } else {
                    Toast.makeText(application, application.getResources().getString(R.string.error) + ": " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }



    public void  signOut () {
        firebaseAuth.signOut();
    }
}
