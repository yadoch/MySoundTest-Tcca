package tw.com.abc.mysoundtest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.SoundPool;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private SoundPool sp;
    private int s1,s2;
    private MediaRecorder mediaRecorder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,},
                    0);

        }else{
            init();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            init();
        }
    }

    private void init() {
        sp = new SoundPool(2, AudioManager.STREAM_MUSIC,0);
        s1=sp.load(this,R.raw.S1,1);
        s2=sp.load(this,R.raw.S2,1);
    }

    public void test1(View view) {
        sp.play(s1,0.5f,0.5f,1,0,1);
    }
    public void test2(View view) {
        sp.play(s2,0.5f,0.5f,1,0,1);
    }
    public void test3(View view) {
        //錄音
        File sdroot = Environment.getExternalStorageDirectory();
        File rfile = new File(sdroot,"brad.3gp");

        //以下順序性請參考 Studio　API 圖
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        mediaRecorder.setOutputFile(rfile.getAbsolutePath());

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void test4(View view) {
        // 停止錄音
        if(mediaRecorder != null){
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder=null;
        }
    }
    public void test5(View view) {
        // 播放錄音檔
        File sdroot = Environment.getExternalStorageDirectory();
        File rfile = new File(sdroot,"brad.3gp");
        MediaPlayer mediaPlayer =new MediaPlayer();

        try {
            mediaPlayer.setDataSource(rfile.getAbsolutePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
