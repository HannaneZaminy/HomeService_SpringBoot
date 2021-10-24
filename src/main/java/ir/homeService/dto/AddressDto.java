package ir.homeService.dto;

public class AddressDto {

    private String address;
    private String postal_address;
    private String address_compact;
    //private String primary;
    private String name;
    private String poi;
    private String country;
    private String province;
    private String county;
    private String district;
    private String rural_district;
    private String city;
    private String village;
    private String region;
    private String neighbourhood;
    private String last;
    private String plaque;
    private String postal_code;

    public AddressDto(String address, String postal_address, String address_compact,
                     String name, String poi, String country,
                      String province, String county, String district, String rural_district,
                      String city, String village, String region, String neighbourhood, String last,
                      String plaque, String postal_code) {
        this.address = address;
        this.postal_address = postal_address;
        this.address_compact = address_compact;
        //this.primary = primary;
        this.name = name;
        this.poi = poi;
        this.country = country;
        this.province = province;
        this.county = county;
        this.district = district;
        this.rural_district = rural_district;
        this.city = city;
        this.village = village;
        this.region = region;
        this.neighbourhood = neighbourhood;
        this.last = last;
        this.plaque = plaque;
        this.postal_code = postal_code;
    }

    public AddressDto() {
    }

    public String getAddress() {
        return address;
    }

    public AddressDto setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPostal_address() {
        return postal_address;
    }

    public AddressDto setPostal_address(String postal_address) {
        this.postal_address = postal_address;
        return this;
    }

    public String getAddress_compact() {
        return address_compact;
    }

    public AddressDto setAddress_compact(String address_compact) {
        this.address_compact = address_compact;
        return this;
    }

//    public String getPrimary() {
//        return primary;
//    }
//
//    public AddressDto setPrimary(String primary) {
//        this.primary = primary;
//        return this;
//    }

    public String getName() {
        return name;
    }

    public AddressDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getPoi() {
        return poi;
    }

    public AddressDto setPoi(String poi) {
        this.poi = poi;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public AddressDto setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getProvince() {
        return province;
    }

    public AddressDto setProvince(String province) {
        this.province = province;
        return this;
    }

    public String getCounty() {
        return county;
    }

    public AddressDto setCounty(String county) {
        this.county = county;
        return this;
    }

    public String getDistrict() {
        return district;
    }

    public AddressDto setDistrict(String district) {
        this.district = district;
        return this;
    }

    public String getRural_district() {
        return rural_district;
    }

    public AddressDto setRural_district(String rural_district) {
        this.rural_district = rural_district;
        return this;
    }

    public String getCity() {
        return city;
    }

    public AddressDto setCity(String city) {
        this.city = city;
        return this;
    }

    public String getVillage() {
        return village;
    }

    public AddressDto setVillage(String village) {
        this.village = village;
        return this;
    }

    public String getRegion() {
        return region;
    }

    public AddressDto setRegion(String region) {
        this.region = region;
        return this;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public AddressDto setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
        return this;
    }

    public String getLast() {
        return last;
    }

    public AddressDto setLast(String last) {
        this.last = last;
        return this;
    }

    public String getPlaque() {
        return plaque;
    }

    public AddressDto setPlaque(String plaque) {
        this.plaque = plaque;
        return this;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public AddressDto setPostal_code(String postal_code) {
        this.postal_code = postal_code;
        return this;
    }
}
