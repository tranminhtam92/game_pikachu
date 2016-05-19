package com.tam.gamepikachu;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
/**
* Hiện thị giao diện trợ giúp, hướng dẫn cách ăn
* @author VAN GIOI
*
*/
public class Help extends MyApp implements OnClickListener {
 
    Button back;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
     
        back = (Button)findViewById(R.id.back);
        back.setOnClickListener(this);
    }
 
    @Override
    public void onClick(View v) {
        //Menu.mSound.playClick();
     
        switch (v.getId()) {
        case R.id.back:
            this.finish();
            break;
 
        default:
            break;
        }
     
    }
 
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Menu.mSound.playClick();
        this.finish();
    }
}
