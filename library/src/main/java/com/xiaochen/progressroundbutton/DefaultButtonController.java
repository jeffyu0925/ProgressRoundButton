package com.xiaochen.progressroundbutton;

import android.graphics.Color;

/**
 * Created by tanfujun on 10/26/16.
 */

public class DefaultButtonController implements ButtonController {


    private boolean enablePress;

    private boolean enableGradient;

    /**
     * 獲得按下的顏色（明度降低10%）
     *
     * @param color
     * @return int
     */
    public int getPressedColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] -= 0.1f;
        return Color.HSVToColor(hsv);
    }

    /**
     * 由右邊的顏色算出左邊的顏色（左邊的顏色比右邊的顏色降飽和度30%，亮度增加30%）
     * +
     *
     * @param color
     * @return
     */
    public int getLighterColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[1] -= 0.3f;
        hsv[2] += 0.3f;
        return Color.HSVToColor(hsv);
    }

    /**
     * 由左邊的顏色生成右邊的顏色
     *
     * @param color
     * @return int
     */
    public int getDarkerColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[1] += 0.3f;
        hsv[2] -= 0.3f;
        return Color.HSVToColor(hsv);
    }

    @Override
    public boolean enablePress() {
        return enablePress;
    }

    @Override
    public boolean enableGradient() {
        return enableGradient;
    }

    public DefaultButtonController setEnablePress(boolean enablePress) {
        this.enablePress = enablePress;
        return this;
    }

    public DefaultButtonController setEnableGradient(boolean enableGradient) {
        this.enableGradient = enableGradient;
        return this;
    }
}
