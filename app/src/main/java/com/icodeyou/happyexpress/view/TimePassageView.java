package com.icodeyou.happyexpress.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.icodeyou.happyexpress.R;
import com.icodeyou.library.util.ScreenUtils;

/**
 * Created by huan on 16/4/5.
 */
public class TimePassageView extends View {

    private Activity mActivity;
    /**
     * 外层逐渐扩大的圆圈个数
     */
    private static final int MAX_GREATER_COUNT = 3;
    /**
     * 多长时间更新一个UI
     */
    private static final int SLEEP_GAP = 100;
    /**
     * 倒计时总时间 单位ms
     */
    private static final int SUM_TIME = 60000;
    /**
     * 缩放倍数
     */
    private static final int SCALE_MULTIPLE = SUM_TIME / SLEEP_GAP;
    /**
     * 每次角度变化的增量
     */
    private static final float DELTA_ANGLE = (float) 360 / (float) SCALE_MULTIPLE;
    /**
     * 外层逐渐扩大的圆几秒消失一次 单位ms
     */
    private static final int GREATER_CIRCLE_DISMISS = 5000;
    /**
     * Alpha 缩放倍数
     */
    private static final int SCALE_ALPHA = GREATER_CIRCLE_DISMISS / SLEEP_GAP;
    /**
     * 外层逐渐扩大的圆每次透明度变化的增量
     */
    private static final float DELTA_ALPHA = (float) 255 / (float) SCALE_ALPHA;
    /**
     * 外圆半径和内圆半径
     */
    private static final int OUTER_RADIUS = 500;
    private static final int INNER_RADIUS = 501;

    /**
     * 多个渐变圆初始半径
     */
    private static final int INIT_FIRST_CIRCLE = INNER_RADIUS;
    private static final int INIT_SECOND_CIRCLE = INNER_RADIUS - 150;
    private static final int INIT_THIRD_CIRCLE = INIT_SECOND_CIRCLE - 150;
    private static int[] mInitCircleRadius = new int[MAX_GREATER_COUNT];

    private static Paint[] mGreaterCirclePaints = new Paint[MAX_GREATER_COUNT];
    /**
     * 时刻记录多个渐变圆的半径
     */
    private static int[] mGreaterCircleRadius = new int[MAX_GREATER_COUNT];
    /**
     * 时刻记录多个渐变圆的Alpha
     */
    private static int[] mGreaterCircleAlpha = new int[MAX_GREATER_COUNT];

    private Context mContext;

    /**
     * 倒计时圆心位置
     */
    int mCenterX, mCenterY;
    /**
     * 外圆半径和内圆半径
     */

    int[] a = new int[3];

    private static Paint mOuterCirclePaint;
    private static Paint mInnerCirclePaint;
    private static TextPaint mWaitTextPaint;
    private static TextPaint mTimeTextPaint;


    float mInnerArcAngle = 0f;
    int mTimeCount = 60;
    static int mGreaterRadius = INNER_RADIUS + 10;

    private InnerAngleThread mInnerAngleThread;

    static {
        mOuterCirclePaint = new Paint();
        mOuterCirclePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mOuterCirclePaint.setStyle(Paint.Style.STROKE);
        mOuterCirclePaint.setStrokeWidth(40);

        mInnerCirclePaint = new Paint();
        mInnerCirclePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mInnerCirclePaint.setStyle(Paint.Style.STROKE);
        mInnerCirclePaint.setStrokeWidth(10);

        mWaitTextPaint = new TextPaint();
        mWaitTextPaint.setAntiAlias(true);
        mWaitTextPaint.setColor(Color.GRAY);
        mWaitTextPaint.setTextSize(40);

        mTimeTextPaint = new TextPaint();
        mTimeTextPaint.setAntiAlias(true);
        mTimeTextPaint.setColor(Color.BLACK);
        mTimeTextPaint.setTextSize(80);

        for (int i = 0; i < MAX_GREATER_COUNT; i++) {
            mGreaterCircleRadius[i] = mGreaterRadius;
        }

        for (int i = 0; i < MAX_GREATER_COUNT; i++) {
            Paint greaterCirclePaint = new Paint();
            greaterCirclePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
            greaterCirclePaint.setStyle(Paint.Style.STROKE);
            greaterCirclePaint.setStrokeWidth(10);
            mGreaterCirclePaints[i] = greaterCirclePaint;
        }

        mGreaterCircleRadius[0] = INIT_FIRST_CIRCLE;
        mGreaterCircleRadius[1] = INIT_SECOND_CIRCLE;
        mGreaterCircleRadius[2] = INIT_THIRD_CIRCLE;

        mInitCircleRadius[0] = INIT_FIRST_CIRCLE;
        mInitCircleRadius[1] = INIT_SECOND_CIRCLE;
        mInitCircleRadius[2] = INIT_THIRD_CIRCLE;

        mGreaterCircleAlpha[0] = 255;
        mGreaterCircleAlpha[1] = 255;
        mGreaterCircleAlpha[2] = 255;
    }

