select * from Movie
select * from Review
select * from MovieCollection
select * from Movie_MovieCollection

-- INSERT MOVIES
insert into Movie(title,releaseYear) values('Mastermind',1717)
insert into Movie(title,releaseYear) values('Paprika',1212)
insert into Movie(title,releaseYear) values('Ghost in the Shell',1212)
insert into Movie(title,releaseYear) values('Im Batmaaaaaaaaaaaan',1234)
insert into Movie(title,releaseYear) values('Documentario',0000)
insert into Movie(title,releaseYear) values('Dá-me o 35',3535)
insert into Movie(title,releaseYear) values('random',6548)
insert into Movie(title,releaseYear) values('spam',9999)
insert into Movie(title,releaseYear) values('yolo9',2009)
insert into Movie(title,releaseYear) values('yolo10',2010)
insert into Movie(title,releaseYear) values('yolo11',2011)
insert into Movie(title,releaseYear) values('yolo12',2012)
-- INSERT RATINGS
update Movie set oneStar = oneStar+1 where id=1
update Movie set twoStars = twoStars+1 where id=1
update Movie set threeStars = threeStars+1 where id=1
update Movie set fourStars = fourStars+1 where id=1
update Movie set fiveStars = fiveStars+1 where id=1
update Movie set oneStar = oneStar+1 where id=2
update Movie set twoStars = twoStars+1 where id=2
update Movie set oneStar = oneStar+1 where id=3
update Movie set twoStars = twoStars+1 where id=3
update Movie set threeStars = threeStars+1 where id=3
update Movie set fourStars = fourStars+1 where id=4
update Movie set fiveStars = fiveStars+1 where id=5
update Movie set fiveStars = fiveStars+1 where id=6
update Movie set fiveStars = fiveStars+1 where id=6
update Movie set fiveStars = fiveStars+1 where id=6
-- INSERT REVIEWS
	-- mastermind
insert into Review(critic,summary,content,rating, movieId) values('dr2','seca', '..........', 1, 1)
update Movie set oneStar = oneStar+1 where id = 1
insert into Review(critic,summary,content,rating, movieId) values('dr2','mastermind...', '..........', 1, 1)
update Movie set oneStar = oneStar+1 where id = 1
	-- Paprika
insert into Review(critic,summary,content,rating, movieId) values('dr3','okk', '..........', 3, 2)
update Movie set threeStars = threeStars+1 where id = 2
insert into Review(critic,summary,content,rating, movieId) values('dr3','okk', '..........', 4, 2)
update Movie set fourStars = fourStars+1 where id = 2
	-- Ghost in the Shell
insert into Review(critic,summary,content,rating, movieId) values('dr1','brutal', '..........', 5, 3)
update Movie set fiveStars = fiveStars+1 where id = 3
insert into Review(critic,summary,content,rating, movieId) values('dr1','afinal nao', '..........', 1, 3)
update Movie set oneStar = oneStar+1 where id = 3
	-- Im Batmaaaaaaaaaaaan
insert into Review(critic,summary,content,rating, movieId) values('dr1','brutal', '..........', 4, 4)
update Movie set fourStars = fourStars+1 where id = 4
	-- Documentario
insert into Review(critic,summary,content,rating, movieId) values('dr1','brutal', '..........', 5, 5)
update Movie set fiveStars = fiveStars+1 where id = 5
insert into Review(critic,summary,content,rating, movieId) values('dr1','afinal nao', '..........', 1, 5)
update Movie set oneStar = oneStar+1 where id = 5
	-- Dá-me o 35
insert into Review(critic,summary,content,rating, movieId) values('dr1','brutal', '..........', 5, 6)
update Movie set fiveStars = fiveStars+1 where id = 6
insert into Review(critic,summary,content,rating, movieId) values('dr1','brutal', '..........', 5, 6)
update Movie set fiveStars = fiveStars+1 where id = 6
insert into Review(critic,summary,content,rating, movieId) values('dr1','brutal', '..........', 5, 6)
update Movie set fiveStars = fiveStars+1 where id = 6
insert into Review(critic,summary,content,rating, movieId) values('dr1','brutal', '..........', 5, 6)
update Movie set fiveStars = fiveStars+1 where id = 6
insert into Review(critic,summary,content,rating, movieId) values('dr1','brutal', '..........', 5, 6)
update Movie set fiveStars = fiveStars+1 where id = 6
-- INSERT COLLECTIONS
insert into MovieCollection (name)values ('Coleção dos Maiores')
insert into MovieCollection (name)values ('Campeão dos Campeões')
insert into MovieCollection (name)values ('Dá-me o 36')
-- INSERT MOVIES IN COLLECTIONS
insert into Movie_MovieCollection values(1,1)
insert into Movie_MovieCollection values(1,2)
insert into Movie_MovieCollection values(1,3)
insert into Movie_MovieCollection values(2,1)
insert into Movie_MovieCollection values(2,2)
insert into Movie_MovieCollection values(3,1)
insert into Movie_MovieCollection values(4,3)
insert into Movie_MovieCollection values(5,3)
insert into Movie_MovieCollection values(6,1)
insert into Movie_MovieCollection values(6,2)
insert into Movie_MovieCollection values(6,3)

select * from Movie
select * from Review
select * from Movie_MovieCollection