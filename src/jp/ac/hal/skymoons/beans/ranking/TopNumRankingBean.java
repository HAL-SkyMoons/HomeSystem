package jp.ac.hal.skymoons.beans.ranking;

/**
 * 獲得数ランキングbeans。
 *
 * @author YAMAZAKI GEN
 * @since 2015/06/09
 * @version 1.0
 */
public class TopNumRankingBean {
    private long value = 0;
    private String name = null;
    private String id = null;
    private String department = null;
    private int level = 0;

    public long getValue() {
	return value;
    }

    public void setValue(long value) {
	this.value = value;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getDepartment() {
	return department;
    }

    public void setDepartment(String department) {
	this.department = department;
    }

    public int getLevel() {
	return level;
    }

    public void setLevel(int level) {
	this.level = level;
    }
}
