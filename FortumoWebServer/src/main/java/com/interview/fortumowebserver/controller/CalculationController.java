package com.interview.fortumowebserver.controller;

import com.interview.fortumowebserver.repository.CalculationRepository;
import com.interview.fortumowebserver.utils.ProcessRequest;
import com.interview.fortumowebserver.utils.Barrier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class CalculationController
{

    @Autowired
    public CalculationRepository calculationRepository;

    Barrier barrier = new Barrier();


    @PostMapping("/{arg}")
    public int getLastSum(@RequestParam("arg") String arg)
    {
        AtomicInteger stage= calculationRepository.getStage();
        int stageInt=stage.get();
        ProcessRequest calc = new ProcessRequest(calculationRepository, arg,stageInt);
        calc.run();

        if("end".equals(arg))
        {
            calculationRepository.incrementStage();
        }
        return calculationRepository.getResult(stageInt);
    }


}
