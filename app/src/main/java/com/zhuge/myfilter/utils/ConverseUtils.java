package com.zhuge.myfilter.utils;

import android.text.TextUtils;

import com.zhuge.filter.filterbar.menuitemview.entity.MenuItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 区域构造方法
 */
public class ConverseUtils {
    /**
     * 返回排序的列表
     */

    public static List<MenuItem> makeSortFilters(List<MenuItem> moreList) {
        List<MenuItem> sortItemList = new ArrayList<>();
        sortItemList.add(new MenuItem("-1", "默认排序", true));
        sortItemList.addAll(moreList);
        return sortItemList;
    }

    /**
     * 返回更多的列表
     */

    public static List<MenuItem> makeMoreFilters(List<MenuItem> moreList) {
        List<MenuItem> moreItemList = new ArrayList<>();
        moreItemList.addAll(moreList);
        return moreItemList;
    }

    /**
     * 返回户型的列表
     */
    public static List<MenuItem> makeRoomFilters(List<MenuItem> roomList) {
        List<MenuItem> roomItemList = new ArrayList<>();
        roomItemList.add(new MenuItem("-1", "不限", true));
        roomItemList.addAll(roomList);
        return roomItemList;
    }

    /**
     * 返回单价的列表
     */
    public static List<MenuItem> makePriceFilters(List<MenuItem> priceList) {
        List<MenuItem> priceItemList = new ArrayList<>();
        priceItemList.add(new MenuItem("-1", "不限", true));
        priceItemList.addAll(priceList);
        return priceItemList;
    }

    /**
     * 返回附近的列表
     */
    public static List<MenuItem> makeNearByFilters(List<MenuItem> nearByList, String parentId, String parentTitle) {
        List<MenuItem> nearItemList = new ArrayList<>();
        nearItemList.add(new MenuItem("-1", "不限", parentId, parentTitle,true));
        for (MenuItem menuItem : nearByList) {
            MenuItem item = new MenuItem(menuItem.getItemId(), menuItem.getItemTitle(), parentId, parentTitle,false);
            nearItemList.add(item);
        }
        return nearItemList;
    }

    /**
     * 返回带有子列表的二级区域列表
     */
    public static List<MenuItem> makeAreaFilters(List<MenuItem> itemList, String parentId, String parentTitle) {
        List<MenuItem> menuItemList = new ArrayList<>();
        menuItemList.add(new MenuItem("-1", "不限", parentId, parentTitle, true));
        for (int i = 0; i < itemList.size(); i++) {
            MenuItem menuItem = itemList.get(i);
            if (TextUtils.isEmpty(menuItem.getParentId())) {//二级列表
                MenuItem childItem = new MenuItem("-1", "不限", menuItem.getItemId(), menuItem.getItemTitle(), true);
                childItem.setParentItem(menuItem);
                List<MenuItem> childItems = new ArrayList<>();
                childItems.add(childItem);
                menuItem.setChildMenus(childItems);
                menuItem.setParentId(parentId);
                menuItem.setParentTitle(parentTitle);
                menuItemList.add(menuItem);
            } else if (menuItem.getParentId().equals(menuItemList.get(menuItemList.size() - 1).getItemId())) {//三级列表
                MenuItem parentItem = menuItemList.get(menuItemList.size() - 1);
                if(menuItem.isChildMulty() && !parentItem.isChildMulty()) {
                    parentItem.setChildMulty(true);
                }
                menuItem.setParentTitle(parentItem.getItemTitle());
                menuItem.setParentItem(parentItem);
                parentItem.getChildMenus().add(menuItem);
            }
        }
        return menuItemList;
    }

    /**
     * 返回地铁线关联的列表
     */
    public static List<MenuItem> makeLineFilters(List<MenuItem> listStation, List<MenuItem> listSubwayLine, String pId, String parentName) {
        List<MenuItem> menuItemList = new ArrayList<>();
        menuItemList.add(new MenuItem("-1", "不限", pId, parentName, true));

        LinkedHashMap<String, MenuItem> stationMap = new LinkedHashMap<>();//保证顺序
        //先存储station和id的映射关系
        for (int i = 0; i < listStation.size(); i++) {
            MenuItem menuStation = listStation.get(i);
            menuStation.setParentTitle(parentName);
            menuStation.setParentId(pId);
            stationMap.put(menuStation.getItemId(), menuStation);
        }

        //根据地铁线的pid为staion添加child
        for (int i = 0; i < listSubwayLine.size(); i++) {
            MenuItem menuSubway = listSubwayLine.get(i);
            String parentId = menuSubway.getParentId();
            MenuItem stationItem = stationMap.get(parentId);
            if (stationItem == null) {
                break;
            }
            List<MenuItem> childItemList;
            if (stationItem.getChildMenus() == null) {
                childItemList = new ArrayList<>();
                MenuItem station = new MenuItem("-1", "不限", stationItem.getItemId(), stationItem.getItemTitle(), true);
                station.setParentItem(stationItem);
                childItemList.add(station);
            } else {
                childItemList = stationItem.getChildMenus();
            }
            menuSubway.setParentItem(stationItem);
            childItemList.add(menuSubway);
            stationItem.setChildMenus(childItemList);
        }

        Set<Map.Entry<String, MenuItem>> entries = stationMap.entrySet();
        Iterator<Map.Entry<String, MenuItem>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, MenuItem> entry = iterator.next();
            menuItemList.add(entry.getValue());
        }

        return menuItemList;
    }
}
