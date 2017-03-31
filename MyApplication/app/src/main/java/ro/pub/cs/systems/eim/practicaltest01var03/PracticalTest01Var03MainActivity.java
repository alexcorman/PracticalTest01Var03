package ro.pub.cs.systems.eim.practicaltest01var03;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01Var03MainActivity extends AppCompatActivity {

    private final static int SECONDARY_ACTIVITY_REQUEST_CODE = 1;

    private Button displayButton = null, navigateToSecondaryActivityButton = null;
    private EditText editText1 = null, editText2 = null;
    private CheckBox checkBox1 = null, checkBox2 = null;
    private TextView textView = null;

    private IntentFilter intentFilter = new IntentFilter();

    private int serviceStatus = Constants.SERVICE_STOPPED;

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[Message]", intent.getStringExtra("message"));
        }
    }

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener
    {

        @Override
        public void onClick(View view)
        {
            int ok1 = 0, ok2 = 0;
            switch(view.getId())
            {
                case R.id.display_info_button:
                    if(checkBox1.isChecked())
                    {
                        if(editText1.getText() == null)
                        {
                            Toast.makeText(getApplicationContext(), "No input in first edit text!", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            ok1 = 1;
                            textView.setText(editText1.getText().toString());
                        }
                    }

                    if(checkBox2.isChecked())
                    {
                        if(editText2.getText() == null)
                        {
                            Toast.makeText(getApplicationContext(), "No input in second edit text!", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            ok2 = 1;
                            textView.setText(textView.getText().toString() + editText2.getText().toString());
                        }
                    }

                    if(ok1 == 1 && ok2 == 1 && serviceStatus == Constants.SERVICE_STOPPED)
                    {
                        Intent intent = new Intent(getApplicationContext(), PracticalTest01Var03Service.class);
                        intent.putExtra("firstText", editText1.getText().toString());
                        intent.putExtra("secondText", editText2.getText().toString());
                        getApplicationContext().startService(intent);
                        serviceStatus = Constants.SERVICE_STARTED;
                    }
                    break;

                case R.id.second_activity_button:
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var03SecondaryActivity.class);

                    intent.putExtra("firstText", editText1.getText().toString());
                    intent.putExtra("secondText", editText2.getText().toString());
                    startActivityForResult(intent, SECONDARY_ACTIVITY_REQUEST_CODE);
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_main);

        displayButton = (Button)findViewById(R.id.display_info_button);
        editText1 = (EditText)findViewById(R.id.first_edit_text);
        editText2 = (EditText)findViewById(R.id.second_edit_text);
        checkBox1 = (CheckBox)findViewById(R.id.checkbox1);
        checkBox2 = (CheckBox)findViewById(R.id.checkbox2);
        textView = (TextView)findViewById(R.id.greeting_text_view);

        displayButton.setOnClickListener(buttonClickListener);

        navigateToSecondaryActivityButton = (Button)findViewById(R.id.second_activity_button);
        navigateToSecondaryActivityButton.setOnClickListener(buttonClickListener);

        for (int index = 0; index < Constants.actionTypes.length; index++) {

            intentFilter.addAction(Constants.actionTypes[index]);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("firstText", editText1.getText().toString());
        savedInstanceState.putString("secondText", editText2.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey("firstText")) {
            editText1.setText(savedInstanceState.getString("firstText"));
        } else {
            editText1.setText("");
        }
        if (savedInstanceState.containsKey("secondText"))
        {
            editText2.setText(savedInstanceState.getString("secondText"));
        } else
        {
            editText2.setText("");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if (requestCode == SECONDARY_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var03Service.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }
}
