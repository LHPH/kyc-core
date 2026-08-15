package com.kyc.core.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

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

    @Temporal(TemporalType.DATE)
    @Column(name="CREATION_DATE")
    private Date creationDate;

    @Temporal(TemporalType.DATE)
    @Column(name="UPDATED_DATE")
    private Date updatedDate;
}
