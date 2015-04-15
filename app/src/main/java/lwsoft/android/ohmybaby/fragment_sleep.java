package lwsoft.android.ohmybaby;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by gilbert on 2015-04-15.
 */
public class fragment_sleep extends Fragment {
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
        // Android에서 제공하는 string 문자열 하나를 출력 가능한 layout으로 어댑터 생성
        mAdapter = new ArrayAdapter<String>( v.getContext(),  android.R.layout.simple_list_item_1);

        // Xml에서 추가한 ListView 연결
        mListView = (ListView) v.findViewById(R.id.listView_sleepsong);

        // ListView에 어댑터 연결
        mListView.setAdapter(mAdapter);

        // ListView 아이템 터치 시 이벤트 추가
        mListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // 이벤트 발생 시 해당 아이템 위치의 텍스트를 출력
                MainActivity.inst.stop_asset( lastSong );
                MainActivity.inst.player_asset( mSongList.get( arg2 ).filePath, false );
                lastSong = mSongList.get( arg2 ).filePath;
                Toast.makeText(v.getContext(), mSongList.get( arg2 ).name, Toast.LENGTH_SHORT).show();
            }
        });

        // ListView에 아이템 추가
        mSongList.add(new songdata("Silent Night Holey Night","lullaby_audio/10_MusicBox_SilentNightHolyNight.mp3"   ));
        mSongList.add(new songdata("Mary Had A Little Lamb","lullaby_audio/11_MusicBox_MaryHadALittleLamb.mp3"   ));
        mSongList.add(new songdata("O Tannen baum","lullaby_audio/12_MusicBox_OTannenbaum.mp3"   ));
        mSongList.add(new songdata("Beautiful Dreamer","lullaby_audio/13_MusicBox_BeautifulDreamer.mp3"   ));
        mSongList.add(new songdata("GreenBottles","lullaby_audio/14_MusicBox_GreenBottles.mp3"   ));

        for( int n=0; n< mSongList.size(); n++){
            mAdapter.add(  mSongList.get(n).name );
        }

        return v;
    }



}
