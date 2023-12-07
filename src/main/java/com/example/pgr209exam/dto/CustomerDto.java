package com.example.pgr209exam.dto;

import com.example.pgr209exam.model.Address;
import com.example.pgr209exam.model.Order;
import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CustomerDto {

    private Long customerId;

    private String customerName;

    private String customerEmail;

}
