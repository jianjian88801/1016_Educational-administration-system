package com.jubilantz.utils;

/**
 * @Author JubilantZ
 * @Date: 2021/4/22 12:03
 * 查询分页工具
 */
public final class PageUtil {
    //当前页
    private Integer index;
    //每页个数
    private Integer count;
    //总数
    private Integer total;
    //行数起始值
    private Integer pageStart;

    //第一个参数当前页  //第二个 每页个数
    public PageUtil(Integer index, Integer count) {
        this.index = index-1;
        this.count = count;
    }

    public Boolean isHasPrev(){
        return index >= 1;
    }

    public Boolean isHasNext(){
        return index + 1 < getTotalPage();
    }

    public Integer getTotalPage(){
        return (int) Math.ceil((double) total / count);
    }

    public PageUtil(){

    }

    public Integer getPageStart() {
        if (index != null) {
            return index * count;
        } else {
            return pageStart;
        }
    }

    public PageUtil setPageStart(Integer pageStart) {
        this.pageStart = pageStart;
        return this;
    }

    public Integer getIndex() {
        return index;
    }

    public PageUtil setIndex(Integer index) {
        this.index = index;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public PageUtil setCount(Integer count) {
        this.count = count;
        return this;
    }

    public Integer getTotal() {
        return total;
    }

    public PageUtil setTotal(Integer total) {
        this.total = total;
        return this;
    }
}
