
--
-- Database setup script
--

if(OBJECT_ID('Movie_MovieCollection') is not null)		drop table dbo.Movie_MovieCollection
if(OBJECT_ID('MovieCollection') is not null)			drop table dbo.MovieCollection
if(OBJECT_ID('Review') is not null)						drop table dbo.Review
if(OBJECT_ID('Rating') is not null)						drop table dbo.Rating
if(OBJECT_ID('Movie') is not null)						drop table dbo.Movie

create table Movie
(
	id int identity(1,1),
	title varchar(100),
	releaseYear int,
	genre varchar(100),
	cast varchar(max),
	directors varchar(max),
	creationDate datetime default getDate(),
	--counters for each rating
	oneStar int default 0,
	twoStars int default 0,
	threeStars int default 0,
	fourStars int default 0,
	fiveStars int default 0,
	--constraints
	unique(title,releaseYear),
	--keys
	primary key(id)
)
--
-- SAI
--
create table Rating
(
	id int identity(1,1),
	rating int not null,
	movieId int,
	--constraints
	check(rating >= 1 and rating <= 5),
	--keys
	foreign key(movieId) references Movie(id),
	primary key(id)
)
create table Review
(
	id int identity(1,1),
	critic varchar(max),
	summary varchar(max),
	content varchar(max),
	rating int not null,
	movieId int not null,
	--constraints
	check(rating >= 1 and rating <= 5),
	--keys
	foreign key(movieId) references Movie(id),
	primary key(id)
)

create table MovieCollection
(
	id int identity(1,1),
	name varchar(100) not null unique,
	description varchar(max),
	primary key(id)
)

-- N-N table
create table Movie_MovieCollection
(
	id int identity(1,1),
	--foreign keys
	movieId int,
	collectionId int,
	--constraints
	unique(movieId,collectionId),
	--keys
	foreign key(movieId) references Movie(id),
	foreign key(collectionId) references MovieCollection(id),
	primary key(id)
)

