package com.pbsaas.connect.core.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageModel<T> {
    private int  pageIndex;//索引
    private int totalPage;//总页数
    private int pageSize;//每页显示条数
    private int count;// 总记录数
    private List<T> list;
    // 动态显示条
    private int start = 1;
    private int end = 10;

    private boolean nextPage;//是否有下一页
    private boolean prePage;//是否有上一页

    public PageModel(int pageIndex, int pageSize, int count) {
        this.pageIndex = pageIndex;
        this.count = count;
        this.pageSize = pageSize;
        int currentPage = pageIndex;
      //  this.pageIndex = (pageIndex - 1) * pageSize;
        this.totalPage = (int) Math.ceil(count * 1.0 / pageSize);

        //是否可以上一页下一页
        if (count==0 || this.totalPage ==1){
            this.nextPage = false;
            this.prePage = false;
        }else if(currentPage<=1){
            this.prePage = false;
            this.nextPage = true;
        }else if(currentPage >= this.totalPage){
            this.prePage = true;
            this.nextPage = false;
        }else if(currentPage < this.totalPage){
            this.prePage = true;
            this.nextPage = true;
        }
        // 3 动态显示条
        // 3.1 初始化数据 -- 显示10个分页
        this.start = 1;
        this.end = 10;

        // 3.2 初始数据 ， totalPage = 4
        if (this.totalPage <= 10) {
            this.end = this.totalPage;
        } else {
            // totalPage = 22
            // 3.3 当前页 前4后5
            this.start = this.pageIndex - 4;
            this.end = this.pageIndex + 5;
            // * pageNum = 1
            if (this.start < 1) {
                this.start = 1;
                this.end = 10;
            }
            // * pageNum = 22
            if (this.end > this.totalPage) {
                this.end = this.totalPage;
                this.start = this.totalPage - 9;
            }
        }
    }
    public int getPageIndex() {
        if (this.pageIndex <= 0) {
            this.pageIndex = 1;
        }
        if (this.pageIndex >= this.totalPage) {
            this.pageIndex = this.totalPage;
        }
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCount() {
        if (pageSize == Integer.MAX_VALUE){
            return list.size();
        }
        return count;
    }

    public void setCount(int dataCount) {
        this.count = count;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public boolean isNextPage() {

        return this.nextPage;
    }

    public boolean isPrePage() {
        return this.prePage;
    }

    /**
     * 用于在客户端不需要全部返回pagebean数据的时候使用，将新封装的dataModel数据以pagebean的返回形式返回
     * @param pageBean
     * @param dataModel
     * @param currentPage
     * @return
     */
    public static Map<String,Object> renderPageInfoData(PageModel pageBean, List<Map<String,Object>> dataModel, int  currentPage){
        int totalPage = pageBean.getTotalPage();//总页数
        int pageSize = pageBean.getPageSize();//每页显示条数
        int count = pageBean.getCount();// 总记录数
        boolean nextPage = pageBean.isNextPage();//是否有下一页
        boolean prePage = pageBean.isPrePage();//是否有上一页
        int start =pageBean.getStart();
        int end =  pageBean.getEnd();
        Map<String,Object> data = new HashMap<String,Object>();
        data.put("pageIndex",currentPage);//不是pageBean 里面的pageIndex
        data.put("totalPage",totalPage);
        data.put("pageSize",pageSize);
        data.put("count",count);
        data.put("nextPage",nextPage);
        data.put("prePage",prePage);
        data.put("start",start);
        data.put("end",end);
        data.put("list",dataModel);
        return data;
    }
    public static Map<String,Object> renderSimpleData(Object data){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("list",data);
        return map;
    }
    @Override
    public String toString() {
        return "PageBean{" +
                "pageIndex=" + pageIndex +
                ", totalPage=" + totalPage +
                ", pageSize=" + pageSize +
                ", count=" + count +
                ", list=" + list.toString() +
                ", start=" + start +
                ", end=" + end +
                ", nextPage=" + nextPage +
                ", prePage=" + prePage +
                '}';
    }
}
