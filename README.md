# NBAPlayers
Present Players Details from API &amp; CSV file

the project use java pring boot in intelij and uses swagger, you can see the swagger 
in this url: http://localhost:8080/swagger-ui.html#

there are 3 methods:

the first one is post method that load a csv file of players that contain several records of id & nickname, 
and merge it with api (https://www.balldontlie.io/#players) that take first name and last name according to id 
and present it.

the second get method read records from mongodb, the records are save and update by player id, 
and there is a job with logger that pull the player details from API by player id every 15 minutes, 
update and save it in mongodb.

the third get method read all players from api (present player id, his first name and last name).
