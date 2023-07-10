package com.example.demo.like.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class LikeStatistics {
    private int count;

    public static LikeStatistics from(List<Like> likes) {
        LikeStatistics likeStatistics = new LikeStatistics();
        likeStatistics.count = likes.size();

        return likeStatistics;
    }
}
