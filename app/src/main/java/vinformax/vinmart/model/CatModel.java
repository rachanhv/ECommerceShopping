package vinformax.vinmart.model;

public class CatModel {

	private String imgname ;

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public void setImgname(String imgname) {
		this.imgname = imgname;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}

	private String imgurl;
	private String discount;
	private String typeid;
	private boolean selected;



//	public CatModel(String rank, String country, String typeid, String population) {
//		this.imgname = rank;
//		this.imgurl = country;
//		this.typeid = typeid;
//		this.discount = population;
//	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		
	}

	public String getimgname() {
		return this.imgname;
	}
	
	public String gettypeid() {
		return this.typeid;
	}

	public String getimgurl() {
		return this.imgurl;
	}

	public String getdiscount() {
		return this.discount;
	}
}