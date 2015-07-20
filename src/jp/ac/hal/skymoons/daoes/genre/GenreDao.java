package jp.ac.hal.skymoons.daoes.genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import jp.ac.hal.skymoons.beans.genre.GenreBean;
import jp.ac.hal.skymoons.controllers.ConnectionGet;

public class GenreDao {

    /** Connection */
    private Connection con;

    /**
     * コンストラクタ
     *
     * @throws NamingException
     * @throws SQLException
     */
    public GenreDao() {
	ConnectionGet get = new ConnectionGet();
	con = get.getCon();
    }

    /**
     * コンストラクタ
     *
     * @param con
     */
    public GenreDao(Connection con) {
	this.con = con;
    }

    /**
     * 接続を閉じる
     *
     * @throws SQLException
     */
    public void close() throws SQLException {
	con.close();
    }

    /**
     * コミット
     *
     * @throws SQLException
     */
    public void commit() throws SQLException {
	con.commit();
    }

    /**
     * ロールバック
     *
     * @throws SQLException
     */
    public void rollback() throws SQLException {
	con.rollback();
    }

    public List<GenreBean> getBigGenreList() throws SQLException {

	PreparedStatement select = con
		.prepareStatement("select * from big_genre;");

	ResultSet result = select.executeQuery();

	ArrayList<GenreBean> table = new ArrayList<GenreBean>();
	while (result.next()) {

	    GenreBean record = new GenreBean();

	    record.setBigGenreId(result.getInt("big_genre_id"));
	    record.setBigGenreName(result.getString("big_genre_name"));

	    table.add(record);
	}
	return table;
    }

    public List<GenreBean> getGenreList() throws SQLException {

	PreparedStatement select = con
		.prepareStatement("select g.genre_id,g.genre_name,g.big_genre_id,b.big_genre_name from genre g,big_genre b where g.big_genre_id = b.big_genre_id;");

	ResultSet result = select.executeQuery();

	ArrayList<GenreBean> table = new ArrayList<GenreBean>();
	while (result.next()) {

	    GenreBean record = new GenreBean();

	    record.setGenreId(result.getInt("g.genre_id"));
	    record.setGenreName(result.getString("g.genre_name"));

	    record.setBigGenreId(result.getInt("g.big_genre_id"));
	    record.setBigGenreName(result.getString("b.big_genre_name"));

	    table.add(record);
	}
	return table;
    }

    public int genreRegister(GenreBean newRecord) throws SQLException {

	PreparedStatement insert = con
		.prepareStatement("insert into genre(genre_name,big_genre_id) values (?,?);");
	insert.setString(1, newRecord.getGenreName());
	insert.setInt(2, newRecord.getBigGenreId());

	return insert.executeUpdate();

    }

    public int bigGenreRegister(GenreBean newRecord) throws SQLException {

	PreparedStatement insert = con
		.prepareStatement("insert into big_genre(big_genre_name) values (?);");
	insert.setString(1, newRecord.getBigGenreName());

	return insert.executeUpdate();

    }


}
