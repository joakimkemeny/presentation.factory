<
tbody
data - bind = "foreach: orders" >
    < tr >
    < td
data - bind = "text: customer" > < /td>
    < td
data - bind = "text: quantity" > < /td>
    < td
data - bind = "text: status" > < /td>

    < td
data - bind = "css: {readonly : status() !== 'Received'}" >
    < button
data - bind = "click: $parent.queue" >
…
<
button
data - bind = "click: $parent.edit" >
…
<
button
data - bind = "click: $parent.delete" >
…
<
/td>
    < /tr>
< /tbody>
