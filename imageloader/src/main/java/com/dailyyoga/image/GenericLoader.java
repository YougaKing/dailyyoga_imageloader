package com.dailyyoga.image;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.dailyyoga.image.GenericAttribute.GenericAttributeBuilder;

/**
 * @author: YougaKingWu@gmail.com
 * @created on: 2019/8/27 15:21
 * @description:
 */
public abstract class GenericLoader implements ILoader {

    protected GenericAttributeBuilder mAttributeBuilder = new GenericAttributeBuilder();
    protected boolean mWebpAnimate;

    protected GenericLoader with(@NonNull Activity activity) {
        return this;
    }

    protected GenericLoader with(@NonNull Fragment fragment) {
        return this;
    }

    protected GenericLoader with(@NonNull Context context) {
        return this;
    }

    @Override
    public ILoader asWebpAnimate() {
        mWebpAnimate = true;
        return this;
    }

    @Override
    public ILoader aspectRatio(float aspectRatio) {
        mAttributeBuilder.aspectRatio(aspectRatio);
        return this;
    }

    @Override
    public ILoader retryImage(int retryImageId) {
        mAttributeBuilder.retryImage(retryImageId);
        return this;
    }

    @Override
    public ILoader failureImage(int failureImageId) {
        mAttributeBuilder.failureImage(failureImageId);
        return this;
    }

    @Override
    public ILoader overlayImage(int overlayImageId) {
        mAttributeBuilder.overlayImage(overlayImageId);
        return this;
    }

    @Override
    public ILoader roundedCornerRadius(int roundedCornerRadius) {
        mAttributeBuilder.roundedCornerRadius(roundedCornerRadius);
        return this;
    }

    @Override
    public ILoader roundAsCircle(boolean roundAsCircle) {
        mAttributeBuilder.roundAsCircle(roundAsCircle);
        return this;
    }

    @Override
    public ILoader roundTopLeft(boolean roundTopLeft) {
        mAttributeBuilder.roundTopLeft(roundTopLeft);
        return this;
    }

    @Override
    public ILoader roundTopRight(boolean roundTopRight) {
        mAttributeBuilder.roundTopRight(roundTopRight);
        return this;
    }

    @Override
    public ILoader roundBottomLeft(boolean roundBottomLeft) {
        mAttributeBuilder.roundBottomLeft(roundBottomLeft);
        return this;
    }

    @Override
    public ILoader roundBottomRight(boolean roundBottomRight) {
        mAttributeBuilder.roundBottomRight(roundBottomRight);
        return this;
    }

    @Override
    public ILoader roundBorderWidth(float roundBorderWidth) {
        mAttributeBuilder.roundBorderWidth(roundBorderWidth);
        return this;
    }

    @Override
    public ILoader roundBorderColor(int roundBorderColor) {
        mAttributeBuilder.roundBorderColor(roundBorderColor);
        return this;
    }

    @Override
    public ILoader blurValue(int iterations, int blurRadius) {
        mAttributeBuilder.blurValue(iterations, blurRadius);
        return this;
    }
}
