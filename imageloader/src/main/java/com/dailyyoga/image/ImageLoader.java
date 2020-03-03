package com.dailyyoga.image;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

/**
 * @author: YougaKingWu@gmail.com
 * @created on: 2019/8/27 11:45
 * @description:
 */
public class ImageLoader {

    private static final ClassLoader<GenericLoader> imageClassLoader = new ImageClassLoader();

    public static void registerInitializationLoader(InitializationLoader initializationLoader) {
        imageClassLoader.setInitializationLoader(initializationLoader);
    }

    public static ILoader with(@NonNull Activity activity) {
        return imageClassLoader.create().with(activity);
    }

    public static ILoader with(@NonNull Fragment fragment) {
        return imageClassLoader.create().with(fragment);
    }

    public static ILoader with(@NonNull Context context) {
        return imageClassLoader.create().with(context);
    }

    private static final class ImageClassLoader implements ClassLoader<GenericLoader> {

        private InitializationLoader initializationLoader;

        //        private Class<?> load() {
//            List<Class<?>> list = new ArrayList<>();
//            try {
//                list.add(Class.forName("com.dailyyoga.image.GlideLoader"));
//            } catch (ClassNotFoundException ex) {
//                // ignore
//            }
//            try {
//                list.add(Class.forName("com.dailyyoga.image.FrescoLoader"));
//            } catch (ClassNotFoundException ex) {
//                // ignore
//            }
//            return list.isEmpty() ? null : list.get(0);
//        }

        @Override
        public void setInitializationLoader(@NonNull InitializationLoader initializationLoader) {
            this.initializationLoader = initializationLoader;
        }

        @Override
        @NonNull
        public GenericLoader create() {
//            try {
//                Class<?> hardcoded = load();
//                if (hardcoded == null) return defaultLoader();
//                return hardcoded.asSubclass(GenericLoader.class).getConstructor().newInstance();
//            } catch (Throwable t) {
//                t.printStackTrace();
//                return defaultLoader();
//            }
            if (initializationLoader != null) {
                initializationLoader.initialize();
            }
            return new FrescoLoader();
        }

//        private GenericLoader defaultLoader() {
//            return new GenericLoader() {
//                @Override
//                public ILoader load(int resourceId) {
//                    return this;
//                }
//
//                @Override
//                public ILoader load(@Nullable String url) {
//                    return this;
//                }
//
//                @Override
//                public ILoader load(@Nullable File file) {
//                    return this;
//                }
//
//                @Override
//                public void into(ImageView imageView) {
//
//                }
//            };
//        }
    }

    private interface ClassLoader<T> {

        void setInitializationLoader(@NonNull InitializationLoader initializationLoader);

        @NonNull
        T create();
    }

    public interface InitializationLoader {

        void initialize();
    }
}
