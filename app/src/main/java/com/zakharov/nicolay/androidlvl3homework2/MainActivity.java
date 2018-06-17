package com.zakharov.nicolay.androidlvl3homework2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

public class MainActivity extends AppCompatActivity {
    public final String TAG = getClass().getSimpleName();
    Observer<String> observer;
    Subscription subscription;
    TextView textView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text_view);
        editText = findViewById(R.id.edit_text);

        observer = new Observer<String>() {

            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError");
                e.printStackTrace();
            }

            @Override
            public void onNext(String s) {
                textView.setText(s);
                Log.d(TAG, "onNext " + s);
            }
        };

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                RXRealisation(editable.toString());
            }
        });


    }

    @Override
    public void onStop(){
        if (subscription != null) {
            subscription.unsubscribe();
        }
        super.onStop();
    }

    private void RXRealisation(String str) {
        subscription = Observable.just(str)
                       .timeout(50, TimeUnit.MILLISECONDS)
                       .subscribe(observer);
    }

    //так в принципе тоже работает
    private void RXRealisationWithoutSubscription(String str){
        Observable.just(str)
                .timeout(50, TimeUnit.MILLISECONDS)
                .subscribe(observer);
    }
}
