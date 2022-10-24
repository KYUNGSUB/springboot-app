package org.zerock.springboot.review.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.springboot.review.dto.ReviewDTO;
import org.zerock.springboot.review.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/reviews")
@Log4j2
@RequiredArgsConstructor
public class ReviewController {
	private final ReviewService reviewService;

    @GetMapping("/{mno}/all")
    public ResponseEntity<List<ReviewDTO>> getList(@PathVariable("mno") Long mno){
        log.info("--------------list---------------");
        log.info("MNO: " + mno);
        List<ReviewDTO> reviewDTOList = reviewService.getListOfMovie(mno);
        return new ResponseEntity<>(reviewDTOList, HttpStatus.OK);
    }

    @PostMapping("/{mno}")
    public ResponseEntity<Long> addReview(@RequestBody ReviewDTO movieReviewDTO){
        log.info("--------------add MovieReview---------------");
        log.info("reviewDTO: " + movieReviewDTO);
        Long reviewnum = reviewService.register(movieReviewDTO);
        return new ResponseEntity<>( reviewnum, HttpStatus.OK);
    }

    @PutMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> modifyReview(@PathVariable Long reviewnum,
                                             @RequestBody ReviewDTO movieReviewDTO){
        log.info("---------------modify MovieReview--------------" + reviewnum);
        log.info("reviewDTO: " + movieReviewDTO);
        reviewService.modify(movieReviewDTO);
        return new ResponseEntity<>( reviewnum, HttpStatus.OK);
    }

    @DeleteMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> removeReview( @PathVariable Long reviewnum){
        log.info("---------------modify removeReview--------------");
        log.info("reviewnum: " + reviewnum);
        reviewService.remove(reviewnum);
        return new ResponseEntity<>( reviewnum, HttpStatus.OK);
    }
}
