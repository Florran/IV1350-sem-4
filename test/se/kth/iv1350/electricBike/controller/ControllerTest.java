package se.kth.iv1350.electricBike.controller;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.electricBike.integration.*;
import se.kth.iv1350.electricBike.model.RepairOrderObserver;
import se.kth.iv1350.electricBike.model.discount.NoDiscount;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains tests for the Controller class.
 */
public class ControllerTest {
    private Controller contr;
    private final PrintStream originalOut = System.out;

    private static class FakeObserver implements RepairOrderObserver {
        private int callCount;
        private RepairOrderDTO updatedRepairOrder;

        @Override
        public void repairOrderUpdated(RepairOrderDTO order) {
            this.callCount += 1;
            this.updatedRepairOrder = order;
        }

        public RepairOrderDTO getRepairOrderDTO() {
            return this.updatedRepairOrder;
        }

        public int getCallCount() {
            return this.callCount;
        }
    }

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
    public void testStateChangeFiresObserver() {
        String savedOrderId = createOrderAndGetId();
        FakeObserver obs = new FakeObserver();

        this.contr.addRepairOrderObserver(obs);
        contr.acceptRepairOrder(savedOrderId, new NoDiscount());

        assertEquals(1, obs.getCallCount(), "Observer should only be called once per RepairOrder update");
        assertEquals("Accepted", obs.getRepairOrderDTO().getState(),
                "The updated values should get forwarded to the observer");
    }

    @Test
    public void testAllObserversGetCalledOnUpdate() {
        String savedOrderId = createOrderAndGetId();
        FakeObserver obsA = new FakeObserver();
        FakeObserver obsB = new FakeObserver();

        this.contr.addRepairOrderObserver(obsA);
        this.contr.addRepairOrderObserver(obsB);

        contr.acceptRepairOrder(savedOrderId, new NoDiscount());

        assertEquals(1, obsA.getCallCount(), "Observer should only be called once per RepairOrder update");
        assertEquals("Accepted", obsA.getRepairOrderDTO().getState(),
                "The updated values should get forwarded to the observer");

        assertEquals(1, obsB.getCallCount(), "Observer should only be called once per RepairOrder update");
        assertEquals("Accepted", obsB.getRepairOrderDTO().getState(),
                "The updated values should get forwarded to the observer");
    }

    @Test
    public void testAllUpdateFunctionsFireObserver() {
        String savedOrderId = createOrderAndGetId();
        FakeObserver obs = new FakeObserver();

        this.contr.addRepairOrderObserver(obs);

        contr.addDiagnosticResult(savedOrderId, "broken");
        assertEquals("broken", obs.getRepairOrderDTO().getDiagnosticResults().getFirst(),
                "Diagnostic result update should get forwarded to observer");

        contr.addRepairTask(savedOrderId, "fix", 200.0);
        assertEquals("fix", obs.getRepairOrderDTO().getRepairTasks().getFirst().getDescription(),
                "Repair task update should get forwarded to observer");

        contr.acceptRepairOrder(savedOrderId, new NoDiscount());
        assertEquals("Accepted", obs.getRepairOrderDTO().getState(),
                "Accept update should get forwarded to the observer");

        assertEquals(3, obs.getCallCount(), "Observer should fire once per state changing call, total of three");
    }

    @Test
    public void testFindExistingCustomer() throws CustomerNotFoundException {
        String phone = "0705556767";
        CustomerDTO result = this.contr.findCustomer(phone);
        assertNotNull(result);
        assertEquals(phone, result.getPhoneNumber());
        assertEquals("Customer1", result.getName());
    }

    @Test
    public void testFindUnknownCustomerThrowsException() {
        String phone = "0700000000";
        try {
            this.contr.findCustomer(phone);
            fail();
        } catch (CustomerNotFoundException exc) {
            assertTrue(exc.getMessage().contains(phone));
            assertEquals(phone, exc.getNumber());
        }
    }

    @Test
    public void testFindCustomerEmptyPhoneNrThrowsException() {
        String phone = "";
        try {
            this.contr.findCustomer(phone);
            fail();
        } catch (CustomerNotFoundException exc) {
            assertEquals(phone, exc.getNumber());
        }
    }

