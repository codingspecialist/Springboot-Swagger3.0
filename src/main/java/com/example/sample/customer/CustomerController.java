package com.example.sample.customer;

import com.example.sample.common.exception.Common400Exception;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(CustomerConstant.url)
@Tag(name = CustomerConstant.name, description = CustomerConstant.description)
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @Operation(summary = "고객 리스트 조회", description = "고객 리스트를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            array = @ArraySchema (
                                    schema = @Schema(implementation = CustomerDTO.class)
                            )
                    )
            ),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
    })
    public Iterable<CustomerDTO> getCustomers() {
        return customerService.getCustomers();
    }


    @GetMapping("/{id}")
    @Operation(summary = "고객 조회", description = "고객 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            schema = @Schema(
                                    implementation = CustomerDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
    })
    public CustomerDTO getCustomer(
            @Parameter(name = "id", description = "고객의 id", in = ParameterIn.PATH) @PathVariable Long id
    ) {
        Optional<Customer> customerOptional = customerService.getCustomer(id);
        if (customerOptional.isEmpty()) {
            throw new Common400Exception(CustomerConstant.notFoundMessage);
        }
        return customerOptional.get().toDTO();
    }

    @PostMapping
    @Operation(summary = "고객 저장", description = "고객을 저장합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            schema = @Schema(
                                    implementation = CustomerDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
    })
    public CustomerDTO saveCustomer(
            @RequestBody @Valid CustomerRequest.CustomerInsertRequest request,
            Errors errors
    ) {
        if (errors.hasErrors()) {
            throw new Common400Exception(errors.getFieldErrors().get(0).getDefaultMessage());
        }

        return customerService.mergeCustomer(request.toEntity()).toDTO();
    }

    @Operation(summary = "고객 전체 수정", description = "고객 정보를 전체 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            schema = @Schema(
                                    implementation = CustomerDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
    })
    @PutMapping("/{id}")
    public CustomerDTO putCustomer(
            @Parameter(name = "id", description = "고객의 id", in = ParameterIn.PATH) @PathVariable Long id,
            @RequestBody @Valid CustomerRequest.CustomerPutUpdateRequest request,
            Errors errors
    ) {
        if (errors.hasErrors()) {
            throw new Common400Exception(errors.getFieldErrors().get(0).getDefaultMessage());
        }

        Optional<Customer> customerOptional = customerService.getCustomer(id);
        if (customerOptional.isEmpty()) {
            throw new Common400Exception(CustomerConstant.notFoundMessage);
        }
        Customer customer = customerOptional.get();
        request.update(customer);

        return customerService.mergeCustomer(customer).toDTO();
    }

    @Operation(summary = "고객 일부 수정", description = "고객 정보를 일부 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            schema = @Schema(
                                    implementation = CustomerDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
    })
    @PatchMapping("/{id}")
    public CustomerDTO patchCustomer(
            @Parameter(name = "id", description = "고객의 id", in = ParameterIn.PATH) @PathVariable Long id,
            @RequestBody @Valid CustomerRequest.CustomerPatchUpdateRequest request,
            Errors errors
    ) {
        if (errors.hasErrors()) {
            throw new Common400Exception(errors.getFieldErrors().get(0).getDefaultMessage());
        }

        Optional<Customer> customerOptional = customerService.getCustomer(id);
        if (customerOptional.isEmpty()) {
            throw new Common400Exception(CustomerConstant.notFoundMessage);
        }
        Customer customer = customerOptional.get();
        request.update(customer);

        return customerService.mergeCustomer(customer).toDTO();
    }


    @Operation(summary = "고객 삭제", description = "고객 정보가 삭제됩니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @DeleteMapping("/{id}")
    public void deleteCustomer(
            @Parameter(name = "id", description = "고객의 id", in = ParameterIn.PATH) @PathVariable Long id
    ) {

        Optional<Customer> customerOptional = customerService.getCustomer(id);
        if (customerOptional.isEmpty()) {
            throw new Common400Exception(CustomerConstant.notFoundMessage);
        }

        customerService.deleteCustomer(customerOptional.get());
    }

}
