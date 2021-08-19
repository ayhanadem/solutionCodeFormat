package com.interview.fortumowebserver.repository;

import com.interview.fortumowebserver.utils.Barrier;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class CalculationRepository
{
    private static final Barrier barrier = new Barrier();
     private AtomicInteger stage=new AtomicInteger(1);
    ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();

    public static Barrier getBarrier()
    {
        return barrier;
    }

    public AtomicInteger getStage()
    {
        return stage;
    }
    public void incrementStage()
    {
        this.stage.incrementAndGet();
    }

    public int getResult(int stage)
    {
        return map.get(stage);
    }

    public synchronized void addNumber(int number, int stage)
    {

        if(map.containsKey(stage))
        {
             map.put(stage, map.get(stage) + number);
        }
        else
        {
             map.put(stage, number);
        }
    }


}
