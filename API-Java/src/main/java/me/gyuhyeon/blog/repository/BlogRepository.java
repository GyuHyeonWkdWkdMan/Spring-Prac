package me.gyuhyeon.blog.repository;

import me.gyuhyeon.blog.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {
}
