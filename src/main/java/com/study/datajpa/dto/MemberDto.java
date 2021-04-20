package com.study.datajpa.dto;

import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class MemberDto {

    private Long id;
    private String username;
    private String teamName;

}
