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
        this.expression = processExpression(expression);
        this.manager = new ScriptEngineManager();
        this.engine = manager.getEngineByName("JavaScript");
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
    private String replaceX(double x)
    {
        String xValue = "(" + Double.toString(x) + ")";
        return this.expression.replaceAll("x", xValue);
    }

    public String getExpression()
    {
        return expression;
    }
    public void setExpression(String expression)
    {
        expression = processExpression(expression);
    }

    private String processExpression(String expression)
    {
        //this is a vibe coded support for string parsing
            // Add implicit multiplication: 2x becomes 2*x
            expression = expression.replaceAll("(\\d)([a-zA-Z])", "$1*$2");
            // Add implicit multiplication: )x becomes )*x
            expression = expression.replaceAll("(\\))([a-zA-Z])", "$1*$2");
            // Add implicit multiplication: x( becomes x*(
            expression = expression.replaceAll("([a-zA-Z])(\\()", "$1*$2");
        
            // Convert mathematical notation to JavaScript:
            // x^2 becomes Math.pow(x, 2) (Nashorn doesn't support ** operator)
            expression = expression.replaceAll("([a-zA-Z0-9)]+)\\^([a-zA-Z0-9(]+)", "Math.pow($1,$2)");
            
            // Add Math. prefix to trig and math functions
            expression = expression.replaceAll("\\bsin\\(", "Math.sin(");
            expression = expression.replaceAll("\\bcos\\(", "Math.cos(");
            expression = expression.replaceAll("\\btan\\(", "Math.tan(");
            expression = expression.replaceAll("\\bsqrt\\(", "Math.sqrt(");
            expression = expression.replaceAll("\\babs\\(", "Math.abs(");
        
            System.out.println("Converted to: " + expression);
        
        return expression;
    }

}
