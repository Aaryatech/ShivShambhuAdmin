package com.ats.ssgs.common;

public class Constants {
	
	//Global
	public static final String url = "http://132.148.151.41:8080/shivShambhuWebApi/";
	public static final String REPORT_SAVE = "/opt/tomcat-latest/webapps/uploads/shiv/Report.pdf";
	public static final String ReportURL = "http://132.148.151.41:8080/shivAdmin/";
	public static final String VEH_IMG_URL = "http://132.148.151.41:8080/shiv/Veh/";
	public static final String WEIGHT_READING_URL = "http://132.148.151.41:8080/shiv/Weight/";
	
	private static final int VEH_IMG_TYPE=2;//fix for local and gloabal
	private static final int WEIGHT_IMG_TYPE=1;//fix for local and gloabal

	//Local

	//public static final String url = "http://localhost:8098/";
	//public static final String REPORT_SAVE = "/opt/tomcat-latest/webapps/uploads/shiv/Report.pdf";
	//public static final String ReportURL = "http://localhost:8081/ssgs/";
	//public static final String VEH_IMG_URL = "http://132.148.151.41:8080/shiv/Veh/";
	//public static final String WEIGHT_READING_URL = "http://132.148.151.41:8080/shiv/Weight/";
	
//End
	
	public static final int CAT_FILE_TYPE = 0;
	public static final String RTO_DOC_URL = "http://tomcat.aaryatechindia.in:2908/rtodocupload/";
	//public static final String ReportURL = "http://localhost:8081/ssgs/";
	
	//public static final Object VEH_IMG_URL = null;
	public static int mainAct;
	public static int subAct;
}
