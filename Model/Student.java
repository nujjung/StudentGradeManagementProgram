package Model;

public class Student {
	private String no;
	private String name;
	private String gread;
	private String ban;
	private String gender;
	private String kor;
	private String eng;
	private String mat;
	private String sci;
	private String soc;
	private String mus;
	private String total;
	private String avg;
	private String date;
	private String imagepath;
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGread() {
		return gread;
	}
	public void setGread(String gread) {
		this.gread = gread;
	}
	public String getBan() {
		return ban;
	}
	public void setBan(String ban) {
		this.ban = ban;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getKor() {
		return kor;
	}
	public void setKor(String kor) {
		this.kor = kor;
	}
	public String getEng() {
		return eng;
	}
	public void setEng(String eng) {
		this.eng = eng;
	}
	public String getMat() {
		return mat;
	}
	public void setMat(String mat) {
		this.mat = mat;
	}
	public String getSci() {
		return sci;
	}
	public void setSci(String sci) {
		this.sci = sci;
	}
	public String getSoc() {
		return soc;
	}
	public void setSoc(String soc) {
		this.soc = soc;
	}
	public String getMus() {
		return mus;
	}
	public void setMus(String mus) {
		this.mus = mus;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getAvg() {
		return avg;
	}
	public void setAvg(String avg) {
		this.avg = avg;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getImagepath() {
		return imagepath;
	}
	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}
	public Student(String no, String name, String gread, String ban, String gender, String kor, String eng, String mat,
			String sci, String soc, String mus, String total, String avg, String date, String imagepath) {
		super();
		this.no = no;
		this.name = name;
		this.gread = gread;
		this.ban = ban;
		this.gender = gender;
		this.kor = kor;
		this.eng = eng;
		this.mat = mat;
		this.sci = sci;
		this.soc = soc;
		this.mus = mus;
		this.total = total;
		this.avg = avg;
		this.date = date;
		this.imagepath = imagepath;
	}
	

}
