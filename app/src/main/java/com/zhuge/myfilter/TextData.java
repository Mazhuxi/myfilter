package com.zhuge.myfilter;

import com.zhuge.filter.filterbar.menuitemview.entity.MoreMenuItem;
import com.zhuge.myfilter.entity.SubwayEntity;
import com.zhuge.filter.filterbar.menuitemview.entity.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试数据
 */
public class TextData {
    public static TextData getInstance() {
        return new TextData();
    }

    /***************************************  新房筛选 *********************************************************/

    /**
     * 排序
     */

    public List<MenuItem> getSortData() {
        List<MenuItem> listSort = new ArrayList<>();
        MenuItem menuItem = new MenuItem("1", "单价由低到高", false);
        listSort.add(menuItem);
        menuItem = new MenuItem("2", "单价由高到低", false);
        listSort.add(menuItem);
        menuItem = new MenuItem("3", "开盘时间顺序", false);
        listSort.add(menuItem);
        menuItem = new MenuItem("4", "开盘时间倒序", false);
        listSort.add(menuItem);
        return listSort;
    }

    /**
     * 更多
     */

    public List<MenuItem> getMoreData() {
        List<MenuItem> listMore = new ArrayList<>();
        MenuItem more = new MenuItem("1", "特色", false);
        more.setChildMulty(true);
        List<MenuItem> moreChild = new ArrayList<>();
        MenuItem child = new MenuItem("1", "低总价", "1", "特色",false);
        moreChild.add(child);
        child = new MenuItem("2", "小户型", "1", "特色",false);
        moreChild.add(child);
        child = new MenuItem("3", "品牌房产", "1", "特色",false);
        moreChild.add(child);
        child = new MenuItem("4", "现房", "1", "特色",false);
        moreChild.add(child);
        child = new MenuItem("5", "地铁沿线", "1", "特色",false);
        moreChild.add(child);
        child = new MenuItem("6", "低密度", "1", "特色",false);
        moreChild.add(child);
        child = new MenuItem("7", "高品质", "1", "特色",false);
        moreChild.add(child);
        child = new MenuItem("8", "国际社区", "1", "特色",false);
        moreChild.add(child);
        child = new MenuItem("9", "生态宜居", "1", "特色",false);
        moreChild.add(child);
        more.setChildMenus(moreChild);
        listMore.add(more);

        more = new MenuItem("2", "类型", false);
        more.setChildMulty(false);
        List<MenuItem> moreChild2 = new ArrayList<>();
        MenuItem child2 = new MenuItem("1", "住宅", "2", "类型",false);
        moreChild2.add(child2);
        child2 = new MenuItem("2", "别墅", "2", "类型",false);
        moreChild2.add(child2);
        child2 = new MenuItem("3", "商业", "2", "类型",false);

        MoreMenuItem moreMenuItem = new MoreMenuItem();
        List<MenuItem> secondMenus = new ArrayList<>();
        moreMenuItem.setSecondChildMulty(true);
        MenuItem item = new MenuItem("1", "关羽",  false);
        item.setParentItem(child2);//需要设置这层关系
        secondMenus.add(item);
        item = new MenuItem("2", "张飞", false);
        item.setParentItem(child2);
        secondMenus.add(item);
        item = new MenuItem("3", "赵云", false);
        item.setParentItem(child2);
        secondMenus.add(item);
        moreMenuItem.setSecondMenus(secondMenus);

        List<MenuItem> moreItems = new ArrayList<>();
        MenuItem moreitem = new MenuItem("1", "行政",false);
        moreitem.setParentItem(child2);
        List<MenuItem> moreChilds = new ArrayList<>();
        MenuItem morechild = new MenuItem("1","貂蝉","1","行政",false);
        morechild.setParentItem(moreitem);
        moreChilds.add(morechild);
        morechild = new MenuItem("2","吕布","1","行政",false);
        morechild.setParentItem(moreitem);
        moreChilds.add(morechild);
        moreitem.setChildMenus(moreChilds);
        moreitem.setChildMulty(true);
        moreItems.add(moreitem);
        moreMenuItem.setMoreMenus(moreItems);

        child2.setMoreMenuItem(moreMenuItem);
        moreChild2.add(child2);
//        child2 = new MenuItem("4", "写字楼", "2", "类型",false);
//        moreChild2.add(child2);
//        child2 = new MenuItem("5", "商铺", "2", "类型",false);
//        moreChild2.add(child2);
//        child2 = new MenuItem("6", "自持租赁", "2", "类型",false);
//        moreChild2.add(child2);
        more.setChildMenus(moreChild2);
        listMore.add(more);
        return listMore;
    }

