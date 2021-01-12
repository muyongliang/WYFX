package com.wyfx.ft.repository;

import com.wyfx.ft.model.Type;

/**
 * Created by liu on 2017/8/10.
 */
public interface TypeRepo extends BaseRepo<Type> {
    Type findByType(String fix);
}
