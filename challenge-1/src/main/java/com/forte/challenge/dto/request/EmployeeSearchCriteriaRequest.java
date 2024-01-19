package com.forte.challenge.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeSearchCriteriaRequest {

    private String name;
    private String position;
    private String department;
}
