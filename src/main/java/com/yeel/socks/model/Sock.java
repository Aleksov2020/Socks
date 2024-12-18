package com.yeel.socks.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Sock {
    @Id
    @GeneratedValue
    private Long id;
    private String color;
    private int percentage;
    private int count;

    public Sock(String color,
                int percentage) {
        this.color = color;
        this.percentage = percentage;
        this.count = 0;
    }

    public Sock increaseCount(int increment) {
        this.count += increment;

        return this;
    }

    public Sock decreaseCount(int decrement) throws IllegalArgumentException {
        if (this.count - decrement < 0) {
            throw new IllegalArgumentException("Not enough socks in stock");
        }

        this.count -= decrement;
        
        return this;
    }

    public Sock update(String color, int percentage, int count) {
        this.color = color;
        this.percentage = percentage;
        this.count = count;

        return this;
    }
}
