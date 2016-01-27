package com.jsqix.multiimgupload;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.google.gson.Gson;
import com.jsqix.multiimgupload.adapter.ImgsGridAdapter;
import com.jsqix.multiimgupload.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import me.nereo.multi_image_selector.utils.PermissionsUtils;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @ViewInject(R.id.gridView)
    private GridView gridView;
    @ViewInject(R.id.btn_submit)
    private Button btnSubmit;
    private ArrayList<String> mSelectPath;
    private ArrayList<String> data = new ArrayList<String>();
    private ImgsGridAdapter imgsGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        this.data.add("add_image.png");
        imgsGridAdapter = new ImgsGridAdapter(this, data);
        gridView.setAdapter(imgsGridAdapter);
        //android 6.0权限问题
        requestPermissions();

    }

    private void requestPermissions() {
        PermissionsUtils.requestPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, 100);
    }

    @Event(value = {R.id.btn_submit})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                this.data.clear();
                this.data.addAll(mSelectPath);
                this.data.add("add_image.png");
                Log.i("imgruls", new Gson().toJson(mSelectPath));
                imgsGridAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 100) {
            PermissionsUtils.requestPermissions(this, Manifest.permission.CAMERA, 101);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}