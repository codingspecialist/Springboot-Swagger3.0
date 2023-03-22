package com.example.sample.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Schema(description = "고객 정보")
@Getter @Setter @AllArgsConstructor
public class CustomerDTO {

    @Schema(description = "고객 ID")
    private Long id;

    @Schema(description = "고객 이름", example = "박진희")
    private String name;

    @Schema(description = "고객 전화번호", example = "01040234504")
    private String tel;

}
