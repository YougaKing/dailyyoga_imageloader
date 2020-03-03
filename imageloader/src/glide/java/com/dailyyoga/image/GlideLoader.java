package com.dailyyoga.image;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.integration.webp.decoder.WebpDrawable;
import com.bumptech.glide.integration.webp.decoder.WebpDrawableTransformation;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: YougaKingWu@gmail.com
 * @created on: 2019/8/27 18:58
 * @description:
 */
public class GlideLoader extends GenericLoader {

    private RequestManager mRequestManager;
    private RequestBuilder<Drawable> mRequestBuilder;

    @Override
    protected GenericLoader with(@NonNull Activity activity) {
        super.with(activity);
        mRequestManager = GlideApp.with(activity);
        return this;
    }

    @Override
    protected GlideLoader with(@NonNull Fragment fragment) {
        super.with(fragment);
        mRequestManager = GlideApp.with(fragment);
        return this;
    }

    @Override
    protected GlideLoader with(@NonNull Context context) {
        super.with(context);
        mRequestManager = GlideApp.with(context);
        return this;
    }

    @Override
    public ILoader load(int resourceId) {
        if (resourceId != 0) {
            mRequestBuilder = mRequestManager.load(resourceId);
        }
        return this;
    }

    @Override
    public ILoader load(@Nullable String url) {
        if (!TextUtils.isEmpty(url)) {
            mRequestBuilder = mRequestManager.load(url);
        }
        return this;
    }

    @Override
    public ILoader load(@Nullable File file) {
        if (file != null) {
            mRequestBuilder = mRequestManager.load(file);
        }
        return this;
    }

    @Override
    public void into(ImageView imageView) {
        if (mRequestBuilder == null || imageView == null) return;
        GenericAttribute attribute = mAttributeBuilder.build();

        List<Transformation<Bitmap>> transformationList = new ArrayList<>();

        Transformation<Bitmap> scaleType = getScaleTypeFromImageView(imageView);
        if (scaleType != null) transformationList.add(scaleType);

        if (attribute.mRoundAsCircle) {
            transformationList.add(new CircleCrop());
        } else if (attribute.mRoundBorderWidth != 0 && attribute.mRoundBorderColor != 0) {
            // TODO: 2019/8/27
        } else if (attribute.mRoundedCornerRadius > 0) {
            transformationList.add(new RoundedCorners(attribute.mRoundedCornerRadius));
        }
        if (attribute.mProcessor != null) {
            transformationList.add(new BlurTransformation(attribute.mProcessor.mBlurRadius, attribute.mProcessor.mIterations));
        }

        Transformation<Bitmap>[] transformations = new Transformation[transformationList.size()];
        transformationList.toArray(transformations);

        if (mWebpAnimate) {
            Transformation<Bitmap> centerInside = new CenterInside();
            mRequestBuilder.optionalTransform(centerInside)
                    .optionalTransform(WebpDrawable.class, new WebpDrawableTransformation(centerInside))
                    .into(imageView);
        } else {
            mRequestBuilder
                    .transform(transformations)
                    .error(attribute.mFailureImageId)
                    .into(imageView);
        }
    }

    private static Transformation<Bitmap> getScaleTypeFromImageView(
            ImageView imageView) {
        if (imageView == null) return null;
        ImageView.ScaleType scaleType = imageView.getScaleType();
        if (scaleType == null) return null;

        switch (scaleType) {
            case FIT_XY: // fitXY
            case FIT_START: // fitStart
            case FIT_END: // fitEnd
            case CENTER: // center
            case FIT_CENTER: // fitCenter
                return new FitCenter();
            case CENTER_INSIDE: // centerInside
                return new CenterInside();
            case CENTER_CROP: // centerCrop
                return new CenterCrop();
            default:
                return null;
        }
    }
}
