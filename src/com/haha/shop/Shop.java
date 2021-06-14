package com.haha.shop;

import com.haha.shop.constant.ShopConstant;
import com.haha.shop.util.ScannerUtil;

import java.util.List;
import java.util.Scanner;

/**
 * @author LLLzc
 * @date 2021/6/13 下午9:46
 */
public class Shop {
    //商场的消费者，难点
    private User consumer = new User();

    public static void main(String[] args) {
        Shop shop = new Shop();
        //展示登录注册页面
        shop.loginView();
        //展示商城购物的页面
        shop.shopView();
    }

    //展示购物页面
    private void shopView() {
        System.out.println("请根据提示选择相应的功能：");
        boolean flag = true;
        while (flag) {
            System.out.println(
                    ShopConstant.GOODS_TYPE_NECESSITIES + "：日用品  " +
                            ShopConstant.GOODS_TYPE_CRUET + "：调味品  " +
                            ShopConstant.GOODS_TYPE_FOOD + "：食品  " +
                            ShopConstant.FUNCTION_LOOK_CART + "：查看购物车  " +
                            ShopConstant.FUNCTION_PAY_MONEY + "：支付  " +
                            ShopConstant.FUNCTION_RECHARGE + "：充值  " +
                            ShopConstant.FUNCTION_ACCOUNT_MANAGER + "：用户信息  " +
                            ShopConstant.FUNCTION_EXIT + "：退出  "
            );
            String function = ScannerUtil.getInput();
            switch (function) {
                case ShopConstant.GOODS_TYPE_NECESSITIES:
                    this.showGoodsMessage(ShopConstant.GOODS_TYPE_NECESSITIES);
                    break;
                case ShopConstant.GOODS_TYPE_CRUET:
                    this.showGoodsMessage(ShopConstant.GOODS_TYPE_CRUET);
                    break;
                case ShopConstant.GOODS_TYPE_FOOD:
                    this.showGoodsMessage(ShopConstant.GOODS_TYPE_FOOD);
                    break;
                case ShopConstant.FUNCTION_LOOK_CART:
                    consumer.lookAtCart();
                    break;
                case ShopConstant.FUNCTION_PAY_MONEY:
                    consumer.pay();
                    break;
                case ShopConstant.FUNCTION_RECHARGE:
                    consumer.recharge();
                    break;
                case ShopConstant.FUNCTION_ACCOUNT_MANAGER:
                    consumer.showUserManger();
                    break;
                case ShopConstant.FUNCTION_EXIT:
                    System.exit(-1);
                    break;
                default:
                    break;
            }
        }
    }

    //展示商品信息
    private void showGoodsMessage(String type){
        List<Goods> goodsByType = Goods.getGoodsByType(type);
        String str = "";
        for (Goods goods : goodsByType) {
            str = str + "商品编号：" + goods.getId() +
                    " - 商品名称：" + goods.getName() +
                    " - 商品价格："+ goods.getPrice() +"\n";
        }
        System.out.println(str);

        System.out.print("请输入要购买的商品编号：");
        String goodsId = ScannerUtil.getInput();
        System.out.print("请输入要购买的商品数量：");
        String goodsCount = ScannerUtil.getInput();
        consumer.addGoodsToCart(goodsId,Integer.parseInt(goodsCount));

    }

    //展示注册登录页面
    private void loginView() {
        boolean flag = true;
        while (flag) {
            System.out.println("请选择功能:" + ShopConstant.USER_LOGIN + "：登录  " +
                    ShopConstant.USER_REGIST + "：注册");
            Scanner scanner = new Scanner(System.in);
            String function = scanner.next();
            switch (function) {
                case ShopConstant.USER_LOGIN:
                    flag = !consumer.login();
                    break;
                case ShopConstant.USER_REGIST:
                    consumer.regist();
                    break;
            }
        }
    }

    public User getConsumer() {
        return consumer;
    }

    public void setConsumer(User consumer) {
        this.consumer = consumer;
    }
}
