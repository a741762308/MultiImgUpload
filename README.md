#MultiImgUpload
====
参考lovetuzitong/MultiImageSelector
https://github.com/lovetuzitong/MultiImageSelector
###动态截图
![](https://github.com/a741762308/MultiImgUpload/blob/master/Screenshots.gif)
###使用方法
#######鉴于lovetuzitong并没有上传maven或jcenter，我仅是将代码上传
###build.gradle文件
dependencies
{
    compile 'me.nereo.multi_image_selector:multi-image-selector:1.0.0'
}
###Manifest文件
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS" />
<uses-permission android:name="android.permission.CAMERA" />
<activity
            android:name="me.nereo.multi_image_selector.MultiImageSelectorActivity"
            android:configChanges="orientation|screenSize" />      <activity android:name="me.nereo.multi_image_selector.ViewPhotoActivity" />
