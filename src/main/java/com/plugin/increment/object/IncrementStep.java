
//--------------------------------------------------------------------------
// Copyright (c) 2010-2020, En.dennisit or Cn.苏若年
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are
// met:
//
// Redistributions of source code must retain the above copyright notice,
// this list of conditions and the following disclaimer.
// Redistributions in binary form must reproduce the above copyright
// notice, this list of conditions and the following disclaimer in the
// documentation and/or other materials provided with the distribution.
// Neither the name of the dennisit nor the names of its contributors
// may be used to endorse or promote products derived from this software
// without specific prior written permission.
// Author: dennisit@163.com | dobby | 苏若年
//--------------------------------------------------------------------------
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