    public TimePassageView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public TimePassageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
    }

    public TimePassageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(mCenterX, mCenterY, OUTER_RADIUS, mOuterCirclePaint);
        RectF rectF = new RectF(mCenterX - INNER_RADIUS, mCenterY - INNER_RADIUS, mCenterX + INNER_RADIUS, mCenterY + INNER_RADIUS);
        canvas.drawArc(rectF, -90, mInnerArcAngle, false, mInnerCirclePaint);

        canvas.drawText("等待接单", mCenterX - 55, mCenterY - 30, mWaitTextPaint);
        canvas.drawText(mTimeCount + " S", mCenterX - 55, mCenterY + 60, mTimeTextPaint);

        for (int i = 0; i < MAX_GREATER_COUNT; i++) {
            if (mGreaterCircleRadius[i] >= INNER_RADIUS) {
                mGreaterCirclePaints[i].setAlpha(mGreaterCircleAlpha[i]);
                canvas.drawCircle(mCenterX, mCenterY, mGreaterCircleRadius[i], mGreaterCirclePaints[i]);
//                Log.d("wanghuan", i + " radius " + " = " + mGreaterCircleRadius[i] + " alpha = " + mGreaterCircleAlpha[i]);
            }
        }
//        for (int i = 0; i < MAX_GREATER_COUNT; i++) {
//            Log.d("wanghuan", "radius " + i + " = " + mGreaterCircleRadius[i]);
//        }
    }

    public void start() {
        // 先计算 centerX,centerY
        mCenterX = ScreenUtils.getScreenWidth(mActivity) / 2 - 10;
        mCenterY = ScreenUtils.getScreenHeight(mActivity) / 2 - 400;

        mOuterCirclePaint.setColor(mActivity.getResources().getColor(R.color.mercury));
        mInnerCirclePaint.setColor(mActivity.getResources().getColor(R.color.spiro_disco_ball));

        for (int i = 0; i < MAX_GREATER_COUNT; i++) {
            mGreaterCirclePaints[i].setColor(mActivity.getResources().getColor(R.color.spiro_disco_ball));
        }

        mInnerAngleThread = new InnerAngleThread(this);
        mInnerAngleThread.start();
    }

    public void setAttachActivity(Activity activity) {
        this.mActivity = activity;
    }

    class InnerAngleThread extends Thread {
        private View mView;

        /**
         * 累计经过时间
         */
        int mPassTime = 0;

        public InnerAngleThread(View view) {
            mView = view;
        }

        @Override
        public void run() {
            super.run();
            while (mInnerArcAngle <= 360) {
                try {
                    sleep(SLEEP_GAP);
                    mPassTime += SLEEP_GAP;
                    /** 每隔一秒倒计时减1 */
                    if (mPassTime % 1000 == 0 && mTimeCount >= 0) {
                        mTimeCount--;
                    }

                    /** 先让半径变化，半径达到INNER_RADIUS后透明度才开始变化 */
                    for (int i = 0; i < MAX_GREATER_COUNT; i++) {
                        mGreaterCircleRadius[i] += 5;
                        if (mGreaterCircleRadius[i] > INNER_RADIUS) {
                            mGreaterCircleAlpha[i] -= DELTA_ALPHA;
                            /** Alpha减为0的时候复位 */
                            if (mGreaterCircleAlpha[i] <= 0) {
                                mGreaterCircleRadius[i] = mInitCircleRadius[i];
                                mGreaterCircleAlpha[i] = 255;
                            }
                        }
                    }
//                    mGreaterAlpha -= DELTA_ALPHA;
//                    if (mGreaterAlpha < 0) {
//                        mGreaterRadius = INNER_RADIUS + 10;
//                        mGreaterAlpha = 255;
//                    }
//                    else
//                        mGreaterRadius += 5;
                    mInnerArcAngle += DELTA_ANGLE;
                    mView.postInvalidate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
