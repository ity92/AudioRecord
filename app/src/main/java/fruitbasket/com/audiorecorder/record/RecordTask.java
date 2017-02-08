package fruitbasket.com.audiorecorder.record;

import android.media.MediaRecorder;

import java.io.File;

import fruitbasket.com.audiorecorder.AppCondition;

public class RecordTask implements Runnable {
	
	private AudioRecordWrapper audioRecordWrapper;

	///这里音源控制方式，上有尚改进的地方。因为如果要以特定的采样频率录制声音的话，又要设置更多的变量
	private int audioSource;

	public RecordTask(){
		this(MediaRecorder.AudioSource.MIC);
	}

	public RecordTask(int audioSource){
		this.audioSource=audioSource;
	}

	public void setAudioSource(int audioSource){
		this.audioSource=audioSource;
	}
	
	@Override
	public void run() {
		audioRecordWrapper =new AudioRecordWrapper();
		if(audioSource== MediaRecorder.AudioSource.MIC){
			audioRecordWrapper.setAudioFullName(AppCondition.APP_FILE_DIR+ File.separator+System.currentTimeMillis()+".mic.wav");
			audioRecordWrapper.startRecording(audioSource);
		}
		else if(audioSource==MediaRecorder.AudioSource.CAMCORDER){
			audioRecordWrapper.setAudioFullName(AppCondition.APP_FILE_DIR+ File.separator+System.currentTimeMillis()+".cam.wav");
			audioRecordWrapper.startRecording(audioSource);
		}
	}

	public void stopRecording(){
		if(audioRecordWrapper !=null){
			audioRecordWrapper.stopRecoding();
			audioRecordWrapper =null;
		}
	}
}
