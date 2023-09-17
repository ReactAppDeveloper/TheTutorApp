package expence.ubiad.example.com.thetutor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by ubiad on 07/06/2017.
 */
public class Classes extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);
        Button bt = (Button)findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Classes.this,GetTutor.class);
                startActivity(intent);
            }
        });
        Button bt1 = (Button)findViewById(R.id.button1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Classes.this,GetTutor.class);
                startActivity(intent);
            }
        });
        Button bt2 = (Button)findViewById(R.id.button2);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Classes.this,GetTutor.class);
                startActivity(intent);
            }
        });
        Button bt3 = (Button)findViewById(R.id.button3);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Classes.this,GetTutor.class);
                startActivity(intent);
            }
        });
        Button bt4 = (Button)findViewById(R.id.button4);
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Classes.this,GetTutor.class);
                startActivity(intent);
            }
        });
        Button bt5 = (Button)findViewById(R.id.button5);
        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Classes.this,GetTutor.class);
                startActivity(intent);
            }
        });
        Button bt6 = (Button)findViewById(R.id.button6);
        bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Classes.this,GetTutor.class);
                startActivity(intent);
            }
        });
        Button bt7 = (Button)findViewById(R.id.button7);
        bt7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Classes.this,GetTutor.class);
                startActivity(intent);
            }
        });
        Button bt8 = (Button)findViewById(R.id.button8);
        bt8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Classes.this,GetTutor.class);
                startActivity(intent);
            }
        });
        Button bt9 = (Button)findViewById(R.id.button9);
        bt9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Classes.this,GetTutor.class);
                startActivity(intent);
            }
        });
        Button bt10 = (Button)findViewById(R.id.button10);
        bt10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Classes.this,GetTutor.class);
                startActivity(intent);
            }
        });
    }
}