    @Test
    public void testFailedLookupDoesNotAffectSubsequentLookup() {
        try {
            this.contr.findCustomer("0700000000");
            fail();
        } catch (CustomerNotFoundException exc) {
        }
        try {
            CustomerDTO result = this.contr.findCustomer("0705556767");
            assertNotNull(result);
        } catch (CustomerNotFoundException exc) {
            fail();
        }
    }

    @Test
    void testAddDiagnosticResultViaController() {
        String savedOrderId = createOrderAndGetId();

        contr.addDiagnosticResult(savedOrderId, "Sensor trasig");

        RepairOrderDTO dto = contr.findRepairOrderById(savedOrderId);
        assertTrue(dto.getDiagnosticResults().contains("Sensor trasig"));
    }

    @Test
    void testAddMultipleDiagnosticResultsViaController() {
        String savedOrderId = createOrderAndGetId();

        contr.addDiagnosticResult(savedOrderId, "Sensor trasig");
        contr.addDiagnosticResult(savedOrderId, "Slitna bromsbelägg");

        RepairOrderDTO dto = contr.findRepairOrderById(savedOrderId);
        assertTrue(dto.getDiagnosticResults().contains("Sensor trasig") &&
                dto.getDiagnosticResults().contains("Slitna bromsbelägg"));
        assertEquals(2, dto.getDiagnosticResults().size());
    }

    @Test
    void testAddRepairTaskViaController() {
        String savedOrderId = createOrderAndGetId();

        contr.addRepairTask(savedOrderId, "Byt kedja", 350.0);

        RepairOrderDTO dto = contr.findRepairOrderById(savedOrderId);
        boolean found = dto.getRepairTasks().stream()
                .anyMatch(t -> t.getDescription().equals("Byt kedja"));
        assertTrue(found);
    }

    @Test
    void testAcceptRepairOrderChangesStateViaController() {
        String savedOrderId = createOrderAndGetId();

        contr.acceptRepairOrder(savedOrderId, new NoDiscount());

        RepairOrderDTO acceptedOrder = contr.findRepairOrderById(savedOrderId);
        assertEquals("Accepted", acceptedOrder.getState());
    }

    @Test
    public void testFindRepairOrderByNumberUnknownPhoneReturnsNull() {
        RepairOrderDTO result = contr.findRepairOrderByNumber("0700000000");

        assertNull(result);
    }

    @Test
    public void testFindRepairOrderByNumberOnEmptyRegistryReturnsNull() {
        RepairOrderDTO result = contr.findRepairOrderByNumber("0701112233");

        assertNull(result);
    }

    @Test
    public void testFindAllRepairOrdersReturnsEmptyListWhenNoOrdersExist() {
        List<RepairOrderDTO> result = this.contr.findAllRepairOrders();

        assertTrue(result.isEmpty());
    }

    @Test
    public void testCreateRepairOrderAddsRepairOrder() {
        String problemDescr = "Motor stangs av i uppforsbacke";
        String customerPhone = "0705556767";
        String bikeSerialNo = "0001";

        this.contr.createRepairOrder(problemDescr, customerPhone, bikeSerialNo);
        List<RepairOrderDTO> result = this.contr.findAllRepairOrders();

        assertEquals(1, result.size());
    }

    @Test
    public void testTwoCreatesProduceTwoDistinctDTOs() {
        this.contr.createRepairOrder("Motor stangs av i uppforsbacke", "0705556767", "0001");
        this.contr.createRepairOrder("Batteriet laddar inte", "0705556768", "0002");

        List<RepairOrderDTO> result = this.contr.findAllRepairOrders();

        assertEquals(2, result.size());
        assertNotEquals(result.get(0).getId(), result.get(1).getId());
    }

    @Test
    public void testCreatedRepairOrderHasCorrectInformation() {
        String problemDescr = "Batteriet laddar inte";
        String customerPhone = "0705556767";
        String bikeSerialNo = "0001";

        this.contr.createRepairOrder(problemDescr, customerPhone, bikeSerialNo);
        RepairOrderDTO result = this.contr.findAllRepairOrders().get(0);

        assertNotNull(result.getId());
        assertFalse(result.getId().trim().isEmpty());
        assertEquals("Newly created", result.getState());
        assertEquals(problemDescr, result.getProblemDescr());
    }
}