RickAndMorty Api Clone

Spring Boot Application for produce clone API of Rick And Morty series. Every request parameters and exception handling messages on official media is cloned.

Official API: (GET - https://rickandmortyapi.com/api/ )




●	Java 11 ve MongoDB 4

●	Working on port 8080

●   Layered Architecture

●	Spring Data (JPA)

● 	There are two profile for launch the application.

	- dev: If user want to store data in local MongoDB.
	- test: If user want to store data in MongoDB Atlas (Cloud) //Compiling can be take 1-2 minutes.

●	After the storing data is completed, I created an example for multithread letter count of all the character data's name value.
It chops the data to 25 equal list of character data and start to count these list with 25 thread. It can be review on com/onurkayahan/rickandmorty/thread/CharacterNameCounterExecuterService.java

●	Scheduled task added for update DB everyday at 00:00. Also it store it on SystemInfo.





Example: http://localhost:8080/api/character/?page=1&orderBy=name

	- Page and orderBy parameters are not required
	- OrderBy can take every object key value.

a.	Character

	i.	/api/character

	ii.	/api/character/{id}

b.	Episode

	i.	/api/episode

	ii.	/api/episode/{id}

c.	Location

	i.	/api/location

	ii.	/api/location/{id}

d.	Report (Return which endpoint get how much request)

	i.	/api/report

e.	SystemInfo (Return DB latest updated date)

	i.	/api/systemInfo


