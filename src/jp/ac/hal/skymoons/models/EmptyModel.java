package jp.ac.hal.skymoons.models;

import java.sql.SQLException;
import jp.ac.hal.skymoons.core.Core;

public class EmptyModel extends Core {

	@Override
	public void executeSQL() throws SQLException{
		return;
	}
}