    /**
     * 户型
     */

    public List<MenuItem> getRoomData() {
        List<MenuItem> listRoom = new ArrayList<>();
        MenuItem room = new MenuItem("1", "一室", false);
        room.setChildMulty(true);
        listRoom.add(room);
        room = new MenuItem("2", "二室", false);
        room.setChildMulty(true);
        listRoom.add(room);
        room = new MenuItem("3", "三室", false);
        room.setChildMulty(true);
        listRoom.add(room);
        room = new MenuItem("4", "四室", false);
        room.setChildMulty(true);
        listRoom.add(room);
        room = new MenuItem("5", "五室", false);
        room.setChildMulty(true);
        listRoom.add(room);
        room = new MenuItem("6", "五室以上", false);
        room.setChildMulty(true);
        listRoom.add(room);
        return listRoom;
    }

    /**
     * 单价
     */

    public List<MenuItem> getPriceData() {
        List<MenuItem> listPrice = new ArrayList<>();
        MenuItem price = new MenuItem("1", "15000元/平以下", false);
        listPrice.add(price);
        price = new MenuItem("2", "15000-20000元/平", false);
        listPrice.add(price);
        price = new MenuItem("3", "20000-25000元/平", false);
        listPrice.add(price);
        price = new MenuItem("4", "25000-30000元/平", false);
        listPrice.add(price);
        price = new MenuItem("5", "30000-40000元/平", false);
        listPrice.add(price);
        price = new MenuItem("6", "40000-50000元/平", false);
        listPrice.add(price);
        return listPrice;
    }

    /**
     * 区域-地铁数据
     */

    public SubwayEntity getLineData() {
        SubwayEntity subwayEntity = new SubwayEntity();

        List<MenuItem> listStation = new ArrayList<>();
        MenuItem menuItem = new MenuItem("1", "1号线", false);
        listStation.add(menuItem);
        menuItem = new MenuItem("2", "2号线", false);
        listStation.add(menuItem);

        List<MenuItem> listSubwayLine = new ArrayList<>();
        //1号线的地铁站
        menuItem = new MenuItem("1", "苹果园", "1", "1号线", false);
        listSubwayLine.add(menuItem);
        menuItem = new MenuItem("2", "古城", "1", "1号线", false);
        listSubwayLine.add(menuItem);
        menuItem = new MenuItem("3", "八角游乐园", "1", "1号线", false);
        listSubwayLine.add(menuItem);
        menuItem = new MenuItem("4", "八宝山", "1", "1号线", false);
        listSubwayLine.add(menuItem);

        //2号线地铁站
        menuItem = new MenuItem("5", "复兴门", "2", "2号线", false);
        listSubwayLine.add(menuItem);
        menuItem = new MenuItem("6", "建国门", "2", "2号线", false);
        listSubwayLine.add(menuItem);
        menuItem = new MenuItem("7", "积水潭", "2", "2号线", false);
        listSubwayLine.add(menuItem);
        menuItem = new MenuItem("8", "安定门", "2", "2号线", false);
        listSubwayLine.add(menuItem);

        subwayEntity.setListStation(listStation);
        subwayEntity.setListSubwayLine(listSubwayLine);
        return subwayEntity;
    }

    /**
     * 区域-区域数据
     */

    public List<MenuItem> getAreaData() {
        List<MenuItem> listArea = new ArrayList<>();
        MenuItem menuItem = new MenuItem("1", "东城", false);
        listArea.add(menuItem);
        menuItem = new MenuItem("1", "安定门", "1", "东城", false);
        listArea.add(menuItem);
        menuItem = new MenuItem("2", "东直门", "1", "东城", false);
        listArea.add(menuItem);
        menuItem = new MenuItem("3", "东单", "1", "东城", false);
        listArea.add(menuItem);
        menuItem = new MenuItem("4", "建国门", "1", "东城", false);
        listArea.add(menuItem);
        menuItem = new MenuItem("5", "其他", "1", "东城", false);
        listArea.add(menuItem);
        menuItem = new MenuItem("6", "王府井", "1", "东城", false);
        listArea.add(menuItem);

        menuItem = new MenuItem("2", "西城", false);
        listArea.add(menuItem);
        menuItem = new MenuItem("1", "复兴门", "2", "西城", false);
        listArea.add(menuItem);
        menuItem = new MenuItem("2", "金融街", "2", "西城", false);
        listArea.add(menuItem);
        menuItem = new MenuItem("3", "其他", "2", "西城", false);
        listArea.add(menuItem);
        menuItem = new MenuItem("4", "西单", "2", "西城", false);
        listArea.add(menuItem);
        menuItem = new MenuItem("5", "西便门", "2", "西城", false);
        listArea.add(menuItem);
        menuItem = new MenuItem("6", "西城", "2", "西城", false);
        listArea.add(menuItem);

        return listArea;
    }

