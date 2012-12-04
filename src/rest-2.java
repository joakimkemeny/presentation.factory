    @ResponseBody
    @RequestMapping(value = "/api/order/{id}",
                    method = RequestMethod.PUT,
                    consumes = "application/json")
    public Order updateOrder(@PathVariable Integer id,
                             @RequestBody Order order) { … }

    @ResponseBody
    @RequestMapping(value = "/api/order/{id}",
                    method = RequestMethod.DELETE)
    public void deleteOrder(@PathVariable Integer id) { … }
}
