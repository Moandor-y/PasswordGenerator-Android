package org.moandor.passwordgenerator;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

/**
 * Created by Moandor on 10/29/2015.
 */
public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
