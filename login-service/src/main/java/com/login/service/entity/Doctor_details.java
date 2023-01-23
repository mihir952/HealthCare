package com.login.service.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

@Entity
public class Doctor_details {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String pnumber;
	private String specialization;
	private boolean avaliability;
	private boolean approval;
	
	
	@OneToOne(targetEntity=user_details.class,cascade={CascadeType.MERGE,CascadeType.PERSIST},mappedBy="doctordetails")
	private user_details userdetails;
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name = "doctorHospitalMap", joinColumns =@JoinColumn (name = "doctorId"), inverseJoinColumns = @JoinColumn(name = "hospitalId"))
	private Set<Hospital> hospitals;
	
	
//	public user_details getUserdetails() {
//		return userdetails;
//	}
//	public void setUserdetails(user_details userdetails) {
//		this.userdetails = userdetails;
//	}
	
	public Doctor_details() {
		super();
	}
	
	public Set<Hospital> getHospitals() {
		return hospitals;
	}

	public void setHospitals(Set<Hospital> hospitals) {
		this.hospitals = hospitals;
	}

	public boolean isApproval() {
		return approval;
	}

	public void setApproval(boolean approval) {
		this.approval = approval;
	}

	public Doctor_details(int id, String name, String pnumber, String specialization) {
		super();
		this.id = id;
		this.name = name;
		this.pnumber = pnumber;
		this.specialization = specialization;
	}
	public Doctor_details( String name, String pnumber, String specialization) {
		super();
		this.name = name;
		this.pnumber = pnumber;
		this.specialization = specialization;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPnumber() {
		return pnumber;
	}
	public void setPnumber(String pnumber) {
		this.pnumber = pnumber;
	}
	public String getSpecialization() {
		return specialization;
	}
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
	
	public boolean isAvaliability() {
		return avaliability;
	}

	public void setAvaliability(boolean avaliability) {
		this.avaliability = avaliability;
	}

	@Override
	public String toString() {
		return "Doctor_details [id=" + id + ", name=" + name + ", pnumber=" + pnumber + ", specialization="
				+ specialization + ", avaliability=" + avaliability + ", approval=" + approval + ", hospitals="
				+ hospitals + "]";
	}

	
	
	
	
	

}
