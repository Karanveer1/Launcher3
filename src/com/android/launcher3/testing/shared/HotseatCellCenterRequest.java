package com.android.launcher3.testing.shared;


import android.os.Parcel;
import android.os.Parcelable;
/**
 * Request object for querying a hotseat cell region in Rect.
 */
public class HotseatCellCenterRequest implements TestInformationRequest {
    public final int cellInd;
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cellInd);
    }
    @Override
    public String getRequestName() {
        return TestProtocol.REQUEST_HOTSEAT_CELL_CENTER;
    }
    public static final Parcelable.Creator<HotseatCellCenterRequest> CREATOR =
            new Parcelable.Creator<HotseatCellCenterRequest>() {
                @Override
                public HotseatCellCenterRequest createFromParcel(Parcel source) {
                    return new HotseatCellCenterRequest(source);
                }
                @Override
                public HotseatCellCenterRequest[] newArray(int size) {
                    return new HotseatCellCenterRequest[size];
                }
            };
    private HotseatCellCenterRequest(int cellInd) {
        this.cellInd = cellInd;
    }
    private HotseatCellCenterRequest(Parcel in) {
        this(in.readInt());
    }
    /**
     * Create a builder for HotseatCellCenterRequest.
     *
     * @return HotseatCellCenterRequest builder.
     */
    public static HotseatCellCenterRequest.Builder builder() {
        return new HotseatCellCenterRequest.Builder();
    }
    /**
     * HotseatCellCenterRequest Builder.
     */
    public static final class Builder {
        private int mCellInd;
        private Builder() {
            mCellInd = 0;
        }
        /**
         * Set the index of hotseat cells.
         */
        public HotseatCellCenterRequest.Builder setCellInd(int i) {
            this.mCellInd = i;
            return this;
        }
        /**
         * build the HotseatCellCenterRequest.
         */
        public HotseatCellCenterRequest build() {
            return new HotseatCellCenterRequest(mCellInd);
        }
    }
}