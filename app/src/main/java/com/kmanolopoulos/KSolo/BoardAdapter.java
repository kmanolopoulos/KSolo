package com.kmanolopoulos.KSolo;

import android.content.Context;
import android.widget.BaseAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BoardAdapter extends BaseAdapter implements OnClickListener
{
    // Board sizes
    private final int GridXSize = 9;
    private final int GridYSize = 9;
    // Board places statuses
    private final int OUT_BOARD = 0;
    private final int EMPTY_BOARD = 1;
    private final int PAWN_BOARD = 2;
    // Views and statuses
    private Context mContext;
    private int startPosition;
    private Button[][] button;
    private BoardClass board;

    public BoardAdapter(Context context)
    {
        mContext = context;
        startPosition = -1;
        button = new Button[GridXSize][GridYSize];
        board = new BoardClass();
        board.InitBoard();
    }

    public int getCount()
    {
        return GridXSize*GridYSize;
    }

    public Object getItem(int position)
    {
        return button[position % GridXSize][position / GridXSize];
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v;
        Button b;
        int x = position % GridXSize;
        int y = position / GridXSize;

        switch (board.GetPosition(x, y))
        {
            case EMPTY_BOARD:
                v = LayoutInflater.from(mContext).inflate(R.layout.empty_button_layout, null);
                b = v.findViewById(R.id.empty_button_layout);
                button[x][y] = b;
                button[x][y].setTag(position);
                button[x][y].setOnClickListener(this);
                break;
            case PAWN_BOARD:
                if (position == startPosition)
                {
                    v = LayoutInflater.from(mContext).inflate(R.layout.selected_button_layout, null);
                    b = v.findViewById(R.id.selected_button_layout);
                }
                else
                {
                    v = LayoutInflater.from(mContext).inflate(R.layout.pawn_button_layout, null);
                    b = v.findViewById(R.id.pawn_button_layout);
                }
                button[x][y] = b;
                button[x][y].setTag(position);
                button[x][y].setOnClickListener(this);
                break;
            default:
                v = LayoutInflater.from(mContext).inflate(R.layout.out_button_layout, null);
                b = v.findViewById(R.id.out_button_layout);
                button[x][y] = b;
                button[x][y].setOnClickListener(null);
                break;
        }

        return v;
    }

    public int BackMove()
    {
        int validmoves;
        validmoves = board.BackMove();
        notifyDataSetChanged();
        return validmoves;
    }

    public int ForwardMove(BoardMove boardmove)
    {
        int validmoves;
        validmoves = board.ForwardMove(boardmove);
        notifyDataSetChanged();
        return validmoves;
    }

    public void GameOver()
    {
        ((GameActivity)mContext).GameOver(board.GetLeftPawns());
    }

    @Override
    public void onClick(View v)
    {
        BoardMove boardmove;
        int validmoves=0;

        if (startPosition < 0)
        {
            startPosition = (int)v.getTag();
            notifyDataSetChanged();
        }
        else
        {
            boardmove = new BoardMove(startPosition % GridXSize, startPosition / GridXSize,
                                      (int)v.getTag() % GridXSize, (int)v.getTag() / GridXSize);

            validmoves = ForwardMove(boardmove);
            startPosition = -1;

            if (validmoves == 0)
            {
                GameOver();
            }
        }
    }

}
