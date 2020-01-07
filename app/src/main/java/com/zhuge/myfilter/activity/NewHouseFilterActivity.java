package com.zhuge.myfilter.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.zhuge.myfilter.R;
import com.zhuge.myfilter.TextData;
import com.zhuge.myfilter.entity.SubwayEntity;
import com.zhuge.filter.FilterBar;
import com.zhuge.myfilter.filterwrapper.NewHouseMenuWrapper;
import com.zhuge.filter.filterbar.menuitemview.entity.MenuItem;
import com.zhuge.filter.filterbar.menuitemview.listener.OnMenuItemClickListener;
import com.zhuge.myfilter.utils.ConverseUtils;

import java.util.HashMap;
import java.util.List;

/**
 * 新房筛选
 */
public class NewHouseFilterActivity extends AppCompatActivity implements OnMenuItemClickListener {
    private FilterBar filterbar;
    private NewHouseMenuWrapper menuWrapper;
    private FrameLayout flFilter;
    @SuppressLint("UseSparseArrays")
    private HashMap<Integer, List<MenuItem>> mapFilter = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);
        filterbar = findViewById(R.id.filterbar);
        flFilter = findViewById(R.id.fl_filter);
        TextView tvHouse = findViewById(R.id.tv_house);
        tvHouse.setText("新房列表");
        menuWrapper = new NewHouseMenuWrapper(this);
        menuWrapper.setTabSelectListener(this);
        filterbar.setTabTexts(new String[]{"区域", "单价", "户型", "更多"})
                .setFilterFrame(flFilter).setMenuWrapper(menuWrapper)
                .setTabLayout();
        //创建假数据
        getAreaData();
        getLineData();
        getPriceData();
        getRoomData();
        getMoreData();
        getSortData();
    }

    /**
     * 排序数据
     */

    private void getSortData() {
        List<MenuItem> listSort = TextData.getInstance().getSortData();
        List<MenuItem> sortList = ConverseUtils.makeSortFilters(listSort);
        menuWrapper.setData(NewHouseMenuWrapper.TABSORT, sortList);
    }

    /**
     * 更多数据
     */

    private void getMoreData() {
        List<MenuItem> listMore = TextData.getInstance().getMoreData();
        List<MenuItem> moreList = ConverseUtils.makeMoreFilters(listMore);
        menuWrapper.setData(NewHouseMenuWrapper.TABMORE, moreList);
    }

    /**
     * 户型数据
     */

    private void getRoomData() {
        List<MenuItem> listRoom = TextData.getInstance().getRoomData();
        List<MenuItem> roomList = ConverseUtils.makeRoomFilters(listRoom);
        if(roomList.get(1).isChildMulty()) {
            menuWrapper.setMultiPly(NewHouseMenuWrapper.TABROOM);
        }
        menuWrapper.setData(NewHouseMenuWrapper.TABROOM, roomList);
    }

    /**
     * 单价数据
     */

    private void getPriceData() {
        List<MenuItem> listPrice = TextData.getInstance().getPriceData();
        List<MenuItem> priceList = ConverseUtils.makePriceFilters(listPrice);
        menuWrapper.setData(NewHouseMenuWrapper.TABPRICE, priceList);
    }

    /**
     * 地铁线数据
     */

    private void getLineData() {
        SubwayEntity subwayEntity = TextData.getInstance().getLineData();
        List<MenuItem> lineList = ConverseUtils.makeLineFilters(subwayEntity.getListStation(), subwayEntity.getListSubwayLine(), String.valueOf(NewHouseMenuWrapper.SUBWAY_INDEX), "地铁");
        menuWrapper.setData(NewHouseMenuWrapper.TABAREA, lineList, NewHouseMenuWrapper.SUBWAY_INDEX, "地铁");
    }

    /**
     * 区域数据
     */

    private void getAreaData() {
        List<MenuItem> listArea = TextData.getInstance().getAreaData();
        List<MenuItem> areaList = ConverseUtils.makeAreaFilters(listArea, String.valueOf(NewHouseMenuWrapper.AREA_INDEX), "区域");
        menuWrapper.setData(NewHouseMenuWrapper.TABAREA, areaList, NewHouseMenuWrapper.AREA_INDEX, "区域");
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
        Toast.makeText(NewHouseFilterActivity.this, "点击了" + str, Toast.LENGTH_SHORT).show();
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
