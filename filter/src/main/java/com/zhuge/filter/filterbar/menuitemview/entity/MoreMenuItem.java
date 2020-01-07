package com.zhuge.filter.filterbar.menuitemview.entity;

import java.util.List;

/**
 * 例如 租房公寓展开的二级子列表
 */
public class MoreMenuItem {
    private boolean isSecondChildMulty;//第二级子item是否多选  例如:品牌公寓下面的第二级子列表

    private List<MenuItem> secondMenus;//第二级子item 目前用于租房更多

    private List<MenuItem> moreMenus;//更多的子Item 目前用于租房更多

    public List<MenuItem> getMoreMenus() {
        return moreMenus;
    }

    public void setMoreMenus(List<MenuItem> moreMenus) {
        this.moreMenus = moreMenus;
    }

    public boolean isSecondChildMulty() {
        return isSecondChildMulty;
    }

    public void setSecondChildMulty(boolean secondChildMulty) {
        isSecondChildMulty = secondChildMulty;
    }

    public List<MenuItem> getSecondMenus() {
        return secondMenus;
    }

    public void setSecondMenus(List<MenuItem> secondMenus) {
        this.secondMenus = secondMenus;
    }

    private boolean isExpend = false;//是否二级ChildItem展开

    public boolean isExpend() {
        return isExpend;
    }

    public void setExpend(boolean expend) {
        isExpend = expend;
    }
}
