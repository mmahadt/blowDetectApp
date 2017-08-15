package mahad.tariq.seecs.blowingapp;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private float blow_value;
    private TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv2 = (TextView)findViewById(R.id.tv2);
        if (isBlowing()){
            tv2.setText("Blowing.........");
        }
    }

    public boolean isBlowing()
    {
        boolean recorder=true;

        int minSize = AudioRecord.getMinBufferSize(8000, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT);
        AudioRecord ar = new AudioRecord(MediaRecorder.AudioSource.MIC, 8000,AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT,minSize);


        short[] buffer = new short[minSize];

        ar.startRecording();
        while(recorder)
        {

            ar.read(buffer, 0, minSize);
            for (short s : buffer)
            {
                if (Math.abs(s) > 27000)   //DETECT VOLUME (IF I BLOW IN THE MIC)
                {
                    blow_value=Math.abs(s);
                    System.out.println("Blow Value="+blow_value);
                    ar.stop();
                    recorder=false;

                    return true;

                }

            }
        }
        return false;

    }
}
