# TensorFlow on Android

## Genegating .pb file by Tensorflow Python Code
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
    <br>Note:armeabi-v7a is for arm-base solution , it should be x86 or x86_64 on Intel Solution
  
### 3. Run TensorFlow Model on Android project

Import Tensor Flow Java Class:
```
import org.tensorflow.contrib.android.TensorFlowInferenceInterface;
```
Declare AssetManager and initialize it to use file in Asset 

Declare TensorFlowInferenceInterface and construct by the .pb file
```
AssetManager mAssetManager = getAssets();
TensorFlowInferenceInterface inferenceInterface = new TensorFlowInferenceInterface(mAssetManager,"file:///android_asset/test.pb");
```
Then we can input data , run model and get output by following methods of TensorFlowInferenceInterface :
```
inferenceInterface.feed(INPUT_NAME,input_x,1,4);
inferenceInterface.run(outputNames,false);
inferenceInterface.fetch(OUTPUT_NAME,out_buffer);
```
- .feed(...)
<INPUT_NAME> is a String ,this content must equals to the labeled input name in original Python code.

input_x,1,4 => it will create a Tensor of Shape[1,4] with content src input_x.

- .run(...)
<outputNames> must be a String array ,these contents must equal to the labeled output/node names in original Python code.

- .fetch(...)
<OUTPUT_NAME> is a String ,this contents must equals to the labeled output/node name in original Python code.

out_buffer must be same type with model output, and must be sure it's size is enough to store the output data.

These methods are undocumented now , please reference the implement of 
[TensorFlow Android Inference Interface](https://github.com/tensorflow/tensorflow/blob/master/tensorflow/contrib/android)

and Java class [Tensor](https://www.tensorflow.org/api_docs/java/reference/org/tensorflow/Tensor) 

Or we can refer  [TensorFlow Android Camera Demo](https://github.com/tensorflow/tensorflow/tree/master/tensorflow/examples/android/src/org/tensorflow/demo) to know how to implement it.

