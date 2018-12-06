package com.ats.ssgs.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ssgs.common.Constants;
import com.ats.ssgs.common.DateConvertor;
import com.ats.ssgs.model.DispatchItemList;
import com.ats.ssgs.model.DispatchItems;
import com.ats.ssgs.model.master.Item;
import com.ats.ssgs.model.master.Plant;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Controller
@Scope("session")
public class DispatchController {

	RestTemplate rest = new RestTemplate();
	DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    DispatchItemList dispatchItemList=null;
    List<String> allDatesString = new ArrayList<String>();

	@RequestMapping(value = "/showDispatchSheet", method = RequestMethod.GET)
	public ModelAndView showDispatchSheet(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("dispatch/dispatchSheet");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			List<Plant> plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);
			
			Date date = new Date();
			model.addObject("fromDate", dateFormat.format(date)); 
			
			
			// get Calendar instance
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());

			// Add 7 days
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)+7);

			// convert to date
			Date datePlusSeven = cal.getTime();
			
			model.addObject("toDate",dateFormat.format(datePlusSeven)); 

		} catch (Exception e) {

			System.err.println("exception In showDispatchSheet at Dispatch Controller " + e.getMessage());

			e.printStackTrace();

		}

		return model;
	}

    private static Date addDays(Date d1, int i) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(d1);
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }
	@RequestMapping(value = "/getItemList", method = RequestMethod.GET)
	public @ResponseBody DispatchItemList getItemList(HttpServletRequest request, HttpServletResponse response) throws ParseException {

		 dispatchItemList=new DispatchItemList();
		
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int plantId = Integer.parseInt(request.getParameter("plantId"));
		String[] listDate =request.getParameterValues("listDate");
		String fromDate =request.getParameter("fromDate");
		String toDate =request.getParameter("toDate");
        System.err.println(fromDate+"fromDate");
		map.add("plantId", plantId);

		Item[] itemArray = rest.postForObject(Constants.url + "getItemsByPlantId", map, Item[].class);
		List<Item> itemList = new ArrayList<Item>(Arrays.asList(itemArray));
		dispatchItemList.setItemList(itemList);

		    SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
	        String date1 = DateConvertor.convertToYMD(fromDate);
	        String date2 = DateConvertor.convertToYMD(toDate);
            allDatesString.add(date1);
	        try {
	            Date d1 = myFormat.parse(date1);
	            Date d2 = myFormat.parse(date2);
	            List<Date> allDates = new ArrayList<Date>();
	          
	            while( d1.before(d2) ){
	                d1 = addDays(d1, 1);  
	                allDates.add(d1);
	                allDatesString.add(formatter.format(d1));
	            }
	            System.out.println(allDates);
	            System.out.println(allDatesString);
	        } catch (ParseException e) {
	        e.printStackTrace();
	        }

		for(int i=0;i<12;i++)
		{
			
		map = new LinkedMultiValueMap<String, Object>();
		map.add("plantId", plantId);

		map.add("date", allDatesString.get(i));

		DispatchItems[] dispatchItems = rest.postForObject(Constants.url + "getDispatchList", map, DispatchItems[].class);
		List<DispatchItems> dispatchItemsList = new ArrayList<DispatchItems>(Arrays.asList(dispatchItems));
		
		System.err.println(dispatchItemsList.toString());
        if(i==0)
        {
        	dispatchItemList.setDate1(dispatchItemsList);
        }
        if(i==1)
        {
        	dispatchItemList.setDate2(dispatchItemsList);
        }
        if(i==2)
        {
        	dispatchItemList.setDate3(dispatchItemsList);
        }
        if(i==3)
        {
        	dispatchItemList.setDate4(dispatchItemsList);
        }
        if(i==4)
        {
        	dispatchItemList.setDate5(dispatchItemsList);
        }
        if(i==5)
        {
        	dispatchItemList.setDate6(dispatchItemsList);
        }
        if(i==6)
        {
        	dispatchItemList.setDate7(dispatchItemsList);
        }
        if(i==7)
        {
        	dispatchItemList.setDate8(dispatchItemsList);
        }
        if(i==8)
        {
        	dispatchItemList.setDate9(dispatchItemsList);
        }
        if(i==9)
        {
        	dispatchItemList.setDate10(dispatchItemsList);
        }
        if(i==10)
        {
        	dispatchItemList.setDate11(dispatchItemsList);
        }
        if(i==11)
        {
        	dispatchItemList.setDate12(dispatchItemsList);
        }
        if((i+1)==allDatesString.size())
		{
			break;
		}
		}
		System.err.println(dispatchItemList.toString());
		return dispatchItemList;

	}
	
	@RequestMapping(value = "/getDispatchItemsByDate", method = RequestMethod.GET)
	public @ResponseBody DispatchItemList getDispatchItemsByDate(HttpServletRequest request, HttpServletResponse response) throws ParseException {
	
		DispatchItemList dispatchItemList=new DispatchItemList();
		try {
			
			int plantId = Integer.parseInt(request.getParameter("plantId"));
			String date =request.getParameter("date");
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("plantId", plantId);

			Item[] itemArray = rest.postForObject(Constants.url + "getItemsByPlantId", map, Item[].class);
			List<Item> itemList = new ArrayList<Item>(Arrays.asList(itemArray));
			
		    map = new LinkedMultiValueMap<String, Object>();
		    map.add("plantId", plantId);
		    map.add("date",date);

		DispatchItems[] dispatchItems = rest.postForObject(Constants.url + "getDispatchList", map, DispatchItems[].class);
		List<DispatchItems> dispatchItemsList = new ArrayList<DispatchItems>(Arrays.asList(dispatchItems));
		dispatchItemList.setItemList(itemList);
		dispatchItemList.setDate1(dispatchItemsList);
		System.err.println(dispatchItemsList.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return dispatchItemList;
	}
	
	
	@RequestMapping(value = "/showDispatchPdf", method = RequestMethod.GET)
	public void showDispatchPdf(HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		System.out.println("Inside Pdf prod From Order Or Plan");

		// --------------------------------------------------------------------------
		Document document = new Document(PageSize.A4);
		DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
		String reportDate = DF.format(new Date());
        document.addHeader("Date: ", reportDate);
        System.err.println("Date: "+ reportDate);
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();

		//System.out.println("time in Gen Bill PDF ==" + dateFormat.format(cal.getTime()));
		String FILE_PATH = Constants.REPORT_SAVE;
		File file = new File(FILE_PATH);

		PdfWriter writer = null;

		FileOutputStream out = new FileOutputStream(FILE_PATH);
		try {
			writer = PdfWriter.getInstance(document, out);
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		int cols = 3;
		float[] cols1 = new float[] { 0.4f, 1.7f, 1.0f };
		if(allDatesString.size()==1) {
			cols1 = new float[] { 0.4f, 1.7f, 1.0f, 1.0f };
			 cols =4;
		}
		if(allDatesString.size()==2) {
			cols1 = new float[] { 0.4f, 1.7f, 1.0f, 1.0f,1.0f };
			 cols = 5;
		}
		if(allDatesString.size()==3) {
			cols1 = new float[] { 0.4f, 1.7f, 1.0f, 1.0f,1.0f,1.0f};
			 cols = 6;
		}
		if(allDatesString.size()==4) {
			cols1 = new float[] { 0.4f, 1.7f, 1.0f, 1.0f,1.0f,1.0f,1.0f };
			 cols = 7;
		}
		if(allDatesString.size()==5) {
			cols1 = new float[] { 0.4f, 1.7f, 1.0f, 1.0f,1.0f,1.0f,1.0f,1.0f};
			 cols = 8;
		}
		if(allDatesString.size()==6) {
			cols1 = new float[] { 0.4f, 1.7f, 1.0f, 1.0f, 1.0f,1.0f,1.0f,1.0f,1.0f };
		 cols = 9;
		}
		if(allDatesString.size()==7) {
			cols1 = new float[] { 0.4f, 1.7f, 1.0f, 1.0f, 1.0f, 1.0f,1.0f,1.0f,1.0f,1.0f };
		 cols =10;
		}
		if(allDatesString.size()==8) {
			cols1 = new float[] { 0.4f, 1.7f, 1.0f, 1.0f , 1.0f, 1.0f, 1.0f, 1.0f,1.0f,1.0f,1.0f};
		 cols =11;
		}
		if(allDatesString.size()==9) {
			cols1 = new float[] { 0.4f, 1.7f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f,1.0f,1.0f,1.0f };
		 cols = 12;
		}
		if(allDatesString.size()==10)
		{
			cols1 = new float[] { 0.4f, 1.7f, 1.0f, 1.0f , 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f,1.0f,1.0f,1.0f};
			 cols =13;
		}
			if(allDatesString.size()==11)
			{
			cols1 = new float[] { 0.4f, 1.7f, 1.0f, 1.0f , 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f,1.0f,1.0f,1.0f,1.0f};
			 cols =14;
			}
			if(allDatesString.size()==12)
			{
			cols1 = new float[] {0.4f, 1.7f, 1.0f, 1.0f , 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f,1.0f, 1.0f,1.0f,1.0f,1.0f };
			 cols = 15;
			}

		PdfPTable table = new PdfPTable(cols);
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			table.setWidths(cols1);
			Font headFont = new Font(FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK);
			Font headFont1 = new Font(FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.WHITE);
			Font f = new Font(FontFamily.TIMES_ROMAN, 11.0f, Font.UNDERLINE, BaseColor.BLUE);
			Font f1 = new Font(FontFamily.TIMES_ROMAN, 11.0f, Font.UNDERLINE, BaseColor.DARK_GRAY);

			PdfPCell hcell = new PdfPCell();
			
			hcell.setPadding(4);
			hcell = new PdfPCell(new Phrase("Sr.No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Item Name", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Item Code", headFont1));
			hcell.setBackgroundColor(BaseColor.PINK);
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			for(int i=0;i<allDatesString.size();i++)
			{
				hcell = new PdfPCell(new Phrase(DateConvertor.convertToDMY(allDatesString.get(i))+"", headFont1));
				hcell.setBackgroundColor(BaseColor.PINK);
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(hcell);
			}
			int index = 0;
			
			for (int i = 0; i < dispatchItemList.getItemList().size(); i++) {
				index++;
				PdfPCell cell;
				
				cell = new PdfPCell(new Phrase(String.valueOf(""+index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(3);
				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(String.valueOf(""+dispatchItemList.getItemList().get(i).getItemName()), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(3);
				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				table.addCell(cell);
				
				
				cell = new PdfPCell(new Phrase(String.valueOf(""+dispatchItemList.getItemList().get(i).getItemCode()), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(3);
				//cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				table.addCell(cell);
				
				for(int j=0;j<allDatesString.size();j++)
				{
					float qty=0;
					for(int k=0;k<dispatchItemList.getDate1().size();k++)
					{
						if(dispatchItemList.getItemList().get(i).getItemId()==dispatchItemList.getDate1().get(k).getItemId())
						{
							qty=dispatchItemList.getDate1().get(k).getRemQty();
							hcell = new PdfPCell(new Phrase(""+qty, headFont));
							//hcell.setBackgroundColor(BaseColor.PINK);
							hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
							table.addCell(hcell);
							break;
						}
					}
					break;
				}
				for(int j=0;j<allDatesString.size();j++)
				{
					float qty=0;
					for(int k=0;k<dispatchItemList.getDate2().size();k++)
					{
						if(dispatchItemList.getItemList().get(i).getItemId()==dispatchItemList.getDate2().get(k).getItemId())
						{
							qty=dispatchItemList.getDate2().get(k).getRemQty();
							hcell = new PdfPCell(new Phrase(""+qty, headFont));
							//hcell.setBackgroundColor(BaseColor.PINK);
							hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
							table.addCell(hcell);
							break;
						}
					}
					break;
				}
				for(int j=0;j<allDatesString.size();j++)
				{
					float qty=0;
					for(int k=0;k<dispatchItemList.getDate3().size();k++)
					{
						if(dispatchItemList.getItemList().get(i).getItemId()==dispatchItemList.getDate3().get(k).getItemId())
						{
							qty=dispatchItemList.getDate3().get(k).getRemQty();
							hcell = new PdfPCell(new Phrase(""+qty, headFont));
							//hcell.setBackgroundColor(BaseColor.PINK);
							hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
							table.addCell(hcell);
							break;
						}
					}
					break;
				}
				for(int j=0;j<allDatesString.size();j++)
				{
					float qty=0;
					for(int k=0;k<dispatchItemList.getDate4().size();k++)
					{
						if(dispatchItemList.getItemList().get(i).getItemId()==dispatchItemList.getDate4().get(k).getItemId())
						{
							qty=dispatchItemList.getDate4().get(k).getRemQty();
							hcell = new PdfPCell(new Phrase(""+qty, headFont));
							//hcell.setBackgroundColor(BaseColor.PINK);
							hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
							table.addCell(hcell);
							break;
						}
					}
					break;
				}
			}
		/*	for (int i = 0; i < subCatAList.size(); i++) {
                int flag=0;
				for (int j = 0; j < itemsList.size(); j++) {
					
					if (subCatAList.get(i).getSubCatId() == itemsList.get(j).getItemGrp2()) {
						
						for (int k = 0; k < moneyOutList.size(); k++) {
                            if(moneyOutList.get(k).getItemId()==itemsList.get(j).getId()) {
                            
							index++;
							if(flag==0)
                            {
							PdfPCell cell;
							
							cell = new PdfPCell(new Phrase(String.valueOf(""), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setPadding(3);
							cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
							table.addCell(cell);

							cell = new PdfPCell(new Phrase(subCatAList.get(i).getSubCatName(), headFont1));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(2);
							cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
							cell.setPadding(3);
							table.addCell(cell);
                        	
							cell = new PdfPCell(new Phrase("", headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(2);
							cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
							cell.setPadding(3);
							table.addCell(cell);
							
							cell = new PdfPCell(new Phrase("", headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(2);
							cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
							cell.setPadding(3);
							table.addCell(cell);
							
							
							flag=1;
                            }
							// FooterTable footerEvent = new FooterTable(table);
							// writer.setPageEvent(footerEvent);
							PdfPCell cell;
							cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setPadding(4);
							table.addCell(cell);

							cell = new PdfPCell(new Phrase(moneyOutList.get(k).getItemName(), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(2);
							cell.setPadding(4);
							table.addCell(cell);
							cell = new PdfPCell(
									new Phrase(String.valueOf(moneyOutList.get(k).getCurOpeQty()), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setPaddingRight(2);
							cell.setPadding(4);
							table.addCell(cell);

							int currentQty = (int) (Math.round(moneyOutList.get(k).getCurOpeQty()));
							int production1Qty = (moneyOutList.get(k).getPlanQty() - currentQty);
							int production2Qty = (moneyOutList.get(k).getOrderQty() - production1Qty);
							if (pdfPlanHeader.getIsPlanned() == 0) {
								cell = new PdfPCell(
										new Phrase(String.valueOf(moneyOutList.get(k).getOrderQty()), headFont));
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setPaddingRight(2);
								cell.setPadding(4);
								table.addCell(cell);
							} else if (pdfPlanHeader.getIsPlanned() == 1) {
								cell = new PdfPCell(
										new Phrase(String.valueOf(moneyOutList.get(k).getPlanQty()), headFont));
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setPaddingRight(2);
								cell.setPadding(4);
								table.addCell(cell);
								cell = new PdfPCell(new Phrase(String.valueOf(production1Qty), headFont));
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setPaddingRight(2);
								cell.setPadding(4);
								table.addCell(cell);
							} else if (pdfPlanHeader.getIsPlanned() == 2) {
								cell = new PdfPCell(
										new Phrase(String.valueOf(moneyOutList.get(k).getPlanQty()), headFont));
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setPaddingRight(2);
								cell.setPadding(4);
								table.addCell(cell);
								cell = new PdfPCell(new Phrase(String.valueOf(production1Qty), headFont));
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setPaddingRight(2);
								cell.setPadding(4);
								table.addCell(cell);
								cell = new PdfPCell(
										new Phrase(String.valueOf(moneyOutList.get(k).getOrderQty()), headFont));
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setPaddingRight(2);
								cell.setPadding(4);
								table.addCell(cell);
								cell = new PdfPCell(new Phrase(String.valueOf(production2Qty), headFont));
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setPaddingRight(2);
								cell.setPadding(4);
								table.addCell(cell);
							}
							cell = new PdfPCell(new Phrase("", headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setPaddingRight(2);
							cell.setPadding(4);
							table.addCell(cell);
						}
                         
						}
					}
				}
			}*/
			
			document.open();
			Paragraph company = new Paragraph("Shiv Shambhu\n", f);
			company.setAlignment(Element.ALIGN_CENTER);
			document.add(company);
			
			

			document.add(new Paragraph("Dispatch Sheet\n"));
			Paragraph heading = new Paragraph(" ");
			document.add(heading);
			document.add(table);
			
			int totalPages = writer.getPageNumber();

			System.out.println("Page no " + totalPages);

			document.close();
			if (file != null) {

				String mimeType = URLConnection.guessContentTypeFromName(file.getName());

				if (mimeType == null) {

					mimeType = "application/pdf";

				}

				response.setContentType(mimeType);

				response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

				response.setContentLength((int) file.length());

				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

				try {
					FileCopyUtils.copy(inputStream, response.getOutputStream());
				} catch (IOException e) {
					System.out.println("Excep in Opening a Pdf File");
					e.printStackTrace();
				}
			}

		} catch (DocumentException ex) {

			System.out.println("Pdf Generation Error: BOm Prod  View Prod" + ex.getMessage());

			ex.printStackTrace();

		}

	}
}
