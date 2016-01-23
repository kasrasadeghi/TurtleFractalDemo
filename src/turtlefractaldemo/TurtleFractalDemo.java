/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turtlefractaldemo;

import apcscvm.CVMProgram;
import apcsturtle.Turtle;
import apcsturtle.TurtleMaster;
import apcsturtle.TurtleSlave;
import apcsturtle.TurtleView;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Color;

/**
 *
 * @author DSTIGANT
 */
public class TurtleFractalDemo
{

    private static PrintStream out;

    // draws a Koch Snowflake of size s
    // afterwards, the Turtle is s pixels away from its starting postion,
    // facing the same direction
    public static void kochSnowflake(TurtleSlave t, double s)
    {
        kochSnowflakeComplete(t, s); 
    }

    // draws a Sierpinski Gasket of size s
    // afterwards, the Turtle is in the same position, facing the same direction
    public static void sierpinskiGasket(TurtleSlave t, double s)
    {
        sierpinskiGasketComplete(t, s);
    }

    // draws a Fractal Tree with two branches and a trunk
    // afterwards, the Turtle is in the same position, facing the same direction
    public static void fractalTree(TurtleSlave t, double s)
    {
        fractalTreeComplete(t, s);
    }

    // draws a circle fractal
    // afterwards, the turtle is in the same position, facing the same direction
    public static void circleFractal(TurtleSlave t, double s)
    {
        circleFractalComplete( t, s );
    }

    public static void drawPentagon(Turtle shelldon, double l)
    {
        for (int i = 0; i < 5; i++) {
            shelldon.forward(l);
            shelldon.right(72);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        TurtleSlave t = setUpTurtleSlave();
        if ( t != null )
        {
            mv(t);
            t.setColor(Color.BLACK);
            
            circleFractal(t, 200);
            
        }
    }
    
    public static void kazKoch(TurtleSlave t, double l, double precision) {
        if (l > precision) {
//            t.setColor(Color.BLUE);
            kazKoch(t, l/3, precision);
            t.left(60);
//            t.setColor(Color.RED);
            kazKoch(t, l/3, precision);
            t.right(120);
//            t.setColor(Color.GREEN);
            kazKoch(t, l/3, precision);
            t.left(60);
//            t.setColor(Color.ORANGE);
            kazKoch(t, l/3, precision);
        } else {
            t.forward(l);
        }
    }
    
    public static void mv( Turtle t) {
        t.setColor(Color.WHITE);
        t.left(90);
        t.forward(100);
        t.right(180);
        
    }

    public static TurtleSlave setUpTurtleSlave()
    {
        try
        {
            Socket s = new Socket("10.160.33.125", 5832);
            out = new PrintStream(s.getOutputStream());
            TurtleSlave t = new TurtleSlave(s.getInputStream());

            TurtleView v = new TurtleView();
            new CVMProgram("Fractal", 1600, 1200, v, v, t).start();
            return t;
            
        } catch (IOException ex)
        {
            Logger.getLogger(TurtleFractalDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static void kochSnowflakeComplete(TurtleSlave t, double s)
    {
        t.startCommandBlock();
        out.println("kochSnowflake " + s);
    }

    public static void sierpinskiGasketComplete(TurtleSlave t, double s)
    {
        t.startCommandBlock();
        out.println("sierpinskiGasket " + s);
    }

    public static void fractalTreeComplete(TurtleSlave t, double s)
    {
        t.startCommandBlock();
        out.println("fractalTree " + s);
    }

    public static void circleFractalComplete(TurtleSlave t, double s)
    {
        t.startCommandBlock();
        out.println("circleFractal " + s);
    }

}
