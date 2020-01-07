package com.zhuge.myfilter.entity;

import com.zhuge.filter.filterbar.menuitemview.entity.MenuItem;

import java.util.List;

/**
 * 新房地铁线路
 */
public class SubwayEntity {
    private List<MenuItem> listStation;
    private List<MenuItem> listSubwayLine;

    public List<MenuItem> getListStation() {
        return listStation;
    }

    public void setListStation(List<MenuItem> listStation) {
        this.listStation = listStation;
    }

    public List<MenuItem> getListSubwayLine() {
        return listSubwayLine;
    }

    public void setListSubwayLine(List<MenuItem> listSubwayLine) {
        this.listSubwayLine = listSubwayLine;
    }
}
