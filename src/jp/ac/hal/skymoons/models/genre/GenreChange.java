package jp.ac.hal.skymoons.models.genre;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.genre.GenreBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.genre.GenreDao;

public class GenreChange extends AbstractModel {
    @Override
    public String doService(HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	GenreDao dao = new GenreDao();

	if (request.getParameter("change") != null) {

	    GenreBean genreBean = dao.getGenreDetail(Integer.valueOf(request
		    .getParameter("genreId")));

	    request.setAttribute("detail", genreBean);

	    List<GenreBean> bigGenreList = dao.getBigGenreList();
	    request.setAttribute("bigGenreList", bigGenreList);
	    dao.close();

	} else if (request.getParameter("genreChange") != null) {

	    String genreName = request.getParameter("genreName");
	    int genreId = Integer.valueOf(request.getParameter("genreId"));
	    int bigGenreId = Integer
		    .valueOf(request.getParameter("bigGenreId"));

	    GenreBean updateRecord = new GenreBean();
	    updateRecord.setBigGenreId(bigGenreId);
	    updateRecord.setGenreName(genreName);
	    updateRecord.setGenreId(genreId);

	    int changeFlag = 0;

	    try {
		changeFlag = dao.genreChange(updateRecord);
	    } catch (Exception e) {
		e.printStackTrace();
	    }

	    if ( changeFlag == 1) {
		List<GenreBean> genreList = dao.getGenreList();
		request.setAttribute("genreList", genreList);
		dao.commit();
		dao.close();

		return "/genre/genreList.jsp";
	    } else {
		GenreBean genreBean = dao.getGenreDetail(Integer
			.valueOf(request.getParameter("genreId")));

		request.setAttribute("detail", genreBean);

		List<GenreBean> bigGenreList = dao.getBigGenreList();
		request.setAttribute("bigGenreList", bigGenreList);
		dao.close();
	    }

	}

	return "/genre/genreChange.jsp";
    }
}
