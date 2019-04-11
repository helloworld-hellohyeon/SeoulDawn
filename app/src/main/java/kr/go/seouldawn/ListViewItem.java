package kr.go.seouldawn;

public class ListViewItem {
    private String Name ;
    private String Add ;
    private String Time ;

    public void setName(String name) {
        Name = name ;
    }
    public void setAdd(String add) {
        Add = add ;
    }
    public void setTime(String time) {
        Time = time ;
    }
    public String getName() { return this.Name ; }
    public String getAdd() {
        return this.Add ;
    }
    public String getTime() {
        return this.Time ;
    }
}