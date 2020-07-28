package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // my variables
    private Button[][] buttons = new Button[3][3]; // Two dimensional array

    private boolean player1Turn = true; // Player starts playing when game is opened.

    private int roundCount; // counts the rounds or number of games
    // points for each player
    private  int player1Points;
    private int player2Points;
    private int drawPoints;
    // display player points
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;
    private TextView textViewDraws;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);
        textViewDraws = findViewById(R.id.text_draw);
        // nested loop to loop throw rows & columns of the two dimensional array
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++){
                String btnID = "btn_" + i + j; // button ids
                int resID = getResources().getIdentifier(btnID, "id", getPackageName()); // find view by id
                buttons[i][j] = findViewById(resID); // reference buttons
                buttons[i][j].setOnClickListener(this); // main activity set to onclicklistener
            }
        }
        Button buttonReset = findViewById(R.id.btn_reset); // Reset button
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (!((Button) view).getText().toString().equals("")) { // checks if button is empty
            return;
        }

        if (player1Turn) { // checks if its player 1s turn to play
            ((Button) view).setText("X"); // if so set button to 'X'
        } else { // if not player 1s turn
            ((Button) view).setText("O"); // set button to 'O'
        }

        roundCount++; // increase round count by 1
        // check who won
        if (checkForWin()){
            if (player1Turn){
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9){
            draw();
        } else {
            player1Turn = !player1Turn;
        }
    }

    private boolean checkForWin() { // check whether some1 wins or not
        String[][] field = new String[3][3];
        // nested loop to loop through all buttons
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();// save buttons in this array
            }
        }
        // go through rows all rows and columns
        for (int i = 0; i < 3; i++) {
            // go through rows
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])// compare three fields next to each other
                    && !field[i][0].equals("")) { // make sure not 3 times empty field
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            // go through columns
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i]) // compare three fields next to each other
                    && !field[0][i].equals("")) { // make sure not 3 times empty field
                return true;
            }
        }
        // check diagonal left to bottom right
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2]) // compare three fields next to each other
                && !field[0][0].equals("")) { // make sure not 3 times empty field
            return true;
        }
        // check diagonal right to bottom left
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0]) // compare three fields next to each other
                && !field[0][2].equals("")) { // make sure not 3 times empty field
            return true;
        }
        return false;
    }
    // show a toast popup for all player1wins and update player1points
    private void player1Wins() {
        player1Points++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }
    // show a toast popup for all player2wins and update player2points
    private void player2Wins() {
        player2Points++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }
    // show a popup for all draws and update drawpoints
    private void draw() {
        drawPoints++;
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }
    // update each players points after a win
    private void updatePointsText() {
        textViewPlayer1.setText("Player 1: " + player1Points);
        textViewPlayer2.setText("Player 2: " + player2Points);
        textViewDraws.setText("Draws: 0" + drawPoints);
    }
    // reset the game board, roundcount, and set player1 to player
    private void resetBoard() {
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        player1Turn = true;
    }
    // reset the board, roundcount, set player1 to play and set all playerpoints to 0
    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
        drawPoints = 0;
        updatePointsText();
        resetBoard();
    }
    // saves state on device rotation
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("drawPoints", drawPoints);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }
    // restores state on device rotation
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        drawPoints = savedInstanceState.getInt("drawPoints");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}