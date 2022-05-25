package com.NBAPlayers.demo.controller;
/*import com.NBAPlayers.demo.repository.NBARepository;*/
import com.NBAPlayers.demo.model.NBADetailList;
import com.NBAPlayers.demo.model.NBAPlayer;
import com.NBAPlayers.demo.repository.NBAPlayerRepository;
import com.NBAPlayers.demo.service.PlayerCsvParsingService;
import com.NBAPlayers.demo.service.PlayerDetailService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.lang.String.valueOf;

@RestController
@RequestMapping("/player")
public class NBAPlayerController {

    @Autowired
    PlayerDetailService playerDetailService;

    @Autowired
    PlayerCsvParsingService playerCsvParsingService;

    @Autowired
    NBAPlayerRepository playerRepository;

    @RequestMapping(value = "/playersApi", method = RequestMethod.GET)
    public ResponseEntity<?> readFromApi() throws IOException {
        return new ResponseEntity(playerDetailService.getAllPlayersFromApi(), HttpStatus.OK);
    }

    @RequestMapping(value = "/players", method = RequestMethod.GET)
    public ResponseEntity<?> readFromDb() throws IOException {
        return new ResponseEntity(playerRepository.findAll(), HttpStatus.OK);
    }


    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> readCsvFile(@RequestBody MultipartFile playerCsv) throws IOException {
        String csvStr = new String(playerCsv.getBytes(), StandardCharsets.UTF_8);
        List<NBAPlayer> initialData = playerCsvParsingService.readPlayerCsv(csvStr);
        NBADetailList dataFromApi = playerDetailService.getAllPlayersFromApi();
        playerDetailService.mergeIntoPlayerData(initialData, dataFromApi);
        return new ResponseEntity<>(initialData, HttpStatus.OK);
    }

}



