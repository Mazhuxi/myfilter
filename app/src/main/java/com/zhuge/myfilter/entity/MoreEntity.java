package com.zhuge.myfilter.entity;

import com.zhuge.filter.filterbar.menuitemview.entity.MenuItem;

import java.util.List;

/**
 * 更多
 */
public class MoreEntity {
    private boolean isMulty;
    private List<MenuItem> moreList;

    public boolean isMulty() {
        return isMulty;
    }

    public void setMulty(boolean multy) {
        isMulty = multy;
    }

    public List<MenuItem> getMoreList() {
        return moreList;
    }

    public void setMoreList(List<MenuItem> moreList) {
        this.moreList = moreList;
    }
}
