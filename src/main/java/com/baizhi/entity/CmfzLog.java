package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author: DarkSunrise
 * @date: 2019/11/28  16:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "log")
public class CmfzLog implements Serializable {
    private String id;
    private String name;
    private String time;
    private String action;
    private String status;
}
