# ProgressRoundButton  

[![](https://jitpack.io/v/jeffyu0925/ProgressRoundButton.svg)](https://jitpack.io/#jeffyu0925/ProgressRoundButton)

## Usage

### step1
gradle
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
```
dependencies {
    implementation 'com.github.jeffyu0925:ProgressRoundButton:1.2.0-sdk'
}
```

### step2 
you can define the button in xml like this:

```xml
<com.xiaochen.progressroundbutton.AnimDownloadProgressButton
android:id="@+id/anim_btn"
android:layout_width="match_parent"
android:layout_height="40dp"
app:progressbtn_background_color="@android:color/holo_orange_light"
app:progressbtn_background_second_color="@android:color/holo_green_light"/>
```
### advanced
* If you want shadow and press effect，please use `AnimButtonLayout` instead of `AnimDownloadProgressButton` :

```xml
<com.xiaochen.progressroundbutton.AnimButtonLayout
android:id="@+id/anim_btn3"
android:layout_width="match_parent"
android:layout_height="40dp"
android:layout_marginTop="40dp"
app:progressbtn_background_color="@android:color/holo_blue_dark"
app:progressbtn_background_second_color="@android:color/darker_gray"
app:progressbtn_enable_gradient="true"
app:progressbtn_enable_press="true"/>
```

* You can also implement your own `ButtonController`,so that you can controller gradient effect;

The Customized properties are in the follow table:

| Property        | Format           | Default  |  
| ------------- |:-------------:| :-----:|  
|progressbtn_radius  |float  |half of the button height  |  
|progressbtn_background_color|color | #6699ff |
|progressbtn_background_second_color|color|Color.LTGRAY|
|progressbtn_text_color|color|progressbtn_background_color|
|progressbtn_text_covercolor|color|Color.WHITE|  
|progressbtn_enable_press|boolean|false|  
|progressbtn_enable_gradient|boolean|false|  

The follow picture make a clear explanation:

![show](http://ww4.sinaimg.cn/large/0060lm7Tgw1ex1yr2b9xjj30eg0go75n.jpg)

open gradient

![gradient](http://ww4.sinaimg.cn/mw690/6ccf7929gw1f96m1ejk01j208m01mjra.jpg)

use `AnimButtonLayout`

![shadow](http://ww2.sinaimg.cn/mw690/6ccf7929gw1f96m1f759gj208h01taa0.jpg)

## License

Copyright 2015 cctanfujun

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
