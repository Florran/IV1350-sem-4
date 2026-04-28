package se.kth.iv1350.electricBike.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.electricBike.integration.*;
import se.kth.iv1350.electricBike.model.RepairOrder;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {
    private Controller contr;
    private String savedOrderId;

    @BeforeEach
    public void setUp() {
        CustomerRegistry customerReg = new CustomerRegistry();
        RepairOrderRegistry repairReg = new RepairOrderRegistry();

        this.contr = new Controller(customerReg, repairReg);
      
        String phone = "0701112233";
        contr.createRepairOrder("Motor error", phone, "SN999");

        List<RepairOrderDTO> history = contr.findRepairOrderHistory(phone);
        savedOrderId = history.get(0).getId();
    }

    @AfterEach
    public void tearDown() {
        this.contr = null;
        this.savedOrderId = null;
    }

    @Test
    public void testFindExistingCustomer() {
        String phone = "0705556767";
        CustomerDTO result = this.contr.findCustomer(phone);
        assertNotNull(result, "Existing customer was not found");
        assertEquals(phone, result.getPhoneNumber(), "Wrong phone number");
        assertEquals("Customer1", result.getName(), "Wrong name.");
    }

    @Test
    public void testFindUnknownCustomerReturnsNull() {
        String phone = "0700000000";
        CustomerDTO result = this.contr.findCustomer(phone);

        assertNull(result, "invalid phone number should return null");

    }

    @Test
    public void testFindCustomerEmptyPhoneNrReturnsNull() {
        String phone = "";
        CustomerDTO result = this.contr.findCustomer(phone);

        assertNull(result, "no phone number should return null");
    }
  
    @Test
    void testAddDiagnosticResultViaController() {
        contr.addDiagnosticResult(savedOrderId, "Sensor trasig");

        RepairOrderDTO updatedOrder = contr.findRepairOrderById(savedOrderId);
        assertNotNull(updatedOrder, "Ordern borde existera och gå att hämta ut.");
    }

    @Test
    void testAcceptRepairOrderChangesStateViaController() {
        contr.acceptRepairOrder(savedOrderId);

        RepairOrderDTO acceptedOrder = contr.findRepairOrderById(savedOrderId);
        assertEquals("Accepted", acceptedOrder.getState(),
                "Orderstatus borde vara 'Accepted' efter att controllern anropats.");
    }

}
