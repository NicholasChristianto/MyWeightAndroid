package com.example.myweight;

import java.text.ParseException;

public interface StepListener {

    public void step(long timeNs) throws ParseException;

}