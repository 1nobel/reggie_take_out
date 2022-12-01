package com.fct.reggie.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Page<T> implements Serializable {
    private static final long serialVersionUID = 8545996863226528798L;
    protected List<T> records;
    protected long total;

}
