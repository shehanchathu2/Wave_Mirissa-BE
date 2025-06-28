package com.wave.Mirissa.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Ring extends Products{

    private double size;

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size=size;}
}