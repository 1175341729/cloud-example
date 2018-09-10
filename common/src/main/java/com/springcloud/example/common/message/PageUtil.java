package com.springcloud.example.common.message;

import com.github.pagehelper.PageInfo;

/***
 *  @Author dengwei
 *  @Description: 分页自定义返回 pageInfo中去掉无效的字段
 *  @Date 2018/6/28 9:36
 */
public class PageUtil {
    public static PageMessage page(PageInfo pageInfo){
        PageMessage pageMessage = new PageMessage();
        pageMessage.setPageNumber(pageInfo.getPageNum());
        pageMessage.setTotalPage(pageInfo.getPages());
        pageMessage.setPageSize(pageInfo.getSize());
        pageMessage.setList(pageInfo.getList());
        return pageMessage;
    }
}
