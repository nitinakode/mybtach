package com.batch2.batch2.all;


import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomItemProcessor implements ItemProcessor<AnswerDto, Answer> {

    @Autowired
    AnswerREpo answerREpo;


    @Override
    public Answer process(AnswerDto item) throws Exception {

        Answer answer= Answer.builder().id(item.getName()).name(item.getName()).school(item.getSchool()).build();
        return answerREpo.save(answer);
    }
}