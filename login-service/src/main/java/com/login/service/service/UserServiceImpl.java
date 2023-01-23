package com.login.service.service;

import java.util.List;

import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.login.service.config.WebSecurityConfig;
import com.login.service.entity.Doctor_details;
import com.login.service.entity.Nurse_details;
import com.login.service.entity.user_details;
import com.login.service.repo.DoctorRepository;
import com.login.service.repo.Userrepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private Userrepository userrepo;
	
	@Autowired
	private DoctorService doctorservice;
	
	@Autowired
	private NurseService nurseservice;
	
	
	@Autowired
	private WebSecurityConfig websecurity;
	
	@Override
	public user_details getbyemail(String email){
		return userrepo.findByEmail(email);
	}
	
	@Override
	public user_details saveuser(user_details user) {
		return userrepo.save(user);
	}

	@Override
	public List<user_details> getalluser() {
		return userrepo.findAll();
	}

	@Override
	public String savedoctor(user_details user) throws Exception  {
		int flag=0;
		try {
		List<user_details> users= userrepo.findAll();
		for(user_details user1:users) {
			if(user1.getEmail().equals(user.getEmail())) {
				flag=1;
			}
		}
		if(flag==1) {
			return "not";
		}
		  Doctor_details d = user.getDoctordetails();
		  d.setApproval(false);
		  d.setAvaliability(true);
		  Doctor_details d1 = doctorservice.savedoctor(d);
		  String  dd=websecurity.passwordEncoder().encode(user.getPassword());
		  System.out.println("============================="+dd);
		  user_details u = new user_details(user.getEmail(),websecurity.passwordEncoder().encode(user.getPassword()), d1);
//		  u.setPassword(websecurity.passwordEncoder().encode(user.getPassword()));
		  userrepo.save(u);
		  return "saved";
		}
		
		catch (Exception e) {
			 Doctor_details d = user.getDoctordetails();
			  d.setApproval(false);
			  d.setAvaliability(true);
			  Doctor_details d1 = doctorservice.savedoctor(d);
			  String  dd=websecurity.passwordEncoder().encode(user.getPassword());
			  System.out.println("============================="+dd);
			  user_details u = new user_details(user.getEmail(),websecurity.passwordEncoder().encode(user.getPassword()), d1);
//			  u.setPassword(websecurity.passwordEncoder().encode(user.getPassword()));
			  userrepo.save(u);
			  return "saved";
		}
	}

	@Override
	public String savenurse(user_details user) {
		int flag=0;
		List<user_details> users= userrepo.findAll();
		for(user_details user1:users) {
			if(user1.getEmail().equals(user.getEmail())) {
				
				flag=1;
				
			}
		}
		if(flag==1) {
			return "not";
		}
		 Nurse_details n = user.getNursedetails();
		  n.setApproval(false);
		  n.setAvaliability(true);
		  Nurse_details n1 = nurseservice.savenurse(n);
		  user_details u = new user_details(user.getEmail(),websecurity.passwordEncoder().encode(user.getPassword()), n1);
		  
		  user_details u1 = userrepo.save(u); 
		  System.out.println("========"+u1);
		  return "saved";
	}

	@Override
	public String loginuser(user_details user) throws Exception {
		String pass = user.getPassword();
		String email=user.getEmail();
		try {
		user_details user2= userrepo.findByEmail(email);
		System.out.println("@@@@@@@@@"+user2);
		System.out.println("============"+user.getPassword());
		System.out.println("============"+user2.getPassword());
		if(user2!=null) {
			Doctor_details d = user2.getDoctordetails();
			if(d==null) {
				Nurse_details n = user2.getNursedetails();
				System.out.println(websecurity.passwordEncoder().matches(user.getPassword(), user2.getPassword()));
				if(websecurity.passwordEncoder().matches(user.getPassword(), user2.getPassword())) {
					if(n.isApproval()) {
						return "successfully";
					}
					else {
						return "wait";
					}
					}
					else {
						return "pass";
					}
				}
			
		else {
			System.out.println(websecurity.passwordEncoder().matches(user.getPassword(), user2.getPassword()));
			if(websecurity.passwordEncoder().matches(user.getPassword(), user2.getPassword())) {
				if(d.isApproval()) {
					return "successfully";
				}
				else {
					return "wait";
				}
				}
				else {
					return "pass";
				}
			}
		}
		else {
			return "failed";
		}
		
			
		
		}
		catch (Exception e) {
			return "failed";
		}
		
		
	}
	}


