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

import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ssgs.common.Constants;
import com.ats.ssgs.model.master.DocTermDetail;
import com.ats.ssgs.model.master.DocTermHeader;
import com.ats.ssgs.model.master.Document;
import com.ats.ssgs.model.master.GetDocTermHeader;
import com.ats.ssgs.model.master.Info;
import com.ats.ssgs.model.master.TempDocDetail;

@Controller
public class DocTermController {
	RestTemplate rest = new RestTemplate();

	List<Document> docList;

	@RequestMapping(value = "/showAddDocTerm", method = RequestMethod.GET)
	public ModelAndView showAddDocTerm(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("docterm/adddocterm");

			Document[] docArray = rest.getForObject(Constants.url + "getAllDocList", Document[].class);
			docList = new ArrayList<Document>(Arrays.asList(docArray));

			model.addObject("docList", docList);

			model.addObject("title", "Add Terms & Conditions");

		} catch (Exception e) {

			System.err.println("exception In showAddDocTerm at Doc Term Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	List<TempDocDetail> tempDocList = new ArrayList<TempDocDetail>();

	@RequestMapping(value = "/addDocTermDetail", method = RequestMethod.GET)
	public @ResponseBody List<TempDocDetail> addDocTermDetail(HttpServletRequest request,
			HttpServletResponse response) {

		try {

			int isDelete = Integer.parseInt(request.getParameter("isDelete"));

			int isEdit = Integer.parseInt(request.getParameter("isEdit"));

			if (isDelete == 1) {
				int key = Integer.parseInt(request.getParameter("key"));

				tempDocList.remove(key);

			} else if (isEdit == 1) {
				int index = Integer.parseInt(request.getParameter("index"));

				int sortNoDetail = Integer.parseInt(request.getParameter("sortNoDetail"));

				String termDesc = request.getParameter("termDesc");
				tempDocList.get(index).setSortNo(sortNoDetail);
				tempDocList.get(index).setTermDesc(termDesc);

			}

			else {
				int sortNoDetail = Integer.parseInt(request.getParameter("sortNoDetail"));

				String termDesc = request.getParameter("termDesc");

				TempDocDetail tempDoc = new TempDocDetail();
				tempDoc.setSortNo(sortNoDetail);
				tempDoc.setTermDesc(termDesc);
				tempDoc.setTermDetailId(0);
				tempDocList.add(tempDoc);
			}

		} catch (Exception e) {
			System.err.println("Exce In atempDocList  temp List " + e.getMessage());
			e.printStackTrace();
		}
		System.err.println(" enq Item List " + tempDocList.toString());

		return tempDocList;

	}

	@RequestMapping(value = "/getDocTermForEdit", method = RequestMethod.GET)
	public @ResponseBody TempDocDetail getDocTermForEdit(HttpServletRequest request, HttpServletResponse response) {

		int termDetailId = Integer.parseInt(request.getParameter("termDetailId"));
		int index = Integer.parseInt(request.getParameter("index"));

		return tempDocList.get(index);

	}

	// insertDocTerm
	@RequestMapping(value = "/insertDocTerm", method = RequestMethod.POST)
	public String insertDocTerm(HttpServletRequest request, HttpServletResponse response) {

		try {

			System.err.println("Inside insert insertDocTerm method");

			int docId = Integer.parseInt(request.getParameter("doc_id"));

			System.err.println("docId Id " + docId);

			String termTitle = request.getParameter("termTitle");

			// int sortNo = Integer.parseInt(request.getParameter("sortNo"));

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			String curDate = dateFormat.format(new Date());
			DocTermHeader docHead = new DocTermHeader();

			DateFormat dateTimeFrmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			docHead.setDelStatus(1);
			docHead.setDocId(docId);
			docHead.setExInt1(0);
			docHead.setExInt2(0);
			docHead.setExVar1("NA");
			docHead.setExVar2("NA");

			docHead.setTermTitle(termTitle);

			try {
				docHead.setSortNo(Integer.parseInt(request.getParameter("sortNo")));
			} catch (Exception e) {
				docHead.setSortNo(0);
			}

			List<DocTermDetail> docDetailList = new ArrayList<>();

			for (int i = 0; i < tempDocList.size(); i++) {

				DocTermDetail dDetail = new DocTermDetail();

				dDetail.setDelStatus(1);
				dDetail.setExInt1(0);
				dDetail.setExVar1("NA");
				dDetail.setExVar2("NA");
				dDetail.setSortNo(tempDocList.get(i).getSortNo());
				dDetail.setTermDesc(tempDocList.get(i).getTermDesc());

				docDetailList.add(dDetail);

			}
			docHead.setDetailList(docDetailList);

			DocTermHeader docInsertRes = rest.postForObject(Constants.url + "saveDocTermHeaderAndDetail", docHead,
					DocTermHeader.class);

		} catch (Exception e) {

			System.err.println("Exce In insertEnq method  " + e.getMessage());
			e.printStackTrace();

		}

		return "redirect:/showAddDocTerm";

	}

	@RequestMapping(value = "/updateDocTerm", method = RequestMethod.POST)
	public String updateDocTerm(HttpServletRequest request, HttpServletResponse response) {

		try {

			System.err.println("Inside  updateDocTerm method");

			int docId = Integer.parseInt(request.getParameter("doc_id"));

			System.err.println("docId Id " + docId);

			String termTitle = request.getParameter("termTitle");

			int sortNo = Integer.parseInt(request.getParameter("sortNo"));

			editDoc.setTermTitle(termTitle);
			editDoc.setSortNo(sortNo);

			for (int i = 0; i < editDoc.getDetailList().size(); i++) {
				int sortNoDetail = Integer.parseInt(
						request.getParameter("detailSortNo" + editDoc.getDetailList().get(i).getTermDetailId()));

				String termDesc = request.getParameter("termDesc" + editDoc.getDetailList().get(i).getTermDetailId());

				editDoc.getDetailList().get(i).setSortNo(sortNoDetail);
				editDoc.getDetailList().get(i).setTermDesc(termDesc);

			}

			DocTermHeader docInsertRes = rest.postForObject(Constants.url + "saveDocTermHeaderAndDetail", editDoc,
					DocTermHeader.class);

		} catch (Exception e) {

			System.err.println("Exce In insertEnq method  " + e.getMessage());
			e.printStackTrace();

		}

		return "redirect:/showAddDocTerm";

	}

	List<GetDocTermHeader> docTermHeaderList;

	@RequestMapping(value = "/showDocTermList", method = RequestMethod.GET)
	public ModelAndView showDocTermList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("docterm/doctermlist");
			GetDocTermHeader[] docArray = rest.getForObject(Constants.url + "getAllDocHeaderList",
					GetDocTermHeader[].class);
			docTermHeaderList = new ArrayList<GetDocTermHeader>(Arrays.asList(docArray));

			model.addObject("title", "Term & Conditions List");
			model.addObject("docHeaderList", docTermHeaderList);
		} catch (Exception e) {

			System.err.println("exception In showDocTermList at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	DocTermHeader editDoc;

	@RequestMapping(value = "/editDocHeader/{termId}", method = RequestMethod.GET)
	public ModelAndView editDocHeader(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int termId) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("docterm/editDocTerm");

			Document[] docArray = rest.getForObject(Constants.url + "getAllDocList", Document[].class);
			docList = new ArrayList<Document>(Arrays.asList(docArray));

			model.addObject("docList", docList);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("termId", termId);
			editDoc = rest.postForObject(Constants.url + "getDocHeaderByTermId", map, DocTermHeader.class);
			model.addObject("title", "Edit Terms & Conditions");
			model.addObject("editDoc", editDoc);
			model.addObject("editDocDetail", editDoc.getDetailList());

		} catch (Exception e) {
			System.err.println("exception In editDoc at Doc Contr" + e.getMessage());
			e.printStackTrace();

		}

		return model;
	}

	@RequestMapping(value = "/deleteDocHeader/{termId}", method = RequestMethod.GET)
	public String deleteDocHeader(HttpServletRequest request, HttpServletResponse response, @PathVariable int termId) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("termId", termId);

			Info errMsg = rest.postForObject(Constants.url + "deleteDocHeader", map, Info.class);

		} catch (Exception e) {

			System.err.println("Exception in /deleteDocHeader @DocContr" + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showDocTermList";
	}

}
