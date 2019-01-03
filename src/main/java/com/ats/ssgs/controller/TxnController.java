package com.ats.ssgs.controller;

import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ssgs.common.Constants;
import com.ats.ssgs.common.DateConvertor;
import com.ats.ssgs.common.VpsImageUpload;
import com.ats.ssgs.model.master.GetPoklenReading;
import com.ats.ssgs.model.master.GetWeighing;
import com.ats.ssgs.model.master.Info;
import com.ats.ssgs.model.master.PoklenReading;
import com.ats.ssgs.model.master.Vehicle;
import com.ats.ssgs.model.master.Weighing;
import com.ats.ssgs.model.mat.Contractor;
import com.ats.ssgs.model.mat.RawMatItem;
import com.ats.ssgs.model.quot.GetQuotHeads;

@Controller
@Scope("session")
public class TxnController {
	RestTemplate rest = new RestTemplate();
	int isError = 0;

	List<Vehicle> vehList;
	List<Contractor> conList;
	List<GetWeighing> weighList;
	List<Vehicle> vehPoklenList;
	List<PoklenReading> pReadingList;
	List<GetPoklenReading> pReading;

	@RequestMapping(value = "/showAddWeighing", method = RequestMethod.GET)
	public ModelAndView showAddWeighing(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("matissue/addweighing");
			model.addObject("isError", isError);
			isError = 0;
			model.addObject("title", "Add Weighing");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("vehicleType", 1);

			Vehicle[] vehArray = rest.postForObject(Constants.url + "getVehListByVehicleType", map, Vehicle[].class);
			vehList = new ArrayList<Vehicle>(Arrays.asList(vehArray));

			model.addObject("vehList", vehList);

			map = new LinkedMultiValueMap<String, Object>();

			map.add("vehicleType", 3);

			Vehicle[] vehPoklenArray = rest.postForObject(Constants.url + "getVehListByVehicleType", map,
					Vehicle[].class);
			vehPoklenList = new ArrayList<Vehicle>(Arrays.asList(vehPoklenArray));

			model.addObject("vehPoklenList", vehPoklenList);

			Contractor[] conArray = rest.getForObject(Constants.url + "getAllContractorList", Contractor[].class);
			conList = new ArrayList<Contractor>(Arrays.asList(conArray));

			model.addObject("conList", conList);

			model.addObject("weighImageUrl", Constants.WEIGHT_READING_URL);

		} catch (Exception e) {

			System.err.println("exception In showAddWeighing at TxnController Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/insertWeighing", method = RequestMethod.POST)
	public String insertWeighing(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("imgInp") List<MultipartFile> file, @RequestParam("imgInp1") List<MultipartFile> file1) {

		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date now = new Date();
			Calendar cal = Calendar.getInstance();

			System.err.println("Inside insertWeighing method");

			int weighId = 0;
			try {
				weighId = Integer.parseInt(request.getParameter("weighId"));
			} catch (Exception e) {
				weighId = 0;
			}

			String curDate = dateFormat.format(new Date());

			int vehId = Integer.parseInt(request.getParameter("vehId"));
			int contraId = Integer.parseInt(request.getParameter("contr_id"));

			int poklenId = Integer.parseInt(request.getParameter("poklenId"));

			float qty = Float.parseFloat(request.getParameter("qty"));

			float vehKm = Float.parseFloat(request.getParameter("vehKm"));
			float poklenKm = Float.parseFloat(request.getParameter("poklenKm"));

			float contRate = Float.parseFloat(request.getParameter("rate"));
			String date = request.getParameter("date");

			System.out.println("Previous Image1" + file.get(0).getOriginalFilename());
			System.out.println("Previous Image2" + file1.get(0).getOriginalFilename());
			String prevImage2 = null;
			String prevImage1 = null;
			try {
				if (!file.get(0).getOriginalFilename().equalsIgnoreCase("")) {
					VpsImageUpload imgUpload = new VpsImageUpload();

					// String tStamp = dateFormat.format(cal.getTime());
					String tStamp = "" + System.currentTimeMillis();
					String extension = FilenameUtils.getExtension(file.get(0).getOriginalFilename());

					// String prevImage1 = new String();
					prevImage1 = tStamp + "_1." + extension;
					imgUpload.saveUploadedFiles(file.get(0), Constants.WEIGHT_IMG_TYPE, prevImage1);

					System.out.println("prevImage1" + prevImage1);
				}
			} catch (Exception e) {
				System.err.println("Exc in uploading WorkType Imag " + e.getMessage());
				e.printStackTrace();

			}
			try {

				if (!file1.get(0).getOriginalFilename().equalsIgnoreCase("")) {
					VpsImageUpload imgUpload = new VpsImageUpload();

					// String tStamp = dateFormat.format(cal.getTime());
					String tStamp = "" + System.currentTimeMillis();
					String extension = FilenameUtils.getExtension(file1.get(0).getOriginalFilename());

					prevImage2 = tStamp + "_2." + extension;
					imgUpload.saveUploadedFiles(file1.get(0), Constants.WEIGHT_IMG_TYPE, prevImage2);
					System.out.println("prevImage2" + prevImage2);

				}
			} catch (Exception e) {
				System.err.println("Exc in uploading WorkType Imag " + e.getMessage());
				e.printStackTrace();

			}

			Weighing weigh = new Weighing();
			weigh.setContraId(contraId);
			weigh.setContRate(contRate);
			weigh.setDate(DateConvertor.convertToYMD(date));
			weigh.setDelStatus(1);
			weigh.setExBool1(1);
			weigh.setExBool2(1);
			weigh.setExDate1(curDate);
			weigh.setExDate2(curDate);
			weigh.setExInt1(1);
			weigh.setExInt2(1);
			weigh.setExInt3(1);
			weigh.setExVar1("NA");
			weigh.setExVar2("NA");
			weigh.setExVar3("NA");
			weigh.setPoklenId(poklenId);
			weigh.setPoklenKm(poklenKm);
			weigh.setVehKm(vehKm);
			weigh.setQuantity(qty);
			weigh.setVehId(vehId);
			weigh.setUserId(1);
			weigh.setWeighId(weighId);
			weigh.setPhoto1(prevImage1);

			try {
				weigh.setRemark(request.getParameter("remark"));

			} catch (Exception e) {
				weigh.setRemark("NA");
			}

			try {

				weigh.setPhoto2(prevImage2);

			} catch (Exception e) {

				weigh.setPhoto2("NA");
			}

			Weighing weighInsertRes = rest.postForObject(Constants.url + "saveWeighing", weigh, Weighing.class);
			if (weighInsertRes != null) {
				isError = 2;
			} else {
				isError = 1;
			}

			System.err.println("plantInsertRes " + weighInsertRes.toString());

		} catch (Exception e) {
			System.err.println("EXCE in weighInsertRes " + e.getMessage());
			e.printStackTrace();

		}
		return "redirect:/showAddWeighing";

	}

