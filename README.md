# TensorFlow on Android Test 


1. Clone tensorflow project and build libtensorflow_inference.so and libandroid_tensorflow_inference_java.jar
https://github.com/tensorflow/tensorflow/blob/master/tensorflow/contrib/android/README.md

bazel build -c opt //tensorflow/contrib/android:libtensorflow_inference.so \
   --crosstool_top=//external:android/crosstool \
   --host_crosstool_top=@bazel_tools//tools/cpp:toolchain \
   --cpu=armeabi-v7a
   Note: --cpu=<target-platform> => armeabi-v7a for qualcomm solution
   
bazel-bin/tensorflow/contrib/android/libandroid_tensorflow_inference_java.jar

