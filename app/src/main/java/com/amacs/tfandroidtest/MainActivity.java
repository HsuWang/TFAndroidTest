package com.amacs.tfandroidtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.res.AssetManager;
import android.widget.TextView;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

public class MainActivity extends AppCompatActivity {

    private static final String INPUT_NAME = "input";
    private static final String OUTPUT_NAME = "output";
    private TensorFlowInferenceInterface inferenceInterface;
    private float[] input_x= {1,2,3,4};
    private float[] out_buffer= new float[4];
    private String outputNames[] =new String[] {"output"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AssetManager mAssetManager = getAssets();
        inferenceInterface = new TensorFlowInferenceInterface(mAssetManager,"file:///android_asset/test.pb");

        TextView inputText = (TextView) findViewById(R.id.model_input);

        inputText.setText("Intput:"+
                String.valueOf(input_x[0])+","+
                String.valueOf(input_x[1])+","+
                String.valueOf(input_x[2])+","+
                String.valueOf(input_x[3])
        );

        inferenceInterface.feed(INPUT_NAME,input_x,1,4);
        inferenceInterface.run(outputNames,false);
        inferenceInterface.fetch(OUTPUT_NAME,out_buffer);


        TextView outText = (TextView) findViewById(R.id.model_output);

        outText.setText("Outtput:\n"+
                String.valueOf(out_buffer[0])+",\n"+
                String.valueOf(out_buffer[1])+",\n"+
                String.valueOf(out_buffer[2])+",\n"+
                String.valueOf(out_buffer[3])
        );

    }
}
