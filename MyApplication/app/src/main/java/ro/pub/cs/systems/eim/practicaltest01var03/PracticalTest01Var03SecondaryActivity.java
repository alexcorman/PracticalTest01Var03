package ro.pub.cs.systems.eim.practicaltest01var03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PracticalTest01Var03SecondaryActivity extends AppCompatActivity {

    private TextView textView1 = null, textView2 = null;
    private Button okButton = null, cancelButton = null;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener
    {

        @Override
        public void onClick(View view)
        {
            switch(view.getId())
            {
                case R.id.ok_button:
                    setResult(RESULT_OK);
                    break;

                case R.id.cancel_button:
                    setResult(RESULT_CANCELED);
                    break;
            }

            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_secondary);

        textView1 = (TextView)findViewById(R.id.text_view_1);
        textView2 = (TextView)findViewById(R.id.text_view_2);
        Intent intent = getIntent();
        if(intent != null && intent.getExtras().containsKey("firstText") && intent.getExtras().containsKey("secondText"))
        {
            String firstText = intent.getStringExtra("firstText");
            textView1.setText(firstText);
            String secondText = intent.getStringExtra("secondText");
            textView2.setText(secondText);
        }

        okButton = (Button)findViewById(R.id.ok_button);
        cancelButton = (Button)findViewById(R.id.cancel_button);
        okButton.setOnClickListener(buttonClickListener);
        cancelButton.setOnClickListener(buttonClickListener);
    }
}
