package com.xwin.common;

import java.util.Random;

/**
 *
 *  类描述:随机数工具类
 *  RandomUtil
 *  @author
 *  @data 2017-7-12下午9:26:43
 *  @version 1.0.0
 */
public class RandomUtil {
    public static String getRandom(){
        String randNum = new Random().nextInt(1000000)+"";
        if(randNum.length()!=6){
            return getRandom();
        }
        return randNum;
    }
}