	@RequestMapping(value = "/showWeighingList", method = RequestMethod.GET)
	public ModelAndView showWeighingList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("matissue/weighList");

			model.addObject("title", "Weighing List");
			GetWeighing[] weighArray = rest.getForObject(Constants.url + "getWeighList", GetWeighing[].class);
			weighList = new ArrayList<GetWeighing>(Arrays.asList(weighArray));

			model.addObject("weighList", weighList);
		} catch (Exception e) {

			System.err.println("exception In showWeighingList at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/editWeighing/{weighId}", method = RequestMethod.GET)
	public ModelAndView editWeighing(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int weighId) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("matissue/editweighing");
			model.addObject("title", "Edit Weighing");
			model.addObject("weighImageUrl", Constants.WEIGHT_READING_URL);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("vehicleType", 1);

			Vehicle[] vehArray = rest.postForObject(Constants.url + "getVehListByVehicleType", map, Vehicle[].class);
			vehList = new ArrayList<Vehicle>(Arrays.asList(vehArray));

			model.addObject("vehList", vehList);

			map = new LinkedMultiValueMap<String, Object>();

			map.add("vehicleType", 3);

			Vehicle[] vehPoklenArray = rest.postForObject(Constants.url + "getVehListByVehicleType", map,
					Vehicle[].class);
			vehPoklenList = new ArrayList<Vehicle>(Arrays.asList(vehPoklenArray));

			model.addObject("vehPoklenList", vehPoklenList);

			Contractor[] conArray = rest.getForObject(Constants.url + "getAllContractorList", Contractor[].class);
			conList = new ArrayList<Contractor>(Arrays.asList(conArray));

			model.addObject("conList", conList);

			map = new LinkedMultiValueMap<String, Object>();

			map.add("weighId", weighId);

			Weighing editWeigh = rest.postForObject(Constants.url + "getWeighingById", map, Weighing.class);

			model.addObject("editWeigh", editWeigh);

			System.out.println(editWeigh.toString());

		} catch (Exception e) {

			System.err.println("exception In editWeigh at txn Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;
	}

	@RequestMapping(value = "/deleteWeighing/{weighId}", method = RequestMethod.GET)
	public String deleteWeighing(HttpServletRequest request, HttpServletResponse response, @PathVariable int weighId) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("weighId", weighId);

			Info errMsg = rest.postForObject(Constants.url + "deleteWeighing", map, Info.class);

		} catch (Exception e) {

			System.err.println("Exception in /deleteWeighing @txncontroller  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showWeighingList";
	}

	@RequestMapping(value = "/deleteRecordofWeigh", method = RequestMethod.POST)
	public String deleteRecordofWeigh(HttpServletRequest request, HttpServletResponse response) {
		try {

			String[] weighIds = request.getParameterValues("weighIds");

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < weighIds.length; i++) {
				sb = sb.append(weighIds[i] + ",");

			}
			String items = sb.toString();
			items = items.substring(0, items.length() - 1);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("weighIds", items);

			Info errMsg = rest.postForObject(Constants.url + "deleteMultiWeighing", map, Info.class);

		} catch (Exception e) {

			System.err.println("Exception in /deleteMultiWeighing @txncontroller  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showWeighList";
	}

	// Ajax call
	@RequestMapping(value = "/getContractrateById", method = RequestMethod.GET)
	public @ResponseBody Contractor getContractrateById(HttpServletRequest request, HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int contrId = Integer.parseInt(request.getParameter("contrId"));

		map.add("contrId", contrId);

		Contractor conArray = rest.postForObject(Constants.url + "getContractorById", map, Contractor.class);

		return conArray;

	}

	@RequestMapping(value = "/showAddPReading", method = RequestMethod.GET)
	public ModelAndView showAddPReading(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("matissue/pokreading");
			model.addObject("isError", isError);
			isError = 0;
			model.addObject("title", "Add Poklen Reading");
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("vehicleType", 3);

			Vehicle[] vehPoklenArray = rest.postForObject(Constants.url + "getVehListByVehicleType", map,
					Vehicle[].class);
			vehPoklenList = new ArrayList<Vehicle>(Arrays.asList(vehPoklenArray));

			model.addObject("vehPoklenList", vehPoklenList);

		} catch (Exception e) {

			System.err.println("exception In showAddPReading at TxnController Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/insertPoklenReading", method = RequestMethod.POST)
	public String insertPoklenReading(HttpServletRequest request, HttpServletResponse response) {

		try {

			System.err.println("Inside insertPoklenReading method");

			int readingId = 0;
			try {
				readingId = Integer.parseInt(request.getParameter("readingId"));
			} catch (Exception e) {
				readingId = 0;
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			String curDate = dateFormat.format(new Date());
			int poklenId = Integer.parseInt(request.getParameter("poklenId"));

			int pokeType = Integer.parseInt(request.getParameter("pokeType"));

			int sType = Integer.parseInt(request.getParameter("sType"));

			float startReading = Float.parseFloat(request.getParameter("startReading"));
			// float endReading = Float.parseFloat(request.getParameter("endReading"));

			String startDate = request.getParameter("start_date");

			// String endDate = request.getParameter("end_date");
			String startTime = request.getParameter("startTime");
			// String endTime = request.getParameter("endTime");

			PoklenReading pReading = new PoklenReading();

			pReading.setDelStatus(1);

			pReading.setExBool1(1);
			pReading.setExDate1(curDate);

			pReading.setExInt2(1);
			pReading.setPoklenId(poklenId);

			pReading.setExVar1("NA");
			pReading.setExVar2("NA");
			pReading.setStartTime(startTime);
			pReading.setStartReading(startReading);
			pReading.setStartDate(DateConvertor.convertToYMD(startDate));
			pReading.setShiftType(sType);
			pReading.setPokType(pokeType);
			pReading.setReadingId(readingId);

			System.out.println("curDate123456===============" + curDate);
			System.out.println("curDate===============" + curDate);

			try {

				String end_date = request.getParameter("end_date");
				System.out.println("end_date" + end_date);
				if (!end_date.isEmpty()) {
					pReading.setEndDate(DateConvertor.convertToYMD(end_date));
				} else {
					pReading.setEndDate(curDate);
				}

			} catch (Exception e) {
				pReading.setEndDate(curDate);

			}
			try {
				pReading.setEndReading(Float.parseFloat(request.getParameter("endReading")));

			} catch (Exception e) {
				pReading.setEndReading(0);

			}

			try {
				pReading.setEndTime(request.getParameter("endTime"));

			} catch (Exception e) {
				pReading.setEndTime("00:00:00");
			}

			if (readingId != 0) {
				pReading.setExInt1(2);
			} else {
				pReading.setExInt1(1);
			}

			PoklenReading prInsertRes = rest.postForObject(Constants.url + "savePoklenReading", pReading,
					PoklenReading.class);
			if (prInsertRes != null) {
				isError = 2;
			} else {
				isError = 1;
			}

			System.err.println("plantInsertRes " + prInsertRes.toString());

		} catch (

		Exception e) {
			System.err.println("EXCE in weighInsertRes " + e.getMessage());
			e.printStackTrace();

		}
		return "redirect:/showAddPReading";

	}

	@RequestMapping(value = "/showPoklenReadingList", method = RequestMethod.GET)
	public ModelAndView showPoklenReadingList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("matissue/poklenReadingList");

			model.addObject("title", "Poklen Reading  List");
		} catch (Exception e) {

			System.err.println("exception In showPoklenReadingList at Txn Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/getPoklenListBetDate", method = RequestMethod.GET)
	public @ResponseBody List<GetPoklenReading> getPoklenListBetDate(HttpServletRequest request,
			HttpServletResponse response) {

		System.err.println(" in getWeighListBetDate");
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");

		map.add("fromDate", DateConvertor.convertToYMD(fromDate));
		map.add("toDate", DateConvertor.convertToYMD(toDate));
		// map.add("status", 0);

		GetPoklenReading[] ordHeadArray = rest.postForObject(Constants.url + "getPokReadingListBetweenDate", map,
				GetPoklenReading[].class);
		pReading = new ArrayList<GetPoklenReading>(Arrays.asList(ordHeadArray));

		return pReading;
	}

	@RequestMapping(value = "/showPendingPoklenReadingList", method = RequestMethod.GET)
	public ModelAndView showPendingPoklenReadingList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("matissue/pendingReadingList");

			model.addObject("title", "Pending Poklen Reading  List");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("status", 1);

			GetPoklenReading[] weighArray = rest.postForObject(Constants.url + "getPokleByStatus", map,
					GetPoklenReading[].class);
			pReading = new ArrayList<GetPoklenReading>(Arrays.asList(weighArray));

			model.addObject("pReadingList", pReading);
		} catch (Exception e) {

			System.err.println("exception In showPendingPoklenReadingList at Txn Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/deletePReading/{readingId}", method = RequestMethod.GET)
	public String deletePReading(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int readingId) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("readingId", readingId);

			Info errMsg = rest.postForObject(Constants.url + "deletePoklenReading", map, Info.class);

		} catch (Exception e) {

			System.err.println("Exception in /deletePoklenReading @txncontroller  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showPoklenReadingList";
	}

	@RequestMapping(value = "/deleteRecordofPReading", method = RequestMethod.POST)
	public String deleteRecordofPReading(HttpServletRequest request, HttpServletResponse response) {
		try {

			String[] readingIds = request.getParameterValues("readingIds");

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < readingIds.length; i++) {
				sb = sb.append(readingIds[i] + ",");

			}
			String items = sb.toString();
			items = items.substring(0, items.length() - 1);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("readingIds", items);

			Info errMsg = rest.postForObject(Constants.url + "deleteMultiPReading", map, Info.class);

		} catch (Exception e) {

			System.err.println("Exception in /deleteMultiPReading @txncontroller  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showPoklenReadingList";
	}

	@RequestMapping(value = "/editPReading/{readingId}", method = RequestMethod.GET)
	public ModelAndView editPReading(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int readingId) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("matissue/editPokReading");
			model.addObject("title", "Edit Poklen Reading");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("vehicleType", 3);

			Vehicle[] vehPoklenArray = rest.postForObject(Constants.url + "getVehListByVehicleType", map,
					Vehicle[].class);
			vehPoklenList = new ArrayList<Vehicle>(Arrays.asList(vehPoklenArray));

			model.addObject("vehPoklenList", vehPoklenList);

			map = new LinkedMultiValueMap<String, Object>();

			map.add("readingId", readingId);

			PoklenReading editPRead = rest.postForObject(Constants.url + "getPoklenReadingById", map,
					PoklenReading.class);

			model.addObject("editPRead", editPRead);

			if (editPRead.getExInt1() == 1) {
				model.addObject("endDate", "-");
				model.addObject("endReading", "-");
				model.addObject("endTime", "-");
			} else {

				model.addObject("endDate", editPRead.getEndDate());
				model.addObject("endReading", editPRead.getEndReading());
				model.addObject("endTime", editPRead.getEndTime());

			}

		} catch (Exception e) {

			System.err.println("exception In editPRead at txn Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;
	}

	@RequestMapping(value = "/showWeighList", method = RequestMethod.GET)
	public ModelAndView showWeighList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("matissue/weighinglist");

			model.addObject("title", "Weighing List");
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("vehicleType", 1);

			Vehicle[] vehArray = rest.postForObject(Constants.url + "getVehListByVehicleType", map, Vehicle[].class);
			vehList = new ArrayList<Vehicle>(Arrays.asList(vehArray));

			model.addObject("vehList", vehList);

			Contractor[] conArray = rest.getForObject(Constants.url + "getAllContractorList", Contractor[].class);
			conList = new ArrayList<Contractor>(Arrays.asList(conArray));

			model.addObject("conList", conList);

		} catch (Exception e) {

			System.err.println("exception In showWeighList at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/getWeighListBetDate", method = RequestMethod.GET)
	public @ResponseBody List<GetWeighing> getWeighListBetDate(HttpServletRequest request,
			HttpServletResponse response) {

		System.err.println(" in getWeighListBetDate");
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int vehId = Integer.parseInt(request.getParameter("vehId"));
		int contrId = Integer.parseInt(request.getParameter("contrId"));

		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");

		map.add("vehicleId", vehId);
		map.add("contrId", contrId);
		map.add("fromDate", DateConvertor.convertToYMD(fromDate));
		map.add("toDate", DateConvertor.convertToYMD(toDate));
		// map.add("status", 0);

		GetWeighing[] ordHeadArray = rest.postForObject(Constants.url + "getWeighListBetweenDate", map,
				GetWeighing[].class);
		weighList = new ArrayList<GetWeighing>(Arrays.asList(ordHeadArray));

		return weighList;
	}

}
