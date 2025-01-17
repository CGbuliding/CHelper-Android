package yancey.chelper.android.completion.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;

import java.util.Objects;
import java.util.function.Consumer;

import yancey.chelper.R;
import yancey.chelper.android.common.dialog.ChoosingDialog;
import yancey.chelper.android.common.view.CustomView;
import yancey.chelper.android.completion.data.Settings;

@SuppressLint("ViewConstructor")
public class SettingsView extends CustomView {

    public SettingsView(@NonNull Context context, Consumer<CustomView> openView) {
        super(context, openView, R.layout.layout_settings);
    }

    @Override
    public void onCreateView(Context context, View view) {
        TextView tv_currentCPack = view.findViewById(R.id.tv_current_cpack);
        RelativeLayout btn_chooseCpack = view.findViewById(R.id.btn_choose_cpack);
        SwitchCompat isCheckingBySelection = view.findViewById(R.id.cb_is_checking_by_selection);
        SwitchCompat isHideWindowWhenCopying = view.findViewById(R.id.cb_is_hide_window_when_copying);
        SwitchCompat isSavingWhenPausing = view.findViewById(R.id.cb_is_saving_when_pausing);
        SwitchCompat isCrowed = view.findViewById(R.id.cb_is_crowed);
        Settings settings = Settings.getInstance(context);
        String[] showStrings = {
                "正式版-原版-1.20.80.05",
                "正式版-实验性玩法-1.20.80.05",
                "测试版-原版-1.21.0.23",
                "测试版-实验性玩法-1.21.0.23",
        };
        String[] cpackPaths = {
                "release-vanilla-1.20.80.05.cpack",
                "release-experiment-1.20.80.05.cpack",
                "beta-vanilla-1.21.0.23.cpack",
                "beta-experiment-1.21.0.23.cpack"
        };
        String cpackPath = settings.getCpackPath(context);
        String prefix = Settings.DIR_NAME + "/";
        if (cpackPath.startsWith(prefix)) {
            cpackPath = cpackPath.substring(prefix.length());
        }
        for (int i = 0; i < cpackPaths.length; i++) {
            if (Objects.equals(cpackPath, cpackPaths[i])) {
                tv_currentCPack.setText(context.getString(R.string.current_cpack, showStrings[i]));
                break;
            }
        }
        btn_chooseCpack.setOnClickListener(v -> new ChoosingDialog(context, showStrings, which -> {
            String cpackPath1 = cpackPaths[which];
            tv_currentCPack.setText(context.getString(R.string.current_cpack, showStrings[which]));
            settings.setCpackPath(cpackPath1);
        }).show());
        isCheckingBySelection.setChecked(settings.isCheckingBySelection);
        isCheckingBySelection.setOnCheckedChangeListener((buttonView, isChecked) -> settings.isCheckingBySelection = isChecked);
        isHideWindowWhenCopying.setChecked(settings.isHideWindowWhenCopying);
        isHideWindowWhenCopying.setOnCheckedChangeListener((buttonView, isChecked) -> settings.isHideWindowWhenCopying = isChecked);
        isSavingWhenPausing.setChecked(settings.isSavingWhenPausing);
        isSavingWhenPausing.setOnCheckedChangeListener((buttonView, isChecked) -> settings.isSavingWhenPausing = isChecked);
        isCrowed.setChecked(settings.isCrowed);
        isCrowed.setOnCheckedChangeListener((buttonView, isChecked) -> settings.isCrowed = isChecked);
    }

    @Override
    public void onPause() {
        super.onPause();
        Settings.getInstance(getContext()).save(getContext());
    }


}
