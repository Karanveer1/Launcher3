package com.android.launcher3.testing.shared;


import android.os.Parcel;
import android.os.Parcelable;
/**
 * Request object for querying a workspace cell region in Rect.
 */
public class WorkspaceCellCenterRequest implements TestInformationRequest {
    public final int cellX;
    public final int cellY;
    public final int spanX;
    public final int spanY;
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cellX);
        dest.writeInt(cellY);
        dest.writeInt(spanX);
        dest.writeInt(spanY);
    }
    public static final Parcelable.Creator<WorkspaceCellCenterRequest> CREATOR =
            new Parcelable.Creator<WorkspaceCellCenterRequest>() {
                @Override
                public WorkspaceCellCenterRequest createFromParcel(Parcel source) {
                    return new WorkspaceCellCenterRequest(source);
                }
                @Override
                public WorkspaceCellCenterRequest[] newArray(int size) {
                    return new WorkspaceCellCenterRequest[size];
                }
            };
    private WorkspaceCellCenterRequest(int cellX, int cellY, int spanX, int spanY) {
        this.cellX = cellX;
        this.cellY = cellY;
        this.spanX = spanX;
        this.spanY = spanY;
    }
    private WorkspaceCellCenterRequest(Parcel in) {
        this(in.readInt(), in.readInt(), in.readInt(), in.readInt());
    }
    /**
     * Create a builder for WorkspaceCellRectRequest.
     *
     * @return WorkspaceCellRectRequest builder.
     */
    public static WorkspaceCellCenterRequest.Builder builder() {
        return new WorkspaceCellCenterRequest.Builder();
    }
    @Override
    public String getRequestName() {
        return TestProtocol.REQUEST_WORKSPACE_CELL_CENTER;
    }
    /**
     * WorkspaceCellRectRequest Builder.
     */
    public static final class Builder {
        private int mCellX;
        private int mCellY;
        private int mSpanX;
        private int mSpanY;
        private Builder() {
            this.mCellX = 0;
            this.mCellY = 0;
            this.mSpanX = 1;
            this.mSpanY = 1;
        }
        /**
         * Set X coordinate of upper left corner expressed as a cell position
         */
        public WorkspaceCellCenterRequest.Builder setCellX(int x) {
            this.mCellX = x;
            return this;
        }
        /**
         * Set Y coordinate of upper left corner expressed as a cell position
         */
        public WorkspaceCellCenterRequest.Builder setCellY(int y) {
            this.mCellY = y;
            return this;
        }
        /**
         * Set span Width in cells
         */
        public WorkspaceCellCenterRequest.Builder setSpanX(int x) {
            this.mSpanX = x;
            return this;
        }
        /**
         * Set span Height in cells
         */
        public WorkspaceCellCenterRequest.Builder setSpanY(int y) {
            this.mSpanY = y;
            return this;
        }
        /**
         * build the WorkspaceCellRectRequest.
         */
        public WorkspaceCellCenterRequest build() {
            return new WorkspaceCellCenterRequest(mCellX, mCellY, mSpanX, mSpanY);
        }
    }
}
