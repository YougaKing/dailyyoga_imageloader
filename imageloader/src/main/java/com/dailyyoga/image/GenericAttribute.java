package com.dailyyoga.image;

import android.graphics.Color;
import android.support.annotation.DrawableRes;

/**
 * @author: YougaKingWu@gmail.com
 * @created on: 2019/8/27 13:56
 * @description:
 */
public class GenericAttribute {

    public float mAspectRatio;
    public int mRetryImageId;
    public int mFailureImageId;
    public int mOverlayImageId;
    public int mRoundedCornerRadius;
    public boolean mRoundAsCircle;
    public boolean mRoundTopLeft;
    public boolean mRoundTopRight;
    public boolean mRoundBottomLeft;
    public boolean mRoundBottomRight;
    public float mRoundBorderWidth;
    public int mRoundBorderColor;
    public Processor mProcessor;

    public GenericAttribute(GenericAttributeBuilder builder) {
        mAspectRatio = builder.mAspectRatio;
        mRetryImageId = builder.mRetryImageId;
        mFailureImageId = builder.mFailureImageId;
        mOverlayImageId = builder.mOverlayImageId;
        mRoundedCornerRadius = builder.mRoundedCornerRadius;
        mRoundAsCircle = builder.mRoundAsCircle;
        mRoundTopLeft = builder.mRoundTopLeft;
        mRoundTopRight = builder.mRoundTopRight;
        mRoundBottomLeft = builder.mRoundBottomLeft;
        mRoundBottomRight = builder.mRoundBottomRight;
        mRoundBorderWidth = builder.mRoundBorderWidth;
        mRoundBorderColor = builder.mRoundBorderColor;
        mProcessor = builder.mProcessor;
    }

    public static class GenericAttributeBuilder {

        private float mAspectRatio;
        private int mRetryImageId;
        private int mFailureImageId;
        private int mOverlayImageId;
        private int mRoundedCornerRadius;
        private boolean mRoundAsCircle;
        private boolean mRoundTopLeft = true;
        private boolean mRoundTopRight = true;
        private boolean mRoundBottomLeft = true;
        private boolean mRoundBottomRight = true;
        private float mRoundBorderWidth;
        private int mRoundBorderColor = Color.TRANSPARENT;
        private Processor mProcessor;

        GenericAttributeBuilder() {
        }

        public GenericAttributeBuilder aspectRatio(float aspectRatio) {
            mAspectRatio = aspectRatio;
            return this;
        }

        public GenericAttributeBuilder retryImage(@DrawableRes int retryImageId) {
            mRetryImageId = retryImageId;
            return this;
        }

        public GenericAttributeBuilder failureImage(@DrawableRes int failureImageId) {
            mFailureImageId = failureImageId;
            return this;
        }

        public GenericAttributeBuilder overlayImage(@DrawableRes int overlayImageId) {
            mOverlayImageId = overlayImageId;
            return this;
        }

        public GenericAttributeBuilder roundedCornerRadius(int roundedCornerRadius) {
            mRoundedCornerRadius = roundedCornerRadius;
            return this;
        }

        public GenericAttributeBuilder roundAsCircle(boolean roundAsCircle) {
            mRoundAsCircle = roundAsCircle;
            return this;
        }

        public GenericAttributeBuilder roundTopLeft(boolean roundTopLeft) {
            mRoundTopLeft = roundTopLeft;
            return this;
        }

        public GenericAttributeBuilder roundTopRight(boolean roundTopRight) {
            mRoundTopRight = roundTopRight;
            return this;
        }

        public GenericAttributeBuilder roundBottomLeft(boolean roundBottomLeft) {
            mRoundBottomLeft = roundBottomLeft;
            return this;
        }

        public GenericAttributeBuilder roundBottomRight(boolean roundBottomRight) {
            mRoundBottomRight = roundBottomRight;
            return this;
        }

        public GenericAttributeBuilder roundBorderWidth(float roundBorderWidth) {
            mRoundBorderWidth = roundBorderWidth;
            return this;
        }

        public GenericAttributeBuilder roundBorderColor(int roundBorderColor) {
            mRoundBorderColor = roundBorderColor;
            return this;
        }

        //iterations 迭代次数，越大越魔化 ,blurRadius 模糊图半径，必须大于0，越大越模糊。
        public GenericAttributeBuilder blurValue(int iterations, int blurRadius) {
            mProcessor = new Processor(iterations, blurRadius);
            return this;
        }

        public GenericAttribute build() {
            return new GenericAttribute(this);
        }
    }

    public static class Processor {
        public int mIterations;
        public int mBlurRadius;

        public Processor(int iterations, int blurRadius) {
            mIterations = iterations;
            mBlurRadius = blurRadius;
        }
    }
}
