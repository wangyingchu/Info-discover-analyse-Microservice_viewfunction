package com.infoDiscover.infoAnalyse.service.restful.vo.dataCRUD;

import java.util.List;

public class ExploreParametersVO {

    private int pageSize;
    private int startPage;
    private int endPage;
    private int resultNumber;
    private String type;
    private String typeName;
    private FilteringItemVO defaultFilteringItem;
    private List<FilteringItemVO> andFilteringItemList;
    private List<FilteringItemVO> orFilteringItemList;
    private String discoverSpaceName;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public int getResultNumber() {
        return resultNumber;
    }

    public void setResultNumber(int resultNumber) {
        this.resultNumber = resultNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FilteringItemVO getDefaultFilteringItem() {
        return defaultFilteringItem;
    }

    public void setDefaultFilteringItem(FilteringItemVO defaultFilteringItem) {
        this.defaultFilteringItem = defaultFilteringItem;
    }

    public List<FilteringItemVO> getAndFilteringItemList() {
        return andFilteringItemList;
    }

    public void setAndFilteringItemList(List<FilteringItemVO> andFilteringItemList) {
        this.andFilteringItemList = andFilteringItemList;
    }

    public List<FilteringItemVO> getOrFilteringItemList() {
        return orFilteringItemList;
    }

    public void setOrFilteringItemList(List<FilteringItemVO> orFilteringItemList) {
        this.orFilteringItemList = orFilteringItemList;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDiscoverSpaceName() {
        return discoverSpaceName;
    }

    public void setDiscoverSpaceName(String discoverSpaceName) {
        this.discoverSpaceName = discoverSpaceName;
    }
}
