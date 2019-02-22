package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class RootController implements Initializable{
	public Stage primaryStage;
	@FXML private TextField textId;
	@FXML private PasswordField textPassword;
	@FXML private Button btnLogin;
	@FXML private Button btnClose;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		textPassword.setOnKeyPressed(event -> {
	          if (event.getCode().equals(KeyCode.ENTER)) {
	             handlerBtnLoginAction();
	          }
	       });
		btnLogin.setOnAction(e->{handlerBtnLoginAction();});
		
		btnClose.setOnAction(e->{handlerBtnCloseAction();});
		
	}

		private void handlerBtnLoginAction() {
		if(textId.getText().equals("root") && textPassword.getText().equals("123456")) {
			callAlert("로그인 성공 : "+textId.getText() +"님 환영합니다. ");
		}else {
			callAlert("로그인 실패 : 아이디나 비밀 번호를 확인해 주세요");		
			textId.clear(); textPassword.clear();
			return;
		}
		
		try {
			Stage mainStage = new Stage();
			FXMLLoader loder = new FXMLLoader(getClass().getResource("../View/main.fxml"));
			Parent root = loder.load();
			MainController mainController = loder.getController();
			mainController.mainStage = mainStage;
			
			Scene scene = new Scene(root);
			mainStage.setScene(scene);
			primaryStage.close();
			mainStage.show();
			
			callAlert("화면 전환 성공 : 메인 화면으로 전환 되었습니다.");
			
			
		} catch (Exception e) {
			callAlert("화면 전환 오류 : 화면 전환에 문제가 있습니다.검토 바랍니다.");
		}

	}
		
	private void handlerBtnCloseAction() {
			Platform.exit();
	}
		
		//기타 알림창 "오류정보 : 값을 제대로 입력해 주세요"  중간에 꼭 :을 적을것
	private void callAlert(String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("경고");
		alert.setHeaderText(contentText.substring(0, contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
		alert.showAndWait();
	}

}
