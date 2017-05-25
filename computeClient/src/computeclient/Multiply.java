/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computeclient;

import compute.common.Task;
import java.io.Serializable;
/**
 *
 * @author igorribeiro
 */
public class Multiply implements Task<Double>, Serializable
{
    private final double a;
    private final double b;
    
    public Multiply(double vala, double valb)
    {
        a = vala;
        b = valb;
    }

    @Override
    public Double execute() 
    {
        return a * b;
    }
}
