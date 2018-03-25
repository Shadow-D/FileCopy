
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FileCopy {
	private static ArrayList<File> files=new ArrayList<File>();// 存放遍历的文件
     
	// 递归获取文件夹下所有文件
	public static ArrayList<File> getFiles(File file) {
    	File[] fileArray=file.listFiles();
    	for (File f:fileArray) {
    		if (f.isDirectory()) {
    			getFiles(f);
    		} else if (f.isFile()) {
    			files.add(f);
    		}
    	}
    	return files;
    }
	
	// 将源文件复制到目标位置
	public static void fileCopy(File source,File target) throws IOException {
		BufferedInputStream bi=new BufferedInputStream(new FileInputStream(source));
		BufferedOutputStream bo=new BufferedOutputStream(new FileOutputStream(target));
		int i;
		byte[] len=new byte[1024];
		while ((i=bi.read(len))!=-1) {
			bo.write(len,0,i);
		}
		bo.flush();
		bi.close();
		bo.close();
	}
	
	// 将源文件夹复制到目标位置
	public static void dirCopy(File sourse,File target) throws IOException {
		ArrayList<File> lists=getFiles(sourse);// 获取所有文件
		String tarPath=target.getPath();
		String souPath=sourse.getPath();
		// 依次复制到目标位置
		for (File f:lists) {
			String path=f.getParent().replace(souPath, tarPath);// 目标路径
			String name=f.getName();
			File file1=new File(path);
			File file2=new File(path+"/"+name);
			file1.mkdirs();
			file2.createNewFile();
			fileCopy(f, file2);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			File f1=new File("/home/shadz/eclipse");
			File f2=new File("/home/shadz/newdir");
			dirCopy(f1,f2);
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
