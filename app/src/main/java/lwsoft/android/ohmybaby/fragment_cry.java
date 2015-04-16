package lwsoft.android.ohmybaby;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.HashMap;

/**
 * Created by gilbert on 2015-04-15.
 */
public class fragment_cry extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cry, container, false);
        setbtn( v, R.id.imageButton, "etc_sound/tiger_1.mp3");
        setbtn( v,R.id.imageButton2, "etc_sound/monkey_1.mp3");
        setbtn( v, R.id.imageButton3, "etc_sound/elephant_1.mp3");

        setbtn( v,R.id.imageButton4, "etc_sound/frog_1.mp3");
        setbtn( v,R.id.imageButton5, "etc_sound/pig_1.mp3");
        setbtn( v,R.id.imageButton6, "etc_sound/horse_1.mp3");

        setbtn( v,R.id.imageButton7, "etc_sound/cat_1.mp3");
        setbtn( v,R.id.imageButton8, "etc_sound/sheep_1.mp3");
        setbtn( v,R.id.imageButton9, "etc_sound/dog_1.mp3");

        setbtn( v, R.id.imageButton10, "etc_sound/cow_2.mp3");
        setbtn( v,R.id.imageButton11, "etc_sound/cow_1.mp3");
        setbtn( v,R.id.imageButton12, "etc_sound/lion_1.mp3");

        setbtn( v, R.id.imageButton13, "effect_sound/15_blender.mp3");
        setbtn( v, R.id.imageButton14, "effect_sound/01_vacuum.mp3");
        setbtn( v, R.id.imageButton15, "effect_sound/02_white_noise.mp3");

        setbtn( v, R.id.imageButton16, "effect_sound/03_heartbeat.mp3");
        setbtn(v, R.id.imageButton17, "effect_sound/04_plasticbag.mp3");
        setbtn(v, R.id.imageButton18, "effect_sound/05_topwater.mp3");

        setbtn(v, R.id.imageButton19, "effect_sound/06_shush.mp3");
        setbtn(v, R.id.imageButton20, "effect_sound/07_storm.mp3");
        setbtn(v, R.id.imageButton21, "effect_sound/08_dryer.mp3");

        setbtn(v, R.id.imageButton22, "effect_sound/09_underwater.mp3");
        setbtn(v, R.id.imageButton23, "effect_sound/10_campfire.mp3");
        setbtn(v, R.id.imageButton24, "effect_sound/11_rain.mp3");

        setbtn(v, R.id.imageButton25, "effect_sound/12_nature.mp3");
        setbtn(v, R.id.imageButton26, "effect_sound/13_fan.mp3");
        setbtn(v, R.id.imageButton27, "effect_sound/14_wave.mp3");

        setbtn_etc(v);
        return v;
    }

    HashMap<Integer, Drawable> mBackupBtnBG = new HashMap<>();
    //
    void setbtn(final View rootView,  final int resid_btn,final String soundFile ){

        final ImageButton v = (ImageButton) rootView.findViewById(resid_btn);
        Drawable db = v.getBackground();
        mBackupBtnBG.put( resid_btn, db );

        v.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {

                                     if( isPlaying(soundFile) )
                                     {
                                         stop_asset(soundFile);
                                         v.setBackgroundColor(  Color.argb(255, 255, 255, 255));
                                         v.setBackgroundDrawable( mBackupBtnBG.get(resid_btn) );
                                         //Toast.makeText(con,"stop : "+soundFile,Toast.LENGTH_SHORT ).show();

                                     }
                                     else
                                     {
                                         player_asset(soundFile, true);
                                         v.setBackgroundColor(  Color.argb(0,255,255,255));
                                         //Toast.makeText(con,"play : "+soundFile,Toast.LENGTH_SHORT ).show();
                                     }
                                 }
                             }
        );
    }

    private void setbtn_etc(final View rootView ){
        final ImageButton v = (ImageButton)rootView.findViewById(R.id.imageButton_stopall);
        v.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                for( String key : mpList.keySet() ){
                    MediaPlayer mp = mpList.get(key);
                    if( mp.isPlaying() )
                    {
                        mp.stop();
                    }
                }

                for( Integer i : mBackupBtnBG.keySet()){
                    ImageButton iv = (ImageButton ) rootView.findViewById(i);
                    iv.setBackgroundColor(  Color.argb(255, 255, 255, 255));
                    iv.setBackgroundDrawable( mBackupBtnBG.get(i) );

                }
            }
        });



    }



    public HashMap<String,MediaPlayer> mpList = new HashMap<>();

    public boolean isPlaying( String str ){
        if( mpList.containsKey( str ) ) {
            MediaPlayer mp = mpList.get(str);
            return mp.isPlaying();
        }
        return false;
    }

    public void stop_asset(String str){

        if( mpList.containsKey( str ) ) {
            MediaPlayer mp = mpList.get(str);
            mp.stop();
            Log.i("tag_", "stop_asset:" + str);
            mp.release();
            mp=null;
            mpList.remove(str);
        }
    }
    public void player_asset( String str,boolean bLoop){

        try {
            MediaPlayer mp = null;
            if(mpList.containsKey(str)){
                mp = mpList.get(str);
            }else
            {
                mp = new MediaPlayer();
                mpList.put(str, mp);
                AssetFileDescriptor descriptor = getActivity().getAssets().openFd(str);
                mp.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
                descriptor.close();
            }

            mp.prepare();
            mp.setVolume(1f, 1f);
            mp.setLooping(bLoop);
            mp.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
