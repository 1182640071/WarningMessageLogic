package guodu.net.warning.entity;

public class Message {
    private String content; //内容
    private String mobile; //手机号
    private String limt; //阀值
    private String oper; //运算符
    private String warning_type; //报警方式 1微信 2短信
    private String yw_type; //业务类型 1直接发 2 短信阀值比较
    
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getLimt() {
		return limt;
	}
	public void setLimt(String limt) {
		this.limt = limt;
	}
	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
	public String getWarning_type() {
		return warning_type;
	}
	public void setWarning_type(String warning_type) {
		this.warning_type = warning_type;
	}
	public String getYw_type() {
		return yw_type;
	}
	public void setYw_type(String yw_type) {
		this.yw_type = yw_type;
	}
    
    
}
