package com.haha.shop;

import com.haha.shop.constant.ShopConstant;
import com.haha.shop.util.ScannerUtil;

/**
 * @author LLLzc
 * @date 2021/6/13 下午9:46
 */
public class GoodsManger {
    public static void main(String[] args) {
        System.out.println("请根据提示选择相应的功能：");
        boolean flag = true;
        while (flag) {
            System.out.println(
                    ShopConstant.GOODS_PUT + "：商品上架  " +
                            ShopConstant.GOODS_OUT + "：商品下架  " +
                            ShopConstant.GOODS_UPDATE + "：商品修改  " +
                            ShopConstant.GOODS_MESSAGE + "：商品信息  " +
                            ShopConstant.FUNCTION_EXIT + "：退出  "
            );
            String function = ScannerUtil.getInput();
            switch (function) {
                case ShopConstant.GOODS_PUT:
                    Goods.goodsPut();
                    break;
                case ShopConstant.GOODS_OUT:
                    Goods.goodsOut();
                    break;
                case ShopConstant.GOODS_UPDATE:
                    Goods.goodsUpdatePrice();
                    break;
                case ShopConstant.GOODS_MESSAGE:
                    Goods.showGoods();
                    break;
                case ShopConstant.FUNCTION_EXIT:
                    System.exit(-1);
                    break;
            }
        }
    }
}
