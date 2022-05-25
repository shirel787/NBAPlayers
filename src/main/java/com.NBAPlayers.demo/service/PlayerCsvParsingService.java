package com.NBAPlayers.demo.service;

import com.NBAPlayers.demo.model.NBAPlayer;
import com.NBAPlayers.demo.repository.NBAPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.NBAPlayers.demo.model.NBAPlayer.NBAPlayerBuilder.aNBAPlayer;
import static java.lang.String.valueOf;

@Service
public class PlayerCsvParsingService {

    public List<NBAPlayer> readPlayerCsv(String csvStr) {
        String[] lines = csvStr.split("\n");
        List<NBAPlayer> res = new ArrayList<>();
        for (String line : lines) {
            NBAPlayer player = readPlayerLine(line);
            res.add(player);
        }
        return res.stream().skip(1).collect(Collectors.toList());
    }

    private NBAPlayer readPlayerLine(String line) {
        String[] fields = line.split(",");
        return aNBAPlayer().Id(fields[0]).nickName(fields[1]).build();
    }
}
