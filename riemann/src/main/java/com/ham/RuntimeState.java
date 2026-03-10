package com.ham;

public class RuntimeState {
    private Function function;
    private String expression;
    private int n;
    private double a;
    private double b;
    private String approxType;
    private boolean caretActive;
    private InputField activeInput;

    private InputField nInput;
    private InputField aInput;
    private InputField bInput;
    private InputField yInput;

    public RuntimeState(int n, double a, double b, String expression, String approxType
                        , InputField nInput, InputField aInput, InputField bInput, InputField yInput)
    {
        this.n = n;
        this.a = a;
        this.b = b;
        this.expression = expression;
        this.approxType = approxType;
        this.function = new Function(expression);
        this.caretActive = false;
        this.activeInput = null;

        this.nInput = nInput;
        this.aInput = aInput;
        this.bInput = bInput;
        this.yInput = yInput;
    }

    //getters and setters 
    //do I need to set function? y depends on expression
    //IM SKIPPING IT FO RNOW
    public void setExpression(String expression)
    {
        this.expression = expression;
        this.function = new Function(expression);
    }
    public String getExpression()
    {
        return expression;
    }

    public void setApproxType(String approxType)
    {
        this.approxType = approxType;
    }
    public String getApproxType()
    {
        return approxType;
    }

    public void setN(int n)
    {
        this.n = n;
    }
    public int getN()
    {
        return n;
    }

    public void setA(double a)
    {
        this.a = a;
    }
    public double getA()
    {
        return a;
    }

    public void setB(double b)
    {
        this.b = b;
    }
    public double getB()
    {
        return b;
    }

    //we dont want to change the input fields themselves just whether they are active or not so no setters
    public InputField getNInput()
    {
        return nInput;
    }
    public InputField getAInput()
    {
        return aInput;
    }
    public InputField getBInput()
    {
        return bInput;
    }
    public InputField getYInput()
    {
        return yInput;
    }

    public InputField getActiveInput()
    {
        return activeInput;
    }
    public void setActiveInput(InputField input)
    {
        activeInput = input;
    }

    public boolean getCaretActive()
    {
        return caretActive;
    }
    public void setCaretActive(boolean active)
    {
        caretActive = active;
    }

    public Function getFunction()
    {
        return function;
    }

    //NOT DONE

    public void setInputActive(boolean nActive, boolean aActive, boolean bActive, boolean yActive)
    {
        nInput.setActive(nActive);
        aInput.setActive(aActive);
        bInput.setActive(bActive);
        yInput.setActive(yActive);
    }
}
