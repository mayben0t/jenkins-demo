package com.example.demo.self;

import redis.clients.jedis.Jedis;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Formatter;

public class Wx {
    private static final LocalDate start= LocalDate.of(2019,10,1);
    private static Jedis jedis = new Jedis("127.0.0.1",6379);
    static {
        jedis.connect();
    }
    public static long duration(LocalDate end){
        return start.until(end, ChronoUnit.DAYS);
    }
    public static boolean setBit(String userId,LocalDate date,boolean flag){
        return jedis.setbit(userId,duration(date),flag);
    }
    public static void getBit(String userId,LocalDate date){
        if(jedis.getbit(userId,duration(date))){
            System.out.println(userId+" "+ date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+" 当天已签到");
        }else {
            System.out.println(userId+" "+ date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+" 当天未签到");
        }
    }
    public static void main(String[] args) {
        String userId = "wybswx";
        LocalDate now = LocalDate.now();
        LocalDate yesterday = now.minusDays(1);

        setBit(userId,now,true);
        setBit(userId,yesterday,false);

        getBit(userId,now);
        getBit(userId,yesterday);
        //ccs
    }
}
