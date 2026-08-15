package com.kyc.core.persistence.entity;

import com.kyc.core.persistence.entity.base.BaseKycUser;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Table(name = "KYC_USER")
@Entity
@NoArgsConstructor
public class KycUser extends BaseKycUser implements Serializable {}
