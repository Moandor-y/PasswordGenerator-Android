package org.moandor.passwordgenerator;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Moandor on 10/31/2015.
 */
public class MainFragment extends Fragment {
    private static final String TAG_SALT_DIALOG = "salt_dialog";

    private TextView mPasswordView;
    private byte[] mSaltBytes = new byte[GlobalContext.SALT_BYTE_COUNT];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPasswordView = (TextView) view.findViewById(R.id.password);
        mPasswordView.setTransformationMethod(new PasswordTransformationMethod());
        CheckBox showPasswordCheckBox = (CheckBox) view.findViewById(R.id.show_password);
        showPasswordCheckBox.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mPasswordView.setTransformationMethod(null);
                } else {
                    mPasswordView.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });
        EditText domainNameEditText = (EditText) view.findViewById(R.id.domain_name);
        domainNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                updateHash();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.activity_main, menu);
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
                dialogFragment.setFinishListener(new SaltDialogFragment.OnFinishListener() {
                    @Override
                    public void onFinish(@Nullable byte[] salt) {
                        mSaltBytes = salt;
                        updateHash();
                    }
                });
                dialogFragment.show(getFragmentManager(), TAG_SALT_DIALOG);
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    private void updateHash() {
        //TODO
    }
}
