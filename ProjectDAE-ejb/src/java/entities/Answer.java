/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author IvoSantos
 */
@Entity
@Table(name = "ANSWERS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries({
    @NamedQuery(name = "getAllAnswersByQuestion", query = "SELECT a FROM Answer a WHERE a.question.id=?1"),
})
public class Answer implements Serializable {

    //private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String answerSender;
    
    @NotNull
    private String answer;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    private Question question;

    public Answer() {
    }

    public Answer(int id, String answerSender, String answer, Question question) {
        this.id = id;
        this.answerSender = answerSender;
        this.answer = answer;
        this.question = question;
    }
    
    public Answer(String answerSender, String answer, Question question) {    
        this.answerSender = answerSender;
        this.answer = answer;
        this.question = question;
    }

    

    public String getAnswerSender() {
        return answerSender;
    }

    public String getAnswer() {
        return answer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setAnswerSender(String answerSender) {
        this.answerSender = answerSender;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
    
    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

   
   
    @Override
    public String toString() {
        return "entities.Answer[ id=" + id + " ]";
    }
    
}
