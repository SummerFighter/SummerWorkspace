package Controller;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.File;

public class MediaRecorderUtil {


    private MediaRecorder mediarecorder=null;//录音功能公共类
    private static final String recordFilePath = Environment.getExternalStorageDirectory() + "/AAA/recorder.aac";
    private RecorderThread recorderThread=null;
    private static final String UPDATE_TAG="update_tag";


    /**
     * 开启录音功能
     */
    public void recorderStart(){

        //启动midiarecoder录音
        recorderThread=new RecorderThread();
        recorderThread.start();

    }


    /**
     * 停止录音，并保存录音
     */
    public void recorderSave(){

        if(mediarecorder!=null){
            mediarecorder.stop();
            mediarecorder.release();
            mediarecorder=null;

            if(recorderThread!=null){
                recorderThread=null;
            }
            Log.e(UPDATE_TAG,"Thread stop voice and save...");
        }

    }


    //开启录音功能线程
    class RecorderThread extends Thread{

        @Override
        public void run() {
            super.run();
            try {

                //创建保存录音文件
                File file=new File(recordFilePath);

                if (mediarecorder==null) {
                    mediarecorder=new MediaRecorder();//实例化录音文件对象
                }
                mediarecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//设置获取录音文件来源
                mediarecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);//设置录音文件输出格式
                mediarecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);//设置录音文件的编码格式
                mediarecorder.setOutputFile(file.getAbsolutePath());//设置录音文件的输出路径

                mediarecorder.prepare();//录音文件的准备工作
                mediarecorder.start();//录音开始

                Log.e(UPDATE_TAG,"Thread start voice...");

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

}