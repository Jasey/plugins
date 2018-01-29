package com.jasey.base.rule;

import com.jasey.base.OrderManager;

import java.util.*;

public enum  CardLevel {
    SINGLE("单牌"),
    COUPLE("对子"),
    ORDER("顺子"),
    SAME_COLOR("同花"),
    SAME_ORDER("顺金"),
    SAME_NUM("豹子");

    private String des;

    private CardLevel(String des) {
        this.des = des;
    }

    public String getDes(){
        return des;
    }

    public static boolean isOrderCard(Set<Integer> numSet){
        //先把所有的num+1,就形成从A开始的所有牌
        List<Integer> nums = new ArrayList<Integer>(numSet.size());
        Iterator<Integer> iterator = numSet.iterator();

        while (iterator.hasNext()){
            nums.add((iterator.next()+1) % OrderManager.NUM);
        }

        Collections.sort(nums);

        for (int index = 0; index < nums.size() - 1; index++){

            if (nums.get(index+1) != nums.get(index) + 1){
                return false;
            }
        }
        return true;

    }
}
