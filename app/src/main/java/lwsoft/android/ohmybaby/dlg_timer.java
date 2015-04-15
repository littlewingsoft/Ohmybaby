package lwsoft.android.ohmybaby;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by gilbert on 2015-04-15.
 */
public class dlg_timer extends DialogFragment {

    public static DialogFragment newInstance() {
        dlg_timer f = new dlg_timer();

        //Bundle args = new Bundle();
        //args.putInt("num", num);
        //f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dlg_timer, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE );
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //View tv = v.findViewById(R.id.text);
        //((TextView)tv).setText("Dialog #" + mNum + ": using style " + getNameForNum(mNum));
        //final Drawable d = new ColorDrawable(Color.BLACK);
        //d.setAlpha(130);

        //getDialog().getWindow().setBackgroundDrawable(d);

        ImageButton iv =(ImageButton )v.findViewById( R.id.dlg_button_unlimit);
        iv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ImageButton iv =(ImageButton ) MainActivity.inst.mPagerAdapter.mFragment_cry.getView().findViewById( R.id.imageButton_timer );
                iv.setImageResource( R.drawable.selector_dlg_button_unlimit );
                MainActivity.inst.timerStop();
                getDialog().dismiss();
            }
        });

        iv =(ImageButton )v.findViewById( R.id.dlg_button_10min );
        iv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ImageButton iv =(ImageButton ) MainActivity.inst.mPagerAdapter.mFragment_cry.getView().findViewById( R.id.imageButton_timer );
                iv.setImageResource( R.drawable.selector_dlg_button_10min);
                MainActivity.inst.timerStart(1000* 60 * 10 );
                getDialog().dismiss();
            }
        });

        iv =(ImageButton )v.findViewById( R.id.dlg_button_20min  );
        iv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ImageButton iv =(ImageButton ) MainActivity.inst.mPagerAdapter.mFragment_cry.getView().findViewById( R.id.imageButton_timer );
                iv.setImageResource( R.drawable.selector_dlg_button_20min);
                MainActivity.inst.timerStart(1000* 60 * 20 );
                getDialog().dismiss();
            }
        });

        iv =(ImageButton )v.findViewById( R.id.dlg_button_30min  );
        iv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ImageButton iv =(ImageButton ) MainActivity.inst.mPagerAdapter.mFragment_cry.getView().findViewById( R.id.imageButton_timer );
                iv.setImageResource( R.drawable.selector_dlg_button_30min);
                MainActivity.inst.timerStart(1000* 60 * 30 );
                getDialog().dismiss();
            }
        });

        iv =(ImageButton )v.findViewById( R.id.dlg_button_60min  );
        iv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ImageButton iv =(ImageButton ) MainActivity.inst.mPagerAdapter.mFragment_cry.getView().findViewById( R.id.imageButton_timer );
                iv.setImageResource( R.drawable.selector_dlg_button_60min);
                MainActivity.inst.timerStart(1000* 60 * 60 );
                getDialog().dismiss();
            }
        });

        iv =(ImageButton )v.findViewById( R.id.dlg_button_120min  );
        iv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ImageButton iv =(ImageButton ) MainActivity.inst.mPagerAdapter.mFragment_cry.getView().findViewById( R.id.imageButton_timer );
                iv.setImageResource( R.drawable.selector_dlg_button_120min);
                MainActivity.inst.timerStart(1000* 60 * 120 );
                getDialog().dismiss();
            }
        });

        return v;
    }


}
