package com.dailyyoga.image;

import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import java.io.File;

/**
 * @author: YougaKingWu@gmail.com
 * @created on: 2019/8/27 15:17
 * @description:
 */
public interface ILoader {

    ILoader load(@DrawableRes int resourceId);

    ILoader load(@Nullable String url);

    ILoader load(@Nullable File file);

    ILoader asWebpAnimate();

    ILoader aspectRatio(float aspectRatio);

    ILoader retryImage(@DrawableRes int retryImageId);

    ILoader failureImage(@DrawableRes int failureImageId);

    ILoader overlayImage(@DrawableRes int overlayImageId);

    ILoader roundedCornerRadius(int roundedCornerRadius);

    ILoader roundAsCircle(boolean roundAsCircle);

    ILoader roundTopLeft(boolean roundTopLeft);

    ILoader roundTopRight(boolean roundTopRight);

    ILoader roundBottomLeft(boolean roundBottomLeft);

    ILoader roundBottomRight(boolean roundBottomRight);

    ILoader roundBorderWidth(float roundBorderWidth);

    ILoader roundBorderColor(int roundBorderColor);

    //iterations 迭代次数，越大越魔化 ,blurRadius 模糊图半径，必须大于0，越大越模糊。
    ILoader blurValue(int iterations, int blurRadius);

    void into(ImageView imageView);
}
