package constants;

public final class Constants {

    //general
    private static final String SIMULATION_START_DATE = "05/28/2014 12:01:00 am";
    private static final String SIMULATION_END_DATE = "06/11/2014 11:59:00 Pm";
    private static final String RANDOM_DELEGATION = "RANDOM";
    private static final String MOST_ONLINE_FRIEND_DELEGATION = "MOST_ONLINE";
    private static final String MOST_DISJOINT_FRIEND_DELEGATION = "MOST_DISJOINT";
    private static final String OPTIMIZED_MOST_DISJOINT_FRIEND_DELEGATION = "OPTIMIZED_MOST_DISJOINT";

    //    private static final String COLLECTION_OUTPUT_PATH = "C:\\Users\\DavutU\\Desktop\\UsersOfflineStatus\\";
    //    private static final String GENERAL_INFO_OUTPUT_PATH = "C:\\Users\\DavutU\\Desktop\\testout\\simulationGeneralInfo";
    //    private static final String OUTPUT_PATH = "C:\\Users\\DavutU\\Desktop\\testout\\simulation";
    //    private static final String OUTPUT_FOLDER_PATH = "C:\\Users\\DavutU\\Desktop\\testout";
    //    private static final String DATA_PATH = "C:\\Users\\DavutU\\Desktop\\test";

    //comments constants
    private static final String DATA_PATH = "C:\\Users\\dauut\\Desktop\\Facebook_online_status3";
    private static final String COLLECTION_OUTPUT_PATH = "C:\\Users\\dauut\\Desktop\\UsersOfflineStatus\\";
    private static final String OUTPUT_PATH = "C:\\Users\\dauut\\Desktop\\testout\\simulation";
    private static final String GENERAL_INFO_OUTPUT_PATH = "C:\\Users\\dauut\\Desktop\\testout\\simulationGeneralInfo";
    private static final String OUTPUT_FOLDER_PATH = "C:\\Users\\dauut\\Desktop\\testout";

    //post simulation constants
    private static final String POST_DATA_PATH="C:\\Users\\dauut\\Desktop\\Facebook_activity";
//    private static final String POST_DATA_PATH="C:\\Users\\dauut\\Desktop\\a";
    private static final String POST_OUTPUT_PATH="C:\\Users\\dauut\\Desktop\\post_testout\\simulation";




    public static String getPostDataPath() {
        return POST_DATA_PATH;
    }

    public static String getPostOutputPath() {
        return POST_OUTPUT_PATH;
    }

    public static String getOptimizedMostDisjointFriendDelegation() {
        return OPTIMIZED_MOST_DISJOINT_FRIEND_DELEGATION;
    }

    public static String getMostDisjointFriendDelegation() {
        return MOST_DISJOINT_FRIEND_DELEGATION;
    }

    public static String getRandomDelegation() {
        return RANDOM_DELEGATION;
    }

    public static String getMostOnlineFriendDelegation() {
        return MOST_ONLINE_FRIEND_DELEGATION;
    }

    public static String getCollectionOutputPath() {
        return COLLECTION_OUTPUT_PATH;
    }

    public static String getOutputPath() {
        return OUTPUT_PATH;
    }

    public static String getOutputFolderPath() {
        return OUTPUT_FOLDER_PATH;
    }

    public static String getGeneralInfoOutputPath() {
        return GENERAL_INFO_OUTPUT_PATH;
    }

    public static String getSimulationStartDate() {
        return SIMULATION_START_DATE;
    }

    public static String getDataPath() {
        return DATA_PATH;
    }

    public static String getSimulationEndDate() {
        return SIMULATION_END_DATE;
    }
}