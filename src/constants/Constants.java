package constants;

public final class Constants {

    //    private static final String DATA_PATH = "C:\\Users\\dauut\\Desktop\\Facebook_online_status1";
    private static final String DATA_PATH = "C:\\Users\\dauut\\Desktop\\test";
    private static final String OUTPUT_PATH = "C:\\Users\\dauut\\Desktop\\testout\\tmpres.txt";
    private static final String SIMULATION_START_DATE = "05/28/2014 12:01:00 am";
    private static final String SIMULATION_END_DATE = "06/11/2014 11:59:00 Pm";

    public static String getOutputPath() {
        return OUTPUT_PATH;
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
