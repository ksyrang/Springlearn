package com.care.spring_db.ajax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface IAjaxDAO {

	void insert(AjaxDTO DTO);

	ArrayList<AjaxDTO> titleAll();

	ArrayList<AjaxDTO> search(String searchData);

	ArrayList<AjaxDTO> searchArtist(String searchData);

	ArrayList<AjaxDTO> searchPrice(String searchData);
	
	ArrayList<AjaxDTO> searchFilter(HashMap<String, String> map);

}
