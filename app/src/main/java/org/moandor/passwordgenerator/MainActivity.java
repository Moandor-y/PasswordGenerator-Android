package org.moandor.passwordgenerator;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

/**
 * Created by Moandor on 10/29/2015.
 */
public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment fragment = getFragmentManager().findFragmentById(R.id.content);
        if (fragment == null) {
            fragment = new MainFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.content, fragment);
            transaction.commit();
        }
    }
}
