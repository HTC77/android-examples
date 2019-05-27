package com.example.androidexamples;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeView extends AppCompatTextView {
    private String titleText;
    private boolean color;

    public TimeView(Context context) {
        super(context);
        setTimeView();
    }

    public TimeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // retrieved values correspond to the position of the attributes
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.TimeView);
        int count = typedArray.getIndexCount();
        try{
            for (int i = 0; i < count; ++i) {
                int attr = typedArray.getIndex(i);
                // the attr corresponds to the attribute
                if(attr == R.styleable.TimeView_title){

                    //set the text for the layout
                    titleText = typedArray.getString(attr);
                    setTimeView();
                }
                else if(attr == R.styleable.TimeView_setColor){
                    // set the color of the attr "setColor"
                    color = typedArray.getBoolean(attr,false);
                    decorateText();
                }
            }
        }
        //the recycle() will be executed obligatorily
        finally{
            // for reuse
            typedArray.recycle();
        }
    }

    public TimeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTimeView();
    }

    private void setTimeView(){
        // has the format hour.minuits am/pm
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mm.aa");
        String time = dateFormat.format(Calendar.getInstance().getTime());

        if(this.titleText !=null)
            setText(this.titleText+" "+time);
        else
            setText(time);
    }

    private void decorateText(){
        // when we set setColor attribute to true in the xml layout
        if(this.color==true){
            // set the characteristics and the color of the shadow
            setShadowLayer(4,2,2, Color.rgb(250,00,250));
            setBackgroundColor(Color.CYAN);
        }else{
            setBackgroundColor(Color.RED);
        }
    }
}
