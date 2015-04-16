/*
 * Copyright (c) 2015, www.jd.com. All rights reserved.
 *
 * 警告：本计算机程序受著作权法和国际公约的保护，未经授权擅自复制或散布本程序的部分或全部、以及其他
 * 任何侵害著作权人权益的行为，将承受严厉的民事和刑事处罚，对已知的违反者将给予法律范围内的全面制裁。
 *
 */
package com.plugin.increment.object;

import java.io.Serializable;

/**
 * Description:
 *              step for sequence increment
 * @author pudongping
 * @version 1.0.1
 */
public class IncrementStep implements Serializable {

    /**
     * increment start value for step increment restrict
     */
    private Long beginValue;

    /**
     * increment end value for step increment restrict
     */
    private Long endValue;


    public IncrementStep() {
        this(0L,0L);
    }

    public IncrementStep(Long beginValue, Long endValue) {
        this.beginValue = beginValue;
        this.endValue = endValue;
    }


    public long getBeginValue() {
        return beginValue;
    }

    public void setBeginValue(Long beginValue) {
        this.beginValue = beginValue;
    }

    public long getEndValue() {
        return endValue;
    }

    public void setEndValue(Long endValue) {
        this.endValue = endValue;
    }

    public Long incrementAndGet() {
        return ++ beginValue;
    }

}
