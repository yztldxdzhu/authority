package com.yhguo.common.tool;

import java.util.LinkedList;

public class IntegerArray {

    //求两个数组的差集
    public static Integer[] substract(Integer[] arr1, Integer[] arr2) {
        LinkedList<Integer> list = new LinkedList<>();
        for (Integer a1 : arr1) {
            if (!list.contains(a1)) {
                list.add(a1);
            }
        }
        for (Integer a2 : arr2) {
            if (list.contains(a2)) {
                list.remove(a2);
            }
        }
        Integer[] res = {};
        return list.toArray(res);
    }

    public static void main(String[] args) {
        Integer[] arr1 = {1, 2, 3};
        Integer[] arr2 = {};
        Integer[] res = substract(arr2, arr1);
        for(Integer a : res){
            System.out.println(a);
        }
    }

}
