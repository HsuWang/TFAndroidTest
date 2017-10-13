# TensorFlow on Android

## Genegating .pb file by TensorflowPython
 test.py is a simple example that modified from https://www.tensorflow.org/get_started/get_started#complete_program
 this code use GradientDescentOptimizer to optimize a linear model
```
 y = w * x + b
```
- Run test.py will genegate a  PB file that store model with weight `w` and bias `b`.
- this file can be loaded from anroid app and run model with trianed weights

## Using TensorFlow on Android Project
### 1. Clone tensorflow project and build TF libraries
　　https://github.com/tensorflow/tensorflow/blob/master/tensorflow/contrib/android/README.md
 - 　　build libtensorflow_inference.so
 ```
bazel build -c opt //tensorflow/contrib/android:libtensorflow_inference.so \
   --crosstool_top=//external:android/crosstool \
   --host_crosstool_top=@bazel_tools//tools/cpp:toolchain \
   --cpu=armeabi-v7a
   ```
   Note: --cpu=<target-platform> => armeabi-v7a for qualcomm solution
   
 - 　　Build libandroid_tensorflow_inference_java.jar
 ```
bazel-bin/tensorflow/contrib/android/libandroid_tensorflow_inference_java.jar
```

### 2. Put Tensor Flow Files to Android project
#### 　　Add .pb file 
 - Project View: app->src->main->Right Click->new->folder->Assets Folder
 - Copy .pb file to app/src/main/assets/
 
#### 　　Add TensorFlow Interface JAVA classes
  - Copy libandroid_tensorflow_inference_java.jar to app/libs/
  - Project View: Find app/libs/libandroid_tensorflow_inference_java.jar ->Right Click -> Add as library
 
#### 　　Add TensorFlow Interface Library
  - Create New Folder app/src/main/jniLibs/armeabi-v7a/
  - Copy libtensorflow_inference.so to this folder
    　　Note:armeabi-v7a is for arm-base solution , it should be x86 or x86_64 on Intel Solution
  
