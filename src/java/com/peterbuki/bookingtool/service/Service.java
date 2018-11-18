package com.peterbuki.bookingtool.service;

import com.peterbuki.bookingtool.model.Dto;

public abstract class Service<T extends Dto> {

    public abstract T findByExample(T example);
}
