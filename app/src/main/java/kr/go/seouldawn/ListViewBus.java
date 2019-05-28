package kr.go.seouldawn;

public class ListViewBus {
    private String stationName;
    private String stationId;
    private String busNum;
    private String info;

    public String getName() { return stationName; }
    public String getId() { return stationId; }
    public String getBus() { return busNum; }
    public ListViewBus(){
    }

    public void setStationName(String stationName){
        this.stationName = stationName;
    }

    public void setStationId(String stationId){
        this.stationId = stationId;
    }

    public void setBusNum(String busNum){this.busNum = busNum;}
}

