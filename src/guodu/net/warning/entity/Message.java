package guodu.net.warning.entity;

public class Message {
    private String content; //����
    private String mobile; //�ֻ���
    private String limt; //��ֵ
    private String oper; //�����
    private String warning_type; //������ʽ 1΢�� 2����
    private String yw_type; //ҵ������ 1ֱ�ӷ� 2 ���ŷ�ֵ�Ƚ�
    
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
