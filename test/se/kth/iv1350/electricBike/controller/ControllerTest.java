package se.kth.iv1350.electricBike.controller;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.electricBike.integration.*;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {
    private Controller contr;
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(new ByteArrayOutputStream()));

        CustomerRegistry customerReg = new CustomerRegistry();
        RepairOrderRegistry repairOrderReg = new RepairOrderRegistry();
        Printer printer = new Printer();

        this.contr = new Controller(customerReg, repairOrderReg, printer);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        this.contr = null;
    }

    private String createOrderAndGetId() {
        String phone = "0707464750";
        contr.createRepairOrder("Motor error", phone, "SN999");
        return contr.findRepairOrderByNumber(phone).getId();
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
        String savedOrderId = createOrderAndGetId();

        contr.addDiagnosticResult(savedOrderId, "Sensor trasig");

        RepairOrderDTO dto = contr.findRepairOrderById(savedOrderId);
        assertTrue(dto.getDiagnosticResults().contains("Sensor trasig"),
                "Diagnostic result should be stored on the order.");
    }

    @Test
    void testAddMultipleDiagnosticResultsViaController() {
        String savedOrderId = createOrderAndGetId();

        contr.addDiagnosticResult(savedOrderId, "Sensor trasig");
        contr.addDiagnosticResult(savedOrderId, "Slitna bromsbelägg");

        RepairOrderDTO dto = contr.findRepairOrderById(savedOrderId);
        assertTrue(dto.getDiagnosticResults().contains("Sensor trasig") &&
                dto.getDiagnosticResults().contains("Slitna bromsbelägg"),
                "Multiple diagnostic results should be stored on the order.");
        assertEquals(2, dto.getDiagnosticResults().size(),
                "The order should contain exactly two diagnostic results.");
    }

    @Test
    void testAddRepairTaskViaController() {
        String savedOrderId = createOrderAndGetId();

        contr.addRepairTask(savedOrderId, "Byt kedja");

        RepairOrderDTO dto = contr.findRepairOrderById(savedOrderId);
        boolean found = dto.getRepairTasks().stream()
                .anyMatch(t -> t.getDescription().equals("Byt kedja"));
        assertTrue(found, "Repair task should be stored on the order.");
    }

    @Test
    void testAcceptRepairOrderChangesStateViaController() {
        String savedOrderId = createOrderAndGetId();

        contr.acceptRepairOrder(savedOrderId);

        RepairOrderDTO acceptedOrder = contr.findRepairOrderById(savedOrderId);
        assertEquals("Accepted", acceptedOrder.getState(),
                "Orderstatus borde vara 'Accepted' efter att controllern anropats.");
    }

    @Test
    public void testFindAllRepairOrdersReturnsEmptyListWhenNoOrdersExist() {
        List<RepairOrderDTO> result = this.contr.findAllRepairOrders();

        assertTrue(result.isEmpty(), "Repair order list should be empty before any order has been created");
    }

    @Test
    public void testCreateRepairOrderAddsRepairOrder() {
        String problemDescr = "Motor stangs av i uppforsbacke";
        String customerPhone = "0705556767";
        String bikeSerialNo = "0001";

        this.contr.createRepairOrder(problemDescr, customerPhone, bikeSerialNo);
        List<RepairOrderDTO> result = this.contr.findAllRepairOrders();

        assertEquals(1, result.size(), "One repair order should have been created");
    }

    @Test
    public void testTwoCreatesProduceTwoDistinctDTOs() {
        this.contr.createRepairOrder("Motor stangs av i uppforsbacke", "0705556767", "0001");
        this.contr.createRepairOrder("Batteriet laddar inte", "0705556768", "0002");

        List<RepairOrderDTO> result = this.contr.findAllRepairOrders();

        assertEquals(2, result.size(), "All stored repair orders should be returned as DTOs");
        assertNotEquals(result.get(0).getId(), result.get(1).getId(),
                "Each stored repair order should be represented by a separate DTO with its own id");
    }

    @Test
    public void testCreatedRepairOrderHasCorrectInformation() {
        String problemDescr = "Batteriet laddar inte";
        String customerPhone = "0705556767";
        String bikeSerialNo = "0001";

        this.contr.createRepairOrder(problemDescr, customerPhone, bikeSerialNo);
        RepairOrderDTO result = this.contr.findAllRepairOrders().get(0);

        assertNotNull(result.getId(), "Created repair order should have an id");
        assertFalse(result.getId().trim().isEmpty(), "Created repair order id should not be blank");
        assertEquals("Newly created", result.getState(), "Created repair order should have initial state");
        assertEquals(problemDescr, result.getProblemDescr(),
                "Created repair order should keep the problem description");
    }
}
