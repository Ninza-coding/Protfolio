package com.portfolio.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.portfolio.model.AboutForm;
import com.portfolio.model.EducationUpload;
import com.portfolio.model.ExperinceUpload;
import com.portfolio.model.MessageForm;
import com.portfolio.model.ResumeImport;
import com.portfolio.model.UploadProjects;
import com.portfolio.model.WorkTitle;
import com.portfolio.service.AboutFormService;
import com.portfolio.service.EducationService;
import com.portfolio.service.ExperienceUploadService;
import com.portfolio.service.MessageFormService;
import com.portfolio.service.ResumeImportService;
import com.portfolio.service.UploadProjectService;
import com.portfolio.service.WorkTitleService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class MyController {

	private MessageFormService messageFormService;
	private UploadProjectService uploadProjectService;
	private EducationService educationService;
	private ResumeImportService resumeImportService;
	private WorkTitleService workTitleService;
	private ExperienceUploadService experienceUploadService;
	private AboutFormService aboutFormService;
	
	@Autowired
	public void setAboutFormService(AboutFormService aboutFormService) {
		this.aboutFormService = aboutFormService;
	}

	@Autowired
	public void setExperienceUploadService(ExperienceUploadService experienceUploadService) {
		this.experienceUploadService = experienceUploadService;
	}

	@Autowired
	public void setWorkTitleService(WorkTitleService workTitleService) {
		this.workTitleService = workTitleService;
	}

	@Autowired
	public void setResumeImportService(ResumeImportService resumeImportService) {
		this.resumeImportService = resumeImportService;
	}

	@Autowired
	public void setEducationService(EducationService educationService) {
		this.educationService = educationService;
	}

	@Autowired
	public void setUploadProjectService(UploadProjectService uploadProjectService) {
		this.uploadProjectService = uploadProjectService;
	}

	@Autowired
	public void setMessageFormService(MessageFormService messageFormService) {
		this.messageFormService = messageFormService;
	}

	@GetMapping(path = { "", "/", "home", "welcome", "index", "portfolio" })
	public String welcomeView(HttpServletRequest request, Model m) {
		String requestURI = request.getRequestURI();
		m.addAttribute("mycurrentpage", requestURI);
		m.addAttribute("messageForm", new MessageForm());

		List<UploadProjects> allProjects = uploadProjectService.readAllProjects();
		m.addAttribute("readProjects", allProjects);
//		System.out.println(allProjects);
//		System.out.println("All Projects: " + allProjects); // Check this in the console

		List<EducationUpload> allEducationService = educationService.readEducationService();
		m.addAttribute("readEducations", allEducationService);
		
		List<ExperinceUpload> experinces = experienceUploadService.readExperince();
		m.addAttribute("experinces",experinces);
		
		List<ResumeImport> resumes = resumeImportService.findAll();
		m.addAttribute("resumes", resumes);
		
		List<WorkTitle> title = workTitleService.readTitle();
		m.addAttribute("titles", title);
		
		List<AboutForm> aboutService = aboutFormService.readAboutService();
		m.addAttribute("abouts",aboutService);

		return "portfolio";
	}

	@PostMapping("messageform")
	public String messageForm(@Valid @ModelAttribute MessageForm messageForm, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Model m) {
		if (bindingResult.hasErrors()) {
			// now to print this in FE we use Model Class org.springframework.ui.Model
			m.addAttribute("bindingResult", bindingResult);
			// this method will print the BindingResult Object In FE or HTML page Now going
			// to HTML page
			return "portfolio";
		}
		// hear checking the The object is coming in post mapping with out errors
		// System.out.println(messageForm);
		// when object is filled with the value we storing the in DB
		MessageForm saveMesagaeFromService = messageFormService.saveMesagaeFromService(messageForm);
		if (saveMesagaeFromService != null) {
			// Using redirect attribute as we are returning to re-subbmited page Model will
			// not be able to print
			// the value in front page as is redirected
			redirectAttributes.addFlashAttribute("message", "Message Sent Successfully");
		} else {
			// if some error happens in saving the data in the DB
			redirectAttributes.addFlashAttribute("message", "Message Failed ");
		}
		return "redirect:portfolio";
	}

	@GetMapping("/login")
	public String adminLoginView(HttpServletRequest request, Model model) {

		ServletContext servletContext = request.getServletContext();
		Object attribute = servletContext.getAttribute("logout");
		if (attribute instanceof Boolean) {
			model.addAttribute("logout", attribute);
			servletContext.removeAttribute("logout");
		}
		return "adminlogin";
	}

	@GetMapping("/download/{id}")
	public ResponseEntity<Resource> downloadFile(@PathVariable int id) {
//		System.out.println("Download requested for ID: " + id);
		ResumeImport resume = resumeImportService.findById(id);
		if (resume == null) {
			return ResponseEntity.notFound().build();
		}

		String fileName = resume.getResume();
//		System.out.println("FileName :" + fileName);
		Path filePath = Paths.get(
				"C:\\Users\\praveen\\OneDrive\\Desktop\\projects\\portfolio-1\\src\\main\\resources\\static\\myresume\\"
						+ fileName);
//		System.out.println("File path: " + filePath.toString());
		Resource resource;
		try {
			resource = new UrlResource(filePath.toUri());
			if (!resource.exists()) {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
}
