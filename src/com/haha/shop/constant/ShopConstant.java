package com.haha.shop.constant;

/**
 * @author LLLzc
 * @date 2021/6/13 下午9:45
 */
public class ShopConstant {
    //登录注册功能常量
    public static final String USER_LOGIN = "1";
    public static final String USER_REGIST = "2";

    //用户功能常量
    //商品上架
    public static final String GOODS_PUT = "1";
    //商品下架
    public static final String GOODS_OUT  = "2";
    //商品修改
    public static final String GOODS_UPDATE = "3";
    //商品信息
    public static final String GOODS_MESSAGE = "4";

    //用户功能常量
    public static final String GOODS_TYPE_NECESSITIES = "1";
    public static final String GOODS_TYPE_CRUET = "2";
    public static final String GOODS_TYPE_FOOD = "3";
    public static final String FUNCTION_LOOK_CART = "4";
    public static final String FUNCTION_PAY_MONEY = "5";
    public static final String FUNCTION_RECHARGE = "6";
    public static final String FUNCTION_ACCOUNT_MANAGER = "7";
    public static final String FUNCTION_EXIT = "8";

    //数据存储位置
    //文件夹的路劲
    public static final String BASE_PATH = "/Users/LLLzc/workspace/test";
    //用户文件的位置
    public static final String USER_PATH = BASE_PATH + "/user.txt";
    //商品文件的位置
    public static final String SHOP_PATH = BASE_PATH + "/shop.txt";
}
