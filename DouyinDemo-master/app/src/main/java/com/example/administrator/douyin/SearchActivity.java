package com.example.administrator.douyin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {

    private Button SearchButton;
    private EditText keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SearchButton = (Button) findViewById(R.id.search);
        keyword = (EditText) findViewById(R.id.keyword);
        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(keyword.getText().toString()!=null){
                    Intent intent=new Intent(SearchActivity.this,SearchResultActivity.class);
                    intent.putExtra("name",keyword.getText().toString());
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}