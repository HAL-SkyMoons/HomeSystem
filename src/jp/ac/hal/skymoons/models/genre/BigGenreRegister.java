package jp.ac.hal.skymoons.models.genre;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.genre.GenreBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.genre.GenreDao;

public class BigGenreRegister extends AbstractModel{
    @Override
    public String doService(HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	if(request.getParameter("bigGenreRegister")!=null){
		GenreDao dao = new GenreDao();
		GenreBean genreBean = new GenreBean();
		genreBean.setBigGenreName(request.getParameter("bigGenreName"));
		if(dao.bigGenreRegister(genreBean)==1){
		    dao.commit();
		}else{
		    dao.rollback();
		}
		dao.close();
	}

	return "/genre/bigGenreRegister.jsp";
    }
}
