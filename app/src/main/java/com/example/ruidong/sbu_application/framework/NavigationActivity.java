package com.example.ruidong.sbu_application.framework;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Stack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ruidong.sbu_application.R;
import com.example.ruidong.sbu_application.chatPlatform.service.ChatMainFragment;
import com.example.ruidong.sbu_application.courseManager.service.CourseManagerPOI;
import com.example.ruidong.sbu_application.courseManager.service.CourseScheduleFragment;
import com.example.ruidong.sbu_application.courseManager.service.CourseSumView;
import com.example.ruidong.sbu_application.courseManager.service.LoginFragment;
import com.example.ruidong.sbu_application.dailylife.service.SbuClusterResultFragment;
import com.example.ruidong.sbu_application.dailylife.service.SbuDailyLifePOI;
import com.example.ruidong.sbu_application.dailylife.service.SbuDailySumView;
import com.example.ruidong.sbu_application.event.service.EventPOI;
import com.example.ruidong.sbu_application.event.service.EventSumView;
import com.example.ruidong.sbu_application.framework.common.tool.FragmentIdPair;
import com.example.ruidong.sbu_application.framework.common.tool.Tool;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.example.ruidong.sbu_application.framework.common.tool.ApiConnector;
import com.example.ruidong.sbu_application.framework.common.tool.GMapV2Direction;
import com.example.ruidong.sbu_application.framework.common.tool.GetAliaseConnector;
import com.example.ruidong.sbu_application.framework.common.tool.GetDirectionsAsyncTask;

import com.example.ruidong.sbu_application.courseManager.service.CourseHistoryFragment;
import com.example.ruidong.sbu_application.courseManager.service.CourseResultListFragment;
import com.example.ruidong.sbu_application.dailylife.service.SbuDailyLifeResultFragment;
import com.example.ruidong.sbu_application.dailylife.service.SbuDailyLifeCategoryFragment;
import com.example.ruidong.sbu_application.event.service.EventCalendarFragment;
import com.example.ruidong.sbu_application.event.service.EventResultListFragment;
import com.example.ruidong.sbu_application.framework.common.tool.slidebutton.ViewPagerAdapter;
import com.example.ruidong.sbu_application.framework.common.tool.slidebutton.ViewPagerChangeListener;
import com.example.ruidong.sbu_application.framework.common.tool.slidingmenuAdapter.NavDrawerItem;
import com.example.ruidong.sbu_application.framework.common.tool.slidingmenuAdapter.NavDrawerListAdapter;
import com.example.ruidong.sbu_application.courseManager.service.CourseManagementService;
import com.example.ruidong.sbu_application.event.service.EventService;
import com.example.ruidong.sbu_application.dailylife.service.SbuDailyLifeService;

public class NavigationActivity extends FragmentActivity implements  ClusterManager.OnClusterInfoWindowClickListener<POI>, ClusterManager.OnClusterItemInfoWindowClickListener<POI> {



    private static final LatLng STARTINGPOINT = new LatLng(40.915823, -73.121903);
    private static final LatLng DESTINATION = new LatLng(40.915962, -73.126264);
    public static GoogleMap map;
    private SupportMapFragment fragment;
    private LatLngBounds latlngBounds;
    private Button navigationButton;
    private Button searchButton;
    private Polyline newPolyline;
    private boolean isTravelingToDestination = false;
    private int width, height;
    private LatLng usercurrentlocation;
    private LocationManager locationManager;
    private LocationListener locationListener = new locationlistener();
    public static AutoCompleteTextView editText ;
    private static String location ;
    private static double destinationLat = 40.915962;
    private static double destinationLng = -73.126264;

