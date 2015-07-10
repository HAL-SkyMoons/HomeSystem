package jp.ac.hal.skymoons.beans.ranking;

/**
 * BatchTable Bean
 * @author YAMAZAKI GEN
 * @since 2015/07/10
 * @version 1.0
 */
public class BatchBean {

	private int batch_id = -1;
	private String batch_name = null;
	private String batch_comment = null;
	
	public int getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(int batch_id) {
		this.batch_id = batch_id;
	}
	public String getBatch_name() {
		return batch_name;
	}
	public void setBatch_name(String batch_name) {
		this.batch_name = batch_name;
	}
	public String getBatch_comment() {
		return batch_comment;
	}
	public void setBatch_comment(String batch_comment) {
		this.batch_comment = batch_comment;
	}	
}