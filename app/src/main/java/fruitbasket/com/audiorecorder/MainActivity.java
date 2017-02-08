package fruitbasket.com.audiorecorder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

import fruitbasket.com.audiorecorder.record.RecordService;

/**
 * Created by Study on 18/07/2016.
 */
public class MainActivity extends Activity {
    private static final String TAG=MainActivity.class.toString();

    private ToggleButton recorderToggleButton;
    private Intent intentToRecordService;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_activity);
        initView();
        intentToRecordService=new Intent(this, RecordService.class);
    }

    private void initView(){
        recorderToggleButton=(ToggleButton)findViewById(R.id.recorder_toggle_button);
        recorderToggleButton.setOnClickListener(new ToggleClickListener());
    }

    private void startRecording(){
        Log.i(TAG,"startRecording()");
        startService(intentToRecordService);
    }

    private void stopRecording(){
        Log.i(TAG,"stopRecording()");
        stopService(intentToRecordService);
    }





    private class ToggleClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.recorder_toggle_button:
                    if(((ToggleButton) view).isChecked()==true){
                        startRecording();
                    }
                    else{
                        stopRecording();
                    }
                    break;
            }
        }
    }
}
