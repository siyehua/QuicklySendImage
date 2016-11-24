package com.siyehua.listenerfile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.File;
import java.util.List;

/**
 * Created by siyehua on 2016/11/23.
 */
public class MyAdapter extends BaseAdapter {
    private List<File> list;

    public MyAdapter(List<File> list) {
        this.list = list;
    }

    public void setList(List<File> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public File getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent,
                    false);
            viewHolder.content1 = (ImageView) convertView.findViewById(R.id.iv_content1);
            viewHolder.content2 = (ImageView) convertView.findViewById(R.id.iv_content2);
            convertView.setTag(viewHolder);
        } else viewHolder = (ViewHolder) convertView.getTag();
        File tmp = list.get(position);
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 表示加载图像的原始宽高
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(tmp.getPath(), options);
        if (options.outWidth < options.outHeight) {
            viewHolder.content1.setVisibility(View.VISIBLE);
            viewHolder.content2.setVisibility(View.GONE);
            viewHolder.content1.setImageBitmap(optimizeBitmap(tmp.getPath(), (int) parent
                    .getContext().getResources().getDimension(R.dimen.w1), (int) parent
                    .getContext().getResources().getDimension(R.dimen.h1)));
            viewHolder.content2.setImageBitmap(null);
        } else {
            viewHolder.content1.setVisibility(View.GONE);
            viewHolder.content2.setVisibility(View.VISIBLE);
            viewHolder.content1.setImageBitmap(null);
            viewHolder.content2.setImageBitmap(optimizeBitmap(tmp.getPath(), (int) parent
                    .getContext().getResources().getDimension(R.dimen.w2), (int) parent
                    .getContext().getResources().getDimension(R.dimen.h2)));
        }
        return convertView;
    }

    static class ViewHolder {
        ImageView content1, content2;
    }

    public static Bitmap optimizeBitmap(String pathName, int maxWidth, int maxHeight) {
        Bitmap result = null;
        try {
            // 图片配置对象，该对象可以配置图片加载的像素获取个数
            BitmapFactory.Options options = new BitmapFactory.Options();
            // 表示加载图像的原始宽高
            options.inJustDecodeBounds = true;
            result = BitmapFactory.decodeFile(pathName, options);
            // Math.ceil表示获取与它最近的整数（向上取值 如：4.1->5 4.9->5）
            int widthRatio = (int) Math.ceil(options.outWidth / maxWidth);
            int heightRatio = (int) Math.ceil(options.outHeight / maxHeight);
            // 设置最终加载的像素比例，表示最终显示的像素个数为总个数的
            if (widthRatio > 1 || heightRatio > 1) {
                if (widthRatio > heightRatio) {
                    options.inSampleSize = widthRatio;
                } else {
                    options.inSampleSize = heightRatio;
                }
            }
            // 解码像素的模式，在该模式下可以直接按照option的配置取出像素点
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inJustDecodeBounds = false;
            result = BitmapFactory.decodeFile(pathName, options);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
