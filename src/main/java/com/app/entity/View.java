package com.app.entity;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "VIEW")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class View extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "BLOG_ID", referencedColumnName = "ID")
    private Blog blogId;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ID")
    private Account accountId;

}
