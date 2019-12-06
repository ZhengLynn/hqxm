package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: DarkSunrise
 * @date: 2019/12/2  18:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapVO implements Serializable {
    private String name;
    private String value;
}
