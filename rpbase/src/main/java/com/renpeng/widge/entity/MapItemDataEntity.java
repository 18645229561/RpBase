package com.renpeng.widge.entity;

import java.util.List;

/**
 * Created by renpeng on 16/11/10.
 */
public class MapItemDataEntity<K,T> {
    public K k;

    public List<T> list;

    public MapItemDataEntity(K k,List<T> list){
        this.k = k;
        this.list = list;
    }
}
