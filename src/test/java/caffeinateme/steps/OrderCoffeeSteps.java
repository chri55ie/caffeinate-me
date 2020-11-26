package caffeinateme.steps;

import caffeinateme.model.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderCoffeeSteps {

    Customer cathy = Customer.named("cathy");
    CoffeeShop coffeeShop = new CoffeeShop();
    Order order;

    @Given("Cathy is {int} metres from the coffee shop")
    public void cathy_is_metres_from_the_coffee_shop(int distanceInMeters) {
       cathy.setDistanceFromShop(distanceInMeters);
    }

    @When("Cathy orders a {}")
    public void cathy_orders_a_large_cappuccino(String OrderedProduct) {
        this.order = Order.of(1,OrderedProduct).forCustomer(cathy);
        cathy.placesAnOrderFor(order).at(coffeeShop);
    }
    @Then("Barry should receive the order")
    public void barry_should_receive_the_order() {


        assert (coffeeShop.getPendingOrders().contains(order));
    }

    @Then("Barry should know that the order is {}")
    public void barry_should_know_that_the_order_is(OrderStatus expectedStatus){
        Order cathysOrder = coffeeShop.getOrderFor(cathy)
                .orElseThrow(() -> new AssertionError("No Order Found!"));
        assertThat(cathysOrder.getStatus()).isEqualTo(expectedStatus);


    }

}
