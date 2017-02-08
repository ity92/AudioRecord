package fruitbasket.com.audiorecorder.record;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.util.Log;


/**
 * Created by Study on 18/07/2016.
 */
public class RecordService extends Service {
    private static final String TAG=RecordService.class.toString();

    private RecordTask micRecordTask;
    private Thread micRecord;

    @Override
    public void onCreate(){
        super.onCreate();
        Log.i(TAG,"onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.i(TAG,"onStartCommmand()");
        micRecordTask=new RecordTask(MediaRecorder.AudioSource.MIC);
        micRecord=new Thread(micRecordTask);
        micRecord.start();

        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        micRecordTask.stopRecording();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}


