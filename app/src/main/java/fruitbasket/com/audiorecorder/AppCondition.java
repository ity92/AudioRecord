package fruitbasket.com.audiorecorder;

import android.os.Environment;

/**
 * Created by Study on 18/07/2016.
 */
public class AppCondition {
    private static final AppCondition APP_CONDITION =new AppCondition();

    public static final String APP_FILE_DIR= Environment.getExternalStorageDirectory()+/*File.separator+*/"/AudioRecorder";
    public static final int SIMPLE_RATE_CD=44100;

    private AppCondition(){
    }

    public static  AppCondition getInstance(){
        return APP_CONDITION;
    }

}
