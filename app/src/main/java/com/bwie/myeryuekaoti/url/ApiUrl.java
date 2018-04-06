package com.bwie.myeryuekaoti.url;

/**
 * Created by admin on 2018/3/31.
 */

public class ApiUrl {
    //https://www.zhaoapi.cn/product/getCarts?uid=4427
    //http://120.27.23.105/getCarts?uid=4427
    public static String gongtong="https://www.zhaoapi.cn/";
    //1.	首页接口
    public static String shouye="ad/getAd";
    //2.	查询购物车
    public static String select="product/getCarts";

    //3.	更新购物车?uid=71&sellerid=1&pid=1&selected=0&num=10
    public static String gengxin="product/updateCarts";
    //4.	删除购物车uid=72&pid=1
    public static String delete="product/deleteCart";


}
