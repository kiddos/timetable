import java.io.*;
import org.apache.poi.xwpf.usermodel.*;

public class TimeTable {
	private String fileOutputPath = "D:\\Computer Science\\Java\\Output File\\Time Table 213.docx";
	private XWPFDocument document = new XWPFDocument();
	private XWPFTable table = document.createTable();
	private XWPFTableRow[] rows;
	private String timeBlockInfo = "";
	
	public TimeTable(){
		try{
			String filePath = "lib\\text\\year2-sem2.txt";
			
			System.out.println("Reading Content From File ---");
			String content = readTextFromFile(filePath);
			System.out.println("File closed");
			
			System.out.println("Setting up the document ---");
			setUpDocx();
			createDocxFile(fileOutputPath, content);
			System.out.println("Finish creating the Time Table!!");
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private String readTextFromFile(String filePath) throws IOException{
		String content = "";
		
		
		File inputFile = new File(filePath);
		FileInputStream input = new FileInputStream(inputFile);
		InputStreamReader r = new InputStreamReader(input, "BIG5");
		BufferedReader reader = new BufferedReader(r);
		
		while(reader.ready()){
			content += reader.readLine() + "\n";
		}
		
		System.out.println("Content:");
		System.out.println(content);
		
		reader.close();
		return content;
	}
	
	private void setUpDocx(){
		// block colume
		XWPFTableRow r1 = table.getRow(0);
		r1.getCell(0).setText("Block");
		// time colume
		r1.createCell().setText("Time");
		
		for(int i = 0 ; i < 5 ; i ++){
			switch(i){
			case 0:
				r1.createCell().setText("Monday");
				break;
			case 1:
				r1.createCell().setText("Tuesday");
				break;
			case 2:
				r1.createCell().setText("Wednesday");
				break;
			case 3:
				r1.createCell().setText("Thursday");
				break;
			case 4:
				r1.createCell().setText("Friday");
				break;
			}
		}
		
		timeBlockInfo = "";
		try{
			FileReader r = new FileReader(new File("lib\\text\\time blocks.txt"));
			BufferedReader reader = new BufferedReader(r);
			while(reader.ready())
				timeBlockInfo += reader.readLine() + "\n";
			
			reader.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		String[] infos = timeBlockInfo.split("\n");
		// setting up the rows length according to the number of the blocks
		rows = new XWPFTableRow[infos.length];
		for(int i = 0 ; i < infos.length ; i ++){
			String info = infos[i];
			String block = info.split("\t")[0];
			String time = info.split("\t")[1];
			
			rows[i] = table.createRow();
			rows[i].getCell(0).setText(block);
			rows[i].getCell(1).setText(time);
		}
	}
	
	private int getRowIndex(String b){
		String[] infos = timeBlockInfo.split("\n");
		int rowIndex = 0;
		for(int i = 0 ; i < infos.length ; i ++){
			String info = infos[i];
			String block = info.split("\t")[0];
			
			rowIndex ++;
			
			if(block.equals(b)){
				break;
			}
		}
		return rowIndex - 1;
	}
	
	private void createDocxFile(String filePath, String content) throws IOException {
		/*
		 * content read from the file contains a unknown character at the beginning
		 * attempting to remove:
		 */
		content = content.substring(1);
		
		String[] courses = content.split("\n");
		for(String course : courses){
			// retrieve all information about the course
			String[] courseDescription = course.split("\t");
			String courseCode = courseDescription[0];
			String courseName = courseDescription[1];
			// String courseNecessity = courseDescription[2]; -----> don't need these
			// String courseCredits = courseDescription[3];   -----> don't need these
			String courseAttendDays = courseDescription[4];
			String courseAttendLocation = courseDescription[5];
			String courseProfessor = courseDescription[6];
			
			courseAttendLocation = courseAttendLocation.split(",")[0];
			
			if(courseAttendDays.contains("一")){
				String[] courseAttendBlock = courseAttendDays.split("一");
				
				for(int i = 1 ; i < courseAttendBlock.length ; i ++){
					int rowIndex = getRowIndex(courseAttendBlock[i]);
					rows[rowIndex].getCell(2).setText(courseName + "\n" + courseCode + "\n" + courseAttendLocation + "\n(" + courseProfessor + ")");
				}
			}else if(courseAttendDays.contains("二")){
				String[] courseAttendBlock = courseAttendDays.split("二");
				
				for(int i = 1 ; i < courseAttendBlock.length ; i ++){
					int rowIndex = getRowIndex(courseAttendBlock[i]);
					rows[rowIndex].getCell(3).setText(courseName + "\n" + courseCode + "\n" + courseAttendLocation + "\n(" + courseProfessor + ")");
				}
			}else if(courseAttendDays.contains("三")){
				String[] courseAttendBlock = courseAttendDays.split("三");
				
				for(int i = 1 ; i < courseAttendBlock.length ; i ++){
					int rowIndex = getRowIndex(courseAttendBlock[i]);
					rows[rowIndex].getCell(4).setText(courseName + "\n" + courseCode + "\n" + courseAttendLocation + "\n(" + courseProfessor + ")");
				}
			}else if(courseAttendDays.contains("四")){
				String[] courseAttendBlock = courseAttendDays.split("四");
				
				for(int i = 1 ; i < courseAttendBlock.length ; i ++){
					int rowIndex = getRowIndex(courseAttendBlock[i]);
					rows[rowIndex].getCell(5).setText(courseName + "\n" + courseCode + "\n" + courseAttendLocation + "\n(" + courseProfessor + ")");
				}
			}else if(courseAttendDays.contains("五")){
				String[] courseAttendBlock = courseAttendDays.split("五");
				
				for(int i = 1 ; i < courseAttendBlock.length ; i ++){
					int rowIndex = getRowIndex(courseAttendBlock[i]);
					rows[rowIndex].getCell(6).setText(courseName + "\n" + courseCode + "\n" + courseAttendLocation + "\n(" + courseProfessor + ")");
				}
			}
		}
		
		FileOutputStream output = new FileOutputStream(filePath);
		document.write(output);
		output.close();
	}
	
	public static void main(String[] args){
		new TimeTable();
	}
}
