/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author IvoSantos
 */
@Entity
@Table(name = "QUESTIONS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "CONFIG_TYPE", discriminatorType = DiscriminatorType.STRING)
@NamedQueries({
    @NamedQuery(name = "getAllQuestions", query = "SELECT q FROM Question q")
    ,
    @NamedQuery(name = "getAllQuestionsByConfiguration", query = "SELECT q FROM Question q WHERE q.configuration.code=?1"),})
public class Question implements Serializable {

    //private static final long serialVersionUID = 1L;
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String questionSender;

    @NotNull
    private String question;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "CONFIGURATION_CODE")
    private Configuration configuration;

    public Question() {
        this.answers = new LinkedList<>();
    }

    public Question(int id, String questionSender, String question, Configuration configuration) {
        this.id = id;
        this.questionSender = questionSender;
        this.question = question;
        this.answers = new LinkedList<>();
        this.configuration = configuration;
    }

    public Question(String questionSender, String question, Configuration configuration) {
        this.questionSender = questionSender;
        this.question = question;
        this.answers = new LinkedList<>();
        this.configuration = configuration;
    }

    public String getQuestionSender() {
        return questionSender;
    }

    public String getQuestion() {
        return question;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setQuestionSender(String questionSender) {
        this.questionSender = questionSender;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public void addAnswer(Answer answer) {
        if (answer != null && !answers.contains(answer)) {
            answers.add(answer);
        }
    }

    public void removeAnswer(Answer answer) {
        if (answer != null && answers.contains(answer)) {
            answers.remove(answer);
        }
    }

    @Override
    public String toString() {
        return "entities.Question[ id=" + id + " ]";
    }

}
