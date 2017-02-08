package fruitbasket.com.audiorecorder.record;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import fruitbasket.com.audiorecorder.AppCondition;

/**
 * 录制声音
 */
public class AudioRecordWrapper {
	private static final String TAG=AudioRecordWrapper.class.toString();

	private String audioFullName;
	private WavHeader wavHeader;
	private boolean isRecording=false;
	
	public AudioRecordWrapper(){
		wavHeader=new WavHeader();
	}

	public void setAudioFullName(String audioFullName){
		this.audioFullName=audioFullName;
	}
	
	/**
	 * 开始录制音频
	 * @return ture 录音完成，false 录音失败
	 */
	public boolean startRecording(){
		return this.startRecording(MediaRecorder.AudioSource.MIC);
	}

	public boolean startRecording(int audioSource){
		return startRecording(
				audioSource,
				AppCondition.SIMPLE_RATE_CD,
				AudioFormat.CHANNEL_IN_STEREO,
				AudioFormat.ENCODING_PCM_16BIT
		);
	}

	/**
	 *
	 * @param sampleRate 声音的采样频率
	 * @param channelIn 声道
	 * @param encoding specific the quantity of bit of each voice frame
     * @return ture 录音完成，false 录音失败
     */
	public boolean startRecording(int audioSource,final int sampleRate,int channelIn,int encoding){
		File audioFile= new File(AppCondition.APP_FILE_DIR+File.separator+"cache");

		int bufferSize = AudioRecord.getMinBufferSize(
				sampleRate,
				channelIn,
				encoding);
		if(bufferSize==AudioRecord.ERROR_BAD_VALUE){
			Log.e(TAG,"recordingBufferSize==AudioRecord.ERROR_BAD_VALUE");
			return false;
		}
		else if(bufferSize==AudioRecord.ERROR){
			Log.e(TAG,"recordingBufferSize==AudioRecord.ERROR");
			return false;
		}
		byte[] buffer = new byte[bufferSize];

		try {
			FileOutputStream output = new FileOutputStream(audioFile);
			AudioRecord audioRecord = new AudioRecord(
					audioSource,
					sampleRate,
					channelIn,
					encoding,
					bufferSize);
			audioRecord.startRecording();

			isRecording = true;
			while (isRecording) {
				int readState = audioRecord.read(buffer, 0, bufferSize);
				if(readState==AudioRecord.ERROR_INVALID_OPERATION){
					Log.e(TAG,"readState==AudioRecord.ERROR_INVALID_OPERATION");
					return false;
				}
				else if(readState==AudioRecord.ERROR_BAD_VALUE){
					Log.e(TAG,"readState==AudioRecord.ERROR_BAD_VALUE");
					return false;
				}
				else{
					output.write(buffer);
				}
			}
			output.close();
			audioRecord.stop();
			audioRecord.release();

			FileInputStream inputStream=new FileInputStream(audioFile);
			FileOutputStream outputStream=new FileOutputStream(audioFullName);

			int length=(int)inputStream.getChannel().size();
			wavHeader.setAdjustFileLength(length-8);
			wavHeader.setAudioDataLength(length-44);
			wavHeader.setBlockAlign(channelIn,encoding);
			wavHeader.setByteRate(channelIn,sampleRate,encoding);
			wavHeader.setChannelCount(channelIn);
			wavHeader.setEncodingBit(encoding);
			wavHeader.setSampleRate(sampleRate);
			wavHeader.setWaveFormatPcm(WavHeader.WAV_FORMAT_PCM);

			outputStream.write(wavHeader.getHeader());
			while (inputStream.read(buffer) != -1) {
				outputStream.write(buffer);
			}
			inputStream.close();
			outputStream.close();

			audioFile.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public void stopRecoding(){
		isRecording = false;
	}
}
