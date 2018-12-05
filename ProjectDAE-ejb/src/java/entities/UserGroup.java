/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Amanda
 */
@Entity
@Table(name = "USERS_GROUPS")
public class UserGroup implements Serializable {

    public static enum GROUP {
        Administrator, Client
    }
    
    @Id
    @Enumerated(EnumType.STRING)
    private GROUP group_name;
    
    @Id
    @OneToOne
    @JoinColumn(name = "USERNAME")
    private User user;

    public UserGroup() {
    }

    public UserGroup(GROUP groupName, User user) {
        this.group_name = groupName;
        this.user = user;
    }

    public GROUP getGroupName() {
        return group_name;
    }

    public void setGroupName(GROUP groupName) {
        this.group_name = groupName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
    
}
