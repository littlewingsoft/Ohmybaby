package lwsoft.android.ohmybaby;
import android.app.ActionBar;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.util.HashMap;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends FragmentActivity  {
    public static MainActivity inst;
    ImageButton imgbtn_cry;
    ImageButton imgbtn_sleep;
    ImageButton imgbtn_juniver;
    pagerAdapter mPagerAdapter;
    ViewPager vp;
    final static int id_fragment_cry = 0;
    final static int id_fragment_sleep = 1;
    final static int id_fragment_juniver = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        inst = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vp =(ViewPager) findViewById(R.id.mainViewPager);

        mPagerAdapter =  new pagerAdapter( getSupportFragmentManager()  );

        vp.setAdapter( mPagerAdapter );
        vp.setCurrentItem( id_fragment_cry );

        setupTabs();

        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

                imgbtn_cry.setSelected(false);
                imgbtn_sleep.setSelected(false);
                imgbtn_juniver.setSelected(false);

                switch(position){
                    case 0:
                        imgbtn_cry.setSelected(true);
                        break;
                    case 1:
                        imgbtn_sleep.setSelected(true);
                        break;
                    case 2:
                        imgbtn_juniver.setSelected(true);
                        break;
                }
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }
        });

        setupTabs();
        //timerStart(3000);
    }

    private void setupTabs() {
        imgbtn_cry = (ImageButton)findViewById(R.id.imageButton_crybaby);
        imgbtn_cry.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                vp.setCurrentItem(id_fragment_cry);
            }
        });

        imgbtn_sleep= (ImageButton)findViewById(R.id.imageButton_sleepbaby);
        imgbtn_sleep.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                vp.setCurrentItem(id_fragment_sleep);
            }
        });

        imgbtn_juniver= (ImageButton)findViewById(R.id.imageButton_juniver);
        imgbtn_juniver.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                vp.setCurrentItem(id_fragment_juniver);
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
                    AssetFileDescriptor descriptor = getAssets().openFd(str);
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
    /**
     * Manager of Sounds
     */
    /*
    protected void playSnd_from_raw(int resid ) {
        if (mp != null  ) {
            if( mp.isPlaying() )
            {
                return;
            }else{
                mp.reset();
                mp.release();
            }

        }
        mp = MediaPlayer.create( this, resid );
        if( mp != null )
            mp.start();
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    Handler mHandler_timer = null;
    Runnable mTimerCallback=null;
    public void timerStop(){
        if( mHandler_timer != null)
        {
            mHandler_timer.removeCallbacks( mTimerCallback );
            mHandler_timer = null;
        }
    }
    public void timerStart( int delaytime ){

        timerStop();

        if( delaytime > 0 )
        {
            mTimerCallback= new Runnable() {
                @Override
                public void run() {
                    //Log.i("tag_", "ok finish!!!");
                    finish();
                }
            };

            mHandler_timer = new Handler();
            mHandler_timer.postDelayed(mTimerCallback, delaytime);
        }
    }

    // FragmentPageAdater : Fragment로써 각각의 페이지를 어떻게 보여줄지 정의한다.
    public class pagerAdapter extends FragmentPagerAdapter {

        public fragment_cry mFragment_cry;
        public fragment_sleep mFragment_sleep;
        public fragment_juniver mFragment_juniver;

        public pagerAdapter(android.support.v4.app.FragmentManager fm) {

            super(fm);
            mFragment_cry = new fragment_cry();
            mFragment_sleep = new fragment_sleep();
            mFragment_juniver = new fragment_juniver();
        }

        // 특정 위치에 있는 Fragment를 반환해준다.
        @Override
        public Fragment getItem(int position) {

            switch(position){
                case id_fragment_cry:
                    return mFragment_cry;
                case id_fragment_sleep:
                    return mFragment_sleep;
                case id_fragment_juniver:
                    return mFragment_juniver;
                default:
                    return null;
            }
        }

        // 생성 가능한 페이지 개수를 반환해준다.
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return 3;
        }
    }

}
