package com.kmanolopoulos.KSolo;

public class BoardMove
{
    int x1, y1, x2, y2;

    public BoardMove(int a1, int b1, int a2, int b2)
    {
        x1 = a1;
        y1 = b1;
        x2 = a2;
        y2 = b2;
    }

    public int GetX1()
    {
        return x1;
    }

    public int GetY1()
    {
        return y1;
    }

    public int GetX2()
    {
        return x2;
    }

    public int GetY2()
    {
        return y2;
    }

    public boolean IsSame(BoardMove b)
    {
        if ((x1 == b.GetX1()) && (y1 == b.GetY1()) && (x2 == b.GetX2()) && (y2 == b.GetY2()))
        {
            return true;
        }
        return false;
    }
}
