package org.moandor.passwordgenerator;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
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

import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Moandor on 10/31/2015.
 */
public class MainFragment extends Fragment {
    private static final String TAG_SALT_DIALOG = "salt_dialog";

    private TextView mPasswordView;
    private EditText mDomainNameEditText;
    @NonNull private byte[] mSaltBytes = new byte[GlobalContext.SALT_BYTE_COUNT];

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
    public void onViewCreated(final View view, Bundle savedInstanceState) {
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
        mDomainNameEditText = (EditText) view.findViewById(R.id.domain_name);
        mDomainNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                updateHash();
            }
        });

        Utilities.setEnabledForAllViews(view, false);
        ReadSaltTask task = new ReadSaltTask();
        task.setOnFinishListener(new ReadSaltTask.OnFinishListener() {
            @Override
            public void onFinish(boolean succeeded, byte[] salt, @Nullable Exception exception) {
                Utilities.setEnabledForAllViews(view, true);
                if (succeeded) {
                    mSaltBytes = salt;
                } else {
                    if (exception instanceof FileNotFoundException) {
                        MainFragment.this.launchSaltDialog();
                    } else {
                        Utilities.showToast(R.string.read_file_failure);
                    }
                }
            }
        });
        Utilities.executeAsyncTask(task);
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
                launchSaltDialog();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    private void launchSaltDialog() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag(TAG_SALT_DIALOG);
        if (prev != null) {
            transaction.remove(prev);
        }
        transaction.commit();
        SaltDialogFragment dialogFragment = new SaltDialogFragment();
        dialogFragment.setFinishListener(new SaltDialogFragment.OnFinishListener() {
            @Override
            public void onFinish(@NonNull byte[] salt) {
                mSaltBytes = salt;
                final View view = getView();
                if (view == null) {
                    throw new RuntimeException("Salt dialog finished before view created");
                }
                Utilities.setEnabledForAllViews(view, false);
                WriteSaltTask task = new WriteSaltTask();
                task.setOnFinishListener(new WriteSaltTask.OnFinishListener() {
                    @Override
                    public void onFinish(boolean succeeded) {
                        Utilities.setEnabledForAllViews(view, true);
                        if (!succeeded) {
                            Utilities.showToast(R.string.write_file_failure);
                        }
                    }
                });
                Utilities.executeAsyncTask(task, mSaltBytes);
                updateHash();
            }
        });
        dialogFragment.show(getFragmentManager(), TAG_SALT_DIALOG);
    }

    private void updateHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            String domainName = mDomainNameEditText.getText().toString();
            digest.update(domainName.getBytes(Charset.forName("UTF-8")));
            digest.update(mSaltBytes);
            byte[] hash = digest.digest();
            String base64 = Base64.encodeToString(hash, Base64.DEFAULT);
            mPasswordView.setText(base64.substring(0, 14));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
