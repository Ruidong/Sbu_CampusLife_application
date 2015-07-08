package com.example.ruidong.sbu_application.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.os.Environment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ruidong.sbu_application.R;
import com.example.ruidong.sbu_application.courseManager.service.CourseManagerPOI;
import com.example.ruidong.sbu_application.courseManager.service.LoginFragment;
import com.example.ruidong.sbu_application.dailylife.service.SbuDailyLifePOI;
import com.example.ruidong.sbu_application.event.service.EventPOI;
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
import com.example.ruidong.sbu_application.event.service.CSVReader;
import com.example.ruidong.sbu_application.framework.common.tool.GMapV2Direction;
import com.example.ruidong.sbu_application.framework.common.tool.GetAliaseConnector;
import com.example.ruidong.sbu_application.framework.common.tool.GetDirectionsAsyncTask;

import com.example.ruidong.sbu_application.courseManager.service.CourseHistoryFragment;
import com.example.ruidong.sbu_application.courseManager.service.CourseResultListFragment;
import com.example.ruidong.sbu_application.dailylife.service.SbuCategoryResultFragment;
import com.example.ruidong.sbu_application.dailylife.service.SbuDailyLifeCategoryFragment;
import com.example.ruidong.sbu_application.event.service.EventCalendarFragment;
import com.example.ruidong.sbu_application.event.service.EventResultListFragment;
import com.example.ruidong.sbu_application.framework.slidebutton.ViewPagerAdapter;
import com.example.ruidong.sbu_application.framework.slidebutton.ViewPagerChangeListener;
import com.example.ruidong.sbu_application.framework.slidinmenu.adapter.NavDrawerItem;
import com.example.ruidong.sbu_application.framework.slidinmenu.adapter.NavDrawerListAdapter;
import com.example.ruidong.sbu_application.courseManager.service.CourseManagementService;
import com.example.ruidong.sbu_application.event.service.EventService;
import com.example.ruidong.sbu_application.general.service.GeneralService;
import com.example.ruidong.sbu_application.dailylife.service.SbuDailyLifeService;

public class NavigationActivity extends FragmentActivity implements  ClusterManager.OnClusterInfoWindowClickListener<POI>, ClusterManager.OnClusterItemInfoWindowClickListener<POI> {



    private static final LatLng STARTINGPOINT = new LatLng(40.915823, -73.121903);
    private static final LatLng DESTINATION = new LatLng(40.915962, -73.126264);
    public static GoogleMap map;
    private SupportMapFragment fragment;
    private LatLngBounds latlngBounds;
    private ImageButton navigationImageButton;
    private ImageButton searchImageButton;
    private Polyline newPolyline;
    private boolean isTravelingToDestination = false;
    private int width, height;
    private LatLng usercurrentlocation;
    private LocationManager locationManager;
    private LocationListener locationListener = new locationlistener();
    public static EditText editText ;
    private static String location ;
    private static double destinationLat = 40.915962;
    private static double destinationLng = -73.126264;

    private CourseManagementService courseService= new CourseManagementService();
    private GeneralService genService= new GeneralService();
    public  static SbuDailyLifeService dailyService=new SbuDailyLifeService();
    private EventService eventService = new EventService();
    public  static ArrayList<POI> myMarkerList;
    private static HashMap<Marker, POI> mMarkersHashMap1 = new HashMap<Marker, POI>();
    public  static HashMap<POI, Marker> mMarkersHashMap2 = new HashMap<POI, Marker>();
    public HashMap<String,POI> markerPOIMap = new HashMap<String, POI>();
    public static boolean showResultListFlag = false;

    private boolean listViewFlag=false;


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
    public static SbuCategoryResultFragment dailyResultFragment;
    public static CourseResultListFragment courseResultFragment;
//    private  ShowListButton showListFragment;

    private ViewPager pager;
    private ViewPagerAdapter mAdapter;
    private  LinearLayout layoutPager;

    public static HashMap<POI, Integer> bottomHash1 = new HashMap<POI, Integer>();
    public static HashMap<Integer, POI> bottomHash2 = new HashMap<Integer, POI>();

