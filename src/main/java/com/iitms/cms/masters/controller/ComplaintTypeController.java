package com.iitms.cms.masters.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iitms.cms.masters.entity.ComplaintTypeEntity;
import com.iitms.cms.masters.service.ComplaintTypeService;

@Controller
//@RequestMapping("/types")
public class ComplaintTypeController {

	private static final Logger logger = LoggerFactory.getLogger(ComplaintTypeController.class);
	
	@Autowired
	private ComplaintTypeService complaintTypeService;
	
	@RequestMapping(value = "/get-complaints")
	public @ResponseBody List<ComplaintTypeEntity> getComplaintList(@RequestParam(required = false, defaultValue = "0") Integer deptId){
		logger.info("Get complaints : " + deptId + " - " + complaintTypeService);
		List<ComplaintTypeEntity> list = complaintTypeService.getComplaints(deptId);
		return list;
	}
	
	@RequestMapping(value = "/add-complaint", method = RequestMethod.POST)
	public String addComplaint(@Valid @ModelAttribute(value = "complaint") ComplaintTypeEntity complaint,
						BindingResult result, Model model){
		logger.info("Add Complaint : " + complaint + " Error :- "  + result.hasErrors());
		//result.reject("complaintTypeName");
		boolean flag  = false;
		
		if(result.hasErrors() == false)
		flag = complaintTypeService.isComplaintTypeExist(complaint.getDeptId(), complaint.getComplaintTypeName());
		
		if(result.hasErrors() || flag){
			model.addAttribute("complaint", complaint);
			List<ComplaintTypeEntity> list = complaintTypeService.getComplaints(0);
			model.addAttribute("list", list);
			model.addAttribute("operation", "add");
			model.addAttribute("flag", flag);
			return "department-type";
		}else
		complaintTypeService.addComplaint(complaint);
		return "redirect:/complaint-type";
	}
	
	@RequestMapping(value = "/update-complaint", method = RequestMethod.POST)
	public String updateComplaint(@Valid @ModelAttribute(value = "complaint") ComplaintTypeEntity complaint,
			BindingResult result, Model model){
		logger.info("Update Complaint : " + complaint + " Error : "+ result.hasErrors());
		if(result.hasErrors()){
			model.addAttribute("complaint", complaint);
			List<ComplaintTypeEntity> list = complaintTypeService.getComplaints(0);
			model.addAttribute("list", list);
			model.addAttribute("operation", "edit");
			return "department-type";
		}
		complaintTypeService.updateComplaint(complaint);
		return "redirect:/complaint-type";
	}
	
	@RequestMapping(value = "/delete-complaint", method = RequestMethod.POST)
	public String deleteComplaint(@RequestParam Integer id){
		logger.info("Complaint : " + id);
		complaintTypeService.deleteComplaint(id);
		return "redirect:/complaint-type";
	}
	
	@RequestMapping(value = "/complaint-type")
	public String getComplaintTypes(@RequestParam(name = "complaintTypeId", required = false, defaultValue = "0") int  complaintTypeId,
			@RequestParam(name = "op", required = false, defaultValue = "") String operation,
									Model model){
		logger.info("Operation : "  + operation + " - complaintTypeId : "+ complaintTypeId);
		ComplaintTypeEntity entity = new ComplaintTypeEntity();
		if(complaintTypeId != 0){
			entity = complaintTypeService.getComplaint(complaintTypeId);
			model.addAttribute("operation", "edit");
		}else if(operation.equals("add")){
			model.addAttribute("operation", "add");
		}
		model.addAttribute("complaint", entity);
		List<ComplaintTypeEntity> list = complaintTypeService.getComplaints(0);
		model.addAttribute("list", list);
		return "department-type";
	}
	
	@RequestMapping(value = "/check-complaint-type", method = RequestMethod.GET)
	public @ResponseBody boolean isComplaintTypeExist(@RequestParam(name  = "deptId") String deptId, @RequestParam String complaintTypeName){
		logger.info("Check Complaint : " + deptId  + " - " + complaintTypeName);
		return complaintTypeService.isComplaintTypeExist(Integer.parseInt(deptId), complaintTypeName);
	}
}
