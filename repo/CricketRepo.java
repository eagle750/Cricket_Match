package repo;

import Cricket.Team;

public interface CricketRepo {
    Team fetchTeamDetails(String teamName);
    void resetPlayerInfo(String teamName);
    void updateBallInfo(int cntr, Team team, int striker1, int run, int striker2,int Series_id,int matchNo);
    void updatePlayerInfo(Team team);
    void updateMatchStatus(String completed);
    int insertSeriesInfo(String teamName1, String teamName2);
    void updateSeriesInfo(int Series_id,String teamName);
    void updateSeriesWinnerInfo(int Series_id);
}
