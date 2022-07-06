package com.dingke.myapp.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Resource {
    private int cpuUseRate;
    private float memoryFree;
    private float memoryTotal;
}
