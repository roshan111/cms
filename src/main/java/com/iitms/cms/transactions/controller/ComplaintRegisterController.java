package com.iitms.cms.transactions.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iitms.cms.transactions.entity.ComplaintRegisterEntity;
import com.iitms.cms.transactions.service.ComplaintRegisterService;

@Controller
public class ComplaintRegisterController {

	private static final Logger logger = LoggerFactory.getLogger(ComplaintRegisterController.class);
	@Autowired
	private ComplaintRegisterService complaintRegisterService;
	
	@RequestMapping(value = "/transaction/complaints")
	public String getComplaintForm(Model model){
		model.addAttribute("complaint", new ComplaintRegisterEntity());
		return "/transaction/complaint-register";
	}
	
	@RequestMapping(value = "/transaction/complaint/list", method = RequestMethod.POST)
	public @ResponseBody List<ComplaintRegisterEntity> getAllComplaints(){
		return complaintRegisterService.getAllComplaints();
	}
	
	@RequestMapping(value = "/transaction/complaints/add")
	private String addComplaint(@ModelAttribute ComplaintRegisterEntity complaint){
		logger.info("Entity : "   + complaint);
		complaintRegisterService.addComplaint(complaint);
		return "redirect:/transaction/complaint";
	}
	
	@RequestMapping(value = "/transaction/complaints/edit")
	private String editComplaint(@ModelAttribute ComplaintRegisterEntity complaint){
		logger.info("Entity : "   + complaint);
		complaintRegisterService.addComplaint(complaint);
		return "redirect:/transaction/complaint";
	}
}
