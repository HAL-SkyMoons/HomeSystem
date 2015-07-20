package jp.ac.hal.skymoons.models.genre;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.genre.GenreBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.genre.GenreDao;

public class BigGenreList extends AbstractModel{
    @Override
    public String doService(HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	GenreDao dao = new GenreDao();
	List<GenreBean> bigGenreList = dao.getBigGenreList();
	request.setAttribute("bigGenreList", bigGenreList);
	dao.close();

	return "/genre/bigGenreList.jsp";
    }
}
