package com.dailyyoga.image;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeHolder;
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.File;

/**
 * @author: YougaKingWu@gmail.com
 * @created on: 2019/8/27 15:27
 * @description:
 */
public class FrescoLoader extends GenericLoader {

    private ImageRequestBuilder builder;

    @Override
    public GenericLoader with(@NonNull Activity activity) {
        return this;
    }

    @Override
    public GenericLoader with(@NonNull Fragment fragment) {
        return this;
    }

    @Override
    public GenericLoader with(@NonNull Context context) {
        return this;
    }

    @Override
    public ILoader load(int resourceId) {
        if (resourceId != 0) {
            this.builder = ImageRequestBuilder.newBuilderWithResourceId(resourceId);
        }
        return this;
    }

    @Override
    public ILoader load(@Nullable String url) {
        if (!TextUtils.isEmpty(url)) {
            this.builder = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url));
        }
        return this;
    }

    @Override
    public ILoader load(@Nullable File file) {
        if (file != null && file.exists()) {
            this.builder = ImageRequestBuilder.newBuilderWithSource(Uri.fromFile(file));
        }
        return this;
    }

    @Override
    public void into(ImageView targetView) {
        if (targetView == null || builder == null) return;

        GenericAttribute attribute = mAttributeBuilder.build();
        if (attribute.mProcessor != null) {
            builder.setPostprocessor(new IterativeBoxBlurPostProcessor(attribute.mProcessor.mIterations, attribute.mProcessor.mBlurRadius));
        }
        GenericDraweeHierarchyBuilder hierarchyBuilder = GenericDraweeHierarchyBuilder.newInstance(targetView.getContext().getResources());
        hierarchyBuilder.setActualImageScaleType(getScaleTypeFromImageView(targetView));
        updateBuilder(attribute, targetView.getContext(), hierarchyBuilder);

        DraweeHolder draweeHolder = (DraweeHolder) targetView.getTag(R.id.fresco_drawee);
        GenericDraweeHierarchy hierarchy = hierarchyBuilder.build();

        PipelineDraweeControllerBuilder controllerBuilder = Fresco.newDraweeControllerBuilder()
                .setImageRequest(builder.build())
                .setAutoPlayAnimations(true);

        DraweeController controller;

        if (draweeHolder == null) {
            draweeHolder = DraweeHolder.create(hierarchy, targetView.getContext());
            controller = controllerBuilder.build();
        } else {
            controller = controllerBuilder.setOldController(draweeHolder.getController()).build();
        }
        // 请求
        draweeHolder.setController(controller);

        // 判断是否ImageView已经 attachToWindow
        if (ViewCompat.isAttachedToWindow(targetView)) {
            draweeHolder.onAttach();
        }
        // 保证每一个ImageView中只存在一个draweeHolder
        targetView.setTag(R.id.fresco_drawee, draweeHolder);
        // 拿到数据
        targetView.setImageDrawable(draweeHolder.getTopLevelDrawable());
    }

    private static void updateBuilder(GenericAttribute attribute, Context context, GenericDraweeHierarchyBuilder builder) {
        if (attribute == null || builder == null) return;
        builder.setRetryImage(getDrawable(context, attribute.mRetryImageId));
        builder.setFailureImage(getDrawable(context, attribute.mFailureImageId));
        builder.setOverlay(getDrawable(context, attribute.mOverlayImageId));

        if (attribute.mRoundAsCircle) {
            getRoundingParams(builder).setRoundAsCircle(true);
        } else if (attribute.mRoundBorderWidth != 0) {
            getRoundingParams(builder).setBorderWidth(attribute.mRoundBorderWidth);
        } else if (attribute.mRoundBorderColor != 0) {
            getRoundingParams(builder).setBorderColor(attribute.mRoundBorderColor);
        }

        if (attribute.mRoundedCornerRadius > 0) {
            getRoundingParams(builder).setCornersRadii(
                    attribute.mRoundTopLeft ? attribute.mRoundedCornerRadius : 0,
                    attribute.mRoundTopRight ? attribute.mRoundedCornerRadius : 0,
                    attribute.mRoundBottomRight ? attribute.mRoundedCornerRadius : 0,
                    attribute.mRoundBottomLeft ? attribute.mRoundedCornerRadius : 0);
        }
    }

    private static RoundingParams getRoundingParams(GenericDraweeHierarchyBuilder builder) {
        if (builder.getRoundingParams() == null) {
            builder.setRoundingParams(new RoundingParams());
        }
        return builder.getRoundingParams();
    }

    /**
     * Returns the scale type indicated in XML, or null if the special 'none' value was found.
     * Important: these values need to be in sync with GenericDraweeHierarchy styleable attributes.
     */
    @Nullable
    private static ScalingUtils.ScaleType getScaleTypeFromImageView(
            ImageView imageView) {
        if (imageView == null) return null;
        ImageView.ScaleType scaleType = imageView.getScaleType();
        if (scaleType == null) return null;

        switch (scaleType) {
            case FIT_XY: // fitXY
                return ScalingUtils.ScaleType.FIT_XY;
            case FIT_START: // fitStart
                return ScalingUtils.ScaleType.FIT_START;
            case FIT_CENTER: // fitCenter
                return ScalingUtils.ScaleType.FIT_CENTER;
            case FIT_END: // fitEnd
                return ScalingUtils.ScaleType.FIT_END;
            case CENTER: // center
                return ScalingUtils.ScaleType.CENTER;
            case CENTER_INSIDE: // centerInside
                return ScalingUtils.ScaleType.CENTER_INSIDE;
            case CENTER_CROP: // centerCrop
                return ScalingUtils.ScaleType.CENTER_CROP;
            default:
                return null;
        }
    }

    @Nullable
    private static Drawable getDrawable(Context context, int resourceId) {
        return (resourceId == 0 || context == null) ? null : context.getResources().getDrawable(resourceId);
    }
}
