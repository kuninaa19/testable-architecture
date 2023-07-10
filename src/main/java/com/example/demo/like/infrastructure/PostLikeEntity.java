package com.example.demo.like.infrastructure;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("P")
public class PostLikeEntity extends LikeEntity {
}
