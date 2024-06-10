package com.example.novgorodhakaton.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.novgorodhakaton.Model.QuestionModel;
import com.example.novgorodhakaton.repository.QuestionRepository;

import java.util.HashMap;
import java.util.List;

public class QuestionViewModel extends ViewModel implements QuestionRepository.OnQuestionLoad, QuestionRepository.OnResultAdded, QuestionRepository.OnResultLoad {
    private MutableLiveData<List<QuestionModel>> questionMutableLiveData;
    private QuestionRepository repository;
    private  MutableLiveData<HashMap<String,Long>> resultMutableLiveData;
    public MutableLiveData<HashMap<String, Long>> getResultMutableLiveData() {
        return resultMutableLiveData;
    }

    public void getResults() {
        repository.getResults();
    }


    public MutableLiveData<List<QuestionModel>> getQuestionMutableLiveData() {
        return questionMutableLiveData;
    }

    public QuestionViewModel() {
        questionMutableLiveData = new MutableLiveData<>();
        resultMutableLiveData=new MutableLiveData<>();
        repository= new QuestionRepository(this, this,this);
    }


    //Создаём модель вопросов для дальнейшего использования в других фрагментах
    public void addResults (HashMap<String, Object> resultMap) {
        repository.addResults(resultMap);
    }

    public void setQuizId (String quizId) {
        repository.setQuizId(quizId);
    }

    public void getQuestion() {
        repository.getQuestions();
    }

    @Override
    public void onLoad(List<QuestionModel> questionModels) {
        questionMutableLiveData.setValue(questionModels);
    }

    @Override
    public boolean onSubmit() {
        return true;
    }

    @Override
    public void onResultLoad(HashMap<String, Long> resultMap) {
        resultMutableLiveData.setValue(resultMap);
    }

    @Override
    public void onError(Exception e) {
        Log.d("QuizError",  "Error: " + e.getMessage());
    }
}