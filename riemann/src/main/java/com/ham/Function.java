package com.ham;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

// https://www.geeksforgeeks.org/java/how-to-evaluate-math-expression-given-in-string-form-in-java/
public class Function {
    String expression;
    ScriptEngineManager manager; 
    ScriptEngine engine;

    // Constructor
    // y = USER INPUT
    public Function()
    {
        this.expression = "";
        this.manager = new ScriptEngineManager();
        this.engine = manager.getEngineByName("JavaScript");
    }
    public Function(String expression)
    {
        //this is a vibe coded support for implicit multiplication
            // Add implicit multiplication: 2x becomes 2*x
            expression = expression.replaceAll("(\\d)([a-zA-Z])", "$1*$2");
            // Add implicit multiplication: )x becomes )*x
            expression = expression.replaceAll("(\\))([a-zA-Z])", "$1*$2");
            // Add implicit multiplication: x( becomes x*(
            expression = expression.replaceAll("([a-zA-Z])(\\()", "$1*$2");
        
        this.expression = expression;
        this.manager = new ScriptEngineManager();

        //another vibe coded support for nashorn deprecation in java 15
            this.engine = manager.getEngineByName("nashorn");
            if (this.engine == null) {
                this.engine = manager.getEngineByName("JavaScript");
            }
            if (this.engine == null) {
                System.err.println("ERROR: JavaScript engine not available!");
            } else {
                System.out.println("JavaScript engine loaded: " + this.expression);
            }
    }

    public double getY(double x)
    {
        try {
            return (double) engine.eval(this.replaceX(x));
        } catch (ScriptException e) {
            e.printStackTrace();
            return Double.NaN;
        }
    }
    public String replaceX(double x)
    {
        String xValue = "(" + Double.toString(x) + ")";
        return this.expression.replaceAll("x", xValue);
    }
}
