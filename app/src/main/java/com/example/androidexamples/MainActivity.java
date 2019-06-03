package com.example.androidexamples;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private String prefName="myPref";
    private EditText editText;
    private SeekBar seekBar;
    private Button btn;
    private float size;
    private static final String FONT_SIZE_KEY="font_size";
    private static final String TEXT_VALUE_KEY="text_value";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText= findViewById(R.id.et1);
        seekBar= findViewById(R.id.sb1);
        size = seekBar.getProgress();
        btn= findViewById(R.id.btnSave);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //--Get shared preferences object--
                prefs=getSharedPreferences(prefName,MODE_PRIVATE);
                SharedPreferences.Editor editor=prefs.edit();
                //--save the valus in the EditText view to preferenced--
                editor.putFloat(FONT_SIZE_KEY,size);
                editor.putString(TEXT_VALUE_KEY,editText.getText().toString());
                //--saves the value--
                editor.commit();
                //--display file saved message--
                Toast.makeText(getBaseContext(),"Font size saved successfully! \n ",Toast.LENGTH_SHORT).show();
            }
        });

        //--load the shared preferences object--
        SharedPreferences prefs=getSharedPreferences(prefName,MODE_PRIVATE);
        //--set the TextView font size to the previously saved values--
        float fontSize=prefs.getFloat(FONT_SIZE_KEY,12);
        //--init the SeekBar and EditText--
        editText.setText(prefs.getString(TEXT_VALUE_KEY,""));
        editText.setTextSize(fontSize);
        seekBar.setProgress((int) fontSize);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //--Change the font size of the EditText--
                editText.setTextSize(size=progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
