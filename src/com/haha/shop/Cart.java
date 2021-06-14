package com.haha.shop;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LLLzc
 * @date 2021/6/13 下午9:47
 */
public class Cart {
    //购物车就是一个订单的集合
    private List<Order> orderList = new ArrayList<>();

    //给购物车添加商品
    public void addGoods(String id, int goodsCount) {
        Goods goods = Goods.getGoodsById(id);
        Order order = new Order();
        order.setGoodsId(goods.getId());
        order.setGoodsName(goods.getName());
        order.setGoodsCount(goodsCount);
        order.setGoodsPrice(goods.getPrice());
        order.setTotalMoney(goodsCount * goods.getPrice());
        orderList.add(order);
        System.out.println("您添加了 " + order.getGoodsCount() + " 个 " +
                order.getGoodsName() +" 到购物车,共计 " +  order.getTotalMoney() + "元。");
    }

    //获取购物车的信息
    public List<Order> getCartMessage(){
        return orderList;
    }

    //清空购物车
    public boolean clear(){
        orderList.clear();
        return true;
    }
}
