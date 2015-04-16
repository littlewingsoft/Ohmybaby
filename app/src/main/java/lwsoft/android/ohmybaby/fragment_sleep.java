package lwsoft.android.ohmybaby;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by gilbert on 2015-04-15.
 */
public class fragment_sleep extends Fragment {
    mp3listPlayer mp3;
    static String lastSong="";
    private class songdata{
        String name;
        String filePath;
        songdata( String _name, String _filepath ){
            name = _name;
            filePath = _filepath;
        }
    };

    private ListView mListView;
    private ArrayAdapter<String> mAdapter;
    private ArrayList<songdata> mSongList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mSongList = new ArrayList<>();
        final View v =inflater.inflate(R.layout.fragment_sleep, container, false);
        mAdapter = new ArrayAdapter<String>( v.getContext(),  android.R.layout.simple_list_item_1);
        mListView = (ListView) v.findViewById(R.id.listView_sleepsong);
        mListView.setAdapter(mAdapter);

        // ListView 아이템 터치 시 이벤트 추가
        mListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // 이벤트 발생 시 해당 아이템 위치의 텍스트를 출력
                mp3.stop_audiolist();
                mp3.audio_stop();
                mp3.audio_play(mSongList.get(arg2).filePath, true);
                lastSong = mSongList.get( arg2 ).filePath;
                Toast.makeText(v.getContext(), mSongList.get( arg2 ).name, Toast.LENGTH_SHORT).show();
            }
        });

        settingSongList();

        Button randomBtn= (Button )v.findViewById(R.id.sleep_randomplay);
        randomBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //shuffle
                Collections.shuffle(mSongList);
                mAdapter.clear();
                for( int n=0; n< mSongList.size(); n++){
                    mAdapter.add(  mSongList.get(n).name );
                }
                mp3.setSongList(mSongList );
                mp3.audio_stop();
                mp3.stop_audiolist();
                mp3.start_audiolist();
             }
        });

        Button btn_sequantially= (Button )v.findViewById(R.id.sleep_sequantial);
        btn_sequantially.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mp3.audio_stop();
                mp3.stop_audiolist();
                mp3.start_audiolist();
            }
        });

        Button btn_stop= (Button )v.findViewById(R.id.sleep_stopall);
        btn_stop.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mp3.audio_stop();
                mp3.stop_audiolist();
            }
        });

        final ImageButton v2 = (ImageButton)v.findViewById(R.id.imagebutton_sleep_timer);
        v2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                showDialog_timer();
            }
        });

        mp3 = new mp3listPlayer(mSongList);
        return v;
    }


    void showDialog_timer() {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog_timer");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment newFragment = dlg_timer.newInstance();
        newFragment.show(ft, "dialog_timer");
    }

    class mp3listPlayer {
        Handler mHandler;
        int currItemPos=0;
        ArrayList<songdata> mSongList;
        private Runnable mPlayTask = new Runnable() {
            @Override
            public void run() {

                int duration = audio_play(mSongList.get(currItemPos).filePath, false);
                Log.i("tag_", String.format( "file:%s  duration:%d",mSongList.get(currItemPos).filePath,duration  ));
                currItemPos++;
                currItemPos %= mSongList.size();
                mHandler.postDelayed( this, duration );
            }
        };

        public void setSongList(ArrayList<songdata> _songlist ){
            mSongList = _songlist;

        }
        public mp3listPlayer( ArrayList<songdata> _songlist  )
        {
            mHandler = new Handler();
            setSongList(_songlist);
            //isPlay.set(true);
        }

        public void stop_audiolist()
        {
            currItemPos = 0;
            mHandler.removeCallbacks( mPlayTask );
        }

        public void start_audiolist(){
            mHandler.post( mPlayTask );
        }

        MediaPlayer mMediaPlayer;

        public void audio_stop(){
            if( mMediaPlayer != null ){
                mMediaPlayer.stop();
                //Log.i("tag_", "stop_asset:");
                mMediaPlayer.release();
                mMediaPlayer=null;
            }
        }

        public int audio_play( String str,boolean bLoop){
            int len = 0;
            try {
                audio_stop();
                mMediaPlayer = new MediaPlayer();
                AssetFileDescriptor descriptor = getActivity().getAssets().openFd(str);
                mMediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
                descriptor.close();
                mMediaPlayer.prepare();
                mMediaPlayer.setVolume(1f, 1f);
                mMediaPlayer.setLooping(bLoop);
                mMediaPlayer.start();
                len = mMediaPlayer.getDuration();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return len;
        }


    }


    void settingSongList(){
        // ListView에 아이템 추가
        mSongList.add(new songdata("Hush Little Baby","lullaby_audio/1_Song_HushLittleBaby.mp3"   ));
        mSongList.add(new songdata("Lullaby Good Night","lullaby_audio/2_Song_LullabyGoodnight.mp3"   ));
        mSongList.add(new songdata("Pretty Little Horses","lullaby_audio/3_Song_PrettyLittleHorses.mp3"   ));
        mSongList.add(new songdata("Rock a bye Baby","lullaby_audio/4_Song_RockabyeBaby.mp3"   ));
        mSongList.add(new songdata("Twinkle","lullaby_audio/5_Song_Twinkle.mp3"   ));

        mSongList.add(new songdata("AveMaria","lullaby_audio/6_Classic_AveMaria.mp3"   ));
        mSongList.add(new songdata("CradleSong","lullaby_audio/7_1_Classic_CradleSong.mp3"   ));
        mSongList.add(new songdata("Lullaby For Baby","lullaby_audio/7_2_Classic_LullabyForBaby.mp3"   ));
        mSongList.add(new songdata("Song To Sleep","lullaby_audio/7_3_Classic_SongToSleep.mp3"   ));
        mSongList.add(new songdata("Golden Slumbers","lullaby_audio/7_Classic_GoldenSlumbers.mp3"   ));

        mSongList.add(new songdata("Goodnight","lullaby_audio/8_MusicBox_Goodnight.mp3"   ));
        mSongList.add(new songdata("The Happy Song","lullaby_audio/9_MusicBox_TheHappySong.mp3"   ));
        mSongList.add(new songdata("Silent Night Holy Night","lullaby_audio/10_MusicBox_SilentNightHolyNight.mp3"   ));
        mSongList.add(new songdata("Mary Had A Little Lamb","lullaby_audio/11_MusicBox_MaryHadALittleLamb.mp3"   ));
        mSongList.add(new songdata("O Tannen baum","lullaby_audio/12_MusicBox_OTannenbaum.mp3"   ));

        mSongList.add(new songdata("Beautiful Dreamer","lullaby_audio/13_MusicBox_BeautifulDreamer.mp3"   ));
        mSongList.add(new songdata("GreenBottles","lullaby_audio/14_MusicBox_GreenBottles.mp3"   ));
        mSongList.add(new songdata("Over The Rainbow","lullaby_audio/15_MusicBox_OverTheRainbow.mp3"   ));
        mSongList.add(new songdata("RainCloud","lullaby_audio/16_MusicBox_RainCloud.mp3"   ));

        for( int n=0; n< mSongList.size(); n++){
            mAdapter.add(  mSongList.get(n).name );
        }
    }


}