    private ClusterManager<POI> mClusterManager;
    private Marker InfoShowedMarker;
    private Cluster<POI> clickedCluster;
    private POI clickedClusterItem;
    private POIRenderer poiRenderer;
    private boolean clusterFlag = false;
    private boolean clusterItemFlag = false;
    private SbuCategoryResultFragment dailyClusterResultFragment;
    private CourseResultListFragment courseClusterResultFragment;
    private EventResultListFragment eventResultListFragment;
    private SbuDailyLifeCategoryFragment dailyLifeCategoryFragment;

    private boolean clusterResultFragmentFlag ;
    private Button showListButton;
    private boolean InfoWindowClickFlg;
    private LatLng currentMapCenter;
    private CourseHistoryFragment courseHistoryFragment;
    private EventCalendarFragment eventCalendarFragment;
    private boolean courseScheduleFlag;
    private int serviceNumber = -1;

    private LoginFragment loginFragment;
    private SharedPreferences.Editor editor;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
           if(resultCode == RESULT_OK){
               Bundle bundle = data.getExtras();
               String str = bundle.getString("userInput");
               editText.setText(str.toLowerCase());
           }
    }

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
        navigationImageButton = (ImageButton) findViewById(R.id.bNavigation);
        searchImageButton = (ImageButton) findViewById(R.id.bSearch);
        editText = (EditText) findViewById(R.id.et_location);
        showListButton=(Button)findViewById(R.id.showListButton);
        showListButton.setOnClickListener(new showListButtonOnClickListener(this) {

        });
        showListButton.setVisibility(View.INVISIBLE);

		    editText.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(NavigationActivity.this, InputSearchActivity.class);
                    startActivityForResult(intent, 0);
                }
            });

        dailyService.setDailyMap();
        courseService.setCourseMap();
        eventService.setEventMap();
