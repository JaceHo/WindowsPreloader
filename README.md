WindowsPreloader
===================

## Introduction
Windows 8 like loading animation for Android.
## Demo
![](https://github.com/kimiscircle/WindowsPreloader/blob/master/demo.gif)

## Usage

### Step 1

Add it in your root build.gradle at the end of repositories:
```groovy
  allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
  dependencies {
     compile 'com.github.kimiscircle:WindowsPreloader:1.0'
     compile 'com.android.support:appcompat-v7:23.1.1'
  }
```

### Step 2

Add the WindowsPreloader to your layout:
```java
   <info.futureme.preloader.WindowsPreloaderView
        android:layout_width="200dp"
        android:layout_height="200dp"
        android:padding="30dp"
        android:background="@android:color/background_dark"
        />
```

##Contact me

 If you have a better idea or way on this project, please let me know, thanks :)

[Email](mailto:895196292@qq.com)

[Weibo](http://weibo.com/5312295202)


### License
```
Copyright 2015 Jeffrey

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

