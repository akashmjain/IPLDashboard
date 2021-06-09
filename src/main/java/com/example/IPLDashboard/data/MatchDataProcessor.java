package com.example.IPLDashboard.data;

import com.example.IPLDashboard.model.Match;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.batch.item.ItemProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MatchDataProcessor implements ItemProcessor<MatchInput, Match> {

    private static final Logger log = LoggerFactory.getLogger(MatchDataProcessor.class);

    @Override
    public Match process(final MatchInput matchInput) throws Exception {
        Match match = new Match();
        match.setId(Long.parseLong(matchInput.getId()));
        match.setCity(matchInput.getCity());
        match.setDate(LocalDate.parse(matchInput.getDate()));
        match.setPlayerOfMatch(matchInput.getPlayer_of_match());
        match.setVenue(matchInput.getVenue());
        // Set Team 1 and Team 2 depending on the inning order.
        String firstInningTeam, secondInningTeam;

        if ("bat".equals(matchInput.getToss_decision())) {
            firstInningTeam = matchInput.getToss_winner();
            secondInningTeam =
                matchInput.getToss_winner().equals(matchInput.getTeam1()) ? matchInput.getTeam2()
                    : matchInput.getTeam1();

        } else {
            firstInningTeam =
                matchInput.getToss_winner().equals(matchInput.getTeam1()) ? matchInput.getTeam2()
                    : matchInput.getTeam1();
            secondInningTeam = matchInput.getToss_winner();
        }
        match.setTeam1(firstInningTeam);
        match.setTeam2(secondInningTeam);
        match.setMatchWinner(matchInput.getToss_winner());
        match.setTossDecision(matchInput.getToss_decision());
        match.setResult(matchInput.getResult());
        match.setResultMargin(matchInput.getResult_margin());
        match.setUmpire1(matchInput.getUmpire1());
        match.setUmpire2(matchInput.getUmpire2());
        return match;
    }
}