package com.example.machenike.myapplication.a;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;


/**
 * Created by aa on 2017/5/5.
 */

public class ImagesUtils {

    public static void disImg(Context context, String imgUrl, ImageView imgv) {
//        Glide.with(context)
//                .load(imgUrl)
////                .placeholder(R.drawable.ic_image_loading)
////                .error(R.drawable.ic_empty_picture)
//                .into(imgv);
    }
    public static void disImg2(Context context, String imgUrl, ImageView imgv) {
//        if(!TextUtils.isEmpty(imgUrl)){
//            Glide.with(context)
//                    .load(imgUrl)
//                    .into(imgv);
//        }
    }


    public static void disImgCircle(Context context, String imgUrl, ImageView imgv) {
        if (!TextUtils.isEmpty(imgUrl)) {
//            Glide.with(context)
//                    .load(imgUrl)
//                    .transform(new GlideCircleTransform(context)).into(imgv);

        }
    }

    public static void disImgCircleNo(Context context, String imgUrl, ImageView imgv) {
        if (!TextUtils.isEmpty(imgUrl)) {
//            Glide.with(context)
//                    .load(imgUrl)
////                    .placeholder(R.drawable.logo_circle)
////                    .error(R.drawable.logo_circle)
//                    .transform(new GlideCircleTransform(context)).into(imgv);

        }
    }
    public static void disImgCircleNo2(Context context, int imgUrl, ImageView imgv) {
//        Glide.with(context)
//                .load(imgUrl)
//                .transform(new GlideCircleTransform(context)).into(imgv);

    }
    public static void disImgRound(Context context, String imgUrl, ImageView imgv) {
//        Glide.with(context).load(imgUrl)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
////                .centerCrop().override(1090, 1090 * 3 / 4)
//                .bitmapTransform(new RoundedCornersTransformation(context, 15, 0, RoundedCornersTransformation.CornerType.ALL))
//                .crossFade().into(imgv);

    }
}
