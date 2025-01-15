package com.kyc.core.persistence.entity;

import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "KYC_USER_TYPE")
@Data
public class KycUserType implements Serializable {

    @Id
    private Long id;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "userType",orphanRemoval = true)
    private List<KycUser> userRelations;

    @Column(name = "DESCRIPTION")
    private String description;
}
