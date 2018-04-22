package constants;

public final class Constants {

    //work
    /*
    private static final String DATA_PATH = "C:\\Users\\DavutU\\Desktop\\test";
    private static final String OUTPUT_FOLDER_PATH = "C:\\Users\\DavutU\\Desktop\\testout";
    private static final String OUTPUT_PATH = "C:\\Users\\DavutU\\Desktop\\testout\\simulation";
    private static final String GENERAL_INFO_OUTPUT_PATH = "C:\\Users\\DavutU\\Desktop\\testout\\simulationGeneralInfo";
*/
    //home
    private static final String DATA_PATH = "C:\\Users\\dauut\\Desktop\\Facebook_online_status1";
    private static final String OUTPUT_PATH = "C:\\Users\\dauut\\Desktop\\testout\\simulation";
    private static final String GENERAL_INFO_OUTPUT_PATH = "C:\\Users\\dauut\\Desktop\\testout\\simulationGeneralInfo";
    private static final String OUTPUT_FOLDER_PATH = "C:\\Users\\dauut\\Desktop\\testout";

    //statistics output
    private static final String COLLECTION_OUTPUT_PATH = "C:\\Users\\dauut\\Desktop\\UsersOfflineStatus\\";

    private static final String SIMULATION_START_DATE = "05/28/2014 12:01:00 am";
    private static final String SIMULATION_END_DATE = "06/11/2014 11:59:00 Pm";

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