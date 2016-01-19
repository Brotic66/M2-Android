package brotic.findmyfriends.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import brotic.findmyfriends.Event.ConfigClickListener;
import brotic.findmyfriends.R;
import brotic.findmyfriends.Security.MyActivity;

public class ConfigActivity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("");

        this.findViewById(R.id.changePhoto).setOnClickListener(new ConfigClickListener());
        this.findViewById(R.id.changeMdp).setOnClickListener(new ConfigClickListener());
        this.findViewById(R.id.deco).setOnClickListener(new ConfigClickListener());
    }
}
