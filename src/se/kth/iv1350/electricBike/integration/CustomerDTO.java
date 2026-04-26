package se.kth.iv1350.electricBike.integration;

/**
 * Data transfer object containing customer and bike information.
 */
public class CustomerDTO {

    private String name;
    private String email;
    private String phoneNumber;
    private String bikeBrand;
    private String bikeSerialNo;
    private String bikeModel;

    /**
     * The CustomerDTO constructor, this function populates the DTO's attributes
     * @param name The name of the customer
     * @param email The email of the customer
     * @param phoneNumber The phone number of the customer
     * @param bikeBrand The brand of the customers bike
     * @param bikeSerialNo The serial number of the customers bike
     * @param bikeModel The model of the customers bike
     */
    public CustomerDTO(
        String name,
        String email,
        String phoneNumber,
        String bikeBrand,
        String bikeSerialNo,
        String bikeModel
    ) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.bikeBrand = bikeBrand;
        this.bikeSerialNo = bikeSerialNo;
        this.bikeModel = bikeModel;
    }

    /**
     * Getter for the name attribute
     * @return Returns the name attribute of the DTO
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter for the email attribute
     * @return Returns the email attribute of the DTO
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Getter for the phoneNumber attribute
     * @return Returns the phoneNumber attribute of the DTO
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * Getter for the bikeBrand attribute
     * @return Returns the bikeBrand attribute of the DTO
     */
    public String getBikeBrand() {
        return this.bikeBrand;
    }

    /**
     * Getter for the bikeSerialNo attribute
     * @return Returns the bikeSerialNo attribute of the DTO
     */
    public String getBikeSerialNo() {
        return this.bikeSerialNo;
    }

    /**
     * Getter for the bikeModel attribute
     * @return Returns the bikeModel attribute of the DTO
     */
    public String getBikeModel() {
        return this.bikeModel;
    }
}
