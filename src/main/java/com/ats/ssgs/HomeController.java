package com.ats.ssgs;

import java.io.IOException;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ssgs.common.Constants;
import com.ats.ssgs.model.DashSaleCount;
import com.ats.ssgs.model.ModuleJson;
import com.ats.ssgs.model.master.LoginResUser;
import com.ats.ssgs.model.master.Plant;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	RestTemplate rest = new RestTemplate();
	List<Plant> plantList;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "login";
	}

	@RequestMapping("/loginProcess")
	public ModelAndView helloWorld(HttpServletRequest request, HttpServletResponse res) throws IOException {

		String name = request.getParameter("username");
		String password = request.getParameter("password");

		ModelAndView mav = new ModelAndView("login");

		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		HttpSession session = request.getSession();

		try {
			System.out.println("Login Process " + name);

			if (name.equalsIgnoreCase("") || password.equalsIgnoreCase("") || name == null || password == null) {

				mav = new ModelAndView("login");
			} else {

				RestTemplate restTemplate = new RestTemplate();

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

				map.add("usrMob", name);
				map.add("userPass", password);

				LoginResUser userObj = restTemplate.postForObject(Constants.url + "loginUser", map, LoginResUser.class);

				String loginResponseMessage = "";

				if (userObj.isError() == false) {

					session.setAttribute("UserDetail", userObj);
					LoginResUser userResponse = (LoginResUser) session.getAttribute("UserDetail");
					session.setAttribute("userInfo", userResponse.getUser());

					mav = new ModelAndView("home");
					session.setAttribute("userName", name);

					loginResponseMessage = "Login Successful";
					mav.addObject("loginResponseMessage", loginResponseMessage);

					map = new LinkedMultiValueMap<String, Object>();
					int userId = userObj.getUser().getUserId();
					map.add("userId", userId);
					System.out.println("user data" + userResponse.toString());

					try {
						ParameterizedTypeReference<List<ModuleJson>> typeRef = new ParameterizedTypeReference<List<ModuleJson>>() {
						};
						ResponseEntity<List<ModuleJson>> responseEntity = restTemplate.exchange(
								Constants.url + "getRoleJson", HttpMethod.POST, new HttpEntity<>(map), typeRef);

						List<ModuleJson> newModuleList = responseEntity.getBody();
						System.err.println("new Module List " + newModuleList.toString());

						session.setAttribute("newModuleList", newModuleList);
						session.setAttribute("sessionModuleId", 0);
						session.setAttribute("sessionSubModuleId", 0);

						Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
						plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

						mav.addObject("plantList", plantList);

						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						SimpleDateFormat dd = new SimpleDateFormat("dd-MM-yyyy");

						Calendar cal = Calendar.getInstance();

						Calendar cal1 = Calendar.getInstance();
						cal.set(cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH), 1);

						String firstDate = sdf.format(cal.getTimeInMillis());
						String firstDate1 = dd.format(cal.getTimeInMillis());
						cal.set(cal.DAY_OF_MONTH, cal.getActualMaximum(cal.DAY_OF_MONTH));
						String endDate = sdf.format(cal.getTimeInMillis());
						String endDate1 = dd.format(cal.getTimeInMillis());
						System.out.println("sd " + firstDate);
						System.out.println("ed " + endDate);
						map = new LinkedMultiValueMap<String, Object>();

						map.add("fromDate", firstDate);

						map.add("toDate", endDate);
						map.add("plantId", 0);

						DashSaleCount dashBoard = rest.postForObject(Constants.url + "/getDashboardCountBetDate", map,
								DashSaleCount.class);

						mav.addObject("dashBoard", dashBoard);

						mav.addObject("fromDate", firstDate1);
						mav.addObject("toDate", endDate1);

					} catch (Exception e) {
						e.printStackTrace();
					}
					return mav;

				} else {

					mav = new ModelAndView("login");
					System.out.println("Invalid login credentials");

				}

			}
		} catch (Exception e) {
			System.out.println("HomeController Login API Excep:  " + e.getMessage());
			e.printStackTrace();

		}

		return mav;

	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		System.out.println("User Logout");

		session.invalidate();
		return "redirect:/";
	}

	@ExceptionHandler(LoginFailException.class)
	public String redirectToLogin() {
		System.out.println("HomeController Login Fail Excep:");

		return "login";
	}

	@RequestMapping(value = "/sessionTimeOut", method = RequestMethod.GET)
	public String sessionTimeOut(HttpSession session) {
		System.out.println("User Logout");

		session.invalidate();
		return "redirect:/";
	}

}
