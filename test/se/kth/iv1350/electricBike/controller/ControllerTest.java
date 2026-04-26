package se.kth.iv1350.electricBike.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.electricBike.integration.*;
import se.kth.iv1350.electricBike.model.RepairOrder;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private Controller contr;
    private String savedOrderId;

    @BeforeEach
    void setUp() {
        CustomerRegistry custReg = new CustomerRegistry();
        RepairOrderRegistry orderReg = new RepairOrderRegistry();
        contr = new Controller(custReg, orderReg);

        String phone = "0701112233";
        contr.createRepairOrder("Motor error", phone, "SN999");

        List<RepairOrderDTO> history = contr.findRepairOrderHistory(phone);
        savedOrderId = history.get(0).getId();
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