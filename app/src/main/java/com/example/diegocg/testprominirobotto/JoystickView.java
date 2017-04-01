package com.example.diegocg.testprominirobotto;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by DiegoCG on 31/03/2017.
 */

public class JoystickView extends View
{

    private Paint circlePaint;
    private Paint handlePaint;
    private int innerPadding;
    private int sensitivity;

    public JoystickView(Context context)
    {
        super(context);
        initJoystickView();
    }
    public JoystickView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initJoystickView();
    }
    public JoystickView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        initJoystickView();
    }
    public void initJoystickView()
    {
        setFocusable(true);

        setUpCircle();

    }
    public void setUpCircle()
    {
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(Color.GRAY);
        circlePaint.setStrokeWidth(1);
        circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        handlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        handlePaint.setColor(Color.DKGRAY);
        handlePaint.setStrokeWidth(1);
        handlePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        innerPadding = 10;
        sensitivity = 10;
    }

    public void setOnJoystickMovedListener(Jo)
}
