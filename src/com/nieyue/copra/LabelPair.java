package com.nieyue.copra;

public class LabelPair {
    public float value;
    public int pos;

    public LabelPair(float var1, int var2) {
        this.value = var1;
        this.pos = var2;
    }

    public String toString() {
        return this.pos + "/" + this.value;
    }
}
