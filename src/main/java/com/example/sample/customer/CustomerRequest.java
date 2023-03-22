package com.example.sample.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CustomerRequest {

    @Getter
    @Setter
    @NoArgsConstructor @AllArgsConstructor
    @Schema(description = "고객 저장 정보")
    public static class CustomerInsertRequest {

        @NotBlank(message = "이름을 입력해주세요.")
        @Size(max = 5, message = "이름은 5자리 이내로 입력해주세요.")
        @Schema(description = "고객 이름", maximum = "5", minimum = "1", example = "박진희")
        private String name;

        @NotBlank(message = "휴대폰 번호를 입력해주세요.")
        @Pattern(regexp = "[0-9]+", message = "휴대폰번호는 숫자로만 입력해주세요.")
        @Size(max = 11, message = "휴대폰 번호는 12자리 이내로 입력해주세요.")
        @Schema(description = "휴대폰 번호", maximum = "11", minimum = "1", example = "01040234504", pattern = "[숫자로만 입력] - [0-9]+")
        private String tel;

        public Customer toEntity () {
            return new Customer(null, name, tel);
        }

    }

    @Getter @Setter @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "고객 전체 수정 정보")
    public static class CustomerPutUpdateRequest {

        @NotBlank(message = "이름을 입력해주세요.")
        @Size(max = 5, message = "이름은 5자리 이내로 입력해주세요.")
        @Schema(description = "고객 이름", maximum = "5", minimum = "1", example = "박진희")
        private String name;

        @NotBlank(message = "휴대폰 번호를 입력해주세요.")
        @Pattern(regexp = "[0-9]+", message = "휴대폰번호는 숫자로만 입력해주세요.")
        @Size(max = 11, message = "휴대폰 번호는 12자리 이내로 입력해주세요.")
        @Schema(description = "휴대폰 번호", maximum = "11", minimum = "1", example = "01040234504", pattern = "[숫자로만 입력] - [0-9]+")
        private String tel;

        public void update (Customer customer) {
            customer.setName(name);
            customer.setTel(tel);
        }
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    @Schema(description = "고객 일부 수정 정보")
    public static class CustomerPatchUpdateRequest {

        @NotBlank(message = "이름을 입력해주세요.")
        @Size(max = 5, message = "이름은 5자리 이내로 입력해주세요.")
        @Schema(description = "고객 이름", maximum = "5", minimum = "1", example = "박진희")
        private String name;

        public void update (Customer customer) {
            customer.setName(name);
        }
    }

}
