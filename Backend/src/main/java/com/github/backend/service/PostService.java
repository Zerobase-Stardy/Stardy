package com.github.backend.service;

public interface PostService {

    void registerPost(String email, String title, String content, String boardKind);
}
