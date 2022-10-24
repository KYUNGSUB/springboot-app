package org.zerock.springboot.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.springboot.review.entity.MovieImage;

public interface MovieImageRepository extends JpaRepository<MovieImage, Long> {

}