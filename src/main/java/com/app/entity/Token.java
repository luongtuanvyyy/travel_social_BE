package com.app.entity;

import com.app.type.TokenType;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenType tokenTye;
    private boolean expried;
    private boolean revoked;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

}
