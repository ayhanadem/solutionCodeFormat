package com.interview.fortumowebserver;


import com.interview.fortumowebserver.controller.CalculationController;
import com.interview.fortumowebserver.repository.CalculationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.concurrent.TimeoutException;

@ExtendWith(MockitoExtension.class)
 class FortumoWebServerTests
{
    @InjectMocks
    CalculationController calculationController;


    @Test
     void testMultipleEnds()
    {
        calculationController.calculationRepository = new CalculationRepository();
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final int[] responseEntity1 = new int[2];
        final int[] responseEntity2 = new int[2];


        new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    responseEntity1[0] = calculationController.getLastSum("5");
                    System.out.println(Thread.currentThread().getName() + " " + responseEntity1[0]);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

        }.start();

        new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    responseEntity1[0] = calculationController.getLastSum("5");
                    System.out.println(Thread.currentThread().getName() + " " + responseEntity1[0]);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

        }.start();

        new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    responseEntity1[0] = calculationController.getLastSum("end");
                    System.out.println(Thread.currentThread().getName() + " " + responseEntity1[0]);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

        }.start();

        assert (responseEntity1[0] == responseEntity2[0]);


        new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    responseEntity1[1] = calculationController.getLastSum("5");
                    System.out.println(Thread.currentThread().getName() + " " + responseEntity1[1]);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

        }.start();

        new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    responseEntity2[1] = calculationController.getLastSum("end");
                    System.out.println(Thread.currentThread().getName() + " " + responseEntity2[1]);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

        }.start();
        assert (responseEntity1[1] == responseEntity2[1]);
    }




    @Test
     void testLoadWithEnd() throws InterruptedException
    {
        calculationController.calculationRepository = new CalculationRepository();
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final int[] responseEntity1 = new int[6];


        for(int i =0;i <5;i++)
        {
            int finalI = i;
            new Thread()
            {
                @Override
                public void run()
                {
                    try
                    {
                        responseEntity1[finalI] = calculationController.getLastSum(String.valueOf(finalI));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }

            }.start();
        }

        new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    responseEntity1[5]  = calculationController.getLastSum("end");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

        }.start();



        assert(responseEntity1[0] == responseEntity1[5]);



    }

    @Test
     void testLoadWithMultipleEnd() throws InterruptedException
    {
        calculationController.calculationRepository = new CalculationRepository();
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final int[] responseEntity1 = new int[11];


        for(int i =0;i <5;i++)
        {
            int finalI = i;
            new Thread()
            {
                @Override
                public void run()
                {
                    try
                    {
                        responseEntity1[finalI] = calculationController.getLastSum(String.valueOf(finalI));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }

            }.start();
        }

        new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    responseEntity1[5]  = calculationController.getLastSum("end");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

        }.start();



        for(int i =6;i <10;i++)
        {
            int finalI = i;
            new Thread()
            {
                @Override
                public void run()
                {
                    try
                    {
                        responseEntity1[finalI] = calculationController.getLastSum(String.valueOf(finalI));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }

            }.start();
        }

        new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    responseEntity1[10]  = calculationController.getLastSum("end");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

        }.start();




        assert (responseEntity1[0] == responseEntity1[5] && responseEntity1[6] == responseEntity1[10]);




    }



}


