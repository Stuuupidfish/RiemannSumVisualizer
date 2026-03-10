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

    private double sum;

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

        this.sum = 0;
    }

    //getters and setters 

    public double getSum()
    {
        return sum;
    }
    public void setSum(double sum)
    {
        this.sum = sum;
    }

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

    //we dont want to change the input fields so no setters
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

    public void setInputsActive(boolean nActive, boolean aActive, boolean bActive, boolean yActive)
    {
        nInput.setActive(nActive);
        aInput.setActive(aActive);
        bInput.setActive(bActive);
        yInput.setActive(yActive);
    }

    public void commitActiveInput(UIHandler UI)
    {
        if (activeInput == null)
        {
            return;
        }
        
        if (activeInput == nInput)
        {
            int newN = parseInt(nInput.getValue(), n);
            setN(newN);
            UI.setN(newN + "");
        }
        else if (activeInput == aInput)
        {
            double newA = parseDouble(aInput.getValue(), a);
            setA(newA);
            UI.setA(newA + "");
        }
        else if (activeInput == bInput)
        {
            double newB = parseDouble(bInput.getValue(), b);
            setB(newB);
            UI.setB(newB + "");
        }
        else if (activeInput == yInput)
        {
            String newExpression = yInput.getValue();
            setExpression(newExpression);
            UI.setExpression(newExpression);
        }

        //auto-swaps a and b if a > b so program doesnt freeze
        if (this.a > this.b)
        {
            double temp = this.a;
            this.a = this.b;
            this.b = temp;
            UI.setA(this.a + "");
            UI.setB(this.b + "");
            aInput.setValue(this.a + "");
            bInput.setValue(this.b + "");
        }

        setInputsActive(false, false, false, false);
        setActiveInput(null);
        setCaretActive(false);
    }

    //parsing helpers
    private double parseDouble(String str, double fallback) throws NumberFormatException
    {
        try
        {
            return Double.parseDouble(str.trim());
        }
        catch (NumberFormatException e)
        {
            return fallback;
        }
    }
    private int parseInt(String str, int fallback) throws NumberFormatException
    {
        try
        {
            return Integer.parseInt(str.trim());
        }
        catch (NumberFormatException e)
        {
            return fallback;
        }
    }
}
