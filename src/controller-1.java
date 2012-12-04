@WebSocketController("order")
@Controller
@RequestMapping(produces = "application/json")
public class OrderController {

    @Autowired
    private WebSocketManager webSocketManager;

    @ResponseBody
    @WebSocketMapping("list")
    @RequestMapping(value = "/api/order",
                    method = RequestMethod.GET)
    public List<Order> listOrders() { … }
