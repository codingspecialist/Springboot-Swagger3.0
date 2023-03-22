package com.example.sample;

import com.example.sample.customer.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
@MockBean(JpaMetamodelMappingContext.class)
class CustomerControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void getList() throws Exception {

        Customer customer = CustomerExample.customer;
        given(customerService.getCustomers()).willReturn(List.of(customer.toDTO()));

        this.mockMvc.perform(get("/customer").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value(customer.getName()))
                .andExpect(jsonPath("$.[0].tel").value(customer.getTel()))
                .andDo(print());
    }

    @Test
    public void getNoCustomer() throws Exception {
        given(customerService.getCustomer(1000L)).willReturn(Optional.empty());

        this.mockMvc.perform(
                        get("/customer/{id}", 1000L)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value(CustomerConstant.notFoundMessage))
                .andExpect(jsonPath("$.status").value(400))
                .andDo(print());
    }

    @Test
    public void getCustomer() throws Exception {

        Customer customer = CustomerExample.customer;
        given(customerService.getCustomer(customer.getId())).willReturn(Optional.of(customer));

        this.mockMvc.perform(
                        get("/customer/{id}", customer.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(customer.getName()))
                .andExpect(jsonPath("$.tel").value(customer.getTel()))
                .andDo(print());
    }

    @Test
    public void putValid() throws Exception {

        CustomerRequest.CustomerPutUpdateRequest request = new CustomerRequest.CustomerPutUpdateRequest("", "01012345678");
        this.mockMvc.perform(
                        put("/customer/{id}", 1000L)
                                .content(objectMapper.writeValueAsBytes(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("이름을 입력해주세요."))
                .andExpect(jsonPath("$.status").value(400))
                .andDo(print());
    }

    @Test
    public void putNoCustomer() throws Exception {

        CustomerRequest.CustomerPutUpdateRequest request = new CustomerRequest.CustomerPutUpdateRequest("박진희", "01012345678");

        given(customerService.getCustomer(1000L)).willReturn(Optional.empty());
        this.mockMvc.perform(
                        put("/customer/{id}", 1000L)
                                .content(objectMapper.writeValueAsBytes(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value(CustomerConstant.notFoundMessage))
                .andExpect(jsonPath("$.status").value(400))
                .andDo(print());
    }

    @Test
    public void putSuccess() throws Exception {
        CustomerRequest.CustomerPutUpdateRequest request = new CustomerRequest.CustomerPutUpdateRequest("박진희", "01012345678");
        Customer customer = CustomerExample.customer;
        given(customerService.getCustomer(customer.getId())).willReturn(Optional.of(customer));
        given(customerService.mergeCustomer(customer)).willReturn(customer);

        this.mockMvc.perform(
                        put("/customer/{id}", customer.getId())
                                .content(objectMapper.writeValueAsBytes(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(customer.getName()))
                .andExpect(jsonPath("$.tel").value(request.getTel()))
                .andDo(print());
    }

    @Test
    public void patchValid() throws Exception {
        CustomerRequest.CustomerPatchUpdateRequest request = new CustomerRequest.CustomerPatchUpdateRequest("");
        this.mockMvc.perform(
                        patch("/customer/{id}", 1000L)
                                .content(objectMapper.writeValueAsBytes(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("이름을 입력해주세요."))
                .andExpect(jsonPath("$.status").value(400))
                .andDo(print());
    }

    @Test
    public void patchNoCustomer() throws Exception {
        CustomerRequest.CustomerPatchUpdateRequest request = new CustomerRequest.CustomerPatchUpdateRequest("유광열");
        given(customerService.getCustomer(1000L)).willReturn(Optional.empty());

        this.mockMvc.perform(
                        patch("/customer/{id}", 1000L)
                                .content(objectMapper.writeValueAsBytes(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value(CustomerConstant.notFoundMessage))
                .andExpect(jsonPath("$.status").value(400))
                .andDo(print());
    }


    @Test
    public void patchSuccess() throws Exception {
        CustomerRequest.CustomerPatchUpdateRequest request = new CustomerRequest.CustomerPatchUpdateRequest("유광열");
        Customer customer = CustomerExample.customer;
        given(customerService.getCustomer(customer.getId())).willReturn(Optional.of(customer));
        given(customerService.mergeCustomer(customer)).willReturn(customer);

        this.mockMvc.perform(
                        patch("/customer/{id}", customer.getId())
                                .content(objectMapper.writeValueAsBytes(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(request.getName()))
                .andExpect(jsonPath("$.tel").value(customer.getTel()))
                .andDo(print());
    }


}
