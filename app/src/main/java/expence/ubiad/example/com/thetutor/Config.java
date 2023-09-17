package expence.ubiad.example.com.thetutor;

/**
 * Created by Xhanii on 2/7/2017.
 */

public class Config {
    //Address of our scripts of the CRUD

    public static final String URL_CREATE_USER = "http://www.thetutor.pk/thetutor/signup.php";
    public static final String LOGIN_URL =       "http://www.thetutor.pk/thetutor/user_login.php";
    public static final String REGISTER_TUTOR =   "http://www.thetutor.pk/thetutor/reg_tutor.php";
    public static final String GET_TUTOR =        "http://www.thetutor.pk/thetutor/gettutor.php";
    public static final String URL_GET_ALL_TUTOR ="http://www.thetutor.pk/thetutor/tutor.php";
    public static final String CONTACT_US =       "http://www.thetutor.pk/thetutor/contactus.php";
    public static final String URL_GET_USER_PROFILE = "http://www.thetutor.pk/thetutor/get_user_profile.php?user_id=";
    public static final String URL_UPDATE_USER =       "http://www.thetutor.pk/thetutor/update_user_profile.php?";
    public static final String URL_RESET_PASS =       "http://www.thetutor.pk/thetutor/resetpass.php?";
    public static final String URL_NEWS_FEED ="http://www.thetutor.pk/thetutor/newsfeed.php";


    //Keys for email and password as defined in our $_POST['key'] in login.php
    public static final String KEY_MAIL = "user_id";
    public static final String KEY_PASSWORD = "user_pass";

    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "success";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "myloginapp";

    //This would be used to store the email of current logged in user
    public static final String EMAIL_SHARED_PREF = "user_id";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";


// for signup


    public static final String SU_USER_NAME = "user_name";
    public static final String SU_USER_EMAIL  = "user_id";
    public static final String SU_USER_MOBILE  = "user_mbl_no";
    public static final String SU_USER_PASS = "user_pass";
    public static final String SU_USER_CATEGORY = "user_category";

    //Register Tutor
    public static final String KEY_TU_FIRSTNAME = "firstname";
    public static final String KEY_TU_FATHERNAME = "fathername";
    public static final String KEY_TU_GENDER = "gender";
    public static final String KEY_TU_DOB = "dob";
    public static final String KEY_TU_MOBILENO = "mobileno";
    public static final String KEY_TU_CNIC = "cnic";
    public static final String KEY_TU_ADDRESSS = "address";
    public static final String KEY_TU_EMAIL = "email";
    public static final String KEY_TU_IMAGE = "image";
    public static final String KEY_TU_EDUCATION = "education";
    public static final String KEY_TU_STUDYPROGRAM = "tu_study_program";
    public static final String KEY_TU_EXPERIENCE = "tu_experience";
    public static final String KEY_TU_TEACHINGSUB = "tu_teaching_subj";
    public static final String KEY_TU_TIMINGTEACH = "timingteach";
    public static final String KEY_TU_STUDYLEVEL = "tu_stdy_lvl_can_teach";
    public static final String KEY_TU_STUDYLEVEL1 = "tu_stdy_lvl_can_teach1";
    public static final String KEY_TU_STUDYLEVEL2 = "tu_stdy_lvl_can_teach2";
    public static final String KEY_TU_STUDYLEVEL3 = "tu_stdy_lvl_can_teach3";
    public static final String KEY_TU_STUDYLEVEL4 = "tu_stdy_lvl_can_teach4";
    public static final String KEY_TU_STUDYLEVEL5 = "tu_stdy_lvl_can_teach5";
    public static final String KEY_TU_STUDYLEVEL6 = "tu_stdy_lvl_can_teach6";
    public static final String KEY_TU_STUDYLEVEL7 = "tu_stdy_lvl_can_teach7";
    public static final String KEY_TU_STUDYLEVEL8 = "tu_stdy_lvl_can_teach8";
    public static final String KEY_TU_STUDYLEVEL9 = "tu_stdy_lvl_can_teach9";
    public static final String KEY_TU_STUDYLEVEL10 = "tu_stdy_lvl_can_teach10";
    public static final String KEY_TU_TUTORING = "tu_tutoring_opt";


    //Get A Tutor
    public static final String KEY_STUDENT_NAME = "st_name";
    public static final String KEY_STUDENT_FNAME = "st_fname";
    public static final String KEY_STUDENT_GENDER = "st_gender";
    public static final String KEY_STUDENT_AGE = "st_age";
    public static final String KEY_STUDENT_PHONENO = "st_phoneno";
    public static final String KEY_STUDENT_EMAIL = "st_email";
    public static final String KEY_STUDENT_ADDRESS = "st_address";
    public static final String KEY_STUDENT_SCHOOL = "st_school";
    public static final String KEY_STUDENT_TIMING = "st_timing";
    public static final String KEY_STUDENT_CLASS = "st_class";
    public static final String KEY_STUDENT_SUBJECTS = "st_subject";
    public static final String KEY_STUDENT_COMMENT = "st_comment";
    public static final String KEY_STUDENT_CATEGORY = "st_category";

    // GetAllTutor
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_TUTOR_NAME = "firstname";
    public static final String TAG_TUTOR_EDUCATION = "education";
    public static final String TAG_TUTOR_EXPERIENCE = "tu_experience";

    // For Contact US
    public static final String TAG_STUDENT_NAME = "st_name";
    public static final String TAG_STUDENT_FATHERNAME = "st_email";
    public static final String TAG_STUDENT_MOBILENO = "st_mobileno";
    public static final String TAG_STUDENT_MESSAGE = "st_message";

// For get user profile
    public static final String T_JSON_ARRAY = "result";
    public static final String TAG_USER_NAME = "user_name";
    public static final String TAG_USER_PASS = "user_pass";
    public static final String TAG_USER_IMAGE = "user_image";
    public static final String TAG_USER_NUMBER = "user_mbl_no";
    public static final String TAG_USER_EMAIL = "user_id";

    // For update user profile
    public static final String U_USER_ID = "user_id";
    public static final String U_USER_NAME = "user_name";
    public static final String U_USER_PASS = "user_pass";
    public static final String U_USER_IMAGE = "user_image";
    public static final String U_USER_NUMBER = "user_mbl_no";

    // For reset pass
    public static final String R_USER_ID = "user_id";
    public static final String R_USER_PASS = "user_pass";

    //News Feed
    public static final String TAG_NEWS_FEED="result";
    public static final String TAG_NEWS_DATE = "date";
    public static final String TAG_NEWS_TITLE = "news_title";
    public static final String TAG_NEWS_DESCRIPTION = "news_description";



}