    private OncampusAppService courseService= new CourseManagementService();
    private OncampusAppService dailyService=new SbuDailyLifeService();
    private OncampusAppService eventService = new EventService();
    public  static ArrayList<POI> myMarkerList;
    private static HashMap<Marker, POI> mMarkersHashMap1 = new HashMap<Marker, POI>();
    public  static HashMap<POI, Marker> mMarkersHashMap2 = new HashMap<POI, Marker>();
    public static boolean showResultListFlag = false;
    public static BottomButton bottomFrag;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    public static Fragment MenuFragment;
    public static SbuDailyLifeResultFragment dailyResultFragment;
//    private  ShowListButton showListFragment;
    private ViewPager pager;
    private ViewPagerAdapter mAdapter;
    private  LinearLayout layoutPager;
    public static HashMap<POI, Integer> bottomHash1 = new HashMap<POI, Integer>();
    public static HashMap<Integer, POI> bottomHash2 = new HashMap<Integer, POI>();
    private ClusterManager<POI> mClusterManager;
    private Cluster<POI> clickedCluster;
    private POI clickedClusterItem;
    private POIRenderer poiRenderer;
    private boolean clusterFlag = false;
    private boolean clusterItemFlag = false;
    private SbuClusterResultFragment dailyClusterResultFragment;
    private CourseResultListFragment courseClusterResultFragment;
    private EventResultListFragment eventClusterResultListFragment;
    private SbuDailyLifeCategoryFragment dailyLifeCategoryFragment;
    private Button showListButton;
    private boolean InfoWindowClickFlg;
    private LatLng currentMapCenter;
    private CourseHistoryFragment courseHistoryFragment;
    private EventCalendarFragment eventCalendarFragment;
    private int serviceNumber = -1;
    private LoginFragment loginFragment;
    private SharedPreferences.Editor editor;
    private ArrayList<String> hintListRead1;
    private ArrayList<String> hintListRead2;
    private SharedPreferences historyPreference;
    private ArrayList<String> historyList = new ArrayList<String>();
    private LinearLayout showClusterListButton;
    private TextView showClusterListButtonText1;
    private TextView showClusterListButtonText2;
    private ArrayList<String> finalHintList = new ArrayList<>();
    private Fragment sumViewFragment;
    public CourseScheduleFragment courseScheduleFragment;
    public static Stack<FragmentIdPair> backButtonStack = new Stack<>();
    private ArrayList<POI> tempMyMarkerList = new ArrayList<>();
    private int tempServiceNumber;
    private ArrayList<OncampusAppService> serviceList = new ArrayList<>();
    private Tool tool = new Tool();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_navigation);
        getSreenDimanstions();
        InitializeMenu(savedInstanceState);
        layoutPager=(LinearLayout)findViewById(R.id.linear1);
        pager = (ViewPager)findViewById(R.id.bottomButton);
        pager.setOnPageChangeListener(new ViewPagerChangeListener(this));
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(),null);
        layoutPager.setVisibility(View.INVISIBLE);

        //map Initialize
        fragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
        map = fragment.getMap();
        map.setMyLocationEnabled(true);

        // Create locationManager and get userlocatoin        
        locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria= new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        Location userlocation=locationManager.getLastKnownLocation(bestProvider);
        if(userlocation !=null ){
            locationListener.onLocationChanged(userlocation);
        }
        locationManager.requestLocationUpdates(bestProvider, 100, Criteria.ACCURACY_FINE,locationListener );



        //Main Interface View Initialize
        navigationButton = (Button) findViewById(R.id.bNavigation);
        searchButton = (Button) findViewById(R.id.bSearch);
        editText = (AutoCompleteTextView) findViewById(R.id.et_location);
        showClusterListButton=(LinearLayout) findViewById(R.id.showClusterListButton);
        showClusterListButtonText1=(TextView) findViewById(R.id.showClusterListButtontext1);
        showClusterListButtonText2=(TextView) findViewById(R.id.showClusterListButtontext2);

        showListButton=(Button)findViewById(R.id.showListButton);
        showListButton.setOnClickListener(new showListButtonOnClickListener(this) {

        });
        showListButton.setVisibility(View.INVISIBLE);

        // Initialize serviceList,so far there are 3 kinds of service, SbuDailyLife, Course Management, event.
        serviceList.add(dailyService);
        serviceList.add(courseService);
        serviceList.add(eventService);

        for(OncampusAppService service : serviceList ){
            service.clearMap();
            service.setMap();
        }


         //  new GetPOIItem().execute(new ApiConnector());
         //  new GetAliase().execute(new GetAliaseConnector());
        courseService.obtainData(new JSONArray());

        /*
        Initialize hintList for SbuDailyService. Actually, the data of hintList should receive from server, application
        write it down in a txt file in local device, then read it from that txt file next time.
        Right now, for convenience, we just initialize its data in main Activity.
        */
        ArrayList<String> hintList1 = new ArrayList<>();
        hintList1.add("food");
        hintList1.add("fastfood");
        ArrayList<String> hintList2 = new ArrayList<>();
        hintList2.add("coffee");
        tool.write(this, hintList1, "TestHintlist1.txt");
        tool.write(this, hintList2, "TestHintlist2.txt");
        hintListRead1 = tool.read(this,"TestHintlist1.txt");
        hintListRead2 = tool.read(this,"TestHintlist2.txt");

        for(String str : hintListRead2){
            hintListRead1.add(str);
        }

        HashSet<String> set = new HashSet<>();
        set.addAll(hintListRead1);
        ArrayList<String> historyList = loadHistroy();
        for(String str: historyList){
            set.add(str);
        }
        finalHintList.addAll(set);

        //Initialize editText;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.hint_list_adapter,R.id.text,finalHintList);
        editText.setThreshold(1);
        editText.setTextColor(Color.BLACK);
        editText.setAdapter(adapter);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        SharedPreferences preferences = getSharedPreferences("UserInfomation", Context.MODE_PRIVATE);
        editor = preferences.edit();
        searchButton.setOnClickListener(new searchOnclickListener() {

        });

        // Determine whether the BottomButton or showListButton should be hidden when user click map.
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng arg0) {

                layoutPager.setVisibility(View.INVISIBLE);



                if (showResultListFlag == true || serviceNumber == 1 || serviceNumber == 2) {
                    showListButton.setVisibility(View.VISIBLE);
                    showClusterListButton.setVisibility(View.INVISIBLE);
                }
            }
        });


        map.setInfoWindowAdapter(new CustomAdapterForCluster());



        // Show the navigation route.
        navigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LatLng DESTINATION = new LatLng(destinationLat, destinationLng);
                LatLng STARTINGPOINT = usercurrentlocation;

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(STARTINGPOINT, 14));
                if (!isTravelingToDestination) {

                    findDirections(STARTINGPOINT.latitude, STARTINGPOINT.longitude, DESTINATION.latitude, DESTINATION.longitude, GMapV2Direction.MODE_WALKING);
                }

            }
        });
    }

    //   Save user Input History
    public void saveHistory(String  input){
        historyPreference = getSharedPreferences("historyPreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = historyPreference.edit();
        if(historyList == null){
            historyList = new ArrayList<String>();
        }
        historyList.add(input);

        HashSet<String> set = new HashSet<>();
        set.addAll(historyList);
        editor.putStringSet("historyset",set);
        editor.commit();

    }
    //   Load user Input History
    public ArrayList<String> loadHistroy(){

        SharedPreferences preference = getSharedPreferences("historyPreference", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet)preference.getStringSet("historyset",null);
        ArrayList<String> inputList = new ArrayList<>();
        if(set != null)
        {
        inputList.addAll(set);
        }
        return inputList;
    }

    public SbuDailyLifeService getDailyLifeService(){
        return (SbuDailyLifeService)this.dailyService;
    }
    public CourseManagementService getCourseService(){
        return (CourseManagementService)this.courseService;
    }
    public EventService getEventService(){
        return  (EventService)this.eventService;
    }


    /*
     Based on what current service is, there will generate different view when user click showListButton.
     */
    private class showListButtonOnClickListener implements View.OnClickListener{
         private NavigationActivity activity;
         showListButtonOnClickListener(NavigationActivity activity){
             this.activity=activity;
         }
        @Override
        public void onClick(View v) {
            switch (serviceNumber) {
                case 0: {

                    // add dailyResultFragment, show it, then push dailyResultFragment into Fragment Stack.
                    FragmentTransaction resultTran = getSupportFragmentManager().beginTransaction()
                            .add(R.id.Category_result_Container, dailyResultFragment);
                    FragmentIdPair pair = new FragmentIdPair(dailyResultFragment,R.id.Category_result_Container,2);
                    backButtonStack.push(pair);
                    resultTran.commit();
                    break;
                }
                case 1: {
                    while (backButtonStack.peek().getFragmentLayoutID() ==R.id.CourseSchedule_container){
                        backButtonStack.pop();
                    }
                    FragmentTransaction resultTran = getSupportFragmentManager().beginTransaction()
                            .add(R.id.CourseSchedule_container, courseScheduleFragment);
                    FragmentIdPair pair = new FragmentIdPair(courseScheduleFragment,R.id.CourseSchedule_container,2);
                    backButtonStack.push(pair);
                    resultTran.commit();
                    break;
                }
                case 2: {
                    eventCalendarFragment = new EventCalendarFragment();
                    FragmentTransaction tran6 = getSupportFragmentManager().beginTransaction().add(R.id.CourseHistory_container, eventCalendarFragment);
                    FragmentIdPair pair = new FragmentIdPair(eventCalendarFragment,R.id.CourseHistory_container,2);
                    backButtonStack.push(pair);
                    tran6.commit();
                    break;
                }
                default: break;
            }
        }
    }

    public Button getShowListButton(){
        return  this.showListButton;
    }

    public void removeCategoryResultFragment(){
                    FragmentTransaction removeTran = getSupportFragmentManager().beginTransaction().remove(dailyResultFragment);
                    removeTran.commit();
                    backButtonStack.pop();
                    showListButton.setVisibility(View.VISIBLE);
    }


    public void removeClusterResult(){
        switch (serviceNumber) {
            case 0: {
                FragmentTransaction removeTran = getSupportFragmentManager().beginTransaction().remove(dailyClusterResultFragment);
                removeTran.commit();
                backButtonStack.pop();
                break;
            }
            case 1: {
                FragmentTransaction removeTran = getSupportFragmentManager().beginTransaction().remove(courseClusterResultFragment);
                removeTran.commit();
                backButtonStack.pop();
                break;
            }
            case 2: {
                FragmentTransaction removeTran = getSupportFragmentManager().beginTransaction().remove(eventClusterResultListFragment);
                removeTran.commit();
                backButtonStack.pop();
                break;
            }
            default:
                break;

        }
        showClusterListButton.setVisibility(View.VISIBLE);
    }

       // When we want to show a Summary view, we need create a Sumview object, set its corresponding POI, set massage, add it into
       // Fragment transaction, add it into Fragment stack.
    public void ShowDailySumViewFragment(POI currentPOI){
        SbuDailySumView dailySumView = new SbuDailySumView();
        dailySumView.setPOI(currentPOI);
        dailySumView.setMsg(dailyService.firstTextInfo(currentPOI), dailyService.secondTextInfo(currentPOI));
        FragmentTransaction dailySumViewStart = getSupportFragmentManager().beginTransaction().add(R.id.summaryView, dailySumView);
        FragmentIdPair pair = new FragmentIdPair(dailySumView,R.id.summaryView,3);
        backButtonStack.push(pair);
        dailySumViewStart.commit();
        sumViewFragment = dailySumView;
    }
    public void ShowCourseSumViewFragment(POI currentPOI){
        CourseSumView courseSumView = new CourseSumView();
        courseSumView.setPOI(currentPOI);
        courseSumView.setMsg(courseService.firstTextInfo(currentPOI), courseService.secondTextInfo(currentPOI));
        FragmentTransaction dailySumViewStart = getSupportFragmentManager().beginTransaction().add(R.id.summaryView, courseSumView);
        FragmentIdPair pair = new FragmentIdPair(courseSumView,R.id.summaryView,3);
        backButtonStack.push(pair);
        dailySumViewStart.commit();
        sumViewFragment = courseSumView;
    }
    public void ShowEventSumViewFragment(POI currentPOI){
        EventSumView eventSumView = new EventSumView();
        eventSumView.setPOI(currentPOI);
        eventSumView.setMsg(eventService.firstTextInfo(currentPOI), eventService.secondTextInfo(currentPOI));
        FragmentTransaction dailySumViewStart = getSupportFragmentManager().beginTransaction().add(R.id.summaryView, eventSumView);
        FragmentIdPair pair = new FragmentIdPair(eventSumView,R.id.summaryView,3);
        backButtonStack.push(pair);
        dailySumViewStart.commit();
        sumViewFragment = eventSumView;
    }

    public void responseOfResultListItemClick(POI currentPOI){
        switch (serviceNumber){
            case 0: {

                FragmentTransaction removeTran = getSupportFragmentManager().beginTransaction().remove(dailyResultFragment);
                removeTran.commit();
                ShowDailySumViewFragment(currentPOI);
                break;
            }
            case 1: {
                FragmentTransaction removeTran = getSupportFragmentManager().beginTransaction().remove(courseScheduleFragment);
                removeTran.commit();
                ShowCourseSumViewFragment(currentPOI);
                break;
            }
            default:
                break;
        }
    }
    public void responseOfClusterResultListItemClick(POI currentPOI){
        switch (serviceNumber){
            case 0: {
                FragmentTransaction removeTran = getSupportFragmentManager().beginTransaction().remove(dailyClusterResultFragment);
                removeTran.commit();
                ShowDailySumViewFragment(currentPOI);
                break;
            }
            case 1: {
                FragmentTransaction removeTran = getSupportFragmentManager().beginTransaction().remove(courseClusterResultFragment);
                removeTran.commit();
                ShowCourseSumViewFragment(currentPOI);
                break;
            }
            case 2: {
                FragmentTransaction removeTran = getSupportFragmentManager().beginTransaction().remove(eventClusterResultListFragment);
                removeTran.commit();
                ShowEventSumViewFragment(currentPOI);
            }
            default:
                break;
        }
    }
    public Fragment getSumViewFragment(){
        return sumViewFragment;
    }

    public void showReadButton(){
        this.showListButton.setVisibility(View.VISIBLE);
    }

    public CourseHistoryFragment getCourseHistoryFragment(){

        return this.courseHistoryFragment;
    }
    public void setCourseHistoryFragment(CourseHistoryFragment historyFragment){
        this.courseHistoryFragment = historyFragment;
    }

    public EventCalendarFragment getEventCalendarFragment(){
        return this.eventCalendarFragment;
    }

    //  based on the current POI, set the currentItem of ViewPager.
    public void setBottomButtonFragment(POI currentPOI){
        Integer position = bottomHash1.get(currentPOI);
        pager.setCurrentItem(position);
        bottomFrag=(BottomButton) mAdapter.getItem(position);
        if(layoutPager != null) {
            layoutPager.setVisibility(View.VISIBLE);
        }
    }

    public void setBottomFragWhenSlide(int position){
        bottomFrag=(BottomButton) mAdapter.getItem(position);
    }

    // Based on POI collection, set each BottomButton Fragment, two parameters needed for a ButtomButton are Text Inforamtion, which are defined in each service.
    // Then add these BottomButton into a list, initialize the ViewPager
    public  void setBottomButtonFragmentList(ArrayList<POI> myMarkerList,int position){
        ArrayList<BottomButton> fragmentlists = new ArrayList<BottomButton>();
        switch (position) {
            case 0:
            {
                for (POI poiElement : myMarkerList) {
                    BottomButton bottom = BottomButton.newInstance(dailyService.firstTextInfo(poiElement), dailyService.secondTextInfo(poiElement),0);
                    fragmentlists.add(bottom);
                    bottomHash1.put(poiElement, fragmentlists.indexOf(bottom));
                    bottomHash2.put(fragmentlists.indexOf(bottom), poiElement);
                    bottom.setPOI(poiElement);
                }
                break;
            }
            case 1:
            {
                for (POI poiElement : myMarkerList) {
                    BottomButton bottom = BottomButton.newInstance(courseService.firstTextInfo(poiElement), courseService.secondTextInfo(poiElement),1);
                    fragmentlists.add(bottom);
                    bottomHash1.put(poiElement, fragmentlists.indexOf(bottom));
                    bottomHash2.put(fragmentlists.indexOf(bottom), poiElement);
                    bottom.setPOI(poiElement);
                }
                break;
            }
            case 2:{
                for (POI poiElement : myMarkerList) {
                    BottomButton bottom = BottomButton.newInstance(eventService.firstTextInfo(poiElement), eventService.secondTextInfo(poiElement),2);
                    fragmentlists.add(bottom);
                    bottomHash1.put(poiElement, fragmentlists.indexOf(bottom));
                    bottomHash2.put(fragmentlists.indexOf(bottom), poiElement);
                    bottom.setPOI(poiElement);
                }
                break;
            }
            default: break;
        }
        mAdapter.bottomButtonList=fragmentlists;
        pager.setAdapter(mAdapter);
    }



    private class searchOnclickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            location = editText.getText().toString();

           // If this is a new input, save it as userInputHistory
            ArrayList<String> historyList1 = loadHistroy();
            if(!historyList1.contains(location)){
                saveHistory(location);
            }
            if(!finalHintList.contains(location)){
                finalHintList.add(location);
            }


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(),R.layout.hint_list_adapter,R.id.text,finalHintList);
            editText.setThreshold(1);
            editText.setTextColor(Color.BLACK);
            editText.setAdapter(adapter);

            // Clear map current state when there is a new search
                map.clear();
               layoutPager.setVisibility(View.INVISIBLE);
               if(!backButtonStack.isEmpty()){
                   FragmentIdPair top = backButtonStack.peek();
                   FragmentTransaction tran = getSupportFragmentManager().beginTransaction().remove(top.getFragment());
                   tran.commit();
                   backButtonStack.clear();
               }
               if(mClusterManager!=null){
               mClusterManager.clearItems();
                 }
               showResultListFlag=false;

                  // If nameList !=null, this means the keyword is a aliase of some POI name or POI category
                  ArrayList<String> nameList = ((SbuDailyLifeService)dailyService).getNameList(location);
                  if (nameList != null) {
                      for (String str : nameList) {
                          location = str;
                      }
                  }

                  //
                  if(dailyService.checkMap(location)) {
                      setServiceNumber(0);
                      myMarkerList = dailyService.getTargetPOI(location);
                      setBottomButtonFragmentList(myMarkerList,0);
                      setUpCluster(myMarkerList);
                      storeMapState(serviceNumber,myMarkerList);

                      //  If there are more than one POI related to user keyword
                      //  create a list to show these poi.
                      if (myMarkerList.size() > 1) {
                          showResultListFlag = true;
                          dailyResultFragment = new SbuDailyLifeResultFragment();
                          ((SbuDailyLifeResultFragment) dailyResultFragment).setTargetList(myMarkerList);
                          showListButton.setVisibility(View.VISIBLE);
                          moveCamera(myMarkerList);
                      }

                      if (myMarkerList.size() == 1) {
                          for (POI currentPOI : myMarkerList) {
                              currentMapCenter = currentPOI.getPosition();
                              setBottomButtonFragment(currentPOI);
                          }
                          showListButton.setVisibility(View.INVISIBLE);
                          map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentMapCenter, 14));
                      }
                  }

                  else {
                  myMarkerList = null;
                  layoutPager.setVisibility(View.INVISIBLE);
                  showListButton.setVisibility(View.INVISIBLE);
              }
        }
    }

   // store current map state, when user press backButton, back to map's previous state in some situation.
    public void storeMapState(int number, ArrayList<POI> list){
        tempMyMarkerList.addAll(tool.removeDuplicateWithOrder(list));
        tempMyMarkerList = tool.removeDuplicateWithOrder(tempMyMarkerList);
        tempServiceNumber=number;
    }

    public void setServiceNumber(int number ){
        this.serviceNumber = number;
        switch (serviceNumber) {
            case 0:
            {
                showListButton.setText("Review Search Result");
                break;
            }
            case 1:
            {
                showListButton.setText("Review CourseSchedule");
                break;
            }
            case 2:
            {
                showListButton.setText("Review Calendar");
                break;
            }
            default:
                break;
        }
    }



    private void InitializeMenu(Bundle savedInstanceState){
        mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        // SBU DailyLife
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Course Management
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // SBU events
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Chat Platform
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1), true, "22"));
        // Login out
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));

        // Recycle the typed array
        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);

        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);

                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item

        }

    }

    @Override
    public void onClusterInfoWindowClick(Cluster<POI> cluster) {
        if (InfoWindowClickFlg == true) {

                if (cluster.getSize() > 1) {
                    switch (serviceNumber) {
                        case 0: {

                            dailyClusterResultFragment = new SbuClusterResultFragment();
                            ArrayList<SbuDailyLifePOI> list = new ArrayList<>();
                            for (POI poi : cluster.getItems()) {
                                SbuDailyLifePOI SDLPoi = (SbuDailyLifePOI) poi;
                                list.add(SDLPoi);
                            }

                            dailyClusterResultFragment.setPoiList(list);
                            FragmentTransaction resultTran = getSupportFragmentManager().beginTransaction()
                                    .add(R.id.Cluster_result_Container, dailyClusterResultFragment);
                            FragmentIdPair pair = new FragmentIdPair(dailyClusterResultFragment,R.id.Cluster_result_Container,2);
                            backButtonStack.push(pair);
                            resultTran.commit();
                            break;
                        }
                        case 1: {
                            courseClusterResultFragment = new CourseResultListFragment();
                            ArrayList<CourseManagerPOI> list = new ArrayList<>();
                            for (POI poi : cluster.getItems()) {
                                CourseManagerPOI CMPoi = (CourseManagerPOI) poi;
                                list.add(CMPoi);
                            }
                            courseClusterResultFragment.setPoiList(list);
                            FragmentTransaction resultTran = getSupportFragmentManager().beginTransaction()
                                    .add(R.id.Cluster_result_Container, courseClusterResultFragment);
                            resultTran.addToBackStack(null);
                            resultTran.commit();
                            break;
                        }
                        case 2: {
                            eventClusterResultListFragment = new EventResultListFragment();
                            ArrayList<EventPOI> list = new ArrayList<>();
                            for (POI poi : cluster.getItems()) {
                                EventPOI eventPoi = (EventPOI) poi;
                                list.add(eventPoi);
                            }
                            eventClusterResultListFragment.setPoiList(list);
                            FragmentTransaction resultTran = getSupportFragmentManager().beginTransaction()
                                    .add(R.id.Cluster_result_Container, eventClusterResultListFragment);
                            resultTran.addToBackStack(null);
                            resultTran.commit();
                            break;
                        }
                        default:
                            break;
                    }
                }
            }
        }


    @Override
    public void onClusterItemInfoWindowClick(POI poi) {

    }

    public class POIRenderer extends DefaultClusterRenderer<POI>{

        public POIRenderer(){
            super(getApplicationContext(), map, mClusterManager);
        }

        @Override
        protected void onBeforeClusterItemRendered(POI item, MarkerOptions markerOptions) {
            markerOptions.title(item.getPoiLabel());
        }

        @Override
        protected boolean shouldRenderAsCluster(Cluster<POI> cluster) {
            return cluster.getSize() > 1;
        }

        @Override
        protected void onClusterItemRendered(POI clusterItem, Marker marker) {
            super.onClusterItemRendered(clusterItem, marker);
            mMarkersHashMap2.put(clusterItem, marker);
        }

        @Override
        protected void onClusterRendered(Cluster<POI> cluster, Marker marker) {
            super.onClusterRendered(cluster, marker);

            for(POI item: cluster.getItems()){
                mMarkersHashMap2.put(item,marker);
            }
        }
    }

    public void setUpCluster(ArrayList<POI> myMarkerList){
          mClusterManager = new ClusterManager<POI>(this,map);
          map.setInfoWindowAdapter(mClusterManager.getMarkerManager());

          mClusterManager.getClusterMarkerCollection().setOnInfoWindowAdapter(new CustomAdapterForCluster());
          mClusterManager.getMarkerCollection().setOnInfoWindowAdapter(new CustomAdapterForCluster());

          poiRenderer =  new POIRenderer();
          mClusterManager.setRenderer(poiRenderer);

          map.setOnCameraChangeListener(mClusterManager);
          map.setOnMarkerClickListener(mClusterManager);
          map.setOnInfoWindowClickListener(mClusterManager);

          mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<POI>() {
              @Override
              public boolean onClusterClick(Cluster<POI> cluster) {

                  clickedCluster = cluster;
                  clusterFlag = true;
                  layoutPager.setVisibility(View.INVISIBLE);
                  destinationLat = cluster.getPosition().latitude;
                  destinationLng = cluster.getPosition().longitude;
                  showClusterListButton.setVisibility(View.VISIBLE);
                  showClusterListButton.setOnClickListener(new ShowClusterListButtonClickListener(cluster));
                  ArrayList<POI> clusterList = new ArrayList<POI>();
                  clusterList.addAll(cluster.getItems());
                  if(clusterList.size()>2){
                      POI item1 = clusterList.get(0);
                      POI item2 = clusterList.get(1);
                      showClusterListButtonText1.setText("Item :" + item1.getPoiLabel()+"," + item2.getPoiLabel()+",...");
                  }
                  else{
                      POI item1 = clusterList.get(0);
                      POI item2 = clusterList.get(1);
                      showClusterListButtonText1.setText("Item :" + item1.getPoiLabel()+"," + item2.getPoiLabel());
                  }
                  String location = clickedCluster.getItems().iterator().next().getPoiLocation();
                  showClusterListButtonText2.setText(location);

                  return false;
              }
          });
            mClusterManager.setOnClusterInfoWindowClickListener(this);
            mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<POI>() {
                @Override
                public boolean onClusterItemClick(POI poi) {

                    clickedClusterItem = poi;
                    clusterItemFlag = true;
                    destinationLat = poi.getPosition().latitude;
                    destinationLng = poi.getPosition().longitude;

                    setBottomButtonFragment(poi);
                    showListButton.setVisibility(View.INVISIBLE);
                    showClusterListButton.setVisibility(View.INVISIBLE);
                    return false;
                }
            });

          mClusterManager.setOnClusterItemInfoWindowClickListener(this);
          addItems(myMarkerList);
          mClusterManager.cluster();

    }

     private void addItems(ArrayList<POI> myMarkerList){
        for(POI poiElement : myMarkerList){
             if(poiElement != null) {
                 mClusterManager.addItem(poiElement);
             }
        }
     }

     private void moveCamera(ArrayList<POI> myMarkerList){
         LatLngBounds.Builder builder = new LatLngBounds.Builder();
         for(POI poiElement : myMarkerList){

             builder.include(poiElement.getPosition());
         }
        LatLngBounds bounds = builder.build();
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150));
     }

    private class ShowClusterListButtonClickListener implements View.OnClickListener{
        private Cluster<POI> cluster;
        ShowClusterListButtonClickListener(Cluster<POI> cluster){
            this.cluster=cluster;
        }
        @Override
        public void onClick(View v) {

            switch (serviceNumber) {
                case 0: {
                    dailyClusterResultFragment = new SbuClusterResultFragment();
                    ArrayList<SbuDailyLifePOI> list = new ArrayList<>();
                    for (POI poi : cluster.getItems()) {
                        SbuDailyLifePOI SDLPoi = (SbuDailyLifePOI) poi;
                        list.add(SDLPoi);
                    }

                    dailyClusterResultFragment.setPoiList(list);
                    FragmentTransaction resultTran = getSupportFragmentManager().beginTransaction()
                            .add(R.id.Cluster_result_Container, dailyClusterResultFragment);
                    FragmentIdPair pair = new FragmentIdPair(dailyClusterResultFragment,R.id.Cluster_result_Container,2);
                    backButtonStack.push(pair);
                    resultTran.commit();
                    break;
                }
                case 1: {
                    courseClusterResultFragment = new CourseResultListFragment();
                    ArrayList<CourseManagerPOI> list = new ArrayList<>();
                    for (POI poi : cluster.getItems()) {
                        CourseManagerPOI CMPoi = (CourseManagerPOI) poi;
                        list.add(CMPoi);
                    }
                    courseClusterResultFragment.setPoiList(list);
                    FragmentTransaction resultTran = getSupportFragmentManager().beginTransaction()
                            .add(R.id.Cluster_result_Container, courseClusterResultFragment);
                    FragmentIdPair pair = new FragmentIdPair(courseClusterResultFragment,R.id.Cluster_result_Container,2);
                    backButtonStack.push(pair);
                    resultTran.commit();
                    break;
                }
                case 2:{
                    eventClusterResultListFragment = new EventResultListFragment();
                    ArrayList<EventPOI> list = new ArrayList<>();
                    for (POI poi : cluster.getItems()) {
                        EventPOI eventPoi = (EventPOI) poi;
                        list.add(eventPoi);
                    }
                    eventClusterResultListFragment.setPoiList(list);
                    FragmentTransaction resultTran = getSupportFragmentManager().beginTransaction()
                            .add(R.id.Cluster_result_Container, eventClusterResultListFragment);
                    FragmentIdPair pair = new FragmentIdPair(eventClusterResultListFragment,R.id.Cluster_result_Container,2);
                    backButtonStack.push(pair);
                    resultTran.commit();
                    break;
                }
                default:break;
            }

        }
    }



    private class CustomAdapterForCluster implements GoogleMap.InfoWindowAdapter {

        private final View myContentsView;

        public CustomAdapterForCluster(){
            myContentsView = getLayoutInflater().inflate(R.layout.custom_cluster_infowindow,null);
        }

        @Override
        public View getInfoWindow(Marker marker) {


            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            if(clusterFlag == true){
                final ArrayList<POI> clusterList = new ArrayList<POI>();
                String location = clickedCluster.getItems().iterator().next().getPoiLocation();
                TextView clusterPosition = (TextView)myContentsView.findViewById(R.id.clusterLocation);
                clusterPosition.setText("Location:" + location);

                for(POI item : clickedCluster.getItems()){
                    clusterList.add(item);
                }

                TextView clusterItemInfo = (TextView)myContentsView.findViewById(R.id.clusterTitle);
                if(clusterList.size()>2){
                    POI item1 = clusterList.get(0);
                    POI item2 = clusterList.get(1);
                    clusterItemInfo.setText("Item :" + item1.getPoiLabel()+"," + item2.getPoiLabel()+",...");
                }
                else{
                    POI item1 = clusterList.get(0);
                    POI item2 = clusterList.get(1);
                    clusterItemInfo.setText("Item :" + item1.getPoiLabel()+"," + item2.getPoiLabel());
                }
                InfoWindowClickFlg = true;

            }

            if(clusterItemFlag == true){
                ArrayList<POI> clusterList = new ArrayList<POI>();
                String location = clickedClusterItem.getPoiLocation();
                TextView clusterItemInfo = (TextView)myContentsView.findViewById(R.id.clusterTitle);
                clusterItemInfo.setText("Item :" + clickedClusterItem.getPoiLabel());
                clusterList.add(clickedClusterItem);
                TextView clusterLocation = (TextView)myContentsView.findViewById(R.id.clusterLocation);
                clusterLocation.setText("Location : "+ location);
                InfoWindowClickFlg = false;

            }

             clusterFlag=false;
             clusterItemFlag=false;

            return myContentsView;
        }

    }




    public ClusterManager getClusterManager(){
        return this.mClusterManager;
    }

    public void setClickedClusterItem (POI poi){
        this.clusterItemFlag = true;
        this.clickedClusterItem = poi;
    }

    public void setClusterItemFragToFalse(){
        this.clusterItemFlag = false;
    }

    public LoginFragment getLoginFragment(){
        return this.loginFragment;
    }


    private class SlideMenuClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            displayView(position);
        }
    }
    private void displayView(int position) {
        // update the main content by replacing fragments
        switch (position) {
            case 0:
                setServiceNumber(0);
                if(MenuFragment != null){
                    FragmentTransaction tran3 = getSupportFragmentManager().beginTransaction().remove(MenuFragment);
                    tran3.commit();
                }
                dailyLifeCategoryFragment=new SbuDailyLifeCategoryFragment();
                MenuFragment = dailyLifeCategoryFragment;
                dailyLifeCategoryFragment.categories.clear();

                if(!backButtonStack.isEmpty()){
                FragmentTransaction tran = getSupportFragmentManager().beginTransaction().remove(backButtonStack.peek().getFragment());
                tran.commit();}

                FragmentTransaction tran4 = getSupportFragmentManager().beginTransaction().add(R.id.Category_Container, MenuFragment);
                tran4.commit();
                FragmentIdPair pair = new FragmentIdPair(MenuFragment,R.id.Category_Container,1);
                backButtonStack.push(pair);

                break;
            case 1:
            if(MenuFragment != null){
                FragmentTransaction tran1 = getSupportFragmentManager().beginTransaction().remove(MenuFragment);
                tran1.commit();
            }
                if(!backButtonStack.isEmpty()){
                    FragmentTransaction tran = getSupportFragmentManager().beginTransaction().remove(backButtonStack.peek().getFragment());
                    tran.commit();}
                SharedPreferences preferences = getSharedPreferences("UserInfomation" , Context.MODE_PRIVATE);
                String username = preferences.getString("username" , null);
            if(username == null){
                loginFragment = new LoginFragment();
                MenuFragment = loginFragment;
                FragmentTransaction tran2 = getSupportFragmentManager().beginTransaction().add(R.id.CourseHistory_container, MenuFragment);
                tran2.addToBackStack(null);
                tran2.commit();
            }
            else {
                courseHistoryFragment = new CourseHistoryFragment();
                MenuFragment = courseHistoryFragment;
                FragmentTransaction tran2 = getSupportFragmentManager().beginTransaction().add(R.id.CourseHistory_container, MenuFragment);
                FragmentIdPair pair1 = new FragmentIdPair(MenuFragment,R.id.CourseHistory_container,1);
                backButtonStack.push(pair1);
                tran2.commit();
            }
               break;
            case 2:
                setServiceNumber(2);
                if(MenuFragment != null){
                    FragmentTransaction tran5 = getSupportFragmentManager().beginTransaction().remove(MenuFragment);
                    tran5.commit();
                }
                if(!backButtonStack.isEmpty()){
                    FragmentTransaction tran = getSupportFragmentManager().beginTransaction().remove(backButtonStack.peek().getFragment());
                    tran.commit();}

                eventCalendarFragment = new EventCalendarFragment();
                MenuFragment = eventCalendarFragment;
                FragmentTransaction tran6 = getSupportFragmentManager().beginTransaction().add(R.id.CourseHistory_container,MenuFragment);
                FragmentIdPair pair2= new FragmentIdPair(MenuFragment,R.id.CourseHistory_container,1);
                backButtonStack.push(pair2);
                tran6.commit();
                break;
            case 3:
                if(MenuFragment != null){
                    FragmentTransaction tran7 = getSupportFragmentManager().beginTransaction().remove(MenuFragment);
                    tran7.commit();
                }
                if(!backButtonStack.isEmpty()){
                    FragmentTransaction tran = getSupportFragmentManager().beginTransaction().remove(backButtonStack.peek().getFragment());
                    tran.commit();
                }
                ChatMainFragment chatMainFragment = new ChatMainFragment();
                MenuFragment = chatMainFragment;
                FragmentTransaction chat_main_tran = getSupportFragmentManager().beginTransaction().add(R.id.CourseHistory_container,MenuFragment);
                FragmentIdPair pair3= new FragmentIdPair(MenuFragment,R.id.CourseHistory_container,1);
                backButtonStack.push(pair3);
                chat_main_tran.commit();

                // operation about Platform, create new Fragment about Chat Platform service
                break;

            case 4:
                editor.clear();
                editor.commit();
                break;

            default:
                break;
        }

        if (MenuFragment != null) {
            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void onBackPressed() {
        if(backButtonStack.isEmpty()){
            exitApplication();
        }
        else {
            FragmentIdPair pair = backButtonStack.pop();
            FragmentTransaction tran = getSupportFragmentManager().beginTransaction().remove(pair.getFragment());
            tran.commit();
            if (pair.getFragmentCategoryNumber() == 1) {
                map.clear();
                setServiceNumber(tempServiceNumber);
                setBottomButtonFragmentList(tempMyMarkerList, tempServiceNumber);
                System.out.println("tempMyMarkList= " + tempMyMarkerList);
                setUpCluster(tempMyMarkerList);
                storeMapState(tempServiceNumber, tempMyMarkerList);

            }
            if(!backButtonStack.isEmpty()){
                FragmentIdPair nextPair = backButtonStack.peek();
                FragmentTransaction tran2 = getSupportFragmentManager().beginTransaction().add(nextPair.getFragmentLayoutID(),nextPair.getFragment());
                tran2.commit();
            }
        }

    }

    private void  exitApplication(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    // A locationlistener to get the Userlocation based on GPS.
    class locationlistener implements LocationListener{
        @Override
        public  void onLocationChanged(Location location) {
            // TODO Auto-generated method stub
            if(location != null)
            {
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                usercurrentlocation = new LatLng(latitude, longitude);

            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.bNormal:
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.bSatellite:
                map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.bHybrid:
                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case R.id.bTerrain:
                map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    private void getSreenDimanstions()
    {
        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
    }

    private LatLngBounds createLatLngBoundsObject(LatLng firstLocation, LatLng secondLocation)
    {
        if (firstLocation != null && secondLocation != null)
        {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(firstLocation).include(secondLocation);

            return builder.build();
        }
        return null;
    }

    public void findDirections(double fromPositionDoubleLat, double fromPositionDoubleLong, double toPositionDoubleLat, double toPositionDoubleLong, String mode)
    {
        Map<String, String> map = new HashMap<String, String>();
        map.put(GetDirectionsAsyncTask.USER_CURRENT_LAT, String.valueOf(fromPositionDoubleLat));
        map.put(GetDirectionsAsyncTask.USER_CURRENT_LONG, String.valueOf(fromPositionDoubleLong));
        map.put(GetDirectionsAsyncTask.DESTINATION_LAT, String.valueOf(toPositionDoubleLat));
        map.put(GetDirectionsAsyncTask.DESTINATION_LONG, String.valueOf(toPositionDoubleLong));
        map.put(GetDirectionsAsyncTask.DIRECTIONS_MODE, mode);

        GetDirectionsAsyncTask asyncTask = new GetDirectionsAsyncTask(this);
        asyncTask.execute(map);
    }

    @Override
    protected void onResume() {

        super.onResume();
        latlngBounds = createLatLngBoundsObject(STARTINGPOINT, DESTINATION);
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(latlngBounds, width, height, 150));

    }

    public void handleGetDirectionsResult(ArrayList<LatLng> directionPoints) {
        PolylineOptions rectLine = new PolylineOptions().width(5).color(Color.BLUE);

        for(int i = 0 ; i < directionPoints.size() ; i++)
        {
            rectLine.add(directionPoints.get(i));
        }
        if (newPolyline != null)
        {
            newPolyline.remove();
        }
        newPolyline = map.addPolyline(rectLine);

        if (isTravelingToDestination)
        {
            latlngBounds = createLatLngBoundsObject(STARTINGPOINT, DESTINATION);
            map.animateCamera(CameraUpdateFactory.newLatLngBounds(latlngBounds, width, height, 300));
        }

    }


    private class GetPOIItem extends AsyncTask<ApiConnector, Long, JSONArray>{

        public GetPOIItem() {
        }

        @Override
        protected JSONArray doInBackground(ApiConnector... params) {

            //  it is executed on Background thread

            return params[0].GetPOI();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray){
            dailyService.obtainData(jsonArray);
              if(dailyService.getServiceMap().size()>1) {
                  Toast.makeText(getApplicationContext(), "Obtain data success", Toast.LENGTH_LONG).show();
              }
              else{
                  Toast.makeText(getApplicationContext(), "Obtain data failed", Toast.LENGTH_LONG).show();
              }

              ArrayList<String> hintList1 = new ArrayList<String>();

              ArrayList<String> list = ((SbuDailyLifeService)dailyService).getServiceHintList();

              for(POI item : (ArrayList<POI>)((SbuDailyLifeService)dailyService).getServiceList()){
                  hintList1.add(item.getPoiLabel());
              }
              tool.write(NavigationActivity.this, hintList1, "Hintlist1.txt");
        }

    }
    private class GetAliase extends AsyncTask<GetAliaseConnector, Long, JSONArray>{

        public GetAliase() {

        }

        @Override
        protected JSONArray doInBackground(GetAliaseConnector... params) {
            return params[0].GetPOI();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray){
               storeAlias(jsonArray);
               ArrayList<String> hintList2 = new ArrayList<String>();
               for(String str : (ArrayList<String>)((SbuDailyLifeService)dailyService).getServiceHintList()){
                   hintList2.add(str);
               }
              tool.write(NavigationActivity.this, hintList2, "Hintlist2.txt");
        }

    }

    private  void storeAlias(JSONArray jsonArray){
        for(int i=0; i<jsonArray.length(); i++){

            JSONObject json = null;
            try{
                json = jsonArray.getJSONObject(i);
                ((SbuDailyLifeService)dailyService).storeAlias(json);


            } catch (JSONException e){
                e.printStackTrace();
            }
        }

    }
}
