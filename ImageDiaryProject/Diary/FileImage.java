 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileImage {
	private File src; //���� ����
	private File copy; //���� �� ����
	private File folder= new File("image");
	private String date;
	public FileImage(String date){
		this.date=date;
	}
	public FileImage(String date,File src) {
		this.src = src;
		this.date=date;
		String path = src.getPath();
		int idx = path.lastIndexOf(".");
		String ext = path.substring(idx);
		String name = date+ext;
		copy = new File(folder+"\\"+name);
		if (!folder.exists()) {
			// ��� ����
			folder.mkdirs();
		}
		if(!copy.exists()) {
			FileInputStream fis = null;
			FileOutputStream fos = null;
			try {
				fis = new FileInputStream(src);
				fos = new FileOutputStream(copy);
				byte[] b = new byte[1024*1024];
				int data = -1 ; 
				while( (data = fis.read(b)) != -1 ) {
					fos.write(b,0,data);
				}		
				
			} catch(IOException e) {
				e.printStackTrace();
			} finally {
				try {
					fis.close();
				} catch(Exception e) {}
				
				try {
					fos.close();
				} catch(Exception e) {}
			}
		}
	}
	
	public File findImage(){
		
		File[] fileList = folder.listFiles();
		String fileName;
		
		if(!(fileList==null)){
			for (File file : fileList) {
				fileName = file.getName();
				int location = fileName.lastIndexOf(".");
				fileName=fileName.substring(0,10);
				if (file.isFile()) {//������ �����ϸ�
					if (fileName.equals(date)) {
						return file;
					}
				}
			}
		}
		return null;
	}
	
	public File getfolder() {
 
		return copy;
	}
}
