package com.wave.Mirissa.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
public class Necklace extends Products{
    private double length;//cm

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length =length;}
}