    /***************************************  二手房筛选 *********************************************************/

    /**
     * 区域-区域数据
     */
    public List<MenuItem> getSecondAreaData() {
        List<MenuItem> listArea = new ArrayList<>();
        MenuItem menuItem = new MenuItem("1", "东城", false);
        listArea.add(menuItem);
        menuItem = new MenuItem("1", "安定门", "1", "东城", false);
        menuItem.setChildMulty(true);
        listArea.add(menuItem);
        menuItem = new MenuItem("2", "东直门", "1", "东城", false);
        menuItem.setChildMulty(true);
        listArea.add(menuItem);
        menuItem = new MenuItem("3", "东单", "1", "东城", false);
        menuItem.setChildMulty(true);
        listArea.add(menuItem);
        menuItem = new MenuItem("4", "建国门", "1", "东城", false);
        menuItem.setChildMulty(true);
        listArea.add(menuItem);
        menuItem = new MenuItem("5", "其他", "1", "东城", false);
        menuItem.setChildMulty(true);
        listArea.add(menuItem);
        menuItem = new MenuItem("6", "王府井", "1", "东城", false);
        menuItem.setChildMulty(true);
        listArea.add(menuItem);

        menuItem = new MenuItem("2", "西城", false);
        listArea.add(menuItem);
        menuItem = new MenuItem("1", "复兴门", "2", "西城", false);
        menuItem.setChildMulty(true);
        listArea.add(menuItem);
        menuItem = new MenuItem("2", "金融街", "2", "西城", false);
        menuItem.setChildMulty(true);
        listArea.add(menuItem);
        menuItem = new MenuItem("3", "其他", "2", "西城", false);
        menuItem.setChildMulty(true);
        listArea.add(menuItem);
        menuItem = new MenuItem("4", "西单", "2", "西城", false);
        menuItem.setChildMulty(true);
        listArea.add(menuItem);
        menuItem = new MenuItem("5", "西便门", "2", "西城", false);
        menuItem.setChildMulty(true);
        listArea.add(menuItem);
        menuItem = new MenuItem("6", "西城", "2", "西城", false);
        menuItem.setChildMulty(true);
        listArea.add(menuItem);

        return listArea;
    }

    /**
     * 区域-地铁数据
     */

    public SubwayEntity getSecondLineData() {
        SubwayEntity subwayEntity = new SubwayEntity();

        List<MenuItem> listStation = new ArrayList<>();
        MenuItem menuItem = new MenuItem("1", "1号线", false);
        menuItem.setChildMulty(true);
        listStation.add(menuItem);
        menuItem = new MenuItem("2", "2号线", false);
        menuItem.setChildMulty(true);
        listStation.add(menuItem);

        List<MenuItem> listSubwayLine = new ArrayList<>();
        //1号线的地铁站
        menuItem = new MenuItem("1", "苹果园", "1", "1号线", false);
        listSubwayLine.add(menuItem);
        menuItem = new MenuItem("2", "古城", "1", "1号线", false);
        listSubwayLine.add(menuItem);
        menuItem = new MenuItem("3", "八角游乐园", "1", "1号线", false);
        listSubwayLine.add(menuItem);
        menuItem = new MenuItem("4", "八宝山", "1", "1号线", false);
        listSubwayLine.add(menuItem);

        //2号线地铁站
        menuItem = new MenuItem("5", "复兴门", "2", "2号线", false);
        listSubwayLine.add(menuItem);
        menuItem = new MenuItem("6", "建国门", "2", "2号线", false);
        listSubwayLine.add(menuItem);
        menuItem = new MenuItem("7", "积水潭", "2", "2号线", false);
        listSubwayLine.add(menuItem);
        menuItem = new MenuItem("8", "安定门", "2", "2号线", false);
        listSubwayLine.add(menuItem);

        subwayEntity.setListStation(listStation);
        subwayEntity.setListSubwayLine(listSubwayLine);
        return subwayEntity;
    }

