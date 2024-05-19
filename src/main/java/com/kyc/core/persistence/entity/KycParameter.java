package com.kyc.core.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "KYC_PARAMETERS")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KycParameter implements Serializable {

    @Id
    @Column(name = "PARAM_KEY")
    private String key;

    @Column(name = "PARAM_VALUE")
    private String value;
}
