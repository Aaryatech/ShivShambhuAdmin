package com.ats.ssgs.common;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

public class VpsImageUpload {

	// public static final String WEIGHING_URL =
	// "/home/lenovo/Desktop/weighing_image";

	public static final String VEH_FOLDER = "/opt/tomcat-latest/webapps/uploads/shiv/Veh/";

	public static final String WEIGHT_FOLDER = "/opt/tomcat-latest/webapps/uploads/shiv/Weight";

	public void saveUploadedFiles(MultipartFile file, int imageType, String imageName) throws IOException {
		try {

			Path path = Paths.get(WEIGHT_FOLDER + imageName);
			if (imageType == 1) {
				path = Paths.get(WEIGHT_FOLDER + imageName);
			} else if (imageType == 2) {
				path = Paths.get(VEH_FOLDER + imageName);
			}

			byte[] bytes = file.getBytes();

			Files.write(path, bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
