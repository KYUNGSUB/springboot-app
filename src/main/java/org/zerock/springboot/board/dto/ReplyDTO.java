package org.zerock.springboot.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReplyDTO {
	private Long rno;
    private String text;
    private Long mid;
    private Long bno;  //게시글 번호
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}