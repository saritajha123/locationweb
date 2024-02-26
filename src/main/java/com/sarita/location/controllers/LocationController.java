package com.sarita.location.controllers;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sarita.location.entities.Location;
import com.sarita.location.repos.LocationRepository;
import com.sarita.location.service.LocationService;
import com.sarita.location.util.EmailUtil;
import com.sarita.location.util.ReportUtil;

@Controller
public class LocationController {
	@Autowired
	LocationService service;

	@Autowired
	LocationRepository repository;

	@Autowired
	ReportUtil reportUtil;

	@Autowired
	EmailUtil emailUtil;
	@Autowired
	ServletContext sc;
	

	@RequestMapping("/showCreate")
	public String showCreate() {
		return "createLocation";
	}

	@RequestMapping("/saveLoc")
	public String saveLocation(@ModelAttribute("Location") Location location, ModelMap modelMap) {

		Location locationSaved = service.saveLocation(location);
		String msg = "Location saved with id" + locationSaved.getId();
		modelMap.addAttribute(msg, msg);
		emailUtil.sendEmail("saritajha37@gmail.com", "LOcation Saved",
				"Location Saved Successfully and about to return a response");
		return "createLocation";
	}

//	@RequestMapping(path = "/displayLocations", method = RequestMethod.GET)
//	public String displayLocations(ModelMap modelMap) {
//		List<Location> locations = service.getAllLocation();
//		modelMap.addAttribute("locations",locations);
//		return "displayLocations";
//	}

//	@RequestMapping("/displayLocations")
//	public ModelAndView displayLocations() {
//		ModelAndView modelAndView = new ModelAndView("displayLocations");
//		List<Location> locations = service.getAllLocation();
//	    modelAndView.addObject("locations",locations);
//	    return modelAndView;
//	}

	@RequestMapping("/displayLocations")
	public String displayLocations(ModelMap modelMap) {
		List<Location> locations = service.getAllLocation();
		modelMap.addAttribute("locations", locations);
		return "displayLocations";
	}

	@RequestMapping("deleteLocation")
	public String deleteLocation(@RequestParam("id") int id, ModelMap modelMap) {
		// Location location =service.getLocationById(id);
		Location location = new Location();
		location.setId(id);
		service.deleteLocation(location);
		List<Location> locations = service.getAllLocation();
		modelMap.addAttribute("locations", locations);
		return "displayLocations";

	}

	@RequestMapping("/showUpdate")
	public String showUpdate(@RequestParam("id") int id, ModelMap modelMap) {
		Location location = service.getLocationById(id);
		modelMap.addAttribute("location", location);
		return "updateLocation";

	}

	@RequestMapping("/updateLoc")
	public String updateLocation(@ModelAttribute("location") Location location, ModelMap modelMap) {
		service.updateLocation(location);
		List<Location> locations = service.getAllLocations();
		modelMap.addAttribute("locations", locations);
		return "displayLocations";

	}

	@RequestMapping("/generateReport")
	public String generateReport() {
		String Path = sc.getRealPath("/");
		List<Object[]> data = repository.findTypeAndTypeCount();

		reportUtil.generatePieChart(Path, data);
		return "report";

	}

}
