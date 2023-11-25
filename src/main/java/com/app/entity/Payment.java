package com.app.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.*;

@Entity
@Table(name = "PAYMENT")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Payment extends BaseEntity {
    private String name;
}
