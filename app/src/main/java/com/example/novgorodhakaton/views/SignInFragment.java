package com.example.novgorodhakaton.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novgorodhakaton.R;
import com.example.novgorodhakaton.viewmodel.AuthViewModel;
import com.google.firebase.auth.FirebaseUser;


public class SignInFragment extends Fragment {
    private AuthViewModel viewModel;
    private NavController navController;
    private EditText editEmail , editPass;
    private TextView signUpText;
    private Button signInBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Идентификация всех элементов на экране
        navController = Navigation.findNavController(view);
        editEmail = view.findViewById(R.id.vvedi_email1);
        editPass = view.findViewById(R.id.vvedi_parol1);
        signUpText = view.findViewById(R.id.textVhod);
        signInBtn = view.findViewById(R.id.vhod);

        //Нажатие на текст
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_signInFragment_to_signUpFragment);
            }
        });

        //Нажатие на кнопку войти
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();
                String pass = editPass.getText().toString();
                if (!email.isEmpty() && !pass.isEmpty()){
                    viewModel.signIn(email, pass);
                    viewModel.getFirebaseUserMutableLiveData().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
                        @Override
                        public void onChanged(FirebaseUser firebaseUser) {
                            if (firebaseUser !=null){
                                if (navController.getCurrentDestination().getId() == R.id.signInFragment)
                                    navController.navigate(R.id.action_signInFragment_to_listFragment);
                            }
                        }
                    });
                }else{
                    Toast.makeText(getContext(), getResources().getString(R.string.empty_line), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this , ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(AuthViewModel.class);

    }
}