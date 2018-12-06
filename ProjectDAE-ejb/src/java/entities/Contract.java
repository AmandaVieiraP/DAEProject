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
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Iolanda
 */
@Entity
@Table(name = "CONTRACTS")
public class Contract implements Serializable {
    
    @Id
    private int code;
    
    @NotNull
    private int totalMaintenanceHoursPerMonth;
    
    @NotNull
    private double price;
    
    @NotNull
    private int durationInYears;
    
    @NotNull
    private String maintenanceSchedule;
    
    @NotNull
    private double adicionalPricePerHour;
    
    @NotNull
    @OneToMany(mappedBy = "contract", cascade = CascadeType.REMOVE)
    private List<Template>templates;

    public Contract() {
        this.templates=new LinkedList<>();
    }

    public Contract(int code, int totalMaintenanceHoursPerMonth, double price, int durationInYears, String maintenanceSchedule, double adicionalPricePerHour) {
        this.code = code;
        this.totalMaintenanceHoursPerMonth = totalMaintenanceHoursPerMonth;
        this.price = price;
        this.durationInYears = durationInYears;
        this.maintenanceSchedule = maintenanceSchedule;
        this.adicionalPricePerHour = adicionalPricePerHour;
        this.templates=new LinkedList<>();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getTotalMaintenanceHoursPerMonth() {
        return totalMaintenanceHoursPerMonth;
    }

    public void setTotalMaintenanceHoursPerMonth(int totalMaintenanceHoursPerMonth) {
        this.totalMaintenanceHoursPerMonth = totalMaintenanceHoursPerMonth;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDurationInYears() {
        return durationInYears;
    }

    public void setDurationInYears(int durationInYears) {
        this.durationInYears = durationInYears;
    }

    public String getMaintenanceSchedule() {
        return maintenanceSchedule;
    }

    public void setMaintenanceSchedule(String maintenanceSchedule) {
        this.maintenanceSchedule = maintenanceSchedule;
    }

    public double getAdicionalPricePerHour() {
        return adicionalPricePerHour;
    }

    public void setAdicionalPricePerHour(double adicionalPricePerHour) {
        this.adicionalPricePerHour = adicionalPricePerHour;
    }

    public List<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
    }
    
    public void addTemplate(Template templateToAdd) {

        if (templateToAdd != null && !templates.contains(templateToAdd)) {
            templates.add(templateToAdd);
        }
    }

    public void removeTemplate(Template templateToRemove) {

        if (templateToRemove != null && templates.contains(templateToRemove)) {
            templates.remove(templateToRemove);
        }
    }
}
