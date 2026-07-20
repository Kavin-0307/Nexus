package com.nexus.services;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.tika.Tika;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileStorageService {
	private static final long MAX_FILE_SIZE = 20 * 1024 * 1024; //20 MB for now but can be increased later
	private final Path directoryPath;
	private final Path documentPath;
	@Value("${storage.base-path}")
	private String basePath;
	public FileStorageService() {
		 directoryPath=Paths.get(basePath);
		 documentPath=Paths.get("D:/NEXUS_FILES/documents");
		 createDirectory();
	}
	private Path createDirectory() {
	
	try {
	Files.createDirectories(directoryPath);
	Files.createDirectories(documentPath);

	Files.createDirectories(Paths.get("D:/NEXUS_FILES/temp"));
	}catch (IOException i) {
		System.out.println("Unable to create the directory");
	}
	return directoryPath;
	}
	public Path saveUploadedFile(MultipartFile file) throws IOException {
		if (file.getSize() > MAX_FILE_SIZE) {
		    throw new IllegalArgumentException(
		            "File size exceeds 20 MB limit");
		}
			Tika tika=new Tika();
			String detectedType="";
			 try (var inputStream = file.getInputStream()) {
			        detectedType = tika.detect(inputStream);
			    }
			
			if("application/pdf".equals(detectedType))
			{
				System.out.println("Valid file");
		
			}
			else
			{
				
				throw new IllegalArgumentException("Invalid file type");
			}
			//Creating a new file name
			 String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
		     String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
		     if (originalFilename.contains("..")) {
		    	    throw new IllegalArgumentException("Invalid filename");
		    	}
		     String newFilename =timestamp +"_"+UUID.randomUUID().toString()+ "_" +originalFilename;
		     Path targetLocation = documentPath.resolve(newFilename);
			Files.copy(file.getInputStream(),targetLocation ,StandardCopyOption.REPLACE_EXISTING);
			return targetLocation;
			
	}
	public Path saveFileFromPath(String existingPath) throws IOException {
		Path path=Paths.get(existingPath);
		File file=path.toFile();
		if(!Files.exists(path)||!file.isFile()) {
			throw new IllegalArgumentException("No file found at locations");
			
		}
		Tika tika=new Tika();
		String detectedType="";
		
		detectedType = tika.detect(file);
		
		
		if("application/pdf".equals(detectedType))
		{
			System.out.println("Valid file");
	
		}
		else
		{
			System.out.println("Invalid");
			return null;
		}

		 String uniqueFilename=UUID.randomUUID().toString()+"_"+file.getName();
		 Path targetPath=documentPath.resolve(uniqueFilename);
		 
		
		 Files.copy(path,targetPath, StandardCopyOption.REPLACE_EXISTING);
		 return targetPath;
	}
	public Path loadFile(String path) {
		Path p1=Paths.get(path);
		if(!Files.exists(p1)) {
			throw new IllegalArgumentException("File not found");
		}
		return p1;
		
	}
	public void deleteFile(String filePath) {
		
			Path path=Paths.get(filePath);
			try{
				Files.deleteIfExists(path);
			}
			catch(IOException i) {
				throw new IllegalArgumentException("File not found at " + filePath);
			}
		
	}
	public Path moveToProcessed(Path filePath) throws IOException {
	    Path processedDir = Paths.get(directoryPath.toString(), "processed");
	    Files.createDirectories(processedDir);

	    Path target = processedDir.resolve(filePath.getFileName());
	    Files.move(filePath, target, StandardCopyOption.REPLACE_EXISTING);

	    return target;
	}
	public String extractText(Path filePath) throws IOException {
	    try (PDDocument document =Loader.loadPDF(filePath.toFile())) {
	        PDFTextStripper stripper = new PDFTextStripper();
	        return stripper.getText(document);
	    }
	}
	public boolean fileExists(String filePath) {
	    return Files.exists(Paths.get(filePath));
	}
		

	public long getFileSize(String filePath) throws IOException{
		return Files.size(Paths.get(filePath));
	}
	public String getFileName(String filePath) {
		return Paths.get(filePath).getFileName().toString();
	}
	
} 