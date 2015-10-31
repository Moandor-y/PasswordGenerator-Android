package org.moandor.passwordgenerator;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

/**
 * Created by Moandor on 10/31/2015.
 */
public class MainFragment extends Fragment {
    private TextView mPasswordView;

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
    }
}
