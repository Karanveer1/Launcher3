package com.android.launcher3.testing.shared;

import android.os.Parcelable;
/**
 * A Request sent to TestInformationHandler can implement this interface to carry more information.
 */
public interface TestInformationRequest extends Parcelable {
    /**
     * The name for handler to dispatch request.
     */
    String getRequestName();
}