package com.NBAPlayers.demo.service;

import com.NBAPlayers.demo.model.NBAPlayer;
import com.NBAPlayers.demo.model.NBAPlayerDetailsResponse;
import com.NBAPlayers.demo.repository.NBAPlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;



@Service
public class NbaPlayerService {

    private static final Logger logger = LoggerFactory.getLogger(NbaPlayerService.class);
    @Autowired
    NBAPlayerRepository nbaPlayerRepository;

    @Autowired
    PlayerDetailService playerDetailService;

    public void saveToDb(NBAPlayer player) {
        NBAPlayer playerInDb = nbaPlayerRepository.findFirstById(player.getId());
        if (playerInDb != null) {
            playerInDb.updateFrom(player);
            nbaPlayerRepository.save(playerInDb);
        }else {
            nbaPlayerRepository.save(player);
        }
    }

    @Scheduled(fixedRate =  15 * 60 * 1000)
    public void updateDbFromApi() throws IOException {
        logger.info("starting job for update cache");
        Map<String, NBAPlayerDetailsResponse> apiPlayers =  playerDetailService.getPlayersFromApiAsMap(playerDetailService.getAllPlayersFromApi());
        List<NBAPlayer> playerList = nbaPlayerRepository.findAll();
        for (NBAPlayer player : playerList) {
            NBAPlayerDetailsResponse apiPlayer = apiPlayers.get(player.getId()) ;
            player.setLast_name(apiPlayer.getLast_name());
            player.setFirst_name(apiPlayer.getFirst_name());
            nbaPlayerRepository.save(player);
        }
        logger.info("finished job for update cache");
    }
}
