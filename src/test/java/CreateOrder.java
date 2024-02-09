public class CreateOrder {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;

    public CreateOrder(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }
    public CreateOrder(){}
//    public static CreateOrder createOrderWithColor(List<String> colorScooter) {
//        String firstNameOrder = "Regina";
//        String lastNameOrder = "Zinnatullina";
//        String address = "Moscow, 24 home";
//        String metroStation = "3";
//        String phone = "+7 965 661 20 81";
//        int rentTime = 2;
//        String deliveryDate = "2024-03-01";
//        String comment = "After 17:00 pm";
//
//        return new CreateOrder (firstNameOrder,lastNameOrder,address,metroStation,phone,rentTime,deliveryDate,comment, colorScooter);
//    }

    public String getFirstName(){return firstName;}
    public void setFirstName(String firstName){
        this.firstName = firstName;}
    public String getLastName(){return lastName;}
    public void setLastName(String lastName){
        this.lastName = lastName;}
    public String getAddress(){return address;}
    public void setAddress(String address){
        this.address = address;}
    public String getMetroStation(){return metroStation;}
    public void setMetroStation(String metroStation){
        this.metroStation = metroStation;}
    public String getPhone(){return phone;}
    public void setPhone(String phone){
        this.phone = phone;}
    public int getRentTime(){return rentTime;}
    public void setRentTime(int rentTime){
        this.rentTime = rentTime;}
    public String getDeliveryDate(){return deliveryDate;}
    public void setDeliveryDate(String deliveryDate){
        this.deliveryDate = deliveryDate;}
    public String getComment(){return comment;}
    public void setComment(String comment){
        this.comment = comment;}
    public String[] getColor(){return color;}
    public void setColor(String[] color){
        this.color = color;}

}
