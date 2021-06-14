package com.haha.shop;

import com.haha.shop.constant.ShopConstant;
import com.haha.shop.util.ScannerUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LLLzc
 * @date 2021/6/13 下午9:46
 */
public class User {

    //user的属性
    private String username;
    private String password;
    //余额
    private double balance;
    private Cart cart = new Cart();

    //------------------------这些是业务-------------------
    //登录业务
    public boolean login() {
        System.out.print("请输入用户名：");
        //登录，用户名
        String username = ScannerUtil.getInput();
        //密码
        System.out.println();
        System.out.print("请输入密码：");
        String password = ScannerUtil.getInput();
        //先拿到用户
        User user = getUserByName(username);
        if (user == null) {
            System.out.println("用户不存在，登录失败");
            return false;
        }
        //如果拿到user，去对比密码
        if (!password.equals(user.getPassword())) {
            System.out.println("密码错误，登录失败");
            return false;
        }

        //难以理解，需要思考
        this.setUsername(username);
        this.setPassword(password);

        System.out.println("登录成功");
        return true;
    }

    //注册业务
    public boolean regist() {
        //注册
        System.out.print("请输入用户名：");
        //用户名
        String username = ScannerUtil.getInput();
        //密码
        System.out.println();
        System.out.print("请输入密码：");
        String password = ScannerUtil.getInput();

        //创建一个用户
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setBalance(0);
        //保存用户
        saveUser(user, true);
        return true;
    }

    //查看购物车业务
    public void lookAtCart() {
        List<Order> cartMessage = cart.getCartMessage();
        System.out.println("以下为购物车：");
        for (int i = 0; i < cartMessage.size(); i++) {
            System.out.println((i + 1) + ":" +
                    cartMessage.get(i).getGoodsName() + "\t" +
                    cartMessage.get(i).getGoodsPrice() + "\t" +
                    cartMessage.get(i).getGoodsCount() + "\t" +
                    cartMessage.get(i).getTotalMoney()
            );
        }
        System.out.println("---------------------------------------------\n");
    }


    //用户支付业务
    public void pay() {
        List<Order> cartMessage = cart.getCartMessage();
        double cartTotalMoney = 0;
        for (Order order : cartMessage) {
            cartTotalMoney += order.getTotalMoney();
        }

        //比较用户剩余的金额看看等不等支付
        //如果用户的金额小于购买商品的金额，
        //大于怎么办，清空购物车 ，把自己的钱减了
        User user = getUserByName(this.getUsername());
        if (user.getBalance() >= cartTotalMoney) {
            //将用户的余额减去话费的钱作为新的余额，并在文件中修改
            double balance = user.getBalance() - cartTotalMoney;
            updateBalance(this.getUsername(), balance);
            //清空购物车
            user.getCart().clear();
            System.out.println("支付成功！");
            System.out.println("本次消费：" + cartTotalMoney + "。您的余额剩余:" + balance + "元。");
        } else {
            System.out.println("余额不足，请先充值！");
        }
    }

    //查看用户信息
    public void showUserManger() {
        User user = getUserByName(this.getUsername());
        System.out.println("用户名：" + user.getUsername());
        System.out.println("剩余金额：" + user.getBalance());
    }

    //充值方法
    public void recharge() {
        //根据登录的用户名从文件获取到用户信息
        User user = getUserByName(this.getUsername());
        System.out.println("当前余额：" + user.getBalance() + "元！");
        System.out.print("请输入充值金额：");
        String input = ScannerUtil.getInput();
        //将原来的钱加上输入的钱就是当前的新余额
        double newBalance = user.getBalance() + Double.parseDouble(input);
        //调用更新余额的方法
        updateBalance(user.getUsername(), newBalance);
        System.out.println("剩余金额：" + newBalance + "元。");
    }

    //添加到购物车
    public boolean addGoodsToCart(String id, int goodsCount) {
        //2、把商品加入购物车
        cart.addGoods(id, goodsCount);
        return true;
    }

    //---------------------------常用的操作数据的方法，增删查改，都一球样-----------------------------------
    //获得所有的user
    private List<User> getAllUsers(String url) {
        File file = new File(ShopConstant.USER_PATH);
        //如果发现文件不存在,直接返回一个空的数组，增加安全性
        if (!file.exists()) {
            return new ArrayList<>();
        }

        List<User> users = new ArrayList<>();
        try (FileReader fs = new FileReader(url);
             BufferedReader bf = new BufferedReader(fs)) {
            String userStr = null;
            while ((userStr = bf.readLine()) != null) {
                String[] str = userStr.split("_");
                User user = new User();
                user.setUsername(str[0]);
                user.setPassword(str[1]);
                //注意字符串转double
                user.setBalance(Double.parseDouble(str[2]));
                users.add(user);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return users;
    }

    //获取一个user的方法
    public User getUserByName(String name) {
        List<User> users = getAllUsers(ShopConstant.USER_PATH);
        for (User user : users) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }
        return null;
    }

    //保存一个user的方法
    //第二个参数表示是追加(true)还是覆盖(false)
    public boolean saveUser(User user, boolean append) {
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            //获取一下这个文件
            File file = new File(ShopConstant.USER_PATH);
            //如果发现文件不存在
            if (!file.exists()) {
                //建立路径的文件夹
                new File(ShopConstant.BASE_PATH).mkdirs();
                //创建文件
                file.createNewFile();
            }

            //使用文件流将用户信息添加在文件中
            fw = new FileWriter(ShopConstant.USER_PATH, append);
            bw = new BufferedWriter(fw);
            bw.append(user.getUsername()).append("_").append(user.getPassword()).append("_").append(Double.toString(user.getBalance())).append("\n");
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
        return true;
    }

    //修改价钱的方法
    public void updateBalance(String username, double newBalance) {
        List<User> allUsers = getAllUsers(ShopConstant.USER_PATH);
        if (username == null) {
            return;
        }
        for (int i = 0; i < allUsers.size(); i++) {
            if (username.equals(allUsers.get(i).getUsername())) {
                allUsers.get(i).setBalance(newBalance);
            }
            if (i == 0) {
                saveUser(allUsers.get(i), false);
            } else {
                saveUser(allUsers.get(i), true);
            }
        }
    }

    //修改密码
    public void updatePassword(String username, String newPass) {
        List<User> allUsers = getAllUsers(ShopConstant.USER_PATH);
        if (username == null) {
            return;
        }
        for (int i = 0; i < allUsers.size(); i++) {
            if (username.equals(allUsers.get(i).getUsername())) {
                allUsers.get(i).setPassword(newPass);
            }
            if (i == 0) {
                saveUser(allUsers.get(i), false);
            } else {
                saveUser(allUsers.get(i), true);
            }
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", cart=" + cart +
                '}';
    }
}
