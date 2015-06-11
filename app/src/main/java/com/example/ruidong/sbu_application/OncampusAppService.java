package com.example.ruidong.sbu_application;

/**
 * Created by Ruidong on 5/27/2015.
 */
import java.util.ArrayList;
import java.util.Collection;

public interface OncampusAppService {

    public String firstTextInfo(POI POI_element);


    public String secondTextInfo(POI POI_element);


    public ArrayList<POI> getTargetPOI(String str);


    public boolean checkMap(String str);

}
