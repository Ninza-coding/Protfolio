package com.portfolio.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.portfolio.config.AdminCredentialsService;
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

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("admin")
public class AdminController {

	private MessageFormService messageFormService;
	private AdminCredentialsService adminCredentialsService;
	private UploadProjectService uploadProjectService;
	private EducationService educationService;
	private ExperienceUploadService experienceUploadService;
	private ResumeImportService resumeImportService;
	private WorkTitleService workTitleService;
	private AboutFormService aboutFormService;
	
	@Autowired
	public void setAboutFormService(AboutFormService aboutFormService) {
		this.aboutFormService = aboutFormService;
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
	public void setExperienceUploadService(ExperienceUploadService experienceUploadService) {
		this.experienceUploadService = experienceUploadService;
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
	public void setAdminCredentialsService(AdminCredentialsService adminCredentialsService) {
		this.adminCredentialsService = adminCredentialsService;
	}

	@Autowired
	public void setMessageFormService(MessageFormService messageFormService) {
		this.messageFormService = messageFormService;
	}

	@GetMapping("dashboard")
	public String adminDashboard() {
		return "admin/dashboard";
	}

	@GetMapping("readAllContacts")
	public String readAllContacts(HttpServletRequest req, Model m) {
		String requestURI = req.getRequestURI();
		m.addAttribute("mycurrentpage", requestURI);

		List<MessageForm> allContactsServices = messageFormService.readAllContactsServices();
		m.addAttribute("allcontacts", allContactsServices);
		System.out.println(allContactsServices);
		System.out.println("Hello");
		return "admin/readallcontacts";
	}

	@GetMapping("deletecontact/{id}")
	public String deleteContact(@PathVariable int id, RedirectAttributes redirectAttributes) {

		messageFormService.deleteMessageService(id);
		redirectAttributes.addFlashAttribute("message", "Contact Deleted");

		return "redirect:/admin/readAllContacts";
	}

	@GetMapping("changeCredentials")
	public String changeCredentialsView() {
		return "admin/changecredentials";
	}

	@PostMapping("changeCredentials")
	public String changeCredentials(@RequestParam("oldusername") String oldusername,
			@RequestParam("oldpassword") String oldpassword, @RequestParam("newusername") String newusername,
			@RequestParam("newpassword") String newpassword, RedirectAttributes redirectAttributes) {

		String result = adminCredentialsService.checkAdminCredentials(oldusername, oldpassword);
		if (result.endsWith("SUCCESS")) {
			// Password Update
			result = adminCredentialsService.updateAdminCredentialsService(newusername, newpassword, oldusername);
			redirectAttributes.addFlashAttribute("message", result);
		} else {
			redirectAttributes.addFlashAttribute("message", result);
		}
		return "redirect:/admin/dashboard";

	}

	@GetMapping("addProjects")
	public String addProjectsview() {
		return "admin/addprojects";
	}

	@InitBinder("uploadProjects") // used to stop the image to stored as string and stored as file
	public void stopBinding(WebDataBinder webDataBinder) {
		webDataBinder.setDisallowedFields("image");
	}

	@PostMapping("addProjects")
	public String addProjects(@ModelAttribute UploadProjects uploadProjects,
			@RequestParam("image") MultipartFile multipartFile, RedirectAttributes redirectAttributes) {
		String originalFilename = multipartFile.getOriginalFilename();
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
		String timestamp = now.format(formatter);
		uploadProjects.setDate(LocalDate.now());
		uploadProjects.setImage(timestamp + originalFilename);
		try {
			UploadProjects projects = uploadProjectService.addProjects(uploadProjects, multipartFile);
			if (projects != null) {
				redirectAttributes.addFlashAttribute("msg", "Project Uploaded Successfully");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Failed to Project Uploaded");

			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("msg", "Something Went Wrong");
		}

		return "redirect:/admin/addProjects";
	}

	@GetMapping("deleteProjects")
	public String deleProjectView(Model model) {
		List<UploadProjects> allProjects = uploadProjectService.readAllProjects();
		model.addAttribute("allProjects", allProjects);
		return "admin/deleteprojects";
	}

	@GetMapping("deleteprojects/{id}")
	public String deleteProject(@PathVariable int id, RedirectAttributes redirectAttributes) {
		uploadProjectService.deleteProject(id);
		System.out.println(id);
		redirectAttributes.addFlashAttribute("message", "Project Deleted");
		return "redirect:/admin/deleteProjects";
	}

	@GetMapping("educationUpload")
	public String edcationUploadView(HttpServletRequest req, Model m) {
		String requestURI = req.getRequestURI();
		m.addAttribute("mycurrentpage", requestURI);
		m.addAttribute("educationUpload", new EducationUpload());

		List<EducationUpload> allEducationService = educationService.readEducationService();
		m.addAttribute("alleducation", allEducationService);

		return "admin/educationupload";
	}

	@PostMapping("educationupload")
	public String edcationUpload(@Valid @ModelAttribute EducationUpload educationUpload, BindingResult bindingResult,
			Model m, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			m.addAttribute("bindingResult", bindingResult);
			return "admin/educationupload";
		}
		if (educationUpload.getDescription().length() > 2000) { // Adjust based on your column size
			bindingResult.rejectValue("description", "error.educationUpload", "Description is too long.");
			return "admin/educationupload";
		}
		EducationUpload saveEducationService = educationService.saveEducationService(educationUpload);
		if (saveEducationService != null) {
			redirectAttributes.addFlashAttribute("message", "Education Upload Successfully");
		} else {
			redirectAttributes.addFlashAttribute("message", "Failed to Upload");
		}
		return "redirect:/admin/educationUpload";
	}

	@GetMapping("deleteeducation/{id}")
	public String deleteCEducation(@PathVariable int id, RedirectAttributes redirectAttributes) {

		educationService.deleteEducationService(id);
		redirectAttributes.addFlashAttribute("message", "Education Deleted");

		return "redirect:/admin/educationUpload";
	}

	@GetMapping("experinceUpload")
	public String getExperinceUploadView(HttpServletRequest req, Model m) {
		String requestURI = req.getRequestURI();
		m.addAttribute("mycurrentpage", requestURI);
		// to check the error in the front page
		m.addAttribute("experinceUpload", new ExperinceUpload());

		List<ExperinceUpload> allexperience = experienceUploadService.readExperince();
		m.addAttribute("allexperience", allexperience);
		// System.out.println(allexperience);
		return "admin/experinceupload";
	}

	@PostMapping("experinceupload")
	public String experinceUpload(@Valid @ModelAttribute ExperinceUpload experinceUpload, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Model m) {
		if (bindingResult.hasErrors()) {
			m.addAttribute("bindingResult", bindingResult);
			return "admin/experinceupload";

		}
		ExperinceUpload saveExperience = experienceUploadService.saveExperience(experinceUpload);
		if (saveExperience != null) {
			redirectAttributes.addFlashAttribute("message", "Experince Uploaded Successfully");
		} else {
			redirectAttributes.addFlashAttribute("message", "Experince Failed to Uploaded");
		}

		return "redirect:/admin/experinceUpload";
	}

	@GetMapping("deleteExperience/{id}")
	public String deleteExperience(@PathVariable int id, RedirectAttributes redirectAttributes) {
		experienceUploadService.deleteExperince(id);
		redirectAttributes.addFlashAttribute("message", "Experince Deleted");
		return "redirect:/admin/experinceUpload";
	}

	// both getImage is used to get the name of the image saved in the folder
	@GetMapping("/getImage")
	// Testing code
	public String imageView(Model model) {
		model.addAttribute("imageForm", new UploadProjects());
		return "admin/getmyimagename"; // This should be the view containing your form
	}

	@PostMapping("getImage")
	// Testing code
	public String getImageFor(@ModelAttribute("imageForm") UploadProjects uploadProjects,
			RedirectAttributes redirectAttributes) {
		String imageById = uploadProjectService.getImageById(uploadProjects.getId());
		System.out.println(imageById);
		redirectAttributes.addFlashAttribute("message", imageById); // Pass image data if needed
		return "redirect:/admin/getImage"; // Redirect to view after processing
	}

	@GetMapping("resumeUpload")
	public String resumeView(Model model) {
		// List<ResumeImport> resumes = resumeImportService.findAll();
		// model.addAttribute("resumes", resumes);
		return "admin/resume";
	}

	@InitBinder("resumeImport")
	public void stopBindingFile(WebDataBinder webDataBinder) {
		webDataBinder.setDisallowedFields("resume");
	}

	@PostMapping("uploadResume")
	public String addResume(@ModelAttribute ResumeImport resumeImport,
			@RequestParam("resume") MultipartFile multipartFile, RedirectAttributes redirectAttributes) {
		// Get the original filename
		String originalFilename = multipartFile.getOriginalFilename();

		// Set only the filename on the ResumeImport model
		resumeImport.setResume(originalFilename);

		try {
			// Call service method to save resume
			ResumeImport saveResume = resumeImportService.saveResume(resumeImport, multipartFile);
			if (saveResume != null) {
				redirectAttributes.addFlashAttribute("msg", "Resume Imported Successfully");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Failed In the Import");
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("msg", "Exception In Import");
		}

		return "redirect:/admin/resumeUpload";
	}

	@GetMapping("resumeUploadTest")
	public String showUploadPage(Model model) {
		List<ResumeImport> resumes = resumeImportService.findAll(); // Assuming you have this method
		model.addAttribute("resumes", resumes);
		return "admin/downloadresume"; // Name of your Thymeleaf template
	}

	@GetMapping("delete/{id}")
	public String deleteResume(@PathVariable int id, RedirectAttributes redirectAttributes) {
		System.out.println(id);
		try {
			resumeImportService.deleteResumeBYId(id);
			redirectAttributes.addFlashAttribute("message", "Deleted Sucessfully");
			return "redirect:/admin/resumeUploadTest";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "Exception");
			return "redirect:/admin/resumeUploadTest";
		}

		// test code
//		ResumeImport resume = resumeImportService.findById(id);
//		String filename= resume.getResume();
//		System.out.println(filename);
	}

	@GetMapping("/download/{id}")
	public ResponseEntity<Resource> downloadFile(@PathVariable int id) {
		ResumeImport resume = resumeImportService.findById(id);
		if (resume == null) {
			return ResponseEntity.notFound().build();
		}

		String fileName = resume.getResume();
		System.out.println("FileName :" + fileName);
		Path filePath = Paths.get(
				"C:\\Users\\praveen\\OneDrive\\Desktop\\projects\\portfolio-1\\src\\main\\resources\\static\\myresume\\"
						+ fileName);
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

	@GetMapping("workTitle")
	public String workTitleView(Model m) {
		m.addAttribute("titleForm", new WorkTitle());

		List<WorkTitle> title = workTitleService.readTitle();
		m.addAttribute("titles", title);
		return "admin/worktitle";
	}

	@PostMapping("workTitle1")
	public String workTitleSave(@Valid @ModelAttribute("titleForm") WorkTitle workTitle, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Model m) {
		if (bindingResult.hasErrors()) {
			m.addAttribute("bindingResult", bindingResult);
			return "admin/worktitle"; // Corrected view name
		}
		WorkTitle saveTitle = workTitleService.saveTitle(workTitle);
		if (saveTitle != null) {
			redirectAttributes.addFlashAttribute("message", "Title Saved Sucessfully");
		} else {
			redirectAttributes.addFlashAttribute("message", "Failed To Save");
		}
		return "redirect:/admin/workTitle";
	}

	@GetMapping("deletetitle/{id}")
	public String deleteTitle(@PathVariable int id, RedirectAttributes redirectAttributes) {
		workTitleService.deleteTitle(id);
		redirectAttributes.addFlashAttribute("message", "Deleted Sucessfully");
		return "redirect:/admin/workTitle";
	}
	@GetMapping("aboutForm")
	public String aboutView(Model m, HttpServletRequest req) {
		String requestURI = req.getRequestURI();
		m.addAttribute("mycurrentpage", requestURI);
		m.addAttribute("aboutForm", new AboutForm());
		
		List<AboutForm> aboutService = aboutFormService.readAboutService();
		m.addAttribute("abouts",aboutService);
		
		return "admin/aboutform";
	}
	@PostMapping("aboutform")
	public String aboutSave(@Valid @ModelAttribute AboutForm aboutForm, BindingResult bindingResult,
			Model m,RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			m.addAttribute("bindingResult", bindingResult);
			return "admin/aboutform";
		}
		if (aboutForm.getAbout().length() > 2000) { // Adjust based on your column size
			bindingResult.rejectValue("description", "error.educationUpload", "Description is too long.");
			return "admin/aboutform";
		}
		AboutForm saveAboutService = aboutFormService.saveAboutService(aboutForm);
		if(saveAboutService!=null) {
			redirectAttributes.addFlashAttribute("message","About is Saved");
		}else {
			redirectAttributes.addFlashAttribute("message","Fail to Save");
		}
		return "redirect:/admin/aboutForm";
	}
	@GetMapping("deleteabout/{id}")
	public String deleteAbout(@PathVariable int id, RedirectAttributes redirectAttributes) {
		aboutFormService.deleteAboutService(id);
		redirectAttributes.addFlashAttribute("message", "Delete Success");
		return "redirect:/admin/aboutForm";
	}
}