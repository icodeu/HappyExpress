package com.icodeyou.happyexpress.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.icodeyou.happyexpress.R;

/**
 * Created by huan on 16/4/6.
 */
public class LogisticsHeaderView extends View {

    private Activity mActivity;

    private static Paint mCirclePaint, mLinePaint;
    private static TextPaint mTextPaint;

    /** 起点圆心半径 */
    private static final int START_POINT_POS_X = 150;
    private static final int START_POINT_POS_Y = 250;
    private static final int START_POINT_RADIUS = 50;
    /** 终点圆心半径 */
    private static final int DEST_POINT_POS_X = 1380;
    private static final int DEST_POINT_POS_Y = START_POINT_POS_Y;
    private static final int DEST_POINT_RADIUS = START_POINT_RADIUS;

    private static final int TEXT_SIZE = 54;

    private String mStartPos = "北京", mDestPos = "天津";

    static {
        mCirclePaint = new Paint();
        mCirclePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.FILL);

        mLinePaint = new Paint();
        mLinePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setStrokeWidth(10);

        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(TEXT_SIZE);
    }

    public LogisticsHeaderView(Context context) {
        super(context);
        init();
    }

    public LogisticsHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LogisticsHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
    }

    public void setAttachActivity(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCirclePaint.setColor(mActivity.getResources().getColor(R.color.safety_orange));
        mLinePaint.setColor(mActivity.getResources().getColor(R.color.safety_orange));
        /** 画起点坐标的圆 */
        canvas.drawCircle(START_POINT_POS_X, START_POINT_POS_Y, START_POINT_RADIUS, mCirclePaint);
        /** 画终点坐标的圆 */
        canvas.drawCircle(DEST_POINT_POS_X, DEST_POINT_POS_Y, DEST_POINT_RADIUS, mCirclePaint);

        /** 起点终点之间连线 */
        canvas.drawLine(START_POINT_POS_X, START_POINT_POS_Y, DEST_POINT_POS_X, DEST_POINT_POS_Y, mLinePaint);

        /** 起点和终点的文字 */
        float startPosLen = mTextPaint.measureText(mStartPos);
        float destPosLen = mTextPaint.measureText(mDestPos);
        mTextPaint.setColor(mActivity.getResources().getColor(R.color.neon_carrot));
        canvas.drawText(mStartPos, START_POINT_POS_X - startPosLen / 2, START_POINT_POS_Y - START_POINT_RADIUS - 20, mTextPaint);
        canvas.drawText(mDestPos, DEST_POINT_POS_X - destPosLen/2, DEST_POINT_POS_Y - DEST_POINT_RADIUS - 20, mTextPaint);
    }
}
