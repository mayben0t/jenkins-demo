package com.example.demo.jedis;

import org.springframework.cglib.core.Local;
import redis.clients.jedis.Jedis;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class UserLoginStatusService {

    private static final String host="127.0.0.1";

    private static final int port=6379;

    private static final Jedis jedis=new Jedis(host,port);

    //日期的初始值（也可以理解为用户的注册时间），
    //下文需要使用日期的偏移量作为redis位图的offset，
    //因此需要将要保存登录状态的日期减去该初始日期。
    //这里使用了Java 8的新日期API
    private static final LocalDate beginDate=LocalDate.of(2018,1,1);

    static {
        jedis.connect();
    }

    public void setLoginStatus(String userId, LocalDate date,boolean isLogin){
        long offset = getDateDuration(beginDate, date);
        jedis.setbit(userId,offset,isLogin);
    }

    public boolean getLoginStatus(String userId,LocalDate date){
        long offset = getDateDuration(beginDate, date);
        return jedis.getbit(userId,offset);
    }

    private long getDateDuration(LocalDate start ,LocalDate end){
        return start.until(end, ChronoUnit.DAYS);
    }

    public static void main(String[] args) {
        UserLoginStatusService userLoginStatusService=new UserLoginStatusService();
        String userId="user_1";
        LocalDate today = LocalDate.now();
        userLoginStatusService.setLoginStatus(userId,today,true);
        boolean todayLoginStatus = userLoginStatusService.getLoginStatus(userId, today);
        System.out.println(String.format("The loginStatus of %s in %s is %s",userId,today,todayLoginStatus));
        LocalDate yesterday = LocalDate.now().minusDays(1);
        boolean yesterdayLoginStatus = userLoginStatusService.getLoginStatus(userId, yesterday);
        System.out.println(String.format("The loginStatus of %s in %s is %s",userId,yesterday,yesterdayLoginStatus));
    }



    static class WxTest{

        public static void main(String[] args) {
            LocalDate firstDay = LocalDate.of(2019, 10, 26);
            LocalDate now = LocalDate.now();

            long until = firstDay.until(now, ChronoUnit.DAYS);

            System.out.println(until);
        }
    }

    static class another{
        public static void main(String[] args) {
            char a = 'h';
            System.out.println((int)a);
            System.out.println(Integer.toBinaryString((int)a));
        }
    }
}