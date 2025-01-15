package com.kyc.core.persistence.entity.base;

import com.kyc.core.persistence.entity.KycUserType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.util.Date;

@Data
@MappedSuperclass
public class BaseKycUser {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name ="SECRET")
    private String secret;

    @Column(name ="ACTIVE")
    private Boolean active;

    @Column(name ="LOCKED")
    private Boolean locked;

    @Column(name ="DATE_CREATION")
    private Date dateCreation;

    @Column(name = "DATE_UPDATED")
    private Date dateUpdated;

    @ManyToOne
    @JoinColumn(name = "USER_TYPE",referencedColumnName = "ID")
    private KycUserType userType;
}
