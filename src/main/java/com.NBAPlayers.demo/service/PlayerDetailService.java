package com.NBAPlayers.demo.service;

import com.NBAPlayers.demo.model.NBADetailList;
import com.NBAPlayers.demo.model.NBAPlayer;
import com.NBAPlayers.demo.model.NBAPlayerDetailsResponse;
import com.NBAPlayers.demo.repository.NBAPlayerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

@Service
public class PlayerDetailService {
    @Autowired
    ObjectMapper om;

    @Autowired
    NbaPlayerService nbaPlayerService;


    public NBADetailList getAllPlayersFromApi() throws IOException {
        String res = getAllPlayersDetailsFromApi();
        return om.readValue(res, NBADetailList.class);
    }

    private String getAllPlayersDetailsFromApi() throws IOException {
        URL url = new URL("https://www.balldontlie.io/api/v1/players/");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(("GET"));
        conn.connect();

        StringBuilder urlText = new StringBuilder();
        if (conn.getResponseCode() == 200) {
            Scanner scan = new Scanner(url.openStream());
            while (scan.hasNext()) {
                urlText.append(scan.nextLine());
            }
        }
        return urlText.toString();
    }

    public void mergeIntoPlayerData(List<NBAPlayer> initialData, NBADetailList dataFromApi) {
        HashMap<String, NBAPlayerDetailsResponse> players = getPlayersFromApiAsMap(dataFromApi);
        for (NBAPlayer nbaPlayer : initialData) {
            NBAPlayerDetailsResponse fromApi = players.get(nbaPlayer.getId());
            if (fromApi != null) {
                nbaPlayer.setFirst_name(fromApi.getFirst_name());
                nbaPlayer.setLast_name(fromApi.getLast_name());
            }
            nbaPlayerService.saveToDb(nbaPlayer);
        }
    }

    public HashMap<String, NBAPlayerDetailsResponse> getPlayersFromApiAsMap(NBADetailList dataFromApi) {
        HashMap<String, NBAPlayerDetailsResponse> players = new HashMap<>();
        for(NBAPlayerDetailsResponse player : dataFromApi.getData()) {
            players.put(player.getId().toString(), player);
        }
        return players;
    }
}
