package com.kmanolopoulos.KSolo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BoardClass
{
    private final int GridXSize = 9;
    private final int GridYSize = 9;
    private final int OUT_BOARD = 0;
    private final int EMPTY_BOARD = 1;
    private final int PAWN_BOARD = 2;
    private int[][] board;
    private List<BoardMove> validmovelist;
    private List<BoardMove> donemovelist;

    public BoardClass()
    {
        board = new int[GridXSize][GridYSize];
        validmovelist = new ArrayList<BoardMove>();
        validmovelist.clear();
        donemovelist = new ArrayList<BoardMove>();
        donemovelist.clear();
    }

    public void InitBoard()
    {
        for (int i=0; i<GridXSize; i++)
        {
            for (int j=0; j<GridYSize; j++)
            {
                if ((i == 4) && (j == 4))
                {
                    board[i][j] = EMPTY_BOARD;
                }
                else if (((i >= 3) && (i <= 5)) || ((j >= 3) && (j <= 5)))
                {
                    board[i][j] = PAWN_BOARD;
                }
                else
                {
                    board[i][j] = OUT_BOARD;
                }
            }
        }

        donemovelist.clear();
        LocateValidMoves();
    }

    public int GetPosition(int x, int y)
    {
        if ((x < 0) || (x >= GridXSize) || (y < 0) || (y >= GridYSize))
        {
            return OUT_BOARD;
        }

        return board[x][y];
    }

    private int LocateValidMoves()
    {
        validmovelist.clear();

        for (int i=0;i<GridXSize;i++)
        {
            for (int j=0;j<GridYSize;j++)
            {
                // left
                if (j-2 >= 0)
                {
                    if ((board[i][j] == PAWN_BOARD) && (board[i][j-1] == PAWN_BOARD) && (board[i][j-2] == EMPTY_BOARD))
                    {
                        validmovelist.add(new BoardMove(i, j, i, j - 2));
                    }
                }

                // right
                if (j+2 < GridYSize)
                {
                    if ((board[i][j] == PAWN_BOARD) && (board[i][j+1] == PAWN_BOARD) && (board[i][j+2] == EMPTY_BOARD))
                    {
                        validmovelist.add(new BoardMove(i, j, i, j + 2));
                    }
                }

                // up
                if (i-2 >= 0)
                {
                    if ((board[i][j] == PAWN_BOARD) && (board[i-1][j] == PAWN_BOARD) && (board[i-2][j] == EMPTY_BOARD))
                    {
                        validmovelist.add(new BoardMove(i, j, i-2, j));
                    }
                }

                // down
                if (i+2 < GridXSize)
                {
                    if ((board[i][j] == PAWN_BOARD) && (board[i+1][j] == PAWN_BOARD) && (board[i+2][j] == EMPTY_BOARD))
                    {
                        validmovelist.add(new BoardMove(i, j, i+2, j));
                    }
                }
            }
        }

        return validmovelist.size();
    }

    private boolean IsValidMove(BoardMove boardMove)
    {
        for (Iterator<BoardMove> b = validmovelist.iterator(); b.hasNext(); )
        {
            BoardMove item = b.next();

            if (boardMove.IsSame(item))
            {
                return true;
            }
        }

        return false;
    }

    public int ForwardMove(BoardMove boardMove)
    {
        int x1, y1, x2, y2;

        if (IsValidMove(boardMove))
        {
            x1 = boardMove.GetX1();
            y1 = boardMove.GetY1();
            x2 = boardMove.GetX2();
            y2 = boardMove.GetY2();
            board[x1][y1] = EMPTY_BOARD;
            board[(x1+x2)/2][(y1+y2)/2] = EMPTY_BOARD;
            board[x2][y2] = PAWN_BOARD;
            donemovelist.add(boardMove);
        }
        return LocateValidMoves();
    }

    public int BackMove()
    {
        int x1, y1, x2, y2;
        BoardMove boardMove;

        if (donemovelist.size() > 0)
        {
            boardMove = donemovelist.get(donemovelist.size() - 1);
            x1 = boardMove.GetX1();
            y1 = boardMove.GetY1();
            x2 = boardMove.GetX2();
            y2 = boardMove.GetY2();
            board[x1][y1] = PAWN_BOARD;
            board[(x1+x2)/2][(y1+y2)/2] = PAWN_BOARD;
            board[x2][y2] = EMPTY_BOARD;
            donemovelist.remove(donemovelist.size() - 1);
        }
        return LocateValidMoves();
    }

    public int GetLeftPawns()
    {
        int pawns = 0;
        for (int i=0;i<GridXSize;i++)
        {
            for (int j = 0; j < GridYSize; j++)
            {
                if (board[i][j] == PAWN_BOARD)
                {
                    pawns++;
                }
            }
        }
        return pawns;
    }
}
