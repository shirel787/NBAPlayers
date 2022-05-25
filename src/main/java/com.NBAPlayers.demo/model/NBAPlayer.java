package com.NBAPlayers.demo.model;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "NBAPlayers")

public class NBAPlayer {

  @org.springframework.data.annotation.Id
  private String id;
  private String nickName;
  private String first_name;
  private String last_name;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getFirst_name() {
    return first_name;
  }

  public void setFirst_name(String first_name) {
    this.first_name = first_name;
  }

  public String getLast_name() {
    return last_name;
  }

  public void setLast_name(String last_name) {
    this.last_name = last_name;
  }

  public void updateFrom(NBAPlayer player) {
    nickName = player.getNickName();
    first_name = player.getFirst_name();
    last_name = player.getLast_name();
  }

  public static final class NBAPlayerBuilder {
    private String Id;
    private String nickName;
    private String first_name;
    private String last_name;

    private NBAPlayerBuilder() {
    }

    public static NBAPlayerBuilder aNBAPlayer() {
      return new NBAPlayerBuilder();
    }

    public NBAPlayerBuilder Id(String Id) {
      this.Id = Id;
      return this;
    }

    public NBAPlayerBuilder nickName(String nickName) {
      this.nickName = nickName;
      return this;
    }

    public NBAPlayerBuilder first_name(String first_name) {
      this.first_name = first_name;
      return this;
    }

    public NBAPlayerBuilder last_name(String last_name) {
      this.last_name = last_name;
      return this;
    }

    public NBAPlayer build() {
      NBAPlayer nBAPlayer = new NBAPlayer();
      nBAPlayer.setId(Id);
      nBAPlayer.setNickName(nickName);
      nBAPlayer.setFirst_name(first_name);
      nBAPlayer.setLast_name(last_name);
      return nBAPlayer;
    }
  }
}
