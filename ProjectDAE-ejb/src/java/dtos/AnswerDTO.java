/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "Question")
@XmlAccessorType(XmlAccessType.FIELD)
public class AnswerDTO implements Serializable {

 
    private int id;
    private String answerSender;
    private String answer;
    private int questionCode;
    
    public AnswerDTO() {
    }

    public AnswerDTO(int id, String answerSender, String answer, int questionCode) {
        this.id = id;
        this.answerSender = answerSender;
        this.answer = answer;
        this.questionCode = questionCode;
    }
   

    
    public int getId() {
        return id;
    }

    public String getAnswerSender() {
        return answerSender;
    }

    public String getAnswer() {
        return answer;
    }

    public int getQuestionCode() {
        return questionCode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAnswerSender(String answerSender) {
        this.answerSender = answerSender;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setQuestionCode(int questionCode) {
        this.questionCode = questionCode;
    }

   
   
    public void reset() {
        setId(0);
        setAnswerSender(null);
        setAnswer(null);
        setQuestionCode(0);
    }

   
}
