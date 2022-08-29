package com.github.backend.persist.post.repository;

import com.github.backend.persist.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
