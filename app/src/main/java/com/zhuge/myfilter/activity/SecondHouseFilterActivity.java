package com.zhuge.myfilter.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuge.filter.FilterBar;
import com.zhuge.filter.filterbar.menuitemview.entity.MenuItem;
import com.zhuge.filter.filterbar.menuitemview.listener.OnMenuItemClickListener;
import com.zhuge.myfilter.R;
import com.zhuge.myfilter.TextData;
import com.zhuge.myfilter.entity.SubwayEntity;
import com.zhuge.myfilter.filterwrapper.NewHouseMenuWrapper;
import com.zhuge.myfilter.filterwrapper.SecondHouseMenuWrapper;
import com.zhuge.myfilter.utils.ConverseUtils;

import java.util.HashMap;
import java.util.List;

/**
 * 二手房筛选
 */
public class SecondHouseFilterActivity extends Activity implements OnMenuItemClickListener {
    private FilterBar filterbar;
    private SecondHouseMenuWrapper menuWrapper;
    @SuppressLint("UseSparseArrays")
    private HashMap<Integer, List<MenuItem>> mapFilter = new HashMap<>();

    private FrameLayout flFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);
        filterbar = findViewById(R.id.filterbar);
        flFilter = findViewById(R.id.fl_filter);
        TextView tvHouse = findViewById(R.id.tv_house);
        tvHouse.setText("二手房列表");
        menuWrapper = new SecondHouseMenuWrapper(this);
        menuWrapper.setTabSelectListener(this);
        filterbar.setTabTexts(new String[]{"区域", "总价", "户型", "更多"})
                .setFilterFrame(flFilter).setMenuWrapper(menuWrapper)
                .setTabLayout();
        //创建假数据
        getAreaData();
        getLineData();
        getNearByData();
        getPriceData();
        getRoomData();
        getMoreData();
    }

    /**
     * 更多数据
     */

    private void getMoreData() {
        List<MenuItem> listMore = TextData.getInstance().getSecondMoreData();
        List<MenuItem> moreList = ConverseUtils.makeMoreFilters(listMore);
        menuWrapper.setData(SecondHouseMenuWrapper.TABMORE, moreList);
    }

    /**
     * 户型数据
     */

    private void getRoomData() {
        List<MenuItem> listRoom = TextData.getInstance().getSecondRoomData();
        List<MenuItem> roomList = ConverseUtils.makeRoomFilters(listRoom);
        if (roomList.get(1).isChildMulty()) {
            menuWrapper.setMultiPly(SecondHouseMenuWrapper.TABROOM);
        }
        menuWrapper.setData(SecondHouseMenuWrapper.TABROOM, roomList);
    }

    /**
     * 总价数据
     */

    private void getPriceData() {
        List<MenuItem> listPrice = TextData.getInstance().getSecondPriceData();
        List<MenuItem> priceList = ConverseUtils.makePriceFilters(listPrice);
        if (priceList.get(1).isChildMulty()) {
            menuWrapper.setMultiPly(SecondHouseMenuWrapper.TABPRICE);
        }
        menuWrapper.setData(SecondHouseMenuWrapper.TABPRICE, priceList);
    }

    /**
     * 区域数据
     */

    private void getAreaData() {
        List<MenuItem> listArea = TextData.getInstance().getSecondAreaData();
        List<MenuItem> areaList = ConverseUtils.makeAreaFilters(listArea, String.valueOf(SecondHouseMenuWrapper.AREA_INDEX), "区域");
        if (areaList.get(1).isChildMulty()) {
            menuWrapper.setMultiPly(SecondHouseMenuWrapper.TABAREA);
        }
        menuWrapper.setData(SecondHouseMenuWrapper.TABAREA, areaList, SecondHouseMenuWrapper.AREA_INDEX, "区域");
    }

    /**
     * 附近数据
     */


    private void getNearByData() {
        List<MenuItem> listNear = TextData.getInstance().getNearByData();
        List<MenuItem> nearList = ConverseUtils.makeNearByFilters(listNear, String.valueOf(SecondHouseMenuWrapper.NEARBY_INDEX), "附近");
        menuWrapper.setData(SecondHouseMenuWrapper.TABAREA, nearList, SecondHouseMenuWrapper.NEARBY_INDEX, "附近");
    }

    /**
     * 地铁数据
     */

    private void getLineData() {
        SubwayEntity subwayEntity = TextData.getInstance().getSecondLineData();
        List<MenuItem> lineList = ConverseUtils.makeLineFilters(subwayEntity.getListStation(), subwayEntity.getListSubwayLine(), String.valueOf(SecondHouseMenuWrapper.SUBWAY_INDEX), "地铁");
        if(lineList.get(1).isChildMulty()) {
            menuWrapper.setMultiPly(SecondHouseMenuWrapper.TABAREA);
        }
        menuWrapper.setData(SecondHouseMenuWrapper.TABAREA, lineList, SecondHouseMenuWrapper.SUBWAY_INDEX, "地铁");
    }

    @Override
    public void onSelectMenuClick(List<MenuItem> seleMenus, int tabIndex) {
        filterbar.closeMenu();
        mapFilter.put(tabIndex, seleMenus);
        filterbar.setMapFilter(mapFilter, tabIndex);
        String str = "";
        for (MenuItem seleMenu : seleMenus) {
            str = str + seleMenu.getItemTitle() + ",";
        }
        Toast.makeText(SecondHouseFilterActivity.this, "点击了" + str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if(flFilter.isShown()) {
            filterbar.closeMenu();
        } else {
            super.onBackPressed();
        }
    }
}
