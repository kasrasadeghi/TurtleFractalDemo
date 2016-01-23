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
import javax.activation.DataSource;
import javax.activation.FileDataSource;

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
    public static void kochSnowflake(Turtle t, double s)
    {
        kochSnowflakeComplete(t, s); 
    }

    // draws a Sierpinski Gasket of size s
    // afterwards, the Turtle is in the same position, facing the same direction
    public static void sierpinskiGasket(Turtle t, double s)
    {
        kgasket(t, s);
    }

    public static void kgasket(Turtle t, double s) {
        if (s > 10)
        {
            kgasket(t, s/2);
            t.forward(s);
            t.right(120);
            
            kgasket(t, s/2);
            t.forward(s);
            t.right(120);
            
            kgasket(t, s/2);
            t.forward(s);
            t.right(120);
        }
    }
    
    // draws a Fractal Tree with two branches and a trunk
    // afterwards, the Turtle is in the same position, facing the same direction
    // long one is s, side of first triangle is s/3, side of first branch off triangle is s/2. (for tree)
    public static void fractalTree(Turtle t, double s)
    {
        if (s < 10) {
            return;
        }
        
        t.forward(s);
        t.left(30);
        t.forward(s/3);
        t.left(30);
        fractalTree(t, s/2);
        
        t.right(150);
        t.forward(s/3);
        t.left(30);
        fractalTree(t, s/2);
        
        t.right(150);
        t.forward(s/3);
        t.left(30);
        t.forward(s);
        t.right(180);
    }
    

    // draws a circle fractal
    // afterwards, the turtle is in the same position, facing the same direction
    //R = 2r/sqrt.3 + r
    public static void circleFractal(Turtle t, double s)
    {
        if (s < 2) return;
        double k = Math.sqrt(3)/(2 + Math.sqrt(3));
        t.arcRight(120, s);
        circleFractal(t, k * s);
        t.arcRight(120, s);
        circleFractal(t, k * s);
        t.arcRight(120, s);
        circleFractal(t, k * s);
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
//        TurtleSlave t = setUpTurtleSlave();
        Turtle t = Turtle.CreateTurtle();

        if ( t != null )
        {
            circleFractal(t, 200);
//            mv(t);
//            fractalTree(t, 200);
        }
    }
    
    public static void kazKoch(Turtle t, double l, double precision) {
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
        t.right(180);
        t.forward(200);
        t.right(180);
        t.setColor(Color.BLACK);
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
    
    public static void kochSnowflakeComplete(Turtle t, double s)
    {
//        t.startCommandBlock();
        out.println("kochSnowflake " + s);
    }

    public static void sierpinskiGasketComplete(Turtle t, double s)
    {
//        t.startCommandBlock();
        out.println("sierpinskiGasket " + s);
    }

    public static void fractalTreeComplete(Turtle t, double s)
    {
//        t.startCommandBlock();
        out.println("fractalTree " + s);
    }

    public static void circleFractalComplete(Turtle t, double s)
    {
//        t.startCommandBlock();
        out.println("circleFractal " + s);
    }

}
