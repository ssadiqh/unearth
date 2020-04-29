package com.demo.exhume.rateing;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ratings")
public class RatingController {

	@Autowired
	private BooksClient booksClient;

	private List<Rating> ratingList = Arrays.asList(new Rating(1L, 1L, 2), new Rating(2L, 1L, 3), new Rating(3L, 2L, 4),
			new Rating(4L, 2L, 5));

	@GetMapping("")
	public List<Rating> findRatingsByBookId(@RequestParam Long bookId) {
		return bookId == null || bookId.equals(0L) ? Collections.EMPTY_LIST
				: ratingList.stream().filter(r -> r.getBookId().equals(bookId)).collect(Collectors.toList());
	}

	@GetMapping("/all")
	public List<Rating> findAllRatings() {
		return ratingList;
	}

	@GetMapping("/both")
	public Book getCombinedResponse(@RequestParam Long bookId) {

		Book book = booksClient.getBookById(bookId);

		List<Rating> ratings = findRatingsByBookId(bookId);
		book.setRatings(ratings);
		return book;
	}
}
