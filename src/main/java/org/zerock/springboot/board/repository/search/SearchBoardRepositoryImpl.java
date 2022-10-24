package org.zerock.springboot.board.repository.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.springboot.board.entity.Board;
import org.zerock.springboot.board.entity.QBoard;
import org.zerock.springboot.board.entity.QReply;
import org.zerock.springboot.member.entity.QMember;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {
	public SearchBoardRepositoryImpl() {
		super(Board.class);
	}

	@Override
    public Board search1() {
        log.info("search1......");
        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;
        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));
        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member.email, reply.count());
        tuple.groupBy(board);
        log.info("---------------------------");
        log.info(tuple);
        log.info("---------------------------");
        List<Tuple> result = tuple.fetch();
        log.info(result);
        return null;
    }

	@Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
        log.info("searchPage.............................");
        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;
        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));
        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member, reply.count());
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = board.bno.gt(0L);
        booleanBuilder.and(expression);
        if(type != null){
            String[] typeArr = type.split("");
            //검색 조건을 작성하기
            BooleanBuilder conditionBuilder = new BooleanBuilder();
            for (String t:typeArr) {
                switch (t){
                case "t":
                    conditionBuilder.or(board.title.contains(keyword));
                    break;
                case "w":
                    conditionBuilder.or(member.email.contains(keyword));
                    break;
                case "c":
                    conditionBuilder.or(board.content.contains(keyword));
                    break;
                }
            }
            booleanBuilder.and(conditionBuilder);
        }
	    tuple.where(booleanBuilder);
	    //order by
        Sort sort = pageable.getSort();
        sort.stream().forEach(order -> {
            Order direction = order.isAscending()? Order.ASC: Order.DESC;
            String prop = order.getProperty();
            PathBuilder orderByExpression = new PathBuilder(Board.class, "board");
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });
	    tuple.groupBy(board);
	    //page 처리
        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());
	    List<Tuple> result = tuple.fetch();
	    log.info(result);
	    long count = tuple.fetchCount();
        log.info("COUNT: " +count);
        return new PageImpl<Object[]>(
                result.stream().map(t -> t.toArray()).collect(Collectors.toList()),
                pageable,
                count);
    }
}