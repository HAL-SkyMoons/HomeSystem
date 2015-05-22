package jp.ac.hal.skymoons.data;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//Data�̃X�[�p�[�N���X
public abstract class MysqlData implements Serializable{
	private static final long serialVersionUID = 1L;
	abstract public ArrayList<String> getValues();
	abstract public void setRequestValues(ArrayList<String> values);
	abstract public void setResultValues(ResultSet result) throws SQLException;
}
