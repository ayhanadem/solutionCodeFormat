package com.interview.fortumowebserver.utils;

import com.interview.fortumowebserver.repository.CalculationRepository;

import java.util.concurrent.atomic.AtomicInteger;

public class ProcessRequest implements Runnable
{


    public ProcessRequest(CalculationRepository rep, String arg, int stage)
    {
        this.rep =rep;
        this.arg = arg;
        this.stage= stage;
    }
    CalculationRepository rep;
    private int stage;


    private String arg;

    @Override

    public void run()
    {
        if ("end".equals(arg))
        {
                rep.getBarrier().releaseAll();
        }
        else
        {
            rep.addNumber(Integer.valueOf(arg), stage);
            try
            {
                rep.getBarrier().block();

            }
            catch (InterruptedException e)
            {
                //avoid error
            }
        }
    }

    public int getStage()
    {
        return stage;
    }
}