    /**
     * 附近数据
     */

    public List<MenuItem> getNearByData() {
        List<MenuItem> listNear = new ArrayList<>();
        MenuItem near = new MenuItem("1", "1000米", false);
        listNear.add(near);
        near = new MenuItem("2", "2000米", false);
        listNear.add(near);
        near = new MenuItem("3", "3000米", false);
        listNear.add(near);
        near = new MenuItem("4", "5000米", false);
        listNear.add(near);
        return listNear;
    }

    /**
     * 总价
     */
    public List<MenuItem> getSecondPriceData() {
        List<MenuItem> listPrice = new ArrayList<>();
        MenuItem price = new MenuItem("1", "200万以下", false);
        price.setChildMulty(true);
        listPrice.add(price);
        price = new MenuItem("2", "200-250万", false);
        price.setChildMulty(true);
        listPrice.add(price);
        price = new MenuItem("3", "250-300万", false);
        price.setChildMulty(true);
        listPrice.add(price);
        price = new MenuItem("4", "300-500万", false);
        price.setChildMulty(true);
        listPrice.add(price);
        price = new MenuItem("5", "500-1000万", false);
        price.setChildMulty(true);
        listPrice.add(price);
        price = new MenuItem("6", "1000万以上", false);
        price.setChildMulty(true);
        listPrice.add(price);
        return listPrice;
    }

    /**
     * 户型
     */
    public List<MenuItem> getSecondRoomData() {
        List<MenuItem> listRoom = new ArrayList<>();
        MenuItem room = new MenuItem("1", "一居室", false);
        room.setChildMulty(true);
        listRoom.add(room);
        room = new MenuItem("2", "二居室", false);
        room.setChildMulty(true);
        listRoom.add(room);
        room = new MenuItem("3", "三居室", false);
        room.setChildMulty(true);
        listRoom.add(room);
        room = new MenuItem("4", "四居室", false);
        room.setChildMulty(true);
        listRoom.add(room);
        room = new MenuItem("5", "五居室", false);
        room.setChildMulty(true);
        listRoom.add(room);
        room = new MenuItem("6", "五居室以上", false);
        room.setChildMulty(true);
        listRoom.add(room);
        return listRoom;
    }

    /**
     * 更多
     */

    public List<MenuItem> getSecondMoreData() {
        List<MenuItem> listMore = new ArrayList<>();
        MenuItem more = new MenuItem("1", "面积", false);
        more.setChildMulty(true);
        List<MenuItem> moreChild = new ArrayList<>();
        MenuItem child = new MenuItem("1", "60平米以下", "1", "面积",false);
        moreChild.add(child);
        child = new MenuItem("2", "60-90平米", "1", "面积",false);
        moreChild.add(child);
        child = new MenuItem("3", "90-140平米", "1", "面积",false);
        moreChild.add(child);
        child = new MenuItem("4", "140-200平米", "1", "面积",false);
        moreChild.add(child);
        child = new MenuItem("5", "200平米以上", "1", "面积",false);
        moreChild.add(child);
        more.setChildMenus(moreChild);
        listMore.add(more);

        more = new MenuItem("2", "特色", false);
        more.setChildMulty(true);
        List<MenuItem> moreChild2 = new ArrayList<>();
        MenuItem child2 = new MenuItem("1", "24小时降价", "2", "特色",false);
        moreChild2.add(child2);
        child2 = new MenuItem("2", "24小时涨价", "2", "特色",false);
        moreChild2.add(child2);
        child2 = new MenuItem("3", "新上房源", "2", "特色",false);
        moreChild2.add(child2);
        child2 = new MenuItem("4", "唯一", "2", "特色",false);
        moreChild2.add(child2);
        child2 = new MenuItem("5", "满五", "2", "特色",false);
        moreChild2.add(child2);
        child2 = new MenuItem("6", "满二", "2", "特色",false);
        moreChild2.add(child2);
        child2 = new MenuItem("7", "近地铁", "2", "特色",false);
        moreChild2.add(child2);
        more.setChildMenus(moreChild2);
        listMore.add(more);
        return listMore;
    }

}
