package automask.nuza.prakash.parbatpost.Pojo;

public class FeatureData{

    String title;
    String nid;
    String image;

    public FeatureData(String title, String nid, String image) {
        this.title = title;
        this.nid = nid;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getNid() {
        return nid;
    }

    public String getImage() {
        return image;
    }
}
