@Controller
@RequestMapping(produces = "application/json")
public class OrderController {

    @ResponseBody
    @RequestMapping(value = "/api/order",
                    method = RequestMethod.GET)
    public List<Order> listOrders() { … }

    @ResponseBody
    @RequestMapping(value = "/api/order",
                    method = RequestMethod.POST,
                    consumes = "application/json")
    public Order createOrder(@RequestBody Order order) { … }