//        new GetPOIItem().execute(new ApiConnector());
//        new GetAliase().execute(new GetAliaseConnector());
        ArrayList<String> hintList1 = new ArrayList<>();
        hintList1.add("food");
        hintList1.add("fastfood");
        ArrayList<String> hintList2 = new ArrayList<>();
        hintList2.add("coffee");
        write(this, hintList1,"TestHintlist1.txt");
        write(this,hintList2,"TestHintlist2.txt");





        try {
            courseService.obtainData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SharedPreferences preferences = getSharedPreferences("UserInfomation", Context.MODE_PRIVATE);
        editor = preferences.edit();
        searchImageButton.setOnClickListener(new searchOnclickListener(dailyService) {

        });


        // Determine whether the bottomBar or detailedListView should be hidden when user click map.
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng arg0) {

                layoutPager.setVisibility(View.INVISIBLE);


                if (showResultListFlag == true || serviceNumber==2) {
                    switch (serviceNumber) {
                        case 0: {
                            FragmentTransaction removeTran = getSupportFragmentManager().beginTransaction().remove(dailyResultFragment);
                            removeTran.commit();
                            break;
                        }
                        case 1: {
                            FragmentTransaction removeTran = getSupportFragmentManager().beginTransaction().remove(courseResultFragment);
                            removeTran.commit();
                            break;
                        }
                        default:break;
                    }
                    showListButton.setVisibility(View.VISIBLE);
                }

                if (clusterResultFragmentFlag == true) {
                    switch (serviceNumber) {
                        case 0: {
                            FragmentTransaction removeTran = getSupportFragmentManager().beginTransaction().remove(dailyClusterResultFragment);
                            removeTran.commit();
                            break;
                        }
                        case 1: {
                            FragmentTransaction removeTran = getSupportFragmentManager().beginTransaction().remove(courseClusterResultFragment);
                            removeTran.commit();
                            break;
                        }
                        case 2:{
                            FragmentTransaction removeTran = getSupportFragmentManager().beginTransaction().remove(eventResultListFragment);
                            removeTran.commit();
                            break;
                        }
                        default:break;
                    }
                }
                clusterResultFragmentFlag = false;
            }

        });

        map.setInfoWindowAdapter(new CustomAdapterForCluster());

        map.setOnMarkerClickListener(new OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {


                POI currentPOI = mMarkersHashMap1.get(marker);
                destinationLat = currentPOI.getPosition().latitude;
                destinationLng = currentPOI.getPosition().longitude;


                marker.showInfoWindow();

                setBottomButtonFragment(currentPOI);

                if (showResultListFlag == true) {
                    switch (serviceNumber){
                        case 0: {
                        FragmentTransaction removeTran = getSupportFragmentManager().beginTransaction().remove(dailyResultFragment);
                            removeTran.commit();
                            break;
                    }
                        case 1: {
                        FragmentTransaction removeTran = getSupportFragmentManager().beginTransaction().remove(courseResultFragment);
                            removeTran.commit();
                            break;
                     }
                        default: break;
                    }
                    showListButton.setVisibility(View.INVISIBLE);
                }
                if (courseHistoryFragment.getCourseScheduleFragment() != null) {
                    FragmentTransaction removeTran1 = getSupportFragmentManager().beginTransaction().remove(courseHistoryFragment.getCourseScheduleFragment());
                    removeTran1.commit();
                }

                return false;
            }
        });


        // Show the navigation route.
        navigationImageButton.setOnClickListener(new View.OnClickListener() {
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
    public static String readCSV(){
        String next[] = {};
        ArrayList<String[]> list = new ArrayList<String[]>();

        try {
            File file = new File(Environment.getExternalStorageDirectory(),
                    "events.csv");
            InputStream is = new FileInputStream(file);
            CSVReader reader = new CSVReader(new InputStreamReader(is));
            while(true) {
                next = reader.readNext();
                if(next != null) {
                    list.add(next);
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list.get(1)[0];
    }

    public CourseManagementService getCourseService(){
        return this.courseService;
    }
    public EventService getEventService(){
        return  this.eventService;
    }

    public LinearLayout getLayoutPager(){
        return this.layoutPager;
    }

    private class showListButtonOnClickListener implements View.OnClickListener{
         private NavigationActivity activity;
         showListButtonOnClickListener(NavigationActivity activity){
             this.activity=activity;
         }
        @Override
        public void onClick(View v) {
            switch (serviceNumber) {
                case 0: {
                    FragmentTransaction resultTran = getSupportFragmentManager().beginTransaction()
                            .add(R.id.Category_result_Container, dailyResultFragment);
                    resultTran.addToBackStack(null);
                    resultTran.commit();
                    break;
                }
                case 1: {
                    FragmentTransaction resultTran = getSupportFragmentManager().beginTransaction()
                            .add(R.id.Category_result_Container, courseResultFragment);
                    resultTran.addToBackStack(null);
                    resultTran.commit();
                    break;
                }
                case 2: {
                    eventCalendarFragment = new EventCalendarFragment();
                    FragmentTransaction tran6 = getSupportFragmentManager().beginTransaction().add(R.id.CourseHistory_container, eventCalendarFragment);
                    tran6.addToBackStack(null);
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

    private class searchOnclickListener implements View.OnClickListener{
        private SbuDailyLifeService   dailyService;


        searchOnclickListener(SbuDailyLifeService dailyService){
            this.dailyService=dailyService;
        }

        @Override
        public void onClick(View v) {
            location = editText.getText().toString();
            // Determine which service's poi is relative with user input.
               map.clear();
               if(mClusterManager!=null){
               mClusterManager.clearItems();
                 }
               showResultListFlag=false;

                  // If nameList !=null, this means the keyword is a aliase of some POI name or POI category
                  ArrayList<String> nameList = dailyService.getNameList(location);
                  if (nameList != null) {
                      for (String str : nameList) {
                          location = str;
                      }
                  }

                  if(dailyService.checkMap(location)) {
                      setServiceNumber(0);
                      myMarkerList = dailyService.getTargetPOI(location);
                      setBottomButtonFragmentList(myMarkerList,0);
                      setUpCluster(myMarkerList);

                      //If there are more than one POI related to user keyword, create a list to show these poi.
                      if (myMarkerList.size() > 1) {
                          showResultListFlag = true;
                          dailyResultFragment = new SbuCategoryResultFragment();
                          ((SbuCategoryResultFragment) dailyResultFragment).setTargetList(myMarkerList);
                          FragmentTransaction resultTran = getSupportFragmentManager().beginTransaction()
                                  .add(R.id.Category_result_Container, dailyResultFragment);
                          resultTran.addToBackStack(null);
                          resultTran.commit();
                          showListButton.setVisibility(View.INVISIBLE);
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
                  else if (courseService.checkMap(location)){
                      setServiceNumber(1);
                      myMarkerList = courseService.getTargetPOI(location);
                      setBottomButtonFragmentList(myMarkerList,1);
                      setUpCluster(myMarkerList);
                      if (myMarkerList.size() > 1) {
                          showResultListFlag = true;
                          courseResultFragment = new CourseResultListFragment();
                          ((CourseResultListFragment) courseResultFragment).setTargetList(myMarkerList);
                          FragmentTransaction resultTran = getSupportFragmentManager().beginTransaction()
                                  .add(R.id.Category_result_Container, courseResultFragment);
                          resultTran.addToBackStack(null);
                          resultTran.commit();
                      }

                      if (myMarkerList.size() == 1) {
                          for (POI currentPOI : myMarkerList) {
                              currentMapCenter = currentPOI.getPosition();
                              setBottomButtonFragment(currentPOI);
                          }

                          map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentMapCenter, 14));
                      }
                      showListButton.setVisibility(View.INVISIBLE);
                  }

                  else {
                  myMarkerList = null;
                  if (dailyResultFragment  !=null) {
                      FragmentTransaction tran = getSupportFragmentManager().beginTransaction().remove(dailyResultFragment);
                      tran.commit();
                  }
                  if (courseResultFragment != null) {
                      FragmentTransaction tran = getSupportFragmentManager().beginTransaction().remove(courseResultFragment);
                      tran.commit();
                  }
                  layoutPager.setVisibility(View.INVISIBLE);
                  showListButton.setVisibility(View.INVISIBLE);
              }
        }
    }
    public void setServiceNumber(int number ){
        this.serviceNumber = number;
        if(number ==0 || number ==1){
            showListButton.setText("Result List");
        }
        else if(number == 2){
            showListButton.setText("Review Calendar");
        }
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
        // Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Find People
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Photos
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Communities, Will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1), true, "22"));
        // Pages
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
           if(InfoWindowClickFlg == true){
             if(clusterResultFragmentFlag == false) {
                 if (cluster.getSize() > 1) {
                     switch (serviceNumber) {
                         case 0: {

                             dailyClusterResultFragment = new SbuCategoryResultFragment();
                             ArrayList<SbuDailyLifePOI> list = new ArrayList<>();
                             for (POI poi : cluster.getItems()) {
                                 SbuDailyLifePOI SDLPoi = (SbuDailyLifePOI) poi;
                                 list.add(SDLPoi);
                             }

                             dailyClusterResultFragment.setPoiList(list);
                             FragmentTransaction resultTran = getSupportFragmentManager().beginTransaction()
                                     .add(R.id.Cluster_result_Container, dailyClusterResultFragment);
                             clusterResultFragmentFlag = true;
                             resultTran.addToBackStack(null);
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
                             clusterResultFragmentFlag = true;
                             resultTran.addToBackStack(null);
                             resultTran.commit();
                             break;
                         }
                         case 2:{
                             eventResultListFragment = new EventResultListFragment();
                             ArrayList<EventPOI> list = new ArrayList<>();
                             for (POI poi : cluster.getItems()) {
                                 EventPOI eventPoi = (EventPOI) poi;
                                 list.add(eventPoi);
                             }
                             eventResultListFragment.setPoiList(list);
                             FragmentTransaction resultTran = getSupportFragmentManager().beginTransaction()
                                     .add(R.id.Cluster_result_Container, eventResultListFragment);
                             clusterResultFragmentFlag = true;
                             resultTran.addToBackStack(null);
                             resultTran.commit();
                             break;
                         }
                         default:break;
                     }
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
                  if (showResultListFlag == true) {
                      switch (serviceNumber) {
                          case 0: {
                              FragmentTransaction removeTran = getSupportFragmentManager().beginTransaction().remove(dailyResultFragment);
                              removeTran.commit();
                              showListButton.setVisibility(View.VISIBLE);
                              break;
                          }
                          case 1: {
                              FragmentTransaction removeTran = getSupportFragmentManager().beginTransaction().remove(courseResultFragment);
                              removeTran.commit();
                              showListButton.setVisibility(View.VISIBLE);
                              break;
                          }
                          default:break;
                      }
                  }
                  if(courseHistoryFragment!=null)
                  if(courseHistoryFragment.getCourseScheduleFragment()!=null){
                      FragmentTransaction removeTran1 = getSupportFragmentManager().beginTransaction().remove(courseHistoryFragment.getCourseScheduleFragment());
                      removeTran1.commit();
                  }
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

                    if (showResultListFlag == true) {
                        switch (serviceNumber) {
                            case 0: {
                                FragmentTransaction removeTran = getSupportFragmentManager().beginTransaction().remove(dailyResultFragment);
                                removeTran.commit();
                                break;
                            }case 1: {
                                FragmentTransaction removeTran = getSupportFragmentManager().beginTransaction().remove(courseResultFragment);
                                removeTran.commit();
                                break;
                            }
                            default:break;
                        }
                        showListButton.setVisibility(View.INVISIBLE);
                    }
                    if(courseHistoryFragment !=null)
                    if(courseHistoryFragment.getCourseScheduleFragment()!=null){
                        FragmentTransaction removeTran1 = getSupportFragmentManager().beginTransaction().remove(courseHistoryFragment.getCourseScheduleFragment());
                        removeTran1.commit();
                    }
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
                String position = clickedCluster.getItems().iterator().next().getPoiLocation();
                TextView clusterPosition = (TextView)myContentsView.findViewById(R.id.clusterTitle);
                clusterPosition.setText("Position:" + position);

                for(POI item : clickedCluster.getItems()){
                    clusterList.add(item);
                }

                TextView clusterItemInfo = (TextView)myContentsView.findViewById(R.id.clusterItemInfo);
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
                String position = clickedClusterItem.getPoiLocation();
                TextView clusterPosition = (TextView)myContentsView.findViewById(R.id.clusterTitle);
                clusterPosition.setText("Position:" + position);
                clusterList.add(clickedClusterItem);
                TextView clusterItemInfo = (TextView)myContentsView.findViewById(R.id.clusterItemInfo);
                clusterItemInfo.setText("Item : "+ clickedClusterItem.getPoiLabel());
                InfoWindowClickFlg = false;

            }

             clusterFlag=false;
             clusterItemFlag=false;

            return myContentsView;
        }

    }

    public SbuCategoryResultFragment getDailyClusterResultFragment(){
        return this.dailyClusterResultFragment;
    }

    public CourseResultListFragment getCourseClusterResultFragment(){
        return this.courseClusterResultFragment;
    }
    public EventResultListFragment getEventClusterResultFragment(){
        return this.eventResultListFragment;
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
    public void setClusterResultFragmentFlagToFalse(){
        this.clusterResultFragmentFlag=false;
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
                if(MenuFragment != null){
                    FragmentTransaction tran1 = getSupportFragmentManager().beginTransaction().remove(MenuFragment);
                    tran1.commit();
                }
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
                    tran2.addToBackStack(null);
                    tran2.commit();
                }

                break;
            case 1:
                if(MenuFragment != null){
                    FragmentTransaction tran3 = getSupportFragmentManager().beginTransaction().remove(MenuFragment);

                    tran3.commit();
                }
                dailyLifeCategoryFragment=new SbuDailyLifeCategoryFragment();
                MenuFragment = dailyLifeCategoryFragment;
                dailyLifeCategoryFragment.categories.clear();

                FragmentTransaction tran4 = getSupportFragmentManager().beginTransaction().add(R.id.Category_Container, MenuFragment);
                tran4.addToBackStack(null);
                tran4.commit();

                if(dailyResultFragment !=null) {
                    FragmentTransaction removeTran1 = getSupportFragmentManager().beginTransaction().remove(dailyResultFragment);
                    removeTran1.commit();
                    editText.setText(" ");
                }
                if(courseResultFragment !=null) {
                    FragmentTransaction removeTran1 = getSupportFragmentManager().beginTransaction().remove(courseResultFragment);
                    removeTran1.commit();
                    editText.setText(" ");
                }

                if(courseHistoryFragment!=null)
                if(courseHistoryFragment.getCourseScheduleFragment()!=null){
                    FragmentTransaction removeTran1 = getSupportFragmentManager().beginTransaction().remove(courseHistoryFragment.getCourseScheduleFragment());
                    removeTran1.commit();
                    showListButton.setVisibility(View.INVISIBLE);
                }
                break;
            case 2:
                if(MenuFragment != null){
                    FragmentTransaction tran5 = getSupportFragmentManager().beginTransaction().remove(MenuFragment);
                    tran5.commit();
                }

                eventCalendarFragment = new EventCalendarFragment();
                MenuFragment = eventCalendarFragment;
                FragmentTransaction tran6 = getSupportFragmentManager().beginTransaction().add(R.id.CourseHistory_container,MenuFragment);
                tran6.addToBackStack(null);
                tran6.commit();
                break;
            case 3:
                // operation about Platform
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
        FragmentManager fm = getSupportFragmentManager();

        if(fm.getBackStackEntryCount() >0){
            fm.popBackStack();
            clusterResultFragmentFlag = false;
        }
        else {
            exitApplication();

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
    public CourseHistoryFragment getCourseHistoryFragment(){

        return this.courseHistoryFragment;
    }

    public EventCalendarFragment getEventCalendarFragment(){
        return this.eventCalendarFragment;
    }

    //  When the list of BottomButton is set up, show these BottomButton.
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


    public void ObtainData(JSONArray jsonArray){

        for(int i=0; i<jsonArray.length(); i++){

            JSONObject json = null;
            try{
                json = jsonArray.getJSONObject(i);
                dailyService.storeData(json);



            } catch (JSONException e){
                e.printStackTrace();
            }
        }

    }



    public static void write(Context context, Object nameOfClass,String filename) {
        File directory = new File(context.getFilesDir().getAbsolutePath()
                + File.separator + "serlization");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = filename;
        ObjectOutput out = null;

        try {
            out = new ObjectOutputStream(new FileOutputStream(directory
                    + File.separator + fileName));
            out.writeObject(nameOfClass);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> read(Context context,String filename) {

        ObjectInputStream input = null;
        ArrayList<String> ReturnClass = null;
        String fileName = filename;
        File directory = new File(context.getFilesDir().getAbsolutePath()
                + File.separator + "serlization");
        try {

            input = new ObjectInputStream(new FileInputStream(directory
                    + File.separator + fileName));
            ReturnClass = (ArrayList<String>) input.readObject();
            input.close();

        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ReturnClass;
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
              ObtainData(jsonArray);
              if(dailyService.dailyMap.size()>0) {
                  Toast.makeText(getApplicationContext(), "Obtain data success", Toast.LENGTH_LONG).show();
              }
              else{
                  Toast.makeText(getApplicationContext(), "Obtain data failed", Toast.LENGTH_LONG).show();
              }

              ArrayList<String> hintList1 = new ArrayList<String>();

              for(POI item : dailyService.dailyList){
                  hintList1.add(item.getPoiLabel());
              }
              write(NavigationActivity.this, hintList1,"Hintlist1.txt");
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
               for(String str : dailyService.dailyHintList){
                   hintList2.add(str);
               }
              write(NavigationActivity.this, hintList2,"Hintlist2.txt");
        }

    }

    public void storeAlias(JSONArray jsonArray){
        for(int i=0; i<jsonArray.length(); i++){

            JSONObject json = null;
            try{
                json = jsonArray.getJSONObject(i);
                dailyService.storeAlias(json);


            } catch (JSONException e){
                e.printStackTrace();
            }
        }

    }



}