package org.moandor.passwordgenerator;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Moandor on 10/29/2015.
 */
public class MainActivity extends BaseActivity {
    private static final String TAG_SALT_DIALOG = "salt_dialog";

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.salt_settings: {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag(TAG_SALT_DIALOG);
                if (prev != null) {
                    transaction.remove(prev);
                }
                transaction.commit();
                SaltDialogFragment dialogFragment = new SaltDialogFragment();
                dialogFragment.show(getFragmentManager(), TAG_SALT_DIALOG);
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }
}
