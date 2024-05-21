package com.batch2.batch2.all;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerREpo extends JpaRepository<Answer,String> {
}
