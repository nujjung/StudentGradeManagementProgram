package Controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Model.Student;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController implements Initializable{
	public Stage mainStage;
//******************************첫번째화면*************************
	@FXML private TextField t1TextName;
	@FXML private ComboBox<String> t1CmbGrade;
	@FXML private ComboBox<String> t1CmbBan;
	@FXML private RadioButton t1RdoMale;
	@FXML private RadioButton t1RdoFemale;
	@FXML private TextField t1TextMath;
	@FXML private TextField t1TextSoc;
	@FXML private TextField t1TextEnglish;
	@FXML private TextField t1TextSci;
	@FXML private TextField t1Textmus;
	@FXML private TextField t1TextKorean;
	@FXML private TextField t1Textsum;
	@FXML private TextField t1Textavg;
	@FXML private Button t1Btnsum;
	@FXML private Button t1Btnavg;
	@FXML private Button t1Btnclear;
	@FXML private Button t1BtnReg;
	@FXML private Button t1BtnEdit;
	@FXML private Button t1BtnDel;
	@FXML private Button t1BtnExit;
	@FXML private TextField t1TextSearch;
	@FXML private Button t1BtnSearch;
	
	@FXML private Button t1BtnBarChart;
	@FXML private DatePicker t1DatePicker;
	@FXML private ToggleGroup group;
	@FXML private TableView<Student> t1TableView;
	@FXML private TextField t1TextNo;
	
	//삽입
	@FXML private Button t1BtnImage;
	@FXML private ImageView t1ImageView;
	
	
	
	ObservableList<Student> t1ListData = FXCollections.observableArrayList();
	ObservableList<String> t1ListGrade = FXCollections.observableArrayList();
	ObservableList<String> t1ListBan = FXCollections.observableArrayList();
	ArrayList<Student> dbArrayList;
	private Student selectStudent;
	private int selectStudentIndex;
	private boolean editFlag;
	//삽입
	private File selectFile = null;
	//파일을 저장하기 위해서 폴더 생성
	private File imageDirectory = new File("C:/images");
	private String fileName = "";
	
	//***************************두번째 화면*************************
	@FXML private Button tab2BtnSecond;
	
	
	
	//***************************세번째 화면*************************
	@FXML private Button tab3BtnThird;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//0. DB  접속 요청 신청
		//Connection con = DBUtility.getConnection();
		//1.테이블 뷰 기본 세팅 진행 한다. 데이터 베이스에서 전체 자료를 가져 온다.
		setTab1TableView();
		//1.2콤보박스(학년, 반) 셋팅을 한다
		setComboBoxGradeBan();
		//1.3텍스트 필드 입력값 포멧설정
		setTextFieldInputFormat();
		//2.버튼과 입력 필드 초기 설정
		setBtnTextFieldInitiate("처음");
		//3.총점 버튼을 눌렀을때 처리 해야하는 함수
		t1Btnsum.setOnAction(e->{handleT1BtnTotalAction();});
		//4.평균 버튼을 눌렀을때 처리하는 함수
		t1Btnavg.setOnAction(e->{handleT1BtnAvgAction();});
		//5.초기화 버튼을 눌렀을때 처리하는 함수 
		t1Btnclear.setOnAction(e->{handleT1BtnClearAction();});
		//6.등록 버튼을 눌렀을때 처리하는 함수 
		t1BtnReg.setOnAction(e->{handleT1BtnRegisterAction();});
		//7. 종료 버튼을 눌렀을때 처리하는 함수 
		t1BtnExit.setOnAction(e->{Platform.exit();});
		//8. 테이블 뷰를 클릭 했을때 처리하는 함수(한번 클릭두번클릭 파이차트)
		t1TableView.setOnMouseClicked((e)-> {handleT1TableViewAction(e);});
		//9. 수정 버튼활성화 되었을때 처리하는 함수
		t1BtnEdit.setOnMousePressed(e->{handleT1BtnEditAction();});
		//10. 삭제 버튼활성화 되었을때 처리하는 함수
		t1BtnDel.setOnMousePressed(e->{handleT1BtnDelAction();});
		//11. 검색 버튼활성화 되었을때 처리하는 함수
		t1BtnSearch.setOnMousePressed(e->{handleT1BtnSearchAction();});
		//12.바차트 버튼을 클릭했을때 처리하는 함수(바차트를 보여줘야 한다)
		t1BtnBarChart.setOnAction(e->{handleT1BtnBarChartAction();});
		//13.이미지 등록 버튼을 클릭 했을때 처리하는 함수
		t1BtnImage.setOnAction(e->{handleT1BtnImageAction();});
	}

		//1.테이블 뷰 기본 세팅 진행 한다.데이터 베이스에서 전체 자료를 가져 온다.
		private void setTab1TableView() {
			TableColumn tcNo = t1TableView.getColumns().get(0);
			tcNo.setCellValueFactory(new PropertyValueFactory<>("no"));
			tcNo.setStyle("-fx-alignment: CENTER;");
			TableColumn tcName = t1TableView.getColumns().get(1);
			tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
			tcName.setStyle("-fx-alignment: CENTER;");
			TableColumn tcGread = t1TableView.getColumns().get(2);
			tcGread.setCellValueFactory(new PropertyValueFactory<>("gread"));
			tcGread.setStyle("-fx-alignment: CENTER;");
			TableColumn tcBan = t1TableView.getColumns().get(3);
			tcBan.setCellValueFactory(new PropertyValueFactory<>("ban"));
			tcBan.setStyle("-fx-alignment: CENTER;");
			TableColumn tcGender = t1TableView.getColumns().get(4);
			tcGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
			tcGender.setStyle("-fx-alignment: CENTER;");
			TableColumn tcKor = t1TableView.getColumns().get(5);
			tcKor.setCellValueFactory(new PropertyValueFactory<>("kor"));
			tcKor.setStyle("-fx-alignment: CENTER;");
			TableColumn tcEng = t1TableView.getColumns().get(6);
			tcEng.setCellValueFactory(new PropertyValueFactory<>("eng"));
			tcEng.setStyle("-fx-alignment: CENTER;");
			TableColumn tcMat = t1TableView.getColumns().get(7);
			tcMat.setCellValueFactory(new PropertyValueFactory<>("mat"));
			tcMat.setStyle("-fx-alignment: CENTER;");
			TableColumn tcSci = t1TableView.getColumns().get(8);
			tcSci.setCellValueFactory(new PropertyValueFactory<>("sci"));
			tcSci.setStyle("-fx-alignment: CENTER;");
			TableColumn tcSoc = t1TableView.getColumns().get(9);
			tcSoc.setCellValueFactory(new PropertyValueFactory<>("soc"));
			tcSoc.setStyle("-fx-alignment: CENTER;");
			TableColumn tcMus = t1TableView.getColumns().get(10);
			tcMus.setCellValueFactory(new PropertyValueFactory<>("mus"));
			tcMus.setStyle("-fx-alignment: CENTER;");
			TableColumn tcTotal = t1TableView.getColumns().get(11);
			tcTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
			tcTotal.setStyle("-fx-alignment: CENTER;");
			TableColumn tcAvg = t1TableView.getColumns().get(12);
			tcAvg.setCellValueFactory(new PropertyValueFactory<>("avg"));
			tcAvg.setStyle("-fx-alignment: CENTER;");
			TableColumn tcDate = t1TableView.getColumns().get(13);
			tcDate.setCellValueFactory(new PropertyValueFactory<>("date"));
			tcDate.setStyle("-fx-alignment: CENTER;");
			TableColumn tcimagepath = t1TableView.getColumns().get(14);
			tcimagepath.setCellValueFactory(new PropertyValueFactory<>("imagepath"));
			tcimagepath.setStyle("-fx-alignment: CENTER;");
			
			
			//ObservableList<Student> t1ListData = FXCollections.observableArrayList();
			//t1ListData.add(new Student("이", "2학년", "8반", "100", "100", "100", "100", "100", "100", "600", "100"));
			t1TableView.setItems(t1ListData);
			
			
			dbArrayList = StudentDAO.getStudentTotaldata();
			for(Student student : dbArrayList) {
				t1ListData.add(student);
				System.out.println(student);
			}
			
		
	}
		//1.2콤보박스(학년, 반) 셋팅을 한다 
		private void setComboBoxGradeBan() {
			t1ListGrade.addAll("1년","2년","3년","4년","5년","6년");
			t1ListBan.addAll("1반","2반","3반","4반","5반","6반");
			t1CmbGrade.setItems(t1ListGrade);
			t1CmbBan.setItems(t1ListBan);
			
		}
		//1.3.텍스트 필드 입력값 포멧설정
		private void setTextFieldInputFormat() {
			inputDecimalFormat(t1TextMath);
			inputDecimalFormat(t1TextSoc);
			inputDecimalFormat(t1TextEnglish);
			inputDecimalFormat(t1TextSci);
			inputDecimalFormat(t1Textmus);
			inputDecimalFormat(t1TextKorean);
			inputDecimalFormatTwoDigut(t1TextNo);
		}

		//2.버튼과 입력 필드 초기 설정	
		private void setBtnTextFieldInitiate(String message) {
			t1Textsum.setEditable(false);
			t1Textavg.setEditable(false);
			switch(message) {
			case "처음": 
				t1Btnsum.setDisable(false);
				t1Btnavg.setDisable(true);
				t1BtnEdit.setDisable(true);
				t1BtnDel.setDisable(true);
				t1BtnReg.setDisable(true);break;
			case "수정 및 삭제": 
				t1Btnsum.setDisable(false);
				t1Btnavg.setDisable(true);
				t1BtnEdit.setDisable(true);
				t1BtnDel.setDisable(false);
				t1BtnReg.setDisable(true);break;
			case "총점" :
				t1Btnsum.setDisable(true);
				t1Btnavg.setDisable(false);
				t1BtnEdit.setDisable(true);
				t1BtnDel.setDisable(true);
				t1BtnReg.setDisable(true);break;
			case "평균" :
				t1Btnsum.setDisable(true);
				t1Btnavg.setDisable(true);
				if(editFlag) {					
					t1BtnReg.setDisable(true);
					t1BtnEdit.setDisable(false);
					editFlag = false;
				}else {
					t1BtnReg.setDisable(false);
				}
				break;
			
			}
		}
		//3.총점 버튼을 눌렀을때 처리 해야하는 함수
		private void handleT1BtnTotalAction() {
			int kor = Integer.parseInt(t1TextKorean.getText());
			int math = Integer.parseInt(t1TextMath.getText());
			int eng = Integer.parseInt(t1TextEnglish.getText());
			int sci = Integer.parseInt(t1TextSci.getText());
			int soc = Integer.parseInt(t1TextSoc.getText());
			int mus = Integer.parseInt(t1Textmus.getText());
			String message = "점수 입력 :";
			if(kor>100) {
				message += ("kor = " + kor);
			}
			if(math>100) {
				message += ("math = " + math);
			}
			if(eng>100) {
				message += ("eng = " + eng);
			}
			if(sci>100) {
				message += ("sci = " + sci);
			}
			if(soc>100) {
				message += ("soc = " + soc);
			}
			if(mus>100) {
				message += ("mus = " + mus);
			}
			if(!message.equals("점수 입력 :")) {
				message +=" 점수를 잘 입력해 주세요";
				callAlert(message);
				return;
			}
			
			t1Textsum.setText(String.valueOf(kor+math+eng+sci+soc+mus));
			setBtnTextFieldInitiate("총점");
			
		}
		//4.평균 버튼을 눌렀을때 처리하는 함수
		private void handleT1BtnAvgAction() {
			double total = Double.parseDouble(t1Textsum.getText())/6;
			t1Textavg.setText(String.format("%.2f", total));
			setBtnTextFieldInitiate("평균");
			
		}
		//5.초기화 버튼을 눌렀을때 처리하는 함수 
		private void handleT1BtnClearAction() {
			t1DatePicker.setValue(null);
			t1TextName.clear();
			t1CmbGrade.getSelectionModel().clearSelection();
			t1CmbBan.getSelectionModel().clearSelection();
			group.selectToggle(null);
			t1TextNo.clear();
//			t1TextGread.clear();
//			t1TextBan.clear();
			t1TextKorean.clear();
			t1TextEnglish.clear();
			t1TextMath.clear();
			t1TextSci.clear();
			t1TextSoc.clear();
			t1Textmus.clear();
			t1Textsum.clear();
			t1Textavg.clear();
			t1TableView.getSelectionModel().clearSelection();
			t1ImageView.setImage(new Image(getClass().getResource("../images/bagicimage.jpg").toString()));
			selectFile = null;
			fileName=null;
			setBtnTextFieldInitiate("처음");
		}
		
		//6.등록 버튼을 눌렀을때 처리하는 함수 
		private void handleT1BtnRegisterAction() {
			
			imageSave();
			if(selectFile == null) {
				callAlert("이미지선택 에러 : 이미지를 선택해 주세요.");
				return;
			}
			
			
			
			
			String studentNo = "";
			String number = "0";
			//10<1 ->01   
			if(t1TextNo.getText().trim().length() ==1) {
				number+=t1TextNo.getText().trim();
			}else {
				number=t1TextNo.getText().trim();
			}
			studentNo = t1CmbGrade.getSelectionModel().getSelectedItem().substring(0, 1)+t1CmbBan.getSelectionModel().getSelectedItem().substring(0, 1)+number;
				Student student = new Student(studentNo,
						t1TextName.getText(),
						t1CmbGrade.getSelectionModel().getSelectedItem(),
						t1CmbBan.getSelectionModel().getSelectedItem(),
						group.getSelectedToggle().getUserData().toString(),
						t1TextKorean.getText(), t1TextMath.getText(), 
						t1TextEnglish.getText(), t1TextSci.getText(), 
						t1TextSoc.getText(), t1Textmus.getText(), 
						t1Textsum.getText(), t1Textavg.getText(),
						t1DatePicker.getValue().toString(),
						fileName);//절대 경로 file:/D://java_project
			
				t1ListData.add(student);
			int count = StudentDAO.insertStudentData(student);
			if(count !=0) {
				callAlert("성공 : 성공");
			}
				
			handleT1BtnClearAction();
		}
		//8. 테이블 뷰를 클릭 했을때 처리하는 함수(한번 클릭 두번클릭 파이차트)
		private void handleT1TableViewAction(MouseEvent e) {
			selectStudent = t1TableView.getSelectionModel().getSelectedItem();
			selectStudentIndex = t1TableView.getSelectionModel().getSelectedIndex();
			if(e.getClickCount()==1) {
				
				t1DatePicker.setValue(LocalDate.parse(selectStudent.getDate()));
				t1TextName.setText(selectStudent.getName());
				t1CmbGrade.getSelectionModel().select(selectStudent.getGread());
				t1CmbBan.getSelectionModel().select(selectStudent.getBan());
				t1TextNo.setText(selectStudent.getNo().substring(2));
				if(selectStudent.getGender().equals("남자")) {
					t1RdoMale.setSelected(true);
				}else {
					t1RdoFemale.setSelected(true);
				}
//				t1TextGread.clear();
//				t1TextBan.clear();
				t1TextKorean.setText(selectStudent.getKor());
				t1TextEnglish.setText(selectStudent.getEng());
				t1TextMath.setText(selectStudent.getMat());
				t1TextSci.setText(selectStudent.getSci());
				t1TextSoc.setText(selectStudent.getSoc());
				t1Textmus.setText(selectStudent.getMus());
				t1Textsum.clear();
				t1Textavg.clear();
				Image image = new Image("file:///"+imageDirectory.getAbsolutePath()+"/"+selectStudent.getImagepath());
				t1ImageView.setImage(image);
				//중요기능  수정 기능을 활성화 시킨다  -> 평균을 눌렀을때
				editFlag = true;
			setBtnTextFieldInitiate("수정 및 삭제");
			}else if(e.getClickCount()==2) {
				try {
					Stage pieStage = new Stage(StageStyle.UTILITY);
					pieStage.initModality(Modality.WINDOW_MODAL);
					pieStage.initOwner(mainStage);
					pieStage.setTitle(selectStudent.getName()+"파이차트");
					FXMLLoader loder = new FXMLLoader(getClass().getResource("../View/piechart.fxml"));
					Parent root = loder.load();
					//*********아이디를 찾아서 와야 한다. = @FXML private Button c1BtnExit**************
					PieChart c2PieChart = (PieChart)root.lookup("#c2PieChart");
					Button c2BtnExit = (Button)root.lookup("#c2BtnExit");
					
					//*********이벤트 등록 및 초기화 핸들러정의 ******************//
					ObservableList pieList = FXCollections.observableArrayList();
					pieList.add(new PieChart.Data("국어", Integer.parseInt(selectStudent.getKor())));
					pieList.add(new PieChart.Data("영어", Integer.parseInt(selectStudent.getEng())));
					pieList.add(new PieChart.Data("수학", Integer.parseInt(selectStudent.getMat())));
					pieList.add(new PieChart.Data("과학", Integer.parseInt(selectStudent.getSci())));
					pieList.add(new PieChart.Data("사회", Integer.parseInt(selectStudent.getSoc())));
					pieList.add(new PieChart.Data("음악", Integer.parseInt(selectStudent.getMus())));
					
					c2PieChart.setData(pieList);
					
					
					
					c2BtnExit.setOnAction(e1->{pieStage.close();});
					
					
					
					Scene scene = new Scene(root);
					pieStage.setScene(scene);
					pieStage.show();
					} catch (Exception e1) {
						callAlert("파이차트창 오류 : 파이 차트 창 오류가 발생되었습니다.");
					}
			}else {return;}
			
		}
		//점검 요망*****************************************
		//9. 수정 버튼활성화 되었을때 처리하는 함수
		private void handleT1BtnEditAction() {
//			callAlert("이미지 경로 :"  + t1ImageView.getImage().getClass().getName());
			if(!fileName.equals(null)) {
				imageDelete();
				imageSave();
				
				
			}
			
			
			String studentNo = "";
			String number = "0";
			//10<1 ->01   
			if(t1TextNo.getText().trim().length() ==1) {
				number+=t1TextNo.getText().trim();
			}else {
				number=t1TextNo.getText().trim();
			}
			studentNo = t1CmbGrade.getSelectionModel().getSelectedItem().substring(0, 1)+t1CmbBan.getSelectionModel().getSelectedItem().substring(0, 1)+number;
			
			
			Student student = new 	Student(studentNo,t1TextName.getText(),
					t1CmbGrade.getSelectionModel().getSelectedItem().toString(),
					t1CmbBan.getSelectionModel().getSelectedItem().toString(),
					group.getSelectedToggle().getUserData().toString(),
					t1TextKorean.getText(), t1TextMath.getText(), 
					t1TextEnglish.getText(), t1TextSci.getText(), 
					t1TextSoc.getText(), t1Textmus.getText(), 
					t1Textsum.getText(), t1Textavg.getText(),
					t1DatePicker.getValue().toString(),
					fileName);  //절대경로
			
			int count = StudentDAO.updateStudentDate(student);
			if(count !=0) {
				
				t1ListData.remove(selectStudentIndex);
				t1ListData.add(selectStudentIndex,student);
				int arrayIndex = dbArrayList.indexOf(selectStudent);
				dbArrayList.set(arrayIndex, student);
				handleT1BtnClearAction();
				callAlert("수정완료 : "+selectStudent.getName()+"님이 수정되었습니다.");
			}else {return;}
			
		}
		//10. 삭제 버튼활성화 되었을때 처리하는 함수
		private void handleT1BtnDelAction() {
			int count = StudentDAO.deleteStudentData(selectStudent.getNo());
			if(count!=0) {
				t1ListData.remove(selectStudentIndex); 
				System.out.println("삭제전"+dbArrayList.size());
				dbArrayList.remove(selectStudent);
				System.out.println("삭제후"+dbArrayList.size());
				editFlag = false;
				
				imageDelete();
				
				//초기화 눌렀을때 함수 콜
				setBtnTextFieldInitiate("처음");
				callAlert("삭제 완료 : 삭제가 완료 되었습니다. ");
				
			}else {return;}
		}
		

		//11. 검색 버튼활성화 되었을때 처리하는 함수
		private void handleT1BtnSearchAction() {
			for(Student  stu   :t1ListData) {
				if(t1TextSearch.getText().trim().equals(stu.getName())) {
					t1TableView.getSelectionModel().select(stu);
				}
			}//end for each
		}
		//12.바차트 버튼을 클릭했을때 처리하는 함수(바차트를 보여줘야 한다)
		private void handleT1BtnBarChartAction() {
			try {
			Stage bcStage = new Stage(StageStyle.UTILITY);
			bcStage.initModality(Modality.WINDOW_MODAL);
			bcStage.initOwner(mainStage);
			bcStage.setTitle("바차트");
			FXMLLoader loder = new FXMLLoader(getClass().getResource("../View/barchart.fxml"));
			Parent root = loder.load();
			//*********아이디를 찾아서 와야 한다. = @FXML private Button c1BtnExit**************
			BarChart c1BarChart = (BarChart)root.lookup("#c1BarChart");
			Button c1BtnExit = (Button)root.lookup("#c1BtnExit");
			
			//*********이벤트 등록 및 초기화 핸들러정의 ******************//
			XYChart.Series seriesKor = new XYChart.Series<>();
			seriesKor.setName("국어");
			ObservableList korList = FXCollections.observableArrayList();
			for(int i=0;i<t1ListData.size();i++) {
				korList.add(new XYChart.Data<>(t1ListData.get(i).getName(),Integer.parseInt(t1ListData.get(i).getKor())));
			}
			seriesKor.setData(korList);
			c1BarChart.getData().add(seriesKor);
			
			
			XYChart.Series seriesEng = new XYChart.Series<>();
			seriesEng.setName("영어");
			ObservableList engList = FXCollections.observableArrayList();
			for(int i=0;i<t1ListData.size();i++) {
				engList.add(new XYChart.Data<>(t1ListData.get(i).getName(),Integer.parseInt(t1ListData.get(i).getEng())));
			}
			seriesEng.setData(engList);
			c1BarChart.getData().add(seriesEng);
			
			c1BtnExit.setOnAction(e->{bcStage.close();});
			
			
			
			Scene scene = new Scene(root);
			bcStage.setScene(scene);
			bcStage.show();
			} catch (Exception e) {
				callAlert("바차트창 오류 : 바 차트 창 오류가 발생되었습니다.");
			}
		}
		//13.이미지 등록 버튼을 클릭 했을때 처리하는 함수(파일창이 열어져야 한다.)
		private void handleT1BtnImageAction() {
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().add(new ExtensionFilter("Image File","*.png","*.jpg","*.gif"));
			selectFile = fileChooser.showOpenDialog(mainStage);
			String localURL = null;
			if(selectFile !=null) {
				try {
					localURL = selectFile.toURI().toURL().toString();
				} catch (MalformedURLException e) {		}
			}
			t1ImageView.setImage(new Image(localURL,false));//바탕화면 배경색으로 가져오는게 아니다.?진짜 본체로 가져온다
			fileName = selectFile.getName();//선택된 파일명을 준다  fileName은 반드시 이미지 파일을 선택했을때 값을 유지한다.
			//callAlert("선택된 이미지 : " + fileName);
		}
		
	//기타 알림창 "오류정보 : 값을 제대로 입력해 주세요"  중간에 꼭 :을 적을것
	public static void callAlert(String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("경고");
		alert.setHeaderText(contentText.substring(0, contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
		alert.showAndWait();
	}
	
	//기타 입력값 필드 포멧 설정 기능 : 숫자 세자리만 받는 기능을 셋팅함
	private void inputDecimalFormat(TextField textField) {
		// 숫자만 입력(정수만 입력받음), 실수입력받고싶으면 new DecimalFormat("###.#");
		DecimalFormat format = new DecimalFormat("###");
		// 점수 입력시 길이 제한 이벤트 처리
		textField.setTextFormatter(new TextFormatter<>(event -> {  
			//입력받은 내용이 없으면 이벤트를 리턴함.  
		if (event.getControlNewText().isEmpty()) { return event; }
		//구문을 분석할 시작 위치를 지정함. 
		    	ParsePosition parsePosition = new ParsePosition(0);
			//입력받은 내용과 분석위치를 지정한지점부터 format 내용과 일치한지 분석함.
		    	Object object = format.parse(event.getControlNewText(), parsePosition); 
		//리턴값이 null 이거나, 입력한길이와 구문분석위치값이 적어버리면(다 분석하지못했음을 뜻함) 거나, 입력한길이가 4이면(3자리를 넘었음을 뜻함.) 이면 null 리턴함. 
		if (object == null || parsePosition.getIndex()<event.getControlNewText().length()
		      || event.getControlNewText().length() == 4) {
		     return null;    
		}else {
		     return event;    
		}   
		}));

	}
	

	private void inputDecimalFormatTwoDigut(TextField t1TextNo2) {
		DecimalFormat format = new DecimalFormat("##");
		// 점수 입력시 길이 제한 이벤트 처리
		t1TextNo2.setTextFormatter(new TextFormatter<>(event -> {  
			//입력받은 내용이 없으면 이벤트를 리턴함.  
		if (event.getControlNewText().isEmpty()) { return event; }
		//구문을 분석할 시작 위치를 지정함. 
		    	ParsePosition parsePosition = new ParsePosition(0);
			//입력받은 내용과 분석위치를 지정한지점부터 format 내용과 일치한지 분석함.
		    	Object object = format.parse(event.getControlNewText(), parsePosition); 
		//리턴값이 null 이거나, 입력한길이와 구문분석위치값이 적어버리면(다 분석하지못했음을 뜻함) 거나, 입력한길이가 4이면(3자리를 넘었음을 뜻함.) 이면 null 리턴함. 
		if (object == null || parsePosition.getIndex()<event.getControlNewText().length()
		      || event.getControlNewText().length() == 3) {
		     return null;    
		}else {
		     return event;    
		}   
		}));
		
	}
	
	//기타 이미지 C:/images/student123412341234_선택된 파일명으로 저장한다.
	private void imageSave() {
		if(!imageDirectory.exists()) {
			imageDirectory.mkdir();   //디렉토리가 생성이 안되어 있으면 폴더를 만든다.
		}
		FileInputStream fis=null;
		BufferedInputStream bis =null;
		FileOutputStream fos=null;
		BufferedOutputStream bos = null;
		//선택된 이미지를 c:/images/  선택된 이미지 이름명으로 저장한다
		try {
			fis = new FileInputStream(selectFile);
			bis = new BufferedInputStream(fis);
			
			//FileChooser에서 선택된 파일명이 C:/kkk/ppp/jjj/name.jpg
			//selecFile.getName()  -> name.jpg 만 가져온다
			//새로운 파일 명을 규정 하는데 -> student+12345678912_name.jpg
			//imageDirectory.getAbsolutePath()+"\\"+fileName 
			//-> C:/images/student+12345678912_name.jpg 이름으로 저장한다
			//C:/kkk/ppp/jjj/name.jpg 읽어서   C:/images/student+12345678912_name.jpg여기로 저장한다
			fileName ="student"+ System.currentTimeMillis()+"_"+selectFile.getName();
			fos = new FileOutputStream(imageDirectory.getAbsolutePath()+"\\"+fileName);
			bos = new BufferedOutputStream(fos);
			int data=-1;
			while((data = bis.read()) != -1) {
				bos.write(data);
				bos.flush();
			}
			
		} catch (Exception e) {
			callAlert("이미지 저장 에러 : c/images/저장파일 에러 점검 바람");
		}finally {
			try {
			if(fis != null) {fis.close();}
			if(bis != null) {bis.close();}
			if(fos != null) {fos.close();}
			if(bos != null) {bos.close();}
			} catch (IOException e) {}
		}
			
	}
	//이미지삭제
	private void imageDelete() {
		boolean delFlag = false;
		File imageFile = new File(imageDirectory.getAbsolutePath()+"\\"+selectStudent.getImagepath());
		if(imageFile.exists() && imageFile.isFile()) {
			delFlag = imageFile.delete();
			if(delFlag == false) {callAlert("이미지 제거 실패:제거실패 점검바람");}
		}
	}
	
}
