package com.jsqix.multiimgupload.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.jsqix.multiimgupload.R;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import me.nereo.multi_image_selector.ViewPhotoActivity;
import me.nereo.multi_image_selector.utils.PermissionsUtils;

/**
 * Created by dq on 2016/1/26.
 */
public class ImgsGridAdapter extends BaseAdapter {
    private Activity mActivity;
    private ArrayList<String> mDataSet;
    private ImageOptions options = new ImageOptions.Builder().build();

    public ImgsGridAdapter(Activity activity, ArrayList<String> list) {
        this.mActivity = activity;
        this.mDataSet = list;
    }

    @Override
    public int getCount() {
        return mDataSet.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.img_grid_item, null);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            holder.delete = (ImageView) convertView.findViewById(R.id.delete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String item = mDataSet.get(position);
        if (position == mDataSet.size() - 1) {
            holder.delete.setVisibility(View.GONE);
            x.image().bind(holder.image, "assets://" + item, options);
        } else {
            holder.delete.setVisibility(View.VISIBLE);
            x.image().bind(holder.image, item, options);
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataSet.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 默认选择
                ArrayList mSelectPath = new ArrayList();
                mSelectPath.addAll(mDataSet);
                if (mSelectPath.size() > 0)
                    mSelectPath.remove(mSelectPath.size() - 1);
                if (mDataSet.size() == 0 || position == mDataSet.size() - 1) {
                    if (PermissionsUtils.hasPermissions(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        Intent intent = new Intent(mActivity, MultiImageSelectorActivity.class);
                        // 是否显示拍摄图片
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                        // 最大可选择图片数量
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 100);
                        // 选择模式
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
                        if (mSelectPath != null && mSelectPath.size() > 0) {
                            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
                        }
                        mActivity.startActivityForResult(intent, 100);
                    } else {
                        Toast.makeText(mActivity, "请到应用管理中授予权限", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Intent intent = new Intent(mActivity, ViewPhotoActivity.class);
                    intent.putExtra("current", position);
                    intent.putStringArrayListExtra("imgUrls", mSelectPath);
                    mActivity.startActivity(intent);
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        private ImageView image, delete;
    }
}
