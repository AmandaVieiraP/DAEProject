/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Question")
@XmlAccessorType(XmlAccessType.FIELD)
public class QuestionDTO implements Serializable {

    private int id;
    private String questionSender;
    private String question;
    private int configurationCode;

    public QuestionDTO() {
    }

    public QuestionDTO(int id, String questionSender, String question, int configurationCode) {
        this.id = id;
        this.questionSender = questionSender;
        this.question = question;
        this.configurationCode = configurationCode;
    }

    public int getId() {
        return id;
    }

    public String getQuestionSender() {
        return questionSender;
    }

    public String getQuestion() {
        return question;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setQuestionSender(String questionSender) {
        this.questionSender = questionSender;
    }

    public int getConfigurationCode() {
        return configurationCode;
    }

    public void setConfigurationCode(int configurationCode) {
        this.configurationCode = configurationCode;
    }

    public void reset() {
        setId(0);
        setQuestionSender(null);
        setQuestion(null);
        setConfigurationCode(0);
    }

}
