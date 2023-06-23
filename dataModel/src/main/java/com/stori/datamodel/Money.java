package com.stori.datamodel;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Money {
    private long number;
    public Money(long number) {
        this.number = number;
    }

    public Money() {

    }


    @Override
    public boolean equals(Object m) {
        if (m == this) {
            return true;
        }

        if (!(m instanceof Money)) {
            return false;
        }
        Money m2 = (Money) m;
        return this.getNumber() == m2.getNumber();
    }

    public void setNumber(long num) {
        this.number = num;
    }

    public long getNumber() {
        return number;
    }
}
