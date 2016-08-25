package pt.isel.ls;

import org.junit.Test;

/**
 * SQL error codes are available here:
 * https://technet.microsoft.com/en-us/library/cc645603(v=sql.105).aspx
 */
public class MovieControllerTest extends BaseDatabaseTest {


	/**
	 * Test request: GET /movies/{mid}/reviews
	 * MovieController.showMovieReviews is the handler responsible
	 */
	@Test
	public void testShowMovieReviewsHandlerWithoutReviews(){

	}
	/**
	 * Test request: GET /movies/{mid}/reviews
	 * MovieController.showMovieReviews is the handler responsible
	 */
	@Test
	public void testShowMovieReviewsHandlerWithOneReview(){

	}
	/**
	 * Test request: GET /movies/{mid}/reviews
	 * MovieController.showMovieReviews is the handler responsible
	 */
	@Test
	public void testShowMovieReviewsHandlerWithMultipleReviews(){

	}


	/**
	 * Test request: GET /movies/{mid}/reviews
	 * MovieController.showMovieReviews is the handler responsible
	 */
	@Test
	public void testShowMovieReviewsHandlerWithNonexistentMovie(){

	}

	/**
	 * Test request: GET /movies/{mid}/reviews/{rid}
	 * MovieController.showMovieReviews is the handler responsible
	 */
	@Test
	public void testShowMovieReviewsHandlerWithIncorrectRatingValue(){

	}

	/**
	 * Test request: GET /movies/{mid}/reviews
	 * MovieController.showReview is the handler responsible
	 */
	@Test
	public void testShowMovieReviewHandlerWithNonexistentMovie(){

	}

	/**
	 * Test request: GET /movies/{mid}/reviews
	 * MovieController.showReview is the handler responsible
	 */
	@Test
	public void testShowMovieReviewHandlerWithCorrectParameters(){

	}

}


/**
 * alterar o caralho dos testes todos
 * e dividi-los mais
 * correr o script de creates antes de cada teste?
 *
 * https://github.com/AliSoftware/OHHTTPStubs/wiki/A-tricky-case-with-Application-Tests
 * http://www.vogella.com/tutorials/JUnit/article.html
 *
 *  QUESTION:
 * The reason why I am asking is my application requires a lot of work to start launch
 * (lots of dependencies and configurations, etc) and using an external testing tool
 * (like JUnit Ant task) would require a lot of work to set up.
 *
 *  ANSWER:
 * You need to remove these dependencies from the code you are testing.
 * The dependencies and configurations are precisely what you are trying to avoid when
 * writing a test framework. For each test, you should be targeting the smallest testable
 * part of an application.
 *
 * For example, if you require a movies connection to execute some process in a class
 * you are trying to test - decouple the movies handling object from your class, pass
 * it in via a constructor or setter method, and in your test use a tool like JMock
 * (or write a stub class) to build a fake movies handling object. This way you are making
 * sure the tests are not dependent on a particular movies configuration, and you are only
 * testing the small portion of code you are interested in, not the entire movies handling
 * layer as well.
 *
 * It might seem like a lot of work at first, but this kind of refactoring is exactly what
 * your test framework should be fleshing out. You might find it useful to get a book on
 * software testing as a reference for decoupling your dependencies. It will pay off a lot
 * more than trying to bootstrap JUnit from inside your running application.
 */
