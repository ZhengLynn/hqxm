package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: DarkSunrise
 * @date: 2019/12/4  15:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Counter implements Serializable {
    @Id
    private String id;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date last_date;
    private Integer counts;
    private String course_id;
    private String user_id;
}
