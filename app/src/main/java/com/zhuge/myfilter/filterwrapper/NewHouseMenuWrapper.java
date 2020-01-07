package com.zhuge.myfilter.filterwrapper;

import android.content.Context;

import com.zhuge.filter.filterbar.filterwrapper.BaseMenuWrapper;
import com.zhuge.filter.filterbar.menuitemview.AreaMenuItemView;
import com.zhuge.filter.filterbar.menuitemview.ListMenuItemView;
import com.zhuge.filter.filterbar.menuitemview.MoreMenuItemView;
import com.zhuge.myfilter.R;

/**
 * 新房包装类(对外开放)
 * 需要展示 区域 单价 户型 更多 排序
 */
public class NewHouseMenuWrapper extends BaseMenuWrapper {
    public static final int AREA_INDEX = 0;//区域
    public static final int SUBWAY_INDEX = 1;//地铁

    public static final int TABAREA = 0;//区域
    public static final int TABPRICE = 1;//单价
    public static final int TABROOM = 2;//户型
    public static final int TABMORE = 3;//更多
    public static final int TABSORT = 4;//排序

    public NewHouseMenuWrapper(Context context) {
        super(context);
        menuItems.add(new AreaMenuItemView(context, R.layout.layout_area, TABAREA));
        ListMenuItemView priceMenuItem = new ListMenuItemView(context, R.layout.layout_price, TABPRICE);
        priceMenuItem = priceMenuItem.setShowBottom(true).setMinHint("最低价").setMaxHint("最高价").setUnit("元/m²").get();
        menuItems.add(priceMenuItem);//单价
        menuItems.add(new ListMenuItemView(context, R.layout.layout_room, TABROOM).get());//户型
        menuItems.add(new MoreMenuItemView(context, R.layout.layout_more, TABMORE));//更多
        menuItems.add(new ListMenuItemView(context, R.layout.layout_room, TABSORT).get());//排序
    }
}
