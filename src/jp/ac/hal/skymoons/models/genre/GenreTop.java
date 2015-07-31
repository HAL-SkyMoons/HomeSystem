package jp.ac.hal.skymoons.models.genre;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.controllers.AbstractModel;

public class GenreTop extends AbstractModel{
    @Override
    public String doService(HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	return "/genre/genreTop.jsp";
    }
}
