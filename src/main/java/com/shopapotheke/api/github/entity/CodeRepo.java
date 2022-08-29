package com.shopapotheke.api.github.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class CodeRepo {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Getter @Setter private Long id;
    @Getter @Setter @Column private String name;
    @Getter @Setter @Column private String owner;
    @Getter @Setter @Column private Long created;
    @Getter @Setter @Column private Long stars;
    @Getter @Setter @Column private String language;


    public CodeRepo(String name, String owner, Long created, Long stars, String language){
        this.name = name;
        this.owner = owner;
        this.stars = stars;
        this.created = created;
        this.language = language;
    }
}
