package com.craftedsouls.data;

import com.craftedsouls.data.types.DamageType;

public class DamageData {
    public DamageType type;
    public int max;
    public int min;
    public DamageData(DamageType type, int min, int max) {
        this.type = type;
        this.max = max;
        this.min = min;
    }
}
