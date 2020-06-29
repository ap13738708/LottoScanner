package com.example.scannerproject;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * Created by P on 2/15/2018.
 */

public class NumDiffCallback extends DiffUtil.Callback {

    private final List<ArrangeNum> mOldEmployeeList;
    private final List<ArrangeNum> mNewEmployeeList;

    public NumDiffCallback(List<ArrangeNum> oldEmployeeList, List<ArrangeNum> newEmployeeList) {
        this.mOldEmployeeList = oldEmployeeList;
        this.mNewEmployeeList = newEmployeeList;
    }

    @Override
    public int getOldListSize() {
        return mOldEmployeeList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewEmployeeList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldEmployeeList.get(oldItemPosition).getLottogroup() == mNewEmployeeList.get(
                newItemPosition).getLottogroup();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final ArrangeNum oldNum = mOldEmployeeList.get(oldItemPosition);
        final ArrangeNum newNum = mNewEmployeeList.get(newItemPosition);

        return oldNum.all.equals(newNum.all);
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
