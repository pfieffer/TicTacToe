package np.com.ravi.tictactoe;

import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.provider.FontRequest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private Button button1,
            button2,
            button3,
            button4,
            button5,
            button6,
            button7,
            button8,
            button9;

    private Button gameButton;
    private Button[] buttons = new Button[9];
    private boolean gameIsOver = false;
    private int currentPlayer = 0;
    private String[] players = {"X", "O"};

    private TextView gameStatus;
    private TextView gameName;

    Typeface typeFace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeItems();

        bindEventListenerOnButtons();

    }

    private void initializeItems() {
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);

        gameName = (TextView) findViewById(R.id.gameName);
        typeFace = Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/GochiHand-Regular.ttf");
        gameName.setTypeface(typeFace);

        gameButton = (Button) findViewById(R.id.gameButton);

        gameStatus = (TextView) findViewById(R.id.gameStatus);

        buttons[0] = button1;
        buttons[1] = button2;
        buttons[2] = button3;
        buttons[3] = button4;
        buttons[4] = button5;
        buttons[5] = button6;
        buttons[6] = button7;
        buttons[7] = button8;
        buttons[8] = button9;

    }

    private void bindEventListenerOnButtons() {

        for(Button gameButton : buttons) { gameButton.setOnClickListener(buttonsListener); }
        gameButton.setOnClickListener(gameButtonListener);
    }

    View.OnClickListener gameButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for(Button button : buttons) {
                button.setText("");
                button.setTextColor(Color.BLACK);
            }

            gameStatus.setText(R.string.game_button);
            currentPlayer = 0;
            gameIsOver = false;
        }
    };

    private View.OnClickListener buttonsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button button = (Button) findViewById(v.getId());
            if(gameIsOver) { return; }
            if(!isValid(button)) {
                gameStatus.setText("Invalid Move!");
            } else {
                setSymbol(button, players[currentPlayer]);
                gameIsOver = winnerExists();

                if(gameIsOver) {
                    playWinSound();
                    gameStatus.setText("Player " + players[currentPlayer] + " wins!");
                    return;
                }

                if(boardIsFull()) { gameStatus.setText("DRAW"); playDrawSound(); return; }

                currentPlayer = currentPlayer ^ 1;

                gameStatus.setText("It\'s " + "Player " + players[currentPlayer] +  "\'s turn!");
            }
        }
    };



    private void setSymbol(Button button, String symbol) {
        button.setText(symbol);
    }

    private boolean isValid(Button button) {
        return button.getText().toString().length() == 0;
    }

    private boolean boardIsFull() {
        for(Button button : buttons) {
            if(isValid(button)) { return false; }
        }
        return true;
    }

    private boolean winnerExists() {
        if(winnerExistsHelper(0, 1, 2)) { return true; }
        if(winnerExistsHelper(3, 4, 5)) { return true; }
        if(winnerExistsHelper(6, 7, 8)) { return true; }

        if(winnerExistsHelper(0, 4, 8)) { return true; }
        if(winnerExistsHelper(2, 4, 6)) { return true; }

        if(winnerExistsHelper(0, 3, 6)) { return true; }
        if(winnerExistsHelper(1, 4, 7)) { return true; }
        if(winnerExistsHelper(2, 5, 8)) { return true; }

        return false;
    }

    private boolean winnerExistsHelper(int firstButton, int secondButton, int thirdButton) {
        if(buttons[firstButton].getText().toString() == players[currentPlayer] &&
                buttons[secondButton].getText().toString() == players[currentPlayer] &&
                buttons[thirdButton].getText().toString() == players[currentPlayer]) {

            changeButtonTextColor(firstButton, secondButton, thirdButton);
            return true;
        }

        return false;
    }

    private void changeButtonTextColor(int firstButton, int secondButton, int thirdButton) {
        buttons[firstButton].setTextColor(Color.argb(255, 230, 79, 97));
        buttons[secondButton].setTextColor(Color.argb(255, 230, 79, 97));
        buttons[thirdButton].setTextColor(Color.argb(255, 230, 79, 97));
    }

    private void playWinSound(){
        MediaPlayer mp = MediaPlayer.create(MainActivity.this, R.raw.win);//for sounds
        mp.start();
    }

    private void playDrawSound() {
        MediaPlayer mp = MediaPlayer.create(MainActivity.this, R.raw.draw);//for sounds
        mp.start();
    }
}
