package kr.go.seouldawn;

public class ListViewBtnItem {
    private String name;
    private String address;
    private String tel;

    public void setName(String text) {
        name = text;
    }
    public void setTel(String tel){ this.tel=tel;}
    public void setAddress(String add){ this.address=add;}


    public String getName() {
        return this.name;
    }
    public String getTel(){ return this.tel;}
    public String getAddress(){ return this.address;}
}
