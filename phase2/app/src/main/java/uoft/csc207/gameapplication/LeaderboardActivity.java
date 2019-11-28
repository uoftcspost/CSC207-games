package uoft.csc207.gameapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import uoft.csc207.gameapplication.Utility.GameRequestService.Models.LeaderBoard;
import uoft.csc207.gameapplication.Utility.GameRequestService.Models.Score;
import uoft.csc207.gameapplication.Utility.GameRequestService.ScoreService;

public class LeaderboardActivity extends AppCompatActivity {

//    private String[] topTenPlayers = new String[10];
//    private String[] topTenScores = new String[10];
//    private String[] topTenLinesCleared = new String[10];
//    private String[] toptenTimes = new String[10];

//    private static final String FILE = "UserData.json";
//    private JSONArray database;

    private ScoreService scoreService = new ScoreService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

//        String[][] topPlays = getTopPlayers();
        scoreService.setContext(this);

        scoreService.getGlobalLeaderboards("WrapperGame", new GlobalLeaderBoardCallback() {
            @Override
            public void onSuccess(LeaderBoard leaderBoard) {
                initalize(leaderBoard);
            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onWait() {

            }
        });

    }

    public void initalize(LeaderBoard leaderBoard) {

        List<Score> scores = leaderBoard.getScores();

        String[] topTenPlayers = new String[10];
        String[] topTenScores = new String[10];

        for (int i = 0; i < 10; i++) {
            Score score = scores.get(i);
            topTenPlayers[i] = score.getUsername();
            topTenScores[i] = score.getScore();
        }

        System.out.println(topTenPlayers[5]);

        int[] playerTextViewIds = {R.id.player1, R.id.player2, R.id.player3, R.id.player4,
                R.id.player5, R.id.player6, R.id.player7, R.id.player8, R.id.player9,
                R.id.player10};

        int[] scoreTextViewIds = {R.id.score1, R.id.score2, R.id.score3, R.id.score4, R.id.score5,
                R.id.score6, R.id.score7, R.id.score8, R.id.score9, R.id.score10};

        for (int i=0; i<10; i++) {


            TextView playerTextView = (TextView) findViewById(playerTextViewIds[i]);
            playerTextView.setTextSize(15);
            playerTextView.setText(topTenPlayers[i]);

            TextView scoreTextView = (TextView) findViewById(scoreTextViewIds[i]);
            scoreTextView.setTextSize(15);
            scoreTextView.setText(topTenScores[i]);

        }

    }


    public interface GlobalLeaderBoardCallback {
        void onSuccess(LeaderBoard leaderBoard);
        void onFailure();
        void onWait();
    }

//    /**
//     * loads the data base from assets or from android memory
//     */
//    private void loadDatabase() {
//        try {
//            int i = 0;
//            String jsonString = "";
//            try {
//                FileInputStream fileInputStream = openFileInput(FILE);
//                while ((i = fileInputStream.read()) != -1) {
//                    jsonString += (char) i;
//                }
//                fileInputStream.close();
//            }
//            catch (FileNotFoundException e) {
//                BufferedReader bufferReader = new BufferedReader(
//                        new InputStreamReader(getAssets().open(FILE)));
//                while ((i = bufferReader.read()) != -1) {
//                    jsonString += (char) i;
//                }
//                bufferReader.close();
//            }
//            JSONObject jsonFile = new JSONObject(jsonString);
//            database = jsonFile.getJSONArray("users");
//        }
//        catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        catch (JSONException e) {
//            e.printStackTrace();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * gets the top plays from the data base
//     * @return a 2 nested list that contains 10 usernames (index 1) and 10 scores (index 0) with
//     *         name corresponding to score respectively
//     */
//    public String[][] getTopPlayers() {
//        String[][] topScores = {{"0", "0"}, {"0", "0"}, {"0", "0"}, {"0", "0"}, {"0", "0"},
//                                {"0", "0"}, {"0", "0"}, {"0", "0"}, {"0", "0"}, {"0", "0"}};
//
//        Comparator<String[]> byValue = new Comparator<String[]>() {
//            @Override
//            public int compare(String[] o1, String[] o2) {
//                return Integer.parseInt(o2[0]) - Integer.parseInt(o1[0]);
//            }
//        };
//
//        for (int i = 0; i < database.length(); i++) {
//            try {
//                JSONObject user = database.getJSONObject(i);
//                String username = user.getString("username");
//                JSONArray userScores = user.getJSONArray("topPlays");
//                for (int s = 0; s < userScores.length(); s++) {
//                    String score = userScores.getJSONObject(s).getString(String.format("top%d", s));
//                    if (Integer.valueOf(score) > Integer.valueOf(topScores[9][0])) {
//                        topScores[9][0] = score;
//                        topScores[9][1] = username;
//                        Arrays.sort(topScores, byValue);
//                    }
//                    else {
//                        break;
//                    }
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//        String[][] TopPlayers = new String[2][10];
//        // 0 is scores
//        // 1 is users
//        for (int i = 0; i < topScores.length; i++) {
//            System.out.println(i);
//            TopPlayers[0][i] = topScores[i][0];
//            TopPlayers[1][i] = topScores[i][1];
//        }
//        return TopPlayers;
//    }
}

