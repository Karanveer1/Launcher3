package com.mtk.ext.icon;

import android.content.Context;
import android.util.DisplayMetrics;

import androidx.preference.Preference;

import com.android.launcher3.DeviceProfile;
import com.android.launcher3.InvariantDeviceProfile;
import com.android.launcher3.R;
import com.android.launcher3.Utilities;
import com.mtk.ext.BaseController;
import com.mtk.ext.UtilitiesExt;

import android.util.Log;

import static com.mtk.ext.LauncherSettingsExtension.PREF_ICON_LABEL_KEY;

public class IconLabelController extends BaseController implements
        Preference.OnPreferenceChangeListener {
    public final static int MIN_ICON_LABEL_LINE = 1;
    private final static int MAX_ICON_LABEL_LINE = 2;

    private final static float MIN_ICON_TEXT_SIZE = 8.0f;

    private boolean mEnable;

    public IconLabelController(Context context) {
        super(context);
        mEnable = Utilities.getPrefs(context).getBoolean(PREF_ICON_LABEL_KEY,
                context.getResources().getBoolean(R.bool.enable_icon_label_show_double_lines));
    }

    public int getIconLabelLine() {
        return mEnable ? MAX_ICON_LABEL_LINE : MIN_ICON_LABEL_LINE;
    }

    public void updateIconSize(DeviceProfile grid, DisplayMetrics dm, boolean isMultiWindowMode) {
        if (grid.maxIconLabelLines == 1 || isMultiWindowMode) {
            return;
        }

        int cellHeightPxWithoutPadding = grid.iconSizePx
                + Utilities.calculateTextHeight(grid.iconTextSizePx) * grid.maxIconLabelLines;
        grid.cellHeightPx = cellHeightPxWithoutPadding + grid.iconDrawablePaddingPx;
        int oldTextRectHeight = grid.cellHeightPx - grid.iconSizePx;
        int cellYPadding = (grid.getCellSize().y - grid.cellHeightPx) / 2;

        // If there is not enough space to display the icon label,
        // need to adjust iconDrawablePaddingPx, iconTextSizePx and iconSizePx.
        if (grid.iconDrawablePaddingPx > cellYPadding
                && !grid.isVerticalBarLayout()
                && !grid.isMultiWindowMode) {
            if (cellYPadding > 0) {
                grid.cellHeightPx -= (grid.iconDrawablePaddingPx - cellYPadding);
                grid.iconDrawablePaddingPx = cellYPadding;
            } else {
                int adjustPadding = (grid.getCellSize().y - cellHeightPxWithoutPadding) / 2;
                if (adjustPadding > 0) {
                    grid.iconDrawablePaddingPx = adjustPadding / 2;
                    grid.cellHeightPx = cellHeightPxWithoutPadding + grid.iconDrawablePaddingPx;
                } else {
                    grid.cellHeightPx = cellHeightPxWithoutPadding;
                    grid.iconDrawablePaddingPx = 0;
                    int newTextHeight = grid.cellHeightPx - grid.iconSizePx;
                    float iconTextScale = (float) newTextHeight / oldTextRectHeight;
                    grid.iconTextSizePx = (int) (grid.iconTextSizePx * iconTextScale);

                    verifyIconTextSize(grid, dm);
                }
            }
        }
    }

    public void updateFolderCellSize(DeviceProfile grid) {
        if (getIconLabelLine() > 1) {
            grid.folderChildTextSizePx = Math.min(grid.iconTextSizePx, grid.folderChildTextSizePx);
            int textHeight = Utilities.calculateTextHeight(grid.folderChildTextSizePx) * getIconLabelLine();
            int minCellPaddingY = mContext.getResources()
                    .getDimensionPixelSize(R.dimen.min_folder_cell_y_padding);

            grid.folderCellHeightPx = grid.folderChildIconSizePx + minCellPaddingY + textHeight;
            grid.folderChildDrawablePaddingPx = Math.max(0,
                    (grid.folderCellHeightPx - grid.folderChildIconSizePx - textHeight) / 3);
        }
    }

    private void verifyIconTextSize(DeviceProfile grid, DisplayMetrics dm) {
        int minIconTextSizePx = Utilities.pxFromSp(MIN_ICON_TEXT_SIZE, dm);
        if (grid.iconTextSizePx < minIconTextSizePx) {
            int oldIconHeight = calculateIconHeight(grid, grid.iconTextSizePx);
            int newIconHeight = calculateIconHeight(grid, minIconTextSizePx);
            float iconScale = (float) newIconHeight / oldIconHeight;
            grid.iconSizePx = (int) (grid.iconSizePx * iconScale);
            grid.iconTextSizePx = minIconTextSizePx;
        }
    }

    private int calculateIconHeight(DeviceProfile grid, int iconTextSizePx) {
        return grid.cellHeightPx - grid.iconDrawablePaddingPx
                - Utilities.calculateTextHeight(iconTextSizePx) * getIconLabelLine();
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        boolean enable = (boolean) newValue;
        if (mEnable != enable) {
            mEnable = enable;
            InvariantDeviceProfile.INSTANCE.get(mContext).applyConfigChanged(mContext);
        }
        return true;
    }
}
