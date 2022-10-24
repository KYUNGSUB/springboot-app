package org.zerock.springboot.review.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.springboot.review.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
//	@Query("select m, avg(coalesce(r.grade,0)), count(distinct r) from Movie m " +
//            "left outer join Review r on r.movie = m group by m")
//    Page<Object[]> getListPage(Pageable pageable);

//    @Query("select m, max(mi), avg(coalesce(r.grade,0)), count(distinct r) from Movie m " +
//            "left outer join MovieImage mi on mi.movie = m " +
//            "left outer join Review r on r.movie = m group by m ")
//    Page<Object[]> getListPage(Pageable pageable);

    @Query("select m, mi, avg(coalesce(r.grade,0)), count(distinct r) from Movie m " +
            "left outer join MovieImage mi on mi.movie = m " +
            "left outer join Review r on r.movie = m group by m ")
    Page<Object[]> getListPage(Pageable pageable);

//    @Query("select m, mi" +
//            " from Movie m left outer join MovieImage mi on mi.movie = m " +
//            " where m.mno = :mno")
//    List<Object[]> getMovieWithAll(@Param("mno") Long mno);

    @Query("select m, mi ,avg(coalesce(r.grade,0)), count(r)" +
            " from Movie m left outer join MovieImage mi on mi.movie = m " +
            " left outer join Review r on r.movie = m "+
            " where m.mno = :mno group by mi")
    List<Object[]> getMovieWithAll(@Param("mno") Long mno);
}