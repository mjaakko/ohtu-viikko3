package ohtu;

public class TennisGame {
    private static final int ADVANTAGE_THRESHOLD = 1;
    private static final int WINNING_THRESHOLD = 1;
    
    private int player1score = 0;
    private int player2score = 0;
    private String player1Name;
    private String player2Name;

    public TennisGame(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    public void wonPoint(String playerName) {
        if (playerName.equals(player1Name)) {
            player1score += 1;
        }
        if (playerName.equals(player2Name)) {
            player2score += 1;
        }
    }

    public String getScore() {
        String score = "";
        int tempScore=0;
        if (player1score == player2score) {
            return getScoreWhenTied(player2score);
        } else if (player1score>=4 || player2score>=4)
        {
            return getScoreWhenWinning();
        }
        else
        {
            return getScoreNormal();
        }
    }
    
    private String getScoreWhenTied(int score) {
        String text = getScoreText(score);
        
        return text == null ? "Deuce" : text+"-All";
    }
    
    private String getScoreWhenWinning() {
        if (player1score - player2score == ADVANTAGE_THRESHOLD) {
            return "Advantage player1";
        } else if (player2score - player1score == ADVANTAGE_THRESHOLD) {
            return "Advantage player2";
        } else if (player1score - player2score >= WINNING_THRESHOLD) {
            return "Win for player1";
        } else {
            return "Win for player2";
        }
    }
    
    private String getScoreNormal() {
        return getScoreText(player1score)+"-"+getScoreText(player2score);
    }
    
    private String getScoreText(int score) {
        switch(score)  {
            case 0:
                return "Love";
            case 1:
                return "Fifteen";
            case 2:
                return "Thirty";
            case 3:
                return "Forty";
            default:
                return null;
        }
    }
}