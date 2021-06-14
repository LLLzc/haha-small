package com.haha.shop;

import com.haha.shop.constant.ShopConstant;
import com.haha.shop.util.ScannerUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author LLLzc
 * @date 2021/6/13 下午9:46
 */
public class Goods {
    //商品类型
    private String type;
    //商品id
    private String id;
    //商品名称
    private String name;
    //商品单价
    private double price;

    // ----------------------业务方法--------------
    //商品上架
    public static void goodsPut() {
        System.out.println("请输入商品类型：（1、日用品，2、调味品，3、食品）");
        String type = ScannerUtil.getInput();
        if (!("1".equals(type) || "2".equals(type) || "3".equals(type))) {
            System.out.println("您输入的类型不对！");
            System.out.print("请重新输入类型编号：");
            type = ScannerUtil.getInput();
        }
        System.out.print("请输入商品名称：");
        String goodsName = ScannerUtil.getInput();
        System.out.print("请输入商品价格：");
        String price = ScannerUtil.getInput();

        Goods goods = new Goods();
        //设置个id总条数+1 就是id自动生成
        List<Goods> allGoods = getAllGoods(ShopConstant.SHOP_PATH);
        //使用打擂台的方式找到最大ID，再加一，当成新的ID
        int maxId = 0;
        for (int i = 0; i < allGoods.size(); i++) {
            if( Integer.parseInt( allGoods.get(i).getId() ) > maxId ){
                maxId = Integer.parseInt( allGoods.get(i).getId() ) ;
            }
        }
        maxId ++;

        goods.setId(Integer.toString(maxId));
        goods.setName(goodsName);
        goods.setType(type);
        goods.setPrice(Double.parseDouble(price));

        saveGoods(goods, true);
        System.out.println(goodsName + "上架成功！");
    }

    //商品下架
    public static void goodsOut() {
        //展示一下商品
        showGoods();
        System.out.println("请输入商品ID：");
        String id = ScannerUtil.getInput();
        //根据id删除
        deleteByGoodsId(id);
        //二次展示
        showGoods();
    }

    //修改商品价钱
    public static void goodsUpdatePrice() {
        showGoods();
        System.out.print("请输入id：");
        String id = ScannerUtil.getInput();
        System.out.print("请输入新价钱：");
        String newPrice = ScannerUtil.getInput();
        updatePrice(id,Double.parseDouble(newPrice));
        showGoods();
    }

    //显示所有的商品
    public static void showGoods() {
        List<Goods> allGoods = getAllGoods(ShopConstant.SHOP_PATH);
        String str = "";
        for (Goods goods : allGoods) {
            str = str + "商品编号：" + goods.getId() +
                    " - 商品名称：" + goods.getName() +
                    " - 商品价格：" + goods.getPrice() + "\n";
        }
        System.out.println("----------------------------------------");
        System.out.println(str);
        System.out.println("----------------------------------------");
    }
    //---------------------业务方法结束-----------------------------------

    //-----------------操作持久层------------------------
    //根据id找到一个商品
    public static Goods getGoodsById(String id) {
        List<Goods> allGoods = getAllGoods(ShopConstant.SHOP_PATH);
        Goods targetGoods = null;
        for (Goods goods : allGoods) {
            if (goods.getId().equals(id)) {
                targetGoods = goods;
                break;
            }
        }
        return targetGoods;
    }

    //根据id删除商品
    private static void updatePrice(String id,double newPrice) {
        //使用迭代器删除一条数据
        List<Goods> allGoods = getAllGoods(ShopConstant.SHOP_PATH);
        for (int i = 0; i < allGoods.size(); i++) {
            if( allGoods.get(i).getId().equals(id) ){
                allGoods.get(i).setPrice(newPrice);
            }
        }
        //重新写入到文件
        for (int i = 0; i < allGoods.size(); i++) {
            if (i == 0) {
                saveGoods(allGoods.get(i), false);
            } else {
                saveGoods(allGoods.get(i), true);
            }
        }
    }

    //根据id删除商品
    private static void deleteByGoodsId(String id) {
        //使用迭代器删除一条数据
        List<Goods> allGoods = getAllGoods(ShopConstant.SHOP_PATH);
        Iterator<Goods> iterator = allGoods.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId().equals(id)) {
                iterator.remove();
            }
        }
        //重新写入到文件
        for (int i = 0; i < allGoods.size(); i++) {
            if (i == 0) {
                saveGoods(allGoods.get(i), false);
            } else {
                saveGoods(allGoods.get(i), true);
            }
        }
    }

    //保存商品
    private static void saveGoods(Goods goods, boolean append) {
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            //获取一下这个文件
            File file = new File(ShopConstant.SHOP_PATH);
            //如果发现文件不存在
            if (!file.exists()) {
                //建立路径的文件夹
                new File(ShopConstant.BASE_PATH).mkdirs();
                //创建文件
                file.createNewFile();
            }

            //使用文件流将用户信息添加在文件中
            fw = new FileWriter(ShopConstant.SHOP_PATH, append);
            bw = new BufferedWriter(fw);
            bw.append(goods.getId()).append("_").append(goods.getType()).append("_")
                    .append(goods.getName()).append("_")
                    .append(Double.toString(goods.getPrice())).append("\n");
            bw.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    //获取所有的商品
    public static List<Goods> getAllGoods(String path) {
        File file = new File(ShopConstant.SHOP_PATH);
        //如果发现文件不存在,直接返回一个空的数组，增加安全性
        if (!file.exists()) {
            return new ArrayList<>();
        }
        List<Goods> goodsList = new ArrayList<>();
        //try里直接定义了流，就不用了关闭流，会自动关闭，实现了closed接口
        try (FileReader fs = new FileReader(path);
             BufferedReader bf = new BufferedReader(fs)) {
            String userStr = null;
            while ((userStr = bf.readLine()) != null) {
                String[] str = userStr.split("_");
                Goods goods = new Goods();
                goods.setId(str[0]);
                goods.setType(str[1]);
                goods.setName(str[2]);
                //Double的静态方法，将字符串转化为double
                //Integer应该也能，自己去试试
                goods.setPrice(Double.parseDouble(str[3]));
                goodsList.add(goods);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return goodsList;
    }

    //查找方式，根据类型查找
    public static List<Goods> getGoodsByType(String type) {
        List<Goods> allGoods = getAllGoods(ShopConstant.SHOP_PATH);
        List<Goods> goodsType = new ArrayList<>();
        for (Goods goods : allGoods) {
            if (goods.getType().equals(type)) {
                goodsType.add(goods);
            }
        }
        return goodsType;
    }
    //---------------------------------持久层方法结束--------------------------------------

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
