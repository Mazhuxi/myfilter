package com.zhuge.filter.filterbar.menuitemview.entity;

import java.util.List;

/**
 * 菜单实体
 */
public class MenuItem {
    public MenuItem(String itemId, String itemTitle,boolean isChecked) {
        this.itemTitle = itemTitle;
        this.itemId = itemId;
        this.isChecked = isChecked;
    }

    public MenuItem(String itemId, String itemTitle, String parentId, String parentTitle, boolean isChecked) {
        this.itemTitle = itemTitle;
        this.parentTitle = parentTitle;
        this.parentId = parentId;
        this.itemId = itemId;
        this.isChecked = isChecked;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getParentTitle() {
        return parentTitle;
    }

    public void setParentTitle(String parentTitle) {
        this.parentTitle = parentTitle;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private String itemTitle;
    private String parentTitle;
    private String parentId;
    private String itemId;
    private boolean isChecked;

    private List<MenuItem> childMenus;

    public List<MenuItem> getChildMenus() {
        return childMenus;
    }

    public void setChildMenus(List<MenuItem> childMenus) {
        this.childMenus = childMenus;
    }

    private MenuItem parentItem;

    public MenuItem getParentItem() {
        return parentItem;
    }

    public void setParentItem(MenuItem parentItem) {
        this.parentItem = parentItem;
    }

    private boolean isChildMulty;//是否子item多选 例如 第一级下面的子列表 ---来源


    public boolean isChildMulty() {
        return isChildMulty;
    }

    public void setChildMulty(boolean childMulty) {
        isChildMulty = childMulty;
    }

    private MoreMenuItem moreMenuItem;

    public MoreMenuItem getMoreMenuItem() {
        return moreMenuItem;
    }

    public void setMoreMenuItem(MoreMenuItem moreMenuItem) {
        this.moreMenuItem = moreMenuItem;
    }
}
