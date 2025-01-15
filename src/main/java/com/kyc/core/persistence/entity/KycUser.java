package com.kyc.core.persistence.entity;

import java.io.Serializable;

import com.kyc.core.persistence.entity.base.BaseKycUser;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;

@Table(name = "KYC_USER")
@Entity
@NoArgsConstructor
public class KycUser extends BaseKycUser implements Serializable {}
