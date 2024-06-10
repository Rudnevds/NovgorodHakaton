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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.novgorodhakaton.R;
import com.example.novgorodhakaton.viewmodel.QuestionViewModel;

import java.util.HashMap;


public class ResultFragment extends Fragment {

    private NavController navController;
    private QuestionViewModel viewModel;
    private TextView correctAnswer , wrongAnswer , notAnswered;
    private TextView percentTv;
    private ProgressBar scoreProgressbar;
    private String quizId;
    private Button homeBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this , ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(QuestionViewModel.class);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Идентификация всех элементов на экране
        navController = Navigation.findNavController(view);
        correctAnswer = view.findViewById(R.id.itog1);
        wrongAnswer = view.findViewById(R.id.itog2);
        notAnswered = view.findViewById(R.id.itog3);
        percentTv = view.findViewById(R.id.rezult_procent);
        scoreProgressbar = view.findViewById(R.id.quiz_progress_itog);
        homeBtn = view.findViewById(R.id.home2);


        //Нажатие на крестик
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_resultFragment_to_listFragment);
            }
        });

        quizId = ResultFragmentArgs.fromBundle(getArguments()).getQuizId();

        viewModel.setQuizId(quizId);
        viewModel.getResults();
        viewModel.getResultMutableLiveData().observe(getViewLifecycleOwner(), new Observer<HashMap<String, Long>>() {

            //
            @Override
            public void onChanged(HashMap<String, Long> stringLongHashMap) {


                Long correct = stringLongHashMap.get(getResources().getString(R.string.positive_verdict));
                Long wrong = stringLongHashMap.get(getResources().getString(R.string.negative_verdict));
                Long noAnswer = stringLongHashMap.get(getResources().getString(R.string.neutral_verdict));

                correctAnswer.setText(correct.toString()); //Правильные ответы
                wrongAnswer.setText(wrong.toString()); //неправильные
                notAnswered.setText(noAnswer.toString()); //без ответа

                //Подсчёт процента итогого результата
                new Thread() {
                    @Override
                    public void run() {

                        Long total = correct + wrong + noAnswer;
                        Long percent = (correct*100)/total;

                        percentTv.setText(String.valueOf(percent));
                        scoreProgressbar.setProgress(percent.intValue());
                    }
                }.start();
            }
        });

    }
}