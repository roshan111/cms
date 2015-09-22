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

import com.iitms.cms.masters.entity.ComplaintItemEntity;
import com.iitms.cms.masters.service.ComplaintItemService;

@Controller
//@RequestMapping("/item")
public class ComplaintItemController {
	
	private static final Logger logger = LoggerFactory.getLogger(ComplaintDepartmentController.class);
	@Autowired
	private ComplaintItemService complaintItemService;
	
	
	@RequestMapping(value = "/complaint-items", method = RequestMethod.GET)
	public String getComplaintItemForm(@RequestParam(name = "complaint-id", required = false, defaultValue = "0") int complaintId,
			@RequestParam(name = "op", required = false, defaultValue = "") String operation,Model model){
		logger.info("Complaint Id : " + complaintId);
		List<ComplaintItemEntity> list = complaintItemService.listComplaintItem();
		ComplaintItemEntity entity = new ComplaintItemEntity();
		if(complaintId != 0){
			entity = complaintItemService.getComplaintItem(complaintId);
			model.addAttribute("operation", "edit");
		}
		else if(operation.equals("add")){
			model.addAttribute("operation", "add");
			entity = new ComplaintItemEntity();
		}
			
		
			model.addAttribute("complaintItem", entity);
		model.addAttribute("list", list);
		return "complaint-items";
		//return "register_complaint";
	}
	
	@RequestMapping(value = "/add-item", method = RequestMethod.POST)
	public String addComplaintItem(@Valid @ModelAttribute("complaintItem") ComplaintItemEntity complaintItem,
			BindingResult result, Model model){
		
		logger.info("complaintItem : " + complaintItem + " Error : " + result.hasErrors() +  " - "  + result.hasFieldErrors("itemCode"));
		 if (result.hasErrors()) {
			 List<ComplaintItemEntity> list = complaintItemService.listComplaintItem();
				//model.addAttribute("complaintItem", new ComplaintItemEntity());
				model.addAttribute("list", list);
			 model.addAttribute("complaintItem", complaintItem);
			 model.addAttribute("operation", "add");
				return "complaint-items";
	        }else{
	        	complaintItemService.addComplaintItem(complaintItem);
	    		return "redirect:/complaint-items";
	        }
	        
		
	}
	
	@RequestMapping(value = "/delete-item", method = RequestMethod.POST)
	public String deleteComplaintItem(@RequestParam Integer  complaintItemId){
		logger.info("Delete Item : " + complaintItemId);
		complaintItemService.deleteComplaintItem(complaintItemId);
		return "redirect:/complaint-items";
	}
	
	@RequestMapping(value = "/update-item", method = RequestMethod.POST)
	public String updateComplaintItem(@Valid @ModelAttribute("complaintItem") ComplaintItemEntity complaintItem,
			BindingResult result, @RequestParam(required  = false) Integer  itemId,  Model model){
		logger.info("complaintItem : " + complaintItem);
		if (result.hasErrors()) {
			 List<ComplaintItemEntity> list = complaintItemService.listComplaintItem();
				model.addAttribute("list", list);
			 model.addAttribute("complaintItem", complaintItem);
			 model.addAttribute("operation", "edit");
				return "complaint-items";
	        }else{
	        	complaintItem.setItemId(itemId);
	    		complaintItemService.updateComplaintItem(complaintItem);
	    		return "redirect:/complaint-items";
	        }
		
	}
}
