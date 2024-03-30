package com.android.launcher3.util;


/**
 * Extension of closeable which does not throw an exception
 */
public interface SafeCloseable extends AutoCloseable {
    @Override
    void close();
}