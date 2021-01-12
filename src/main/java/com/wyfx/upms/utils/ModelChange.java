package com.wyfx.upms.utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: liuxingyu
 * DATE: 2017-07-03.15:33
 * description:
 * version:
 */
public class ModelChange {
    public static <T, A> List<A> changeList(Class<A> c, List<T> page) throws Exception {
        List<A> list = new ArrayList(page.size());
        for (T t : page) {
            A a = c.newInstance();
            BeanUtils.copyProperties(t, a);
            list.add(a);
        }
        return list;
    }

    public static <T, A> A changeEntity(Class<A> c, T t) throws Exception {
        A a = c.newInstance();
        BeanUtils.copyProperties(t, a);
        return a;
    }
}